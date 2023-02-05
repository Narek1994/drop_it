package com.buildbox.adapter.custom;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.buildbox.AdIntegratorInterface;
import com.buildbox.AdIntegratorManager;
import com.buildbox.AdIntegratorManagerInterface;
import com.buildbox.CustomIntegrator;
import com.buildbox.consent.ConsentHelper;
import com.buildbox.consent.SdkConsentInfo;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;


import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

public class AdIntegrator implements AdIntegratorInterface, CustomIntegrator {

    // TODO - PUT your adIds HERE
    private static String bannerID = "ca-app-pub-4343203573796350/6501347057";
    private static String interstitialID = "ca-app-pub-4343203573796350/6563527431";
    private static String rewardedVideoID = "ca-app-pub-4343203573796350/3937364093";


    private static String customNetworkName = "";
    private static String adNetworkId = "custom";
    private static String TAG = "AdIntegratorCustom";
    private static WeakReference<Activity> activity;
    private static AdIntegratorManagerInterface adIntegratorManager;
    private static AdView adMobBanner;
    private static InterstitialAd adMobInterstitial;
    private static RewardedAd admobRewardedVideo;





    private RelativeLayout adContainerView;
    private boolean userConsent;
    private boolean isNewBannerLoaded;

    @Override
    public void initAds(HashMap<String, String> initValues, WeakReference<Activity> act, AdIntegratorManagerInterface managerInterface) {

        activity = act;
        adIntegratorManager = managerInterface;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.get());
        setUserConsent(preferences.getBoolean(ConsentHelper.getConsentKey("admob"),false));


        if (bannerID == null || interstitialID == null || rewardedVideoID == null) {
            Log.e(TAG, "Network sdk not configured correctly");
            Log.e(TAG, "Initializing pairs are : " + initValues);
            if (bannerID == null) {
                Log.e(TAG, "Banner ID is not found in keys");
            }
            if (interstitialID == null) {
                Log.e(TAG, "Interstitial ID is not found in keys");

            }
            if (rewardedVideoID == null) {
                Log.e(TAG, "Rewarded Video ID is not found in keys");
            }
            networkFailed();
            return;
        }

        MobileAds.initialize(activity.get(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.d(TAG, "Network configured with status: " + initializationStatus);
                networkLoaded();
            }
        });
    }


    @Override
    public void initBanner() {

        FrameLayout frameLayout = activity.get().findViewById(android.R.id.content);
        adContainerView = new RelativeLayout(activity.get());
        frameLayout.addView(adContainerView);
        RelativeLayout.LayoutParams bannerLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        bannerLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        bannerLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);

        adMobBanner = new AdView(activity.get());
        adMobBanner.setLayoutParams(bannerLayoutParams);
        adMobBanner.setAdSize(AdSize.SMART_BANNER);
        adMobBanner.setAdUnitId(bannerID);
        adMobBanner.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Log.d(TAG, "Banner closed");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Log.d(TAG, "Banner failed to load with status: " + loadAdError);
                bannerFailed();
            }
            @Override
            public void onAdOpened() {
                Log.d(TAG, "Banner opened");
            }

            @Override
            public void onAdLoaded() {
                bannerLoaded();
            }

            @Override
            public void onAdClicked() {
            }

            @Override
            public void onAdImpression() {
                adIntegratorManager.bannerImpression(adNetworkId);
            }
        });
        adContainerView.addView(adMobBanner);
        adMobBanner.setVisibility(View.GONE);
        adMobBanner.loadAd(getAdRequest());
    }

    @Override
    public void showBanner() {
        Log.d(TAG, "showBanner");
        adMobBanner.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBanner() {
        adMobBanner.setVisibility(View.GONE);
    }

    @Override
    public boolean isBannerVisible() {
        return adMobBanner.getVisibility()==View.VISIBLE;
    }

    @Override
    public boolean isRewardedVideoAvailable() {
        return admobRewardedVideo!=null;
    }

    @Override
    public void showInterstitial() {
        if (adMobInterstitial!=null) {
            adMobInterstitial.show(activity.get());
            Log.d(TAG, "showInterstitial");
        }
    }


    @Override
    public void initInterstitial() {
        InterstitialAd.load(activity.get(),interstitialID, getAdRequest(),
                new InterstitialAdLoadCallback() {


                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        adMobInterstitial = interstitialAd;
                        adMobInterstitial.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                super.onAdFailedToShowFullScreenContent(adError);
                                interstitialFailed();
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                super.onAdShowedFullScreenContent();
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();
                                interstitialClosed();
                            }

                            @Override
                            public void onAdImpression() {
                                super.onAdImpression();
                                adIntegratorManager.interstitialImpression(adNetworkId);
                            }
                        });
                        interstitialLoaded();
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error

                        adMobInterstitial = null;
                        Log.d(TAG, "Interstitial failed to load with status: " + loadAdError);
                        interstitialFailed();
                    }
                });
    }

    @Override
    public void initRewardedVideo() {
//        // RewardedAd is a one-time-use object, so a new instance should be created to request another ad

        RewardedAd.load(activity.get(), rewardedVideoID,
                getAdRequest(), new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d(TAG, loadAdError.getMessage());
                        admobRewardedVideo = null;
                        rewardedVideoFailed();
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        admobRewardedVideo = rewardedAd;
                        rewardedVideoLoaded();
                    }
                });
    }

    @Override
    public void setUserConsent(boolean consentGiven) {
        userConsent = consentGiven;

    }

    @Override
    public void setTargetsChildren(boolean b) {

    }

    private AdRequest getAdRequest() {
        Log.d(TAG, "userConsent:"+userConsent);
        if (userConsent) {
            return new AdRequest.Builder().build();
        } else {
            Bundle extras = new Bundle();
            extras.putString("npa", "1");
            return new AdRequest.Builder()
                    .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                    .build();
        }
    }

    @Override
    public void showRewardedVideo() {
        if (admobRewardedVideo!=null) {
            admobRewardedVideo.show(activity.get(), new OnUserEarnedRewardListener(){
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {

                    Log.d(TAG, "showRewardedVideo");
                    adIntegratorManager.rewardedVideoImpression(adNetworkId);
                    rewardedVideoDidReward(true);
                }
            });
            admobRewardedVideo.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                    rewardedVideoFailed();
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent();
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    rewardedVideoDidEnd(true);
                }

                @Override
                public void onAdImpression() {
                    super.onAdImpression();
                    adIntegratorManager.rewardedVideoImpression(adNetworkId);
                }
            });

        } else {
            Log.d(TAG, "The rewarded ad wasn't ready yet.");
            rewardedVideoDidEnd(false);
        }
    }

    /**
     * Call this method when a user closes an interstitial
     */
    @Override
    public void interstitialClosed() {
        Log.d(TAG, "interstitial closed");
        adIntegratorManager.interstitialClosed(adNetworkId);
    }

    /**
     *  Call this method passing true if a rewarded video reward is successfully received.
     *  Call this method passing false if a rewarded video
     * @param value - was a reward received
     */
    @Override
    public void rewardedVideoDidReward(boolean value) {
        Log.d(TAG, "rewarded video did reward " + value);
        adIntegratorManager.rewardedVideoDidReward(adNetworkId, value);
    }

    /**
     * Call this method passing true if a rewarded video completes without failure
     * Call this method passing false if a rewarded video fails to show
     * @param value - did the video complete without failure
     */
    @Override
    public void rewardedVideoDidEnd(boolean value) {
        Log.d(TAG, "rewarded video did end " + value);
        adIntegratorManager.rewardedVideoDidEnd(adNetworkId, value);
    }

    /**
     * Call this method when the network is initialized
     */
    @Override
    public void networkLoaded() {
        Log.d(TAG, "Network loaded");
        adIntegratorManager.sdkLoaded(adNetworkId);
    }

    /**
     * Call this method when a banner is successfully loaded
     */
    @Override
    public void bannerLoaded() {
        Log.d(TAG, "Banner Loaded");
        adIntegratorManager.bannerLoaded(adNetworkId);
    }

    /**
     * Call this method when an interstitial is successfully loaded
     */
    @Override
    public void interstitialLoaded() {
        Log.d(TAG, "Interstitial Loaded");
        adIntegratorManager.interstitialLoaded(adNetworkId);
    }

    /**
     * Call this method when a rewarded video is successfully loaded
     */
    @Override
    public void rewardedVideoLoaded() {
        Log.d(TAG, "Rewarded Loaded");
        adIntegratorManager.rewardedVideoLoaded(adNetworkId);
    }

    /**
     * Call this method when if a network fails
     */
    @Override
    public void networkFailed() {
        Log.d(TAG, "network failed");
        adIntegratorManager.sdkFailed(adNetworkId);
    }

    /**
     * Call this method when a banner fails for any reason
     */
    @Override
    public void bannerFailed() {
        Log.d(TAG, "banner failed");
        adIntegratorManager.bannerFailed(adNetworkId);
    }

    /**
     * Call this method when an interstitial fails for any reason
     */
    @Override
    public void interstitialFailed() {
        Log.d(TAG, "interstitial failed");
        adIntegratorManager.interstitialFailed(adNetworkId);
    }

    /**
     * Call this method when a rewarded video fails for any reason
     */
    @Override
    public void rewardedVideoFailed() {
        Log.d(TAG, "rewarded video failed");
        adIntegratorManager.rewardedVideoFailed(adNetworkId);
    }

    @Override
    public void onActivityCreated(Activity activity) {
        //Use this method for handling activity lifecycle if the network requires it
    }

    @Override
    public void onActivityStarted(Activity activity) {
        //Use this method for handling activity lifecycle if the network requires it
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    @Override
    public void loadingDidComplete() {

    }

    @Override
    public void screenOnEnter(String screenName) {

    }

    @Override
    public void screenOnExit(String screenName) {

    }

    @Override
    public void sceneOnEnter(String sceneName) {

    }

    @Override
    public void sceneOnExit(String sceneName) {

    }

    @Override
    public void buttonActivated(String buttonName) {

    }

    @Override
    public boolean buttonVisible(String buttonName) {
        return false;
    }
}

package com.buildbox;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import com.apponboard.aob_sessionreporting.AOBReporting;
import com.buildbox.consent.ConsentHelper;
import com.buildbox.consent.SdkConsentInfo;
import com.secrethq.utils.PTJniHelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


public class AdIntegratorManager implements AdIntegratorManagerInterface {

    private static HashMap<String, AdIntegratorInterface> integrators = new HashMap<>();
    private static AdIntegratorManager instance;
    private static WeakReference<Activity> activity;
    private static String TAG = "AdIntegratorManager";

    public static AdIntegratorManager getInstance() {
        if (instance == null) {
            instance = new AdIntegratorManager();
        }
        return instance;
    }

    public static void initBridge(Activity act) {
        activity = new WeakReference<>(act);

        /* custom */ integrators.put("custom", new com.buildbox.adapter.custom.AdIntegrator()); /* custom */

    }

    public static void fetchRemoteConfig() {
        Log.d(TAG, "fetchRemoteConfig()");
        GetRemoteAdboxConfigTask task = new GetRemoteAdboxConfigTask();
        task.execute();
    }

    public static void initSdk(String sdkId, final HashMap<String, String> initValues) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity.get());
        final boolean userConsent = sharedPreferences.getBoolean(ConsentHelper.getConsentKey(sdkId), false);
        final boolean targetsChildren = PTJniHelper.targetsChildren();
        Log.d(TAG, "ad init ads hit with network " + sdkId);
        final AdIntegratorInterface integrator = integrators.get(sdkId);
        if (integrator != null) {
            Log.d(TAG, "initializing ads for " + sdkId);
            Log.d(TAG, "setting user consent: " + userConsent);
            activity.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    integrator.setTargetsChildren(targetsChildren);
                    integrator.setUserConsent(userConsent);
                    integrator.initAds(initValues, activity, getInstance());
                }
            });
        } else {
            Log.e(TAG, "Ad network " + sdkId + " not found in map");
            Log.d(TAG, "failing ad network " + sdkId);
            getInstance().networkFailed(sdkId);
        }
    }

    public static void initBanner(String sdkId) {
        Log.d(TAG, "initBanner");
        final AdIntegratorInterface integrator = integrators.get(sdkId);
        if (integrator != null) {
            Log.d(TAG, "initializing banner for " + sdkId);
            activity.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    integrator.initBanner();
                }
            });
        } else {
            Log.e(TAG, "Ad network " + sdkId + " not found in map");
        }
    }

    public static void initInterstitial(String sdkId) {
        Log.d(TAG, "initInterstitial");
        final AdIntegratorInterface integrator = integrators.get(sdkId);
        if (integrator != null) {
            Log.d(TAG, "initializing inter for " + sdkId);
            activity.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    integrator.initInterstitial();
                }
            });
        } else {
            Log.e(TAG, "Ad network " + sdkId + " not found in map");
        }
    }

    public static void initRewardedVideo(String sdkId) {
        Log.d(TAG, "initrewarded");
        final AdIntegratorInterface integrator = integrators.get(sdkId);
        if (integrator != null) {
            Log.d(TAG, "initializing reward for " + sdkId);
            activity.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    integrator.initRewardedVideo();
                }
            });
        } else {
            Log.e(TAG, "Ad network " + sdkId + " not found in map");
        }
    }

    public static void showBanner(String sdkId) {
        Log.d(TAG, "showbanner");
        final AdIntegratorInterface integrator = integrators.get(sdkId);
        if (integrator != null) {
            Log.d(TAG, "show banner for " + sdkId);
            activity.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    integrator.showBanner();
                }
            });
        } else {
            Log.e(TAG, "Ad network " + sdkId + " not found in map");
        }
    }

    public static void hideBanner(String sdkId) {
        Log.d(TAG, "hidebanner");
        final AdIntegratorInterface integrator = integrators.get(sdkId);
        if (integrator != null) {
            Log.d(TAG, "hide banner for " + sdkId);
            activity.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    integrator.hideBanner();
                }
            });
        } else {
            Log.e(TAG, "Ad network " + sdkId + " not found in map");
        }
    }

    public static void showInterstitial(String sdkId) {
        Log.d(TAG, "showinterstitial");
        final AdIntegratorInterface integrator = integrators.get(sdkId);
        if (integrator != null) {
            Log.d(TAG, "show inter for " + sdkId);
            activity.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    integrator.showInterstitial();
                }
            });
        } else {
            Log.e(TAG, "Ad network " + sdkId + " not found in map");
        }
    }

    public static void showRewardedVideo(String sdkId) {
        Log.d(TAG, "showrewarded");
        final AdIntegratorInterface integrator = integrators.get(sdkId);
        if (integrator != null) {
            Log.d(TAG, "show rewarded for " + sdkId);
            activity.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    integrator.showRewardedVideo();
                }
            });
        } else {
            Log.e(TAG, "Ad network " + sdkId + " not found in map");
        }
    }

    public static boolean isBannerVisible(String sdkId) {
        Log.d(TAG, "isbannervisible");
        final AdIntegratorInterface integrator = integrators.get(sdkId);
        if (integrator != null) {
            return integrator.isBannerVisible();
        } else {
            Log.e(TAG, "Ad network " + sdkId + " not found in map");
        }
        return false;
    }

    public static boolean isRewardedVideoAvailable(String sdkId) {
        //Log.d(TAG, "isrewardedavailable");
        final AdIntegratorInterface integrator = integrators.get(sdkId);
        if (integrator != null) {
            //Log.d(TAG, "is rewarded available for " + sdkId);
            return integrator.isRewardedVideoAvailable();
        } else {
            //Log.e(TAG, "Ad network " + sdkId + " not found in map");
        }
        return false;
    }

    public static void setUserConsent(String sdkId, final boolean consentGiven) {
        Log.d(TAG, "setuserconsent");
        final AdIntegratorInterface integrator = integrators.get(sdkId);
        if (integrator != null) {
            activity.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    integrator.setUserConsent(consentGiven);
                }
            });
        } else {
            Log.e(TAG, "Ad network " + sdkId + " not found in map");
        }
    }

    public static void revokeAllConsent() {
        Log.d(TAG, "revokeAllConsent");
        activity.get().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(activity.get());
                for (SdkConsentInfo sdkConsentInfo : ConsentHelper.getSdkConsentInfos()) {
                    Log.d(TAG, "Revoking consent for " + sdkConsentInfo.getSdkId());
                    sharedPreferences
                            .edit()
                            .putBoolean(ConsentHelper.getConsentKey(sdkConsentInfo.getSdkId()), false)
                            .commit();
                    final AdIntegratorInterface integrator = integrators.get(sdkConsentInfo.getSdkId());
                    if (integrator!=null){
                        integrator.setUserConsent(false);
                    } else {
                        Log.e(TAG, "Ad network " + sdkConsentInfo.getSdkId() + " not found in map");
                    }
                }
                Toast toast = Toast.makeText(activity.get(), "Consent revoked for all ad networks", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    public void bannerImpression(String sdkId) {
        Log.d(TAG, "Banner impression");
        AOBReporting.bannerAdAttempt(sdkId, true);
    }

    public void interstitialImpression(String sdkId) {
        Log.d(TAG, "Interstitial impression");
        AOBReporting.interstitialAdAttempt(sdkId, true);
    }

    public void rewardedVideoImpression(String sdkId) {
        Log.d(TAG, "Rewarded video impression");
        AOBReporting.rewardedVideoAdAttempt(sdkId, true);
    }

    @Override
    public void bannerAdZoneAttempt(String networkName, String zoneId, boolean didFill) {
        AOBReporting.bannerAdZoneAttempt(networkName, zoneId, didFill);
    }

    @Override
    public void interstitialAdZoneAttempt(String networkName, String zoneId, boolean didFill) {
        AOBReporting.interstitialAdZoneAttempt(networkName, zoneId, didFill);
    }

    @Override
    public void rewardedVideoAdZoneAttempt(String networkName, String zoneId, boolean didFill) {
        AOBReporting.rewardedVideoAdZoneAttempt(networkName, zoneId, didFill);
    }

    public static void onActivityCreated(Activity activity) {
        for (Map.Entry mapElement : integrators.entrySet()) {
            ((AdIntegratorInterface) mapElement.getValue()).onActivityCreated(activity);
        }
    }

    public static void onActivityStarted(Activity activity) {
        for (Map.Entry mapElement : integrators.entrySet()) {
            ((AdIntegratorInterface) mapElement.getValue()).onActivityStarted(activity);
        }
    }

    public static void onActivityResumed(Activity activity) {
        for (Map.Entry mapElement : integrators.entrySet()) {
            ((AdIntegratorInterface) mapElement.getValue()).onActivityResumed(activity);
        }
    }

    public static void onActivityPaused(Activity activity) {
        for (Map.Entry mapElement : integrators.entrySet()) {
            ((AdIntegratorInterface) mapElement.getValue()).onActivityPaused(activity);
        }
    }

    public static void onActivityStopped(Activity activity) {
        for (Map.Entry mapElement : integrators.entrySet()) {
            ((AdIntegratorInterface) mapElement.getValue()).onActivityStopped(activity);
        }
    }

    public static void onActivityDestroyed(Activity activity) {
        for (Map.Entry mapElement : integrators.entrySet()) {
            ((AdIntegratorInterface) mapElement.getValue()).onActivityDestroyed(activity);
        }
    }

    public void interstitialClosed(String sdkId) {
        interstitialClosedNative(sdkId);
    }

    public void rewardedVideoDidReward(String sdkId, boolean value) {
        rewardedVideoDidRewardNative(sdkId, value);
    }

    public void rewardedVideoDidEnd(String sdkId, boolean value) {
        rewardedVideoDidEndNative(sdkId, value);
    }

    public void bannerLoaded(String sdkId) {
        bannerLoadedNative(sdkId);
    }

    public void interstitialLoaded(String sdkId) {
        interstitialLoadedNative(sdkId);
    }

    public void rewardedVideoLoaded(String sdkId) {
        rewardedVideoLoadedNative(sdkId);
    }

    public void bannerFailed(String sdkId) {
        bannerFailedNative(sdkId);
        AOBReporting.bannerAdAttempt(sdkId, false);
    }

    public void interstitialFailed(String sdkId) {
        interstitialFailedNative(sdkId);
        AOBReporting.interstitialAdAttempt(sdkId, false);
    }

    public void rewardedVideoFailed(String sdkId) {
        rewardedVideoFailedNative(sdkId);
        AOBReporting.rewardedVideoAdAttempt(sdkId, false);
    }

    public void sdkLoaded(String sdkId) {
        sdkLoadedNative(sdkId);
    }

    /**
     * @deprecated Replaced with {@link #sdkLoaded(String)}.
     */
    public void networkLoaded(String adNetworkId) {
        sdkLoadedNative(adNetworkId);
    }

    public void sdkFailed(String sdkId) {
        sdkFailedNative(sdkId);
    }

    /**
     * @deprecated Replaced with {@link #sdkFailed(String)}.
     */
    public void networkFailed(String adNetworkId) {
        sdkFailedNative(adNetworkId);
    }

    public static CustomIntegrator getCustomIntegrator() {
        if (integrators.containsKey("custom")) {
            Integrator integrator = integrators.get("custom");

            if (integrator instanceof CustomIntegrator) {
                return (CustomIntegrator)integrator;
            }
        }

        return null;
    }

    public static native void sdkLoadedNative(String sdkId);

    public static native void sdkFailedNative(String sdkId);

    public static native void interstitialClosedNative(String sdkId);

    public static native void rewardedVideoDidRewardNative(String sdkId, boolean value);

    public static native void rewardedVideoDidEndNative(String sdkId, boolean value);

    public static native void bannerLoadedNative(String sdkId);

    public static native void interstitialLoadedNative(String sdkId);

    public static native void rewardedVideoLoadedNative(String sdkId);

    public static native void bannerFailedNative(String sdkId);

    public static native void interstitialFailedNative(String sdkId);

    public static native void rewardedVideoFailedNative(String sdkId);

    public static native void remoteConfigResultNative(boolean jsuccess, String jvalue);


    private static class GetRemoteAdboxConfigTask extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... params) {
            String content = "adbox-vungle,adbox-applovin";
            try {
                URL url = new URL("https://sdks.api.8cell.com/zones/network_priority.json");
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                String line;
                content = "";
                while ((line = br.readLine()) != null) {
                    content += line;
                }

                br.close();
            } catch (Exception e) {
                Log.e(TAG, "unable to fetch remote config");
                e.printStackTrace();
                remoteConfigResultNative(false, content);
            }
            Log.d(TAG, "pulled remote config: " + content);
            return content;
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(String result) {
            // this is executed on the main thread after the process is over
            // update your UI here
            // displayMessage(result);
            Log.d(TAG, "onPostExecute: " + result);
            activity.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "got remote config: " + result);
                    remoteConfigResultNative(true, result);
                }
            });
        }
    }
}

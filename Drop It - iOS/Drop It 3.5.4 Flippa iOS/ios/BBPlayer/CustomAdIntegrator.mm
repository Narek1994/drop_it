
#import <UIKit/UIKit.h>
#import "CustomAdIntegrator.h"

#define CustomAdNetworkName @""


@implementation CustomAdIntegrator

NSString *bannerID=@"ca-app-pub-4343203573796350/8089760895";
NSString *interstitialID=@"ca-app-pub-4343203573796350/4258327091";
NSString *rewardedAdID=@"ca-app-pub-4343203573796350/9319082086";

- (id)initWithListener:(id<AdSdkListener>)listener {
    if (self = [super initWithListener:listener]) {
        _sdkId = @"custom";
        _logTag = CustomAdNetworkName;
    }
    
    return self;
}

#pragma mark Initialization


- (void)setUserConsent:(BOOL)consentGiven {
    [super setUserConsent:consentGiven];
}


- (void)initSdkWithValues:(NSDictionary<NSString*, NSString*>*)keyValuePairs withViewController:(UIViewController*)viewController {
    [super initSdkWithValues:keyValuePairs withViewController:viewController];
    self.view=viewController.view;
    self.viewController2=viewController;
    [[GADMobileAds sharedInstance] startWithCompletionHandler:nil];

    [self sdkLoaded];
   
}


#pragma mark Banner

- (void)initBanner {
    [super initBanner];
    self.bannerView = [[GADBannerView alloc]
                       initWithAdSize:GADAdSizeBanner];

      [self addBannerViewToView:self.bannerView];
    self.bannerView.adUnitID = bannerID;
    self.bannerView.rootViewController = self.viewController2;
    self.bannerView.delegate = self;
    [self.bannerView loadRequest:[GADRequest request]];
    [self.bannerView setHidden:true];
}

- (void)addBannerViewToView:(UIView *)bannerView {
  bannerView.translatesAutoresizingMaskIntoConstraints = NO;
  [self.view addSubview:bannerView];
  [self.view addConstraints:@[
    [NSLayoutConstraint constraintWithItem:bannerView
                               attribute:NSLayoutAttributeBottom
                               relatedBy:NSLayoutRelationEqual
                                  toItem:self.viewController2.bottomLayoutGuide
                               attribute:NSLayoutAttributeTop
                              multiplier:1
                                constant:0],
    [NSLayoutConstraint constraintWithItem:bannerView
                               attribute:NSLayoutAttributeCenterX
                               relatedBy:NSLayoutRelationEqual
                                  toItem:self.view
                               attribute:NSLayoutAttributeCenterX
                              multiplier:1
                                constant:0]
                                ]];
}


- (void)showBanner {
    [super showBanner];
    [self.bannerView setHidden:false];
}


- (void)hideBanner {
    [super hideBanner];
    [self.bannerView setHidden:true];
}

/**
 * @return true if the banner is currently visible (e.g. return [_bannerView isHidden]).
 */
- (BOOL)isBannerVisible {
    return !self.bannerView.isHidden;
}

- (void)bannerViewDidReceiveAd:(GADBannerView *)bannerView {
    [self bannerLoaded];
  NSLog(@"bannerViewDidReceiveAd");
}

- (void)bannerView:(GADBannerView *)bannerView didFailToReceiveAdWithError:(NSError *)error {
    [self bannerFailed];
  NSLog(@"bannerView:didFailToReceiveAdWithError: %@", [error localizedDescription]);
}

- (void)bannerViewDidRecordImpression:(GADBannerView *)bannerView {
    [self bannerImpression];
  NSLog(@"bannerViewDidRecordImpression");
}

- (void)bannerViewWillPresentScreen:(GADBannerView *)bannerView {
  NSLog(@"bannerViewWillPresentScreen");
}

- (void)bannerViewWillDismissScreen:(GADBannerView *)bannerView {
  NSLog(@"bannerViewWillDismissScreen");
}

- (void)bannerViewDidDismissScreen:(GADBannerView *)bannerView {
  NSLog(@"bannerViewDidDismissScreen");
}


#pragma mark Interstitial


- (void)initInterstitial {
    [super initInterstitial];
    GADRequest *request = [GADRequest request];
      [GADInterstitialAd loadWithAdUnitID:interstitialID
                                  request:request
                        completionHandler:^(GADInterstitialAd *ad, NSError *error) {
        if (error) {
          NSLog(@"Failed to load interstitial ad with error: %@", [error localizedDescription]);
            [self interstitialFailed];
            self.interstitial = NULL;
          return;
        }
        self.interstitial = ad;
          [self interstitialLoaded];
      }];
}


- (void)showInterstitial {
    [super showInterstitial];
    if (self.interstitial) {
        [self.interstitial presentFromRootViewController:self.viewController2];
        self.interstitial.fullScreenContentDelegate = self;
      } else {
        NSLog(@"Ad wasn't ready");
      }
}


#pragma mark RewardedVideo


- (void)initRewardedVideo {
    [super initRewardedVideo];
    GADRequest *request = [GADRequest request];
      [GADRewardedAd
           loadWithAdUnitID:rewardedAdID
                    request:request
          completionHandler:^(GADRewardedAd *ad, NSError *error) {
            if (error) {
              NSLog(@"Rewarded ad failed to load with error: %@", [error localizedDescription]);
                [self rewardedVideoFailed];
                self.rewardedAd = NULL;
              return;
            }
            self.rewardedAd = ad;
            NSLog(@"Rewarded ad loaded.");
            self.rewardedAd.fullScreenContentDelegate = self;
            [self rewardedVideoLoaded];
          }];
}


- (void)showRewardedVideo {
    [super showRewardedVideo];
    if (self.rewardedAd) {
        [self.rewardedAd presentFromRootViewController:self.viewController2
                                      userDidEarnRewardHandler:^{
                                      GADAdReward *reward =
                                          self.rewardedAd.adReward;
                                      // TODO: Reward the user!
                                        [self rewardedVideoDidReward];
                                    }];
      } else {
        NSLog(@"Ad wasn't ready");
      }
}

- (void)ad:(nonnull id<GADFullScreenPresentingAd>)ad
    didFailToPresentFullScreenContentWithError:(nonnull NSError *)error {
    if(ad == self.rewardedAd)
        [self rewardedVideoFailed];
    else
        [self interstitialFailed];
    NSLog(@"Ad did fail to present full screen content.");
}


- (void)adWillPresentFullScreenContent:(nonnull id<GADFullScreenPresentingAd>)ad {
    if(ad == self.rewardedAd)
        [self rewardedVideoImpression];
    else
        [self interstitialImpression];
    NSLog(@"Ad did present full screen content.");
}


- (void)adDidDismissFullScreenContent:(nonnull id<GADFullScreenPresentingAd>)ad {
    if(ad == self.rewardedAd)
        [self rewardedVideoDidEnd];
    else
        [self interstitialClosed];
    NSLog(@"Ad did dismiss full screen content.");
}


/**
 * @return true if a rewarded video is loaded and ready to show.
 */
- (BOOL)isRewardedVideoAvailable {
    return self.rewardedAd!=NULL;
}


#pragma mark AdCallbacks


- (void)sdkLoaded {
    [self.listener sdkLoaded:self.sdkId];
}


- (void)sdkFailed {
    [self.listener sdkFailed:self.sdkId];
}


- (void)bannerLoaded {
    [self.listener bannerLoaded:self.sdkId];
}


- (void)bannerFailed {
    [self.listener bannerFailed:self.sdkId];
}


- (void)bannerImpression {
    [self.listener bannerImpression:[NSString stringWithFormat:@"%@ - %@", _sdkId, CustomAdNetworkName]];
}


- (void)interstitialLoaded {
    [self.listener interstitialLoaded:self.sdkId];
}


- (void)interstitialFailed {
    [self.listener interstitialFailed:self.sdkId];
}


- (void)interstitialImpression {
    [self.listener interstitialImpression:[NSString stringWithFormat:@"%@ - %@", _sdkId, CustomAdNetworkName]];
}


- (void)interstitialClosed {
    [self.listener interstitialClosed:self.sdkId];
}


- (void)rewardedVideoLoaded {
    [self.listener rewardedVideoLoaded:self.sdkId];
}


- (void)rewardedVideoFailed {
    [self.listener rewardedVideoFailed:self.sdkId];
}


- (void)rewardedVideoImpression {
    [self.listener rewardedVideoImpression:[NSString stringWithFormat:@"%@ - %@", _sdkId, CustomAdNetworkName]];
}


- (void)rewardedVideoDidReward {
    [self.listener rewardedVideoDidReward:self.sdkId withValue:true];
}


- (void)rewardedVideoDidEnd {
    [self.listener rewardedVideoDidEnd:self.sdkId withValue:true];
}


#pragma mark LifecycleCallbacks

- (void)loadingDidComplete {
}

- (void)screenOnEnter:(NSString*)screenName {
}

- (void)screenOnExit:(NSString*)screenName {
}

- (void)sceneOnEnter:(NSString*)sceneName {
}

- (void)sceneOnExit:(NSString*)sceneName {
}

- (void)buttonActivated:(NSString*)buttonName {
}

- (bool)buttonVisible:(NSString*)buttonName; {
    return true;
}

@end

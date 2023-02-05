

#import <BuildboxIntegratorInterface/AdSdkIntegrator.h>
#import <Foundation/Foundation.h>
#import <GoogleMobileAds/GoogleMobileAds.h>

@interface CustomAdIntegrator: AdSdkIntegrator <GADFullScreenContentDelegate,GADBannerViewDelegate>

@property(nonatomic, strong) GADRewardedAd *rewardedAd;
@property(nonatomic, strong) GADInterstitialAd *interstitial;
@property(nonatomic, strong) GADBannerView *bannerView;
@property(nonatomic, strong) UIViewController *viewController2;
@property(nonatomic, strong) UIView *view;



- (void)loadingDidComplete;
- (void)screenOnEnter:(NSString*)screenName;
- (void)screenOnExit:(NSString*)screenName;
- (void)sceneOnEnter:(NSString*)sceneName;
- (void)sceneOnExit:(NSString*)sceneName;
- (void)buttonActivated:(NSString*)buttonName;
- (bool)buttonVisible:(NSString*)buttonName;

@end


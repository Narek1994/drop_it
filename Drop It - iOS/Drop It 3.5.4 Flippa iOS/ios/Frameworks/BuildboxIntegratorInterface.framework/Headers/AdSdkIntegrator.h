//
//  AdSdkIntegrator.h
//  BuildboxIntegratorInterface
//
//  Created by Takaji Messer on 5/5/21.
//  Copyright © 2021 AppOnboard. All rights reserved.
//

#ifndef AdSdkIntegrator_h
#define AdSdkIntegrator_h

#import "SdkIntegrator.h"
#import "AdSdkListener.h"

@interface AdSdkIntegrator : SdkIntegrator

@property(readonly, nonatomic, strong) id<AdSdkListener> listener;

- (id)initWithListener:(id<AdSdkListener>)listener;

- (void)initBanner;
- (void)showBanner;
- (void)hideBanner;
- (BOOL)isBannerVisible;

- (void)initInterstitial;
- (void)showInterstitial;

- (void)initRewardedVideo;
- (void)showRewardedVideo;
- (BOOL)isRewardedVideoAvailable;

@end

#endif /* AdSdkIntegrator_h */

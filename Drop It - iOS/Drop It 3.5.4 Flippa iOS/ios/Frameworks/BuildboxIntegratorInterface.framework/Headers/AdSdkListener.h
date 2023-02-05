//
//  AdSdkListener.h
//  BuildboxIntegratorInterface
//
//  Created by Takaji Messer on 5/5/21.
//  Copyright © 2021 AppOnboard. All rights reserved.
//

#ifndef AdSdkListener_h
#define AdSdkListener_h

#import "SdkListener.h"

@protocol AdSdkListener<SdkListener>

- (void)bannerLoaded:(NSString*)sdkId;
- (void)bannerFailed:(NSString*)sdkId;
- (void)bannerImpression:(NSString*)sdkId;
- (void)bannerZone:(NSString*)sdkId withZone:(NSString*)zoneId succeeded:(BOOL)success;

- (void)interstitialLoaded:(NSString*)sdkId;
- (void)interstitialFailed:(NSString*)sdkId;
- (void)interstitialImpression:(NSString*)sdkId;
- (void)interstitialClosed:(NSString*)sdkId;
- (void)interstitialZone:(NSString*)sdkId withZone:(NSString*)zoneId succeeded:(BOOL)success;

- (void)rewardedVideoLoaded:(NSString*)sdkId;
- (void)rewardedVideoFailed:(NSString*)sdkId;
- (void)rewardedVideoImpression:(NSString*)sdkId;
- (void)rewardedVideoDidReward:(NSString*)sdkId withValue:(BOOL)value;
- (void)rewardedVideoDidEnd:(NSString*)sdkId withValue:(BOOL)value;
- (void)rewardedVideoZone:(NSString*)sdkId withZone:(NSString*)zoneId succeeded:(BOOL)success;

@end

#endif /* AdSdkListener_h */

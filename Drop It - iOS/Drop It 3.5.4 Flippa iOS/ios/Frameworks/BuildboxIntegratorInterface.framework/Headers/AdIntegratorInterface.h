//
//  AdIntegratorInterface.h
//  BuildboxIntegratorInterface
//
//  Created by Cody Thompson on 11/26/19.
//  Copyright © 2019 AppOnboard. All rights reserved.
//

#ifndef AdIntegratorInterface_h
#define AdIntegratorInterface_h

@interface AdIntegratorInterface : NSObject

+ (void)sdkLoaded:(NSString*)sdkId;
+ (void)sdkFailed:(NSString*)sdkId;
+ (void)networkLoaded:(NSString*)networkId __deprecated_msg("Replaced with +sdkLoaded:");
+ (void)networkFailed:(NSString*)networkId __deprecated_msg("Replaced with +sdkFailed:");

+ (void)interstitialLoaded:(NSString*)sdkId;
+ (void)interstitialImpression:(NSString*)sdkId;
+ (void)interstitialFailed:(NSString*)sdkId;
+ (void)interstitialClosed:(NSString*)sdkId;
+ (void)bannerLoaded:(NSString*)sdkId;
+ (void)bannerImpression:(NSString*)sdkId;
+ (void)bannerFailed:(NSString*)sdkId;
+ (void)rewardedVideoLoaded:(NSString*)sdkId;
+ (void)rewardedVideoFailed:(NSString*)sdkId;
+ (void)rewardedVideoImpression:(NSString*)sdkId;
+ (void)rewardedVideoDidReward:(NSString*)sdkId withValue:(bool)value;
+ (void)rewardedVideoDidEnd:(NSString*)sdkId withValue:(bool)value;

@end

#endif /* AdIntegratorInterface_h */

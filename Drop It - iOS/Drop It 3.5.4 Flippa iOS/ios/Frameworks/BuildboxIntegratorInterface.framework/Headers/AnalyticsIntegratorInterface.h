//
//  AnalyticsIntegratorInterface.h
//  BuildboxIntegratorInterface
//
//  Created by Takaji Messer on 02/02/20.
//  Copyright © 2019 AppOnboard. All rights reserved.
//

#ifndef AnalyticsIntegratorInterface_h
#define AnalyticsIntegratorInterface_h

@interface AnalyticsIntegratorInterface : NSObject

+ (void)sdkLoaded:(NSString*)sdkId;
+ (void)sdkFailed:(NSString*)sdkId;

@end

#endif /* AnalyticsIntegratorInterface_h */

//
//  AnalyticsSdkIntegrator.h
//  BuildboxIntegratorInterface
//
//  Created by Takaji Messer on 5/5/21.
//  Copyright © 2021 AppOnboard. All rights reserved.
//

#ifndef AnalyticsSdkIntegrator_h
#define AnalyticsSdkIntegrator_h

#import "SdkIntegrator.h"
#import "AnalyticsSdkListener.h"

@interface AnalyticsSdkIntegrator : SdkIntegrator

@property(readonly, nonatomic, strong) id<AnalyticsSdkListener> listener;

- (id)initWithListener:(id<AnalyticsSdkListener>)listener;

- (void)logEvent:(NSString*)eventId;

@end

#endif /* AnalyticsSdkIntegrator_h */

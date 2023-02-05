//
//  Integrator.h
//  BuildboxIntegratorInterface
//
//  Created by Takaji Messer on 5/5/21.
//  Copyright © 2021 AppOnboard. All rights reserved.
//

#ifndef Integrator_h
#define Integrator_h

#import "SdkListener.h"

@protocol Integrator

@property(readonly, nonatomic, strong) NSString* sdkId;

- (void)setUserConsent:(BOOL)consentGiven;
- (void)setTargetsChildren:(BOOL)targetsChildren;
- (void)initSdkWithValues:(NSDictionary<NSString*, NSString*>*)keyValuePairs withViewController:(UIViewController*)viewController;

@end

#endif /* Integrator_h */

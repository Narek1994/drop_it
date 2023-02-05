//
//  SdkIntegrator.h
//  BuildboxIntegratorInterface
//
//  Created by Takaji Messer on 5/5/21.
//  Copyright © 2021 AppOnboard. All rights reserved.
//

#ifndef SdkIntegrator_h
#define SdkIntegrator_h

#import "Integrator.h"

@interface SdkIntegrator : NSObject<Integrator> {
    @protected
    NSString* _sdkId;
    NSString* _logTag;
}

@property(readonly, nonatomic, strong) NSString* sdkId;
@property(readonly, nonatomic, strong) NSString* logTag;
@property(readonly, nonatomic, assign) BOOL didConsent;
@property(readonly, nonatomic, assign) BOOL targetsChildren;
@property(readonly, nonatomic, weak) UIViewController* viewController;

- (void)log:(NSString*)format, ...;

@end

#endif /* SdkIntegrator_h */

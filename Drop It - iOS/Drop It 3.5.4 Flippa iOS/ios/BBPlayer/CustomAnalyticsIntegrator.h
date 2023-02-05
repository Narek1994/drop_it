//
//  CustomAnalyticsIntegrator.h
//  BBPlayer
//
//  Created by Takaji Messer on 02/20/20.
//  Copyright © 2019 Buildbox. All rights reserved.
//

#import <BuildboxIntegratorInterface/AnalyticsSdkIntegrator.h>
#import <Foundation/Foundation.h>

@interface CustomAnalyticsIntegrator : AnalyticsSdkIntegrator

- (void)loadingDidComplete;
- (void)screenOnEnter:(NSString*)screenName;
- (void)screenOnExit:(NSString*)screenName;
- (void)sceneOnEnter:(NSString*)sceneName;
- (void)sceneOnExit:(NSString*)sceneName;
- (void)buttonActivated:(NSString*)buttonName;
- (bool)buttonVisible:(NSString*)buttonName;

@end


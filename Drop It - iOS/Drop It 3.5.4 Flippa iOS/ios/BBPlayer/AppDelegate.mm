//
//  AppDelegate.m
//  BBPlayer
//
//  Created by Nik Rudenko on 9/12/17.
//
//

#import "AppDelegate.h"
#import <GLKit/GLKit.h>
#include <BBRuntime/BBRuntime.h>

/* AppTrackingTransparency */
#import <AppTrackingTransparency/ATTrackingManager.h>
/* AppTrackingTransparency */

@interface AppDelegate ()

@end

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {

    return YES;
}


- (void)applicationWillResignActive:(UIApplication *)application {
    BBRuntimeResignActive();
}

- (void)applicationDidEnterBackground:(UIApplication *)application {
    BBRuntimeEnterBackground();
}

- (void)applicationWillEnterForeground:(UIApplication *)application {
    BBRuntimeEnterForeground();
}

- (void)applicationDidBecomeActive:(UIApplication *)application {
    BBRuntimeBecomeActive();
}

- (void)applicationWillTerminate:(UIApplication *)application {
  
}

-(void)showCustomFullscreenAd{
}

- (BOOL)needsTrackingRequest {
    /* AppTrackingTransparency */
    if (@available(iOS 14, *)) {
        return true;
    }
    else
    /* AppTrackingTransparency */
    {
        return false;
    }
}

- (void)requestTrackingWithCompletionHandler:(void(^)(bool))completionHandler {
    /* AppTrackingTransparency */
    if (@available(iOS 14, *)) {
        [ATTrackingManager requestTrackingAuthorizationWithCompletionHandler:^(ATTrackingManagerAuthorizationStatus status) {
            completionHandler(status == ATTrackingManagerAuthorizationStatusAuthorized);
        }];
    }
    /* AppTrackingTransparency */
}

- (void)loadingDidComplete {
    BBRuntimeDidLoad();
}

- (void)screenOnEnter:(NSString*)name {	
}

- (void)screenOnExit:(NSString*)name {
}

- (void)sceneOnEnter:(NSString*)name {
}

- (void)sceneOnExit:(NSString*)name {
}

- (void)buttonActivated:(NSString*)name {
}

- (bool)buttonVisible:(NSString*)name {
    return true;
}

@end

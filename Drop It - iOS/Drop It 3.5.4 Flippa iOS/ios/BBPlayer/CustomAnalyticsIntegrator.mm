// 
// CustomAnalyticsIntegrator.mm
// BBPlayer
// 
// Edit this file to integrate a 3rd party analytics library.
// 
// Update the methods in the "Initialization" section.
// Call the methods in the "AnalyticsCallbacks" section when appropriate.
// Leave any lines that begin with "[super" untouched.
// 
// NOTE: This class is ignored in the free version of Buildbox.
// 

#import <UIKit/UIKit.h>
#import "CustomAnalyticsIntegrator.h"

// TODO - Set to implemented analytics sdk name here
#define CustomAnalyticsLibraryName @""

@implementation CustomAnalyticsIntegrator

- (id)initWithListener:(id<AnalyticsSdkListener>)listener {
    if (self = [super initWithListener:listener]) {
        _sdkId = @"custom-analytics";
        _logTag = CustomAnalyticsLibraryName;
    }
    
    return self;
}

/** 
 * Complete the following stubbed methods. They will be called automatically by the Buildbox player.
 */
#pragma mark Initialization

/**
 * Pass the consent result to your analytics library. This method will be called when the user has decided to change their acceptance of a privacy policy.
 */
- (void)setUserConsent:(BOOL)consentGiven {
    [super setUserConsent:consentGiven];
}

/**
 * Initialize your analytics library. This method should either call "sdkLoaded" or "sdkFailed" on completion.
 */
- (void)initSdkWithValues:(NSDictionary<NSString*, NSString*>*)keyValuePairs withViewController:(UIViewController*)viewController {
    [super initSdkWithValues:keyValuePairs withViewController:viewController];

    [self log:@"Custom analytics integrator needs to be implemented!"];
    //[self sdkLoaded];
    [self sdkFailed];
}



/**
 * The following methods are used to report analytics library events to Buildbox.
 * Call these methods when appropriate (e.g. call sdkLoaded when the analytics library has been initialized).
 */
#pragma mark AnalyticsCallbacks

/**
 * Call this when you are done configuring the analytics library (e.g. at the end of initSdkWithValues).
 */
- (void)sdkLoaded {
    [self.listener sdkLoaded:self.sdkId];
}

/**
 * Call this if you are unable to configure the analytics library.
 * Buildbox will stop trying to run methods in the integrator after you call this.
 */
- (void)sdkFailed {
    [self.listener sdkFailed:self.sdkId];
}

/** 
 * These methods are legacy calls for plugging your analytics library into the game lifecycle.
 * If unsure, leave this be.
 */
#pragma mark LifecycleCallbacks

- (void)loadingDidComplete {
}

- (void)screenOnEnter:(NSString*)screenName {
}

- (void)screenOnExit:(NSString*)screenName {
}

- (void)sceneOnEnter:(NSString*)sceneName {
}

- (void)sceneOnExit:(NSString*)sceneName {
}

- (void)buttonActivated:(NSString*)buttonName {
}

- (bool)buttonVisible:(NSString*)buttonName; {
    return true;
}

@end

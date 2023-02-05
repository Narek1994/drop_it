//
//  AppDelegate.h
//  BBPlayer
//
//  Created by Nik Rudenko on 9/12/17.
//
//

#import <UIKit/UIKit.h>

/* AppTrackingTransparency */
#import <BBRuntime/TrackingRequester.h>
/* AppTrackingTransparency */

@interface AppDelegate : UIResponder<UIApplicationDelegate/* AppTrackingTransparency */, TrackingRequester/* AppTrackingTransparency */>{
}

@property (strong, nonatomic) UIWindow *window;


@end


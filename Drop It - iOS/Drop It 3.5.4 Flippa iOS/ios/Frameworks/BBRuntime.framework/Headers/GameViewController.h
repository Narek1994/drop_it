//
//  GameViewController.h
//  BBPlayer
//
//  Created by Nik Rudenko on 9/12/17.
//
//

#import <UIKit/UIKit.h>
#import <GLKit/GLKit.h>

@interface GameViewController : GLKViewController

@property(nonatomic, assign) BOOL bbworldEnabled;

- (void)gameDidLoad;
- (void)promptForConsent;

@end

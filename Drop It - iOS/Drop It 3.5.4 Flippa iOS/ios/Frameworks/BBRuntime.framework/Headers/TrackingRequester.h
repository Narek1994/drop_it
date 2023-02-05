//
//  TrackingRequester.h
//  BBPlayer
//
//  Created by Taka on 4/26/2021.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@protocol TrackingRequester

- (bool)needsTrackingRequest;
- (void)requestTrackingWithCompletionHandler:(void(^)(bool))completionHandler;

@end

NS_ASSUME_NONNULL_END

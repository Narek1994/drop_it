//
//  PostOperation.h
//  AppLovinAdIntegrator
//
//  Created by Takaji Messer on 4/29/21.
//  Copyright © 2021 Buildbox. All rights reserved.
//

#ifndef PostOperation_h
#define PostOperation_h

#import "AsyncOperation.h"
#import "PostListener.h"

@interface PostOperation : AsyncOperation

- (id)initWithUrl:(NSString*)url postData:(NSData*)data postListener:(id<PostListener>)listener;

@end

#endif /* PostOperation_h */

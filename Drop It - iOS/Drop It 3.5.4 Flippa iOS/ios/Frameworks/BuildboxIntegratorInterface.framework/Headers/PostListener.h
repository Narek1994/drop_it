//
//  PostListener.h
//  AppLovinAdIntegrator
//
//  Created by Takaji Messer on 4/29/21.
//  Copyright © 2021 Buildbox. All rights reserved.
//

#ifndef PostListener_h
#define PostListener_h

@protocol PostListener

- (void)postSucceededWithData:(NSData*)data;
- (void)postFailedWithError:(NSError*)error;

@end

#endif /* PostListener_h */

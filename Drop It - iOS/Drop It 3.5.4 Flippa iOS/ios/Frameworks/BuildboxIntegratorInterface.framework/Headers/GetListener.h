//
//  GetListener.h
//  AppLovinAdIntegrator
//
//  Created by Takaji Messer on 4/29/21.
//  Copyright © 2021 Buildbox. All rights reserved.
//

#ifndef GetListener_h
#define GetListener_h

@protocol GetListener

- (void)getSucceededWithData:(NSData*)data;
- (void)getFailedWithError:(NSError*)error;

@end

#endif /* GetListener_h */

//
//  GetOperation.h
//  AppLovinAdIntegrator
//
//  Created by Takaji Messer on 4/29/21.
//  Copyright © 2021 Buildbox. All rights reserved.
//

#ifndef GetOperation_h
#define GetOperation_h

#import "AsyncOperation.h"
#import "GetListener.h"

@interface GetOperation : AsyncOperation

- (id)initWithUrl:(NSString*)url withHeaders:(NSDictionary<NSString*, NSString*>*)headers getListener:(id<GetListener>)listener;

@end

#endif /* GetOperation_h */

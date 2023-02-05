//
//  SdkListener.h
//  BuildboxIntegratorInterface
//
//  Created by Takaji Messer on 5/5/21.
//  Copyright © 2021 AppOnboard. All rights reserved.
//

#ifndef SdkListener_h
#define SdkListener_h

@protocol SdkListener

- (void)sdkLoaded:(NSString*)sdkId;
- (void)sdkFailed:(NSString*)sdkId;

@end

#endif /* SdkListener_h */

//
//  BoloPlugin.h
//
//  Created by lihh on 15-3-20.
//  Copyright (c) 2015年 bolo. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <AVFoundation/AVFoundation.h>
#import <Cordova/CDV.h>

@interface BoloPlugin : CDVPlugin

- (void)exitApp:(CDVInvokedUrlCommand*)command;

- (void)getDeviceToken:(CDVInvokedUrlCommand*)command;

- (void)getLoaded:(CDVInvokedUrlCommand*)command;

- (void)setLoaded:(CDVInvokedUrlCommand*)command;

- (void)appReload:(CDVInvokedUrlCommand *)command;

@end

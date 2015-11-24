//
//  BoloPlugin.h
//
//  Created by lihh on 15-3-20.
//  Copyright (c) 2015年 bolo. All rights reserved.
//

#import "BoloPlugin.h"
#import "BoloCustomGlobal.h"

@implementation BoloPlugin

#pragma mark -
#pragma mark 对外开放接口

//退出应用
- (void)exitApp:(CDVInvokedUrlCommand*)command
{
    [[NSURLCache sharedURLCache] removeAllCachedResponses];
    exit(0);
}

//获取设备标识
- (void)getDeviceToken:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult* result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:[BoloCustomGlobal getInstance].deviceToken];
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

//设置设备标识
+ (void)setDeviceToken:(NSString *)deviceToken{
    NSMutableDictionary *dictionary = [[NSMutableDictionary alloc] init];
    [dictionary setObject:deviceToken forKey:@"type"];
    
    NSData *jsonData = [NSJSONSerialization dataWithJSONObject:dictionary options:0 error:nil];
    NSString *jsonString = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
    NSString *jsToEval = [NSString stringWithFormat : @"navigator.splashscreen.setDeviceToken(%@);", jsonString];
    
    __block CDVPlugin* pluginObj = [[CDVPlugin alloc] init];
    
    [pluginObj.commandDelegate  evalJs:jsToEval];
}

//获取是否是本次启动应用中的首次登录
- (void)getLoaded:(CDVInvokedUrlCommand *)command
{
    CDVPluginResult* result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsInt:[[BoloCustomGlobal getInstance].appLoaded intValue]];
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}
//设置为已登录
- (void)setLoaded:(CDVInvokedUrlCommand *)command
{
    NSNumber* loaded = [command argumentAtIndex:0];
    [BoloCustomGlobal getInstance].appLoaded = loaded;
}

//应用页面重载
- (void)appReload:(CDVInvokedUrlCommand *)command
{
    NSString* url = [command argumentAtIndex:0];
    NSURL *nsurl = [NSURL URLWithString:url];
    [self.webView loadRequest:[NSURLRequest requestWithURL:nsurl]];
}
@end


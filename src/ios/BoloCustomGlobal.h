//
//  BoloCustomGlobal.h
//  PowerB
//
//  Created by bossbolo on 15/11/16.
//
//

#import <Foundation/Foundation.h>

@interface BoloCustomGlobal : NSObject
{
    NSMutableDictionary *_jsonData;
    NSString *_deviceToken;
    NSNumber *_appLoaded;
    NSNumber *_appBadge;
}

@property(readwrite,retain) NSMutableDictionary * jsonData;
@property(readwrite,retain) NSString *deviceToken;
@property(readwrite,retain) NSNumber *appLoaded;
@property(readwrite,retain) NSNumber *appBadge;


+(BoloCustomGlobal*) getInstance;
-(void)initData;

@end
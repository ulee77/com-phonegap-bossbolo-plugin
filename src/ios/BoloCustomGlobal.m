//
//  BoloCustomGlobal.m
//  PowerB
//
//  Created by bossbolo on 15/11/16.
//
//

#import "BoloCustomGlobal.h"

static BoloCustomGlobal *cunstomGlobal = nil;

@implementation BoloCustomGlobal

@synthesize jsonData = _jsonData;
@synthesize deviceToken = _deviceToken;
@synthesize appLoaded = _appLoaded;
@synthesize appBadge = _appBadge;

+(BoloCustomGlobal *)getInstance
{
    if(cunstomGlobal == nil){
        cunstomGlobal = [[BoloCustomGlobal alloc]init];
        [cunstomGlobal initData];
    }
    return cunstomGlobal;
}

-(void)initData
{
    _jsonData = [[NSMutableDictionary alloc]init];
    _deviceToken = nil;
    _appLoaded = 0;
    _appBadge = 0;
}

@end
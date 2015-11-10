/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
*/

var exec = require('cordova/exec');

LoadedStatus = {

};

var BoloPlugin = {
    LoadedStatus : {
        LOADED : 1,
        NOT_LOADED : 0
    },

    //退出应用
    exitApp:function() {
        exec(null, null, "SplashScreen", "exitApp", []);
    },

    //获取应用设备唯一标识
    getDeviceToken : function(callback) {
        exec(callback, null, "SplashScreen", "getDeviceToken", []);
    },

    //检查版本,需集成友盟自动更新插件(仅支持Android)
    checkVersion : function(callback){
        if($.os.ios)return;
        callback = callback || function(flag){};
        var checkResult = function(result){callback(result==1);};
        exec(checkResult, null, "BoloPlugin", "checkVersion", []);
    },

    //获取/设置 本次载入主页时在本次应用启动生命周期内是否已呗载入过，通常与appReload配合使用，判断是否需要自动登录。
    getLoaded:function(callback) {
        exec(callback, null, "SplashScreen", "getLoaded", []);
    },
    setLoaded : function(loaded){
        exec(null, null, "SplashScreen", "setLoaded", [loaded]);
    },
    //重载应用，即页面完全刷新
    appReload : function(){},

    //卸载指定包名应用(仅支持Android)
    uninstallBPP : function(pageName){
        if($.os.ios)return;
        pageName = pageName || "com.bossbolo.bppapp";
        exec(null, null, "BoloPlugin", "uninstallBPP", [pageName]);
    }
};

module.exports = BoloPlugin;

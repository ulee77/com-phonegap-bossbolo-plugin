/*
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
package com.phonegap.bossbolo.plugin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.ConfigXmlParser;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

public class BoloPlugin extends CordovaPlugin {
    private static final String TAG = "BoosboloPlugin";
    private CordovaWebView webView;
    private Activity activity;
    private Window window;
    
    private Boolean haveUpdate = null;
    private Boolean updateIsCallback = false;

    /**
     * Sets the context of the Command. This can then be used to do things like
     * get file paths associated with the Activity.
     *
     * @param cordova The context of the main Activity.
     * @param webView The CordovaWebView Cordova is running in.
     */
    @Override
    public void initialize(final CordovaInterface cordova, CordovaWebView webView) {
        Log.v(TAG, "StatusBar: initialization");
        super.initialize(cordova, webView);
        this.webView = webView;
        this.activity = cordova.getActivity();
        this.window = this.activity.getWindow();
        
        ConfigXmlParser parser = new ConfigXmlParser();
        CustomGlobal.getInstance().setLaunchUrl(parser.getLaunchUrl());
        
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.setUpdateAutoPopup(false);
        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
            @Override
            public void onUpdateReturned(int updateStatus,UpdateResponse updateInfo) {
            	if(updateStatus == UpdateStatus.Yes){
            		haveUpdate = true;
            		UmengUpdateAgent.showUpdateDialog(activity.getApplicationContext(), updateInfo);
            	}
            	updateIsCallback = true;
            }
        });

        /*this.cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Clear flag FLAG_FORCE_NOT_FULLSCREEN which is set initially
                // by the Cordova.
            }
        });*/
    }

    /**
     * Executes the request and returns PluginResult.
     *
     * @param action            The action to execute.
     * @param args              JSONArry of arguments for the plugin.
     * @param callbackContext   The callback id used when calling back into JavaScript.
     * @return                  True if the action was valid, false otherwise.
     */
    @Override
    public boolean execute(final String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {
        Log.v(TAG, "Executing action: " + action);
        if (action.equals("exitApp")) {
        	exitAppp ();
//        	return true;
        } else if(action.equals("checkVersion")){
        	checkVersion(callbackContext);
        } else if(action.equals("getLoaded")){
        	getLoaded(args, callbackContext);
        } else if(action.equals("setLoaded")){
        	setLoaded(args, callbackContext);
        } else if(action.equals("appReload")){
        	appReload();
        } else if(action.equals("uninstallBPP")){
        	uninstallBPP();
        }else{
            return false;
        }
		return true;
    }

    
    public void exitAppp () {
    	android.os.Process.killProcess(android.os.Process.myPid()); //获取PID 
    	System.exit(0);   //常规java、c#的标准退出法，返回值为0代表正常退出
    }
    
    public void checkVersion(CallbackContext callbackContext){
    	updateIsCallback = false;
    	UmengUpdateAgent.update(this.webView.getContext());
    	while (!updateIsCallback) {
			Log.v("update", "版本更新响应");
		}
    	callbackContext.success(haveUpdate?1:-1);
    }
    
    public void getLoaded(JSONArray args, final CallbackContext callbackContext){
    	callbackContext.success(CustomGlobal.getInstance().getLoaded());
    }
    
    public void setLoaded(JSONArray args, final CallbackContext callbackContext){
    	int loaded = 1;
		try {
			loaded = args.getInt(0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	CustomGlobal.getInstance().setLoaded(loaded);
    	callbackContext.success();
    }
    
    public void appReload(){
//    	webView.clearCache();
    	webView.loadUrlIntoView(CustomGlobal.getInstance().getLaunchUrl(), false);
    }
    
    public void uninstallBPP(){
    	final String packageName = "com.bossbolo.bppapp";
    	final CordovaPlugin currentPlugin = (CordovaPlugin) this;
        try {
			ApplicationInfo info = this.cordova.getActivity().getPackageManager().getApplicationInfo(
			        packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
			new  AlertDialog.Builder(this.cordova.getActivity())
				.setTitle("温馨提示" )
				.setMessage("尊敬的用户您好！\n    感谢您对布络软件的支持，由于B++已经更名跑布，且B++将停止维护、使用，我们即将为您卸载旧版本B++，如需保留请在卸载界面中进行取消。\n    给您带来不便望请谅解！")
				.setPositiveButton("确定" ,  new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface dialoginterface, int i){
							Intent intent = new Intent();
						    intent.setAction(Intent.ACTION_DELETE);
						    intent.setData(Uri.parse("package:"+packageName));
						    currentPlugin.cordova.startActivityForResult(currentPlugin, intent, 2298816);
						}
					})
				.show();
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}

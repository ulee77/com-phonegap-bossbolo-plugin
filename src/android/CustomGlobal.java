package com.phonegap.bossbolo.plugin;

import org.apache.cordova.CordovaArgs;
import org.json.JSONObject;

/**
 * 自定义全局变量
 * @author lihh
 */
public class CustomGlobal {
	
	private static CustomGlobal instance;
	
	private JSONObject json = null;
	private int loaded = 0;
	private String launchUrl;
	private int heartMessageType;
	
	private CordovaArgs connectArgs;
	

	public CordovaArgs getConnectArgs() {
		return connectArgs;
	}

	public void setConnectArgs(CordovaArgs connectArgs) {
		this.connectArgs = connectArgs;
	}

	public int getHeartMessageType() {
		return heartMessageType;
	}

	public void setHeartMessageType(int heartMessageType) {
		this.heartMessageType = heartMessageType;
	}
	
	public String getLaunchUrl() {
		return launchUrl;
	}

	public void setLaunchUrl(String launchUrl) {
		this.launchUrl = launchUrl;
	}

	public int getLoaded() {
		return loaded;
	}

	public void setLoaded(int loaded) {
		this.loaded = loaded;
	}
	
	public static CustomGlobal getInstance() {
        if (null == instance) {
            instance = new CustomGlobal();
            instance.setHeartMessageType(0);
        }
        return instance;
    }

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}
}

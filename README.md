# com-phonegap-bossbolo-plugin
布络phonegap插件库


## 安装插件
phonegap plugin add com-phonegap-bossbolo-plugin
或
phonegap plugin add https://github.com/ulee77/com-phonegap-bossbolo-plugin.git

##平台支持
- phoengap 5+
- Android 4+
- IOS 5+

##通用接口说明

退出应用
window.BoloPlugin.exitApp();

获取应用设备唯一标识
window.BoloPlugin.

检查版本,需集成友盟自动更新插件(仅支持Android)
window.BoloPlugin.

获取 本次载入主页时在本次应用启动生命周期内是否已被载入过，通常与appReload配合使用，判断是否需要自动登录。
var callback = function(result){
    console.log(result);//result：默认为0，定义为首次载入；获取值依setLoaded()方法设置值而定
}
window.BoloPlugin.getLoaded(callback);

设置载入值
window.BoloPlugin.setLoaded(1);

//重载应用，即页面完全刷新
window.BoloPlugin.appReload();

卸载指定包名应用(仅支持Android)
window.BoloPlugin.uninstallBPP(pageName); //pageName为指定包名，若不传值则检查默认包名

package com.yuzhengwen.yxposed;

import com.yuzhengwen.yxposed.hooks.AndroidHook;
import com.yuzhengwen.yxposed.hooks.SettingsHook;
import com.yuzhengwen.yxposed.hooks.SystemUIHook;

import android.content.res.XModuleResources;
import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Xposed implements IXposedHookLoadPackage, IXposedHookZygoteInit, IXposedHookInitPackageResources {

	public static XSharedPreferences mXSharedPreferences;
	public static final String FILE_NAME = "com.yuzhengwen.yxposed";
	private String MODULE_PATH;

	public final static String SYSTEMUI_PKG_NAME = "com.android.systemui";
	public final static String SETTINGS_PKG_NAME = "com.android.settings";
	public final static String ANDROID_PKG_NAME = "android";

	@Override
	public void initZygote(StartupParam startupParam) throws Throwable {
		mXSharedPreferences = new XSharedPreferences(FILE_NAME);

		MODULE_PATH = startupParam.modulePath;
	}

	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		setParentClassLoaders(lpparam);
		mXSharedPreferences.reload();

		if (lpparam.packageName.equals(SYSTEMUI_PKG_NAME)) {
			XposedHelpers.findAndHookMethod("com.android.systemui.statusbar.phone.PhoneStatusBar", lpparam.classLoader,
					"shouldDisableNavbarGestures", new XC_MethodHook() {
						@Override
						protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
							param.setResult(true);
						}
					});

			SystemUIHook.hook(lpparam);
		}
	}

	private void setParentClassLoaders(LoadPackageParam lpparam) throws Throwable {

		// todos os classloaders
		ClassLoader packge = lpparam.classLoader;
		ClassLoader module = getClass().getClassLoader();
		ClassLoader xposed = module.getParent();

		// package classloader parente é: xposed classloader
		XposedHelpers.setObjectField(packge, "parent", xposed);

		// módulo parente classcloader é: package classloader
		XposedHelpers.setObjectField(module, "parent", packge);

	}

	@Override
	public void handleInitPackageResources(InitPackageResourcesParam resparam) throws Throwable {
		mXSharedPreferences.reload();
		XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH, resparam.res);
		if (resparam.packageName.equals(ANDROID_PKG_NAME))
			AndroidHook.hookResources(resparam, modRes);
		if (resparam.packageName.equals(SYSTEMUI_PKG_NAME))
			SystemUIHook.hookResources(resparam, modRes);
		if (resparam.packageName.equals(SETTINGS_PKG_NAME))
			SettingsHook.hookResources(resparam, modRes);
	}
}
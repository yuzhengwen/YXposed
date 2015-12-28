package com.yuzhengwen.yxposed;

import com.yuzhengwen.yxposed.hooks.SystemUI_NavBar;
import com.yuzhengwen.yxposed.hooks.SystemUI_StatusBar;

import android.content.res.XModuleResources;
import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Xposed implements IXposedHookLoadPackage, IXposedHookZygoteInit, IXposedHookInitPackageResources {

	public static XSharedPreferences mXSharedPreferences;
	public static String FILE_NAME = "com.yuzhengwen.yxposed";
	private String MODULE_PATH;

	@Override
	public void initZygote(StartupParam startupParam) throws Throwable {
		mXSharedPreferences = new XSharedPreferences(FILE_NAME);

		MODULE_PATH = startupParam.modulePath;
	}

	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		if (!lpparam.packageName.equals("com.android.systemui"))
			return;

		setParentClassLoaders(lpparam);

		mXSharedPreferences.reload();

		SystemUI_StatusBar.hook(lpparam);
		SystemUI_NavBar.hook(lpparam);
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
		if (resparam.packageName.equals("android")) {
			ResourceHook.hookResources(resparam, modRes);
		}
		if (resparam.packageName.equals("com.android.systemui"))
			SystemUIResourceHook.hook(resparam, modRes);
	}
}
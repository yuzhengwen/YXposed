package com.yuzhengwen.yxposed;

import com.yuzhengwen.yxposed.hooks.SystemUI_StatusBar;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Xposed implements IXposedHookLoadPackage, IXposedHookZygoteInit {

	public static XSharedPreferences mXSharedPreferences;
	public static String FILE_NAME = "com.yuzhengwen.yxposed";

	@Override
	public void initZygote(StartupParam startupParam) throws Throwable {
		mXSharedPreferences = new XSharedPreferences(FILE_NAME);
	}

	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		if (!lpparam.packageName.equals("com.android.systemui"))
			return;

		mXSharedPreferences.reload();
		
		SystemUI_StatusBar.hook(lpparam);
	}
}
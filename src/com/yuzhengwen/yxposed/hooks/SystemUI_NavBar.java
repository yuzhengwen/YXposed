package com.yuzhengwen.yxposed.hooks;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import com.android.systemui.statusbar.phone.NavigationBarView;
import com.yuzhengwen.yxposed.SettingsFragment;
import com.yuzhengwen.yxposed.Xposed;

import android.view.View;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class SystemUI_NavBar {

	private static boolean menuAlwaysShow;

	public static void hook(LoadPackageParam lpparam) {
		try {
			updatePreferences();
			findAndHookMethod("com.android.systemui.statusbar.phone.NavigationBarView", lpparam.classLoader,
					"setMenuVisibility", new XC_MethodHook() {

						@Override
						protected void afterHookedMethod(MethodHookParam param) throws Throwable {
							NavigationBarView nbv = (NavigationBarView) param.thisObject;
							nbv.getMenuButton().setVisibility(View.VISIBLE);
						}
					});
		} catch (Exception e) {
			XposedBridge.log(e);
		}
	}

	public static void updatePreferences() {
		menuAlwaysShow = Xposed.mXSharedPreferences.getBoolean(SettingsFragment.MENU_ALWAYS_SHOW_KEY,
				SettingsFragment.MENU_ALWAYS_SHOW_DEFAULT);
	}
}

package com.yuzhengwen.yxposed.hooks;

import com.yuzhengwen.yxposed.Constants;
import com.yuzhengwen.yxposed.Xposed;

import android.content.res.XModuleResources;
import android.content.res.XResources;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;

public class AndroidHook implements Constants {

	private static boolean enableAmbientDisplay, wakeUpWhenUnplugged;
	private static int navBarHeight;

	public static void hookResources(InitPackageResourcesParam resparam, final XModuleResources modRes) {
		updatePreferences();

		if (enableAmbientDisplay) {
			XResources.setSystemWideReplacement("android", "string", "config_dozeComponent",
					"com.android.systemui/com.android.systemui.doze.DozeService");
			XResources.setSystemWideReplacement("android", "bool", "config_dozeAfterScreenOff", true);
			XResources.setSystemWideReplacement("android", "bool", "config_powerDecoupleInteractiveModeFromDisplay",
					true);
			XResources.setSystemWideReplacement("android", "integer", "config_screenBrightnessDoze", 17);
		}

		XResources.setSystemWideReplacement("android", "bool", "config_unplugTurnsOnScreen", wakeUpWhenUnplugged);
		// XResources.setSystemWideReplacement("android", "dimen",
		// "navigation_bar_height",
		// SettingsFragment.NAVBAR_HEIGHT_DEFAULT);
	}

	private static void updatePreferences() {
		// misc
		XSharedPreferences p = Xposed.mXSharedPreferences;
		enableAmbientDisplay = p.getBoolean(ENABLE_AMBIENT_DISPLAY_KEY, true);
		wakeUpWhenUnplugged = p.getBoolean(WAKE_WHEN_UNPLUGGED_KEY, false);
	}
}

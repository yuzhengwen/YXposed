package com.yuzhengwen.yxposed;

import android.content.res.XModuleResources;
import android.content.res.XResources;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;

public class ResourceHook {

	private static boolean wakeUpWhenUnplugged;

	public static void hookResources(InitPackageResourcesParam resparam, XModuleResources modRes) {
		updatePreferences();
		XResources.setSystemWideReplacement("android", "bool", "config_unplugTurnsOnScreen", wakeUpWhenUnplugged);
	    //<bool name="config_showNavigationBar">true</bool>
		XResources.setSystemWideReplacement("android", "dimen", "navigation_bar_height",
				modRes.fwd(R.dimen.navigation_bar_height));
	}

	public static void updatePreferences() {
		wakeUpWhenUnplugged = Xposed.mXSharedPreferences.getBoolean(SettingsFragment.WAKE_WHEN_UNPLUGGED_KEY,
				SettingsFragment.WAKE_WHEN_UNPLUGGED_DEFAULT);

	}
}

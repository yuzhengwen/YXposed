package com.yuzhengwen.yxposed.hooks;

import com.yuzhengwen.yxposed.R;
import com.yuzhengwen.yxposed.Xposed;

import android.content.res.XModuleResources;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;

public class SettingsHook {

	public static void hookResources(InitPackageResourcesParam resparam, final XModuleResources modRes) {
		String SETTINGS_PKG_NAME = Xposed.SETTINGS_PKG_NAME;

		resparam.res.setReplacement(SETTINGS_PKG_NAME, "array", "window_animation_scale_entries",
				modRes.fwd(R.array.window_animation_scale_entries));
		resparam.res.setReplacement(SETTINGS_PKG_NAME, "array", "window_animation_scale_values",
				modRes.fwd(R.array.window_animation_scale_values));
		resparam.res.setReplacement(SETTINGS_PKG_NAME, "array", "transition_animation_scale_entries",
				modRes.fwd(R.array.transition_animation_scale_entries));
		resparam.res.setReplacement(SETTINGS_PKG_NAME, "array", "transition_animation_scale_values",
				modRes.fwd(R.array.transition_animation_scale_values));
		resparam.res.setReplacement(SETTINGS_PKG_NAME, "array", "animator_duration_scale_entries",
				modRes.fwd(R.array.animator_duration_scale_entries));
		resparam.res.setReplacement(SETTINGS_PKG_NAME, "array", "animator_duration_scale_values",
				modRes.fwd(R.array.animator_duration_scale_values));
	}
}

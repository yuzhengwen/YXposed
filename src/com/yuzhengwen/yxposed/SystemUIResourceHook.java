package com.yuzhengwen.yxposed;

import com.android.systemui.statusbar.policy.KeyButtonView;

import android.content.res.XModuleResources;
import android.content.res.XResources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract.StatusUpdates;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;
import de.robv.android.xposed.callbacks.XC_LayoutInflated;

public class SystemUIResourceHook {

	private static int quickSettingsColumnNo;
	private static boolean showCloseAllButton, showDataSwitch, showTicker;
	private static int statusBarHeaderColor;
	private static int clockColor;

	public static void hook(InitPackageResourcesParam resparam, final XModuleResources modRes) {
		updatePreferences();

		// status bar
		resparam.res.setReplacement("com.android.systemui", "color", "status_bar_clock_color", clockColor);
		resparam.res.setReplacement("com.android.systemui", "bool", "enable_ticker", showTicker);

		// status bar header
		resparam.res.setReplacement("com.android.systemui", "integer", "quick_settings_num_columns",
				quickSettingsColumnNo);
		resparam.res.setReplacement("com.android.systemui", "bool", "config_enableDataSwitch", showDataSwitch);
		resparam.res.setReplacement("com.android.systemui", "drawable", "notification_header_bg",
				new XResources.DrawableLoader() {

					@Override
					public Drawable newDrawable(XResources res, int id) throws Throwable {
						return new ColorDrawable(statusBarHeaderColor);
						// return
						// modRes.getDrawable(R.drawable.my_notification_header_bg);

					}
				});

		// recents
		resparam.res.setReplacement("com.android.systemui", "bool", "config_enableCloseAllButton", showCloseAllButton);

		// nav
		resparam.res.hookLayout("com.android.systemui", "layout", "navigation_bar", new XC_LayoutInflated() {
			@Override
			public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
				KeyButtonView menu = (KeyButtonView) liparam.view
						.findViewById(liparam.res.getIdentifier("menu", "id", "com.android.systemui"));
				menu.setVisibility(View.VISIBLE);
			}
		});

	}

	public static void updatePreferences() {
		XSharedPreferences p = Xposed.mXSharedPreferences;

		// status bar
		showTicker = p.getBoolean(SettingsFragment.SHOW_TICKER_KEY, SettingsFragment.SHOW_TICKER_DEFAULT);
		String quickSettingsColumnNoStr = p.getString(SettingsFragment.QUICKSETTINGS_COLUMN_NO_KEY,
				SettingsFragment.QUICKSETTINGS_COLUMN_NO_DEFAULT);
		clockColor = p.getInt(SettingsFragment.CLOCK_COLOR_KEY, SettingsFragment.CLOCK_COLOR_DEFAULT);

		// status bar header
		quickSettingsColumnNo = Integer.parseInt(quickSettingsColumnNoStr);
		showDataSwitch = p.getBoolean(SettingsFragment.SHOW_DATA_SWITCH_KEY, SettingsFragment.SHOW_DATA_SWITCH_DEFAULT);
		statusBarHeaderColor = p.getInt(SettingsFragment.STATUS_BAR_HEADER_COLOR_KEY,
				SettingsFragment.STATUS_BAR_HEADER_COLOR_DEFAULT);

		// recents
		showCloseAllButton = p.getBoolean(SettingsFragment.SHOW_CLOSEALL_BUTTON_KEY,
				SettingsFragment.SHOW_CLOSEALL_BUTTON_DEFAULT);
	}
}

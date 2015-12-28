package com.yuzhengwen.yxposed;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceFragment;

public class SettingsFragment extends PreferenceFragment {

	// DEFAULTS
	public static boolean AM_TO_CLOCK_DEFAULT = true;
	public static boolean CLOCK_SHOW_DAY_DEFAULT = true;
	public static boolean CLOCK_SHOW_DEFAULT = true;
	public static String CLOCK_GRAVITY_DEFAULT = "Center";
	public static int CLOCK_COLOR_DEFAULT = Color.WHITE;
	public static boolean SHOW_TICKER_DEFAULT = true;

	public static String QUICKSETTINGS_COLUMN_NO_DEFAULT = "3";
	public static boolean SHOW_DATA_SWITCH_DEFAULT = true;
	public static int STATUS_BAR_HEADER_COLOR_DEFAULT;

	public static boolean SHOW_CLOSEALL_BUTTON_DEFAULT = true;

	public static boolean MENU_ALWAYS_SHOW_DEFAULT = true;

	public static boolean WAKE_WHEN_UNPLUGGED_DEFAULT = true;

	// KEYS
	public static String AM_TO_CLOCK_KEY = "amToClock";
	public static String CLOCK_SHOW_DAY_KEY = "clockShowDay";
	public static String CLOCK_SHOW_KEY = "clockShow";
	public static String CLOCK_GRAVITY_KEY = "clockGravity";
	public static String CLOCK_COLOR_KEY = "clockColor";
	public static String SHOW_TICKER_KEY = "showTicker";

	public static String QUICKSETTINGS_COLUMN_NO_KEY = "quickSettingsColumnNo";
	public static String SHOW_DATA_SWITCH_KEY = "showDataSwitch";
	public static String STATUS_BAR_HEADER_COLOR_KEY = "statusBarHeaderColor";

	public static String SHOW_CLOSEALL_BUTTON_KEY = "showCloseAllButton";

	public static String MENU_ALWAYS_SHOW_KEY = "menuAlwaysShow";

	public static String WAKE_WHEN_UNPLUGGED_KEY = "wakeUpWhenUnplugged";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		STATUS_BAR_HEADER_COLOR_DEFAULT = getResources().getColor(R.color.statusBarHeaderColorDefault);

		getPreferenceManager().setSharedPreferencesMode(Context.MODE_WORLD_READABLE);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.settings);
	}
}

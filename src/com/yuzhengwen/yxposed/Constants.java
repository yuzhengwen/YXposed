package com.yuzhengwen.yxposed;

import android.graphics.Color;

public interface Constants {


	// status bar------------------------
	public static String AM_TO_CLOCK_KEY = "amToClock";
	public static String CLOCK_SHOW_DAY_KEY = "clockShowDay";
	public static String CLOCK_SHOW_KEY = "clockShow";
	public static String CLOCK_GRAVITY_DEFAULT = "Center";
	public static String CLOCK_GRAVITY_KEY = "clockGravity";
	public static int CLOCK_COLOR_DEFAULT = Color.WHITE;
	public static String CLOCK_COLOR_KEY = "clockColor";
	public static String SHOW_TICKER_KEY = "showTicker";

	// status bar header------------------------
	public static String QUICKSETTINGS_COLUMN_NO_DEFAULT = "3";
	public static String QUICKSETTINGS_COLUMN_NO_KEY = "quickSettingsColumnNo";
	public static String SHOW_DATA_SWITCH_KEY = "showDataSwitch";
	public static String STATUS_BAR_HEADER_COLOR_KEY = "statusBarHeaderColor";
	public static String QUICKSETTINGS_BACKGROUND_COLOR_KEY = "quickSettingsBackgroundColor";

	// notifications------------------------
	public static String NOTIFICATION_MIN_HEIGHT_KEY = "notificationMinHeight";

	// recents------------------------
	public static String SHOW_CLOSEALL_BUTTON_KEY = "showCloseAllButton";

	// navbar------------------------
	public static String MENU_ALWAYS_SHOW_KEY = "menuAlwaysShow";
	public static String DISABLE_SWIPE_KEY = "disableSwipe";
	
	//lockscreen
	public static String SHOW_ALBUM_ART = "showAlbumArt";

	// misc------------------------
	public static String WAKE_WHEN_UNPLUGGED_KEY = "wakeUpWhenUnplugged";
	public static String ENABLE_AMBIENT_DISPLAY_KEY = "enableAmbientDisplay";

}

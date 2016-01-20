package com.yuzhengwen.yxposed;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.TypedValue;

public class SettingsFragment extends PreferenceFragment {

	// status bar------------------------

	// status bar header------------------------
	public static int STATUS_BAR_HEADER_COLOR_DEFAULT;
	
	public static int QUICKSETTINGS_BACKGROUND_COLOR_DEFAULT;

	// notifications------------------------
	public static int NOTIFICATION_MIN_HEIGHT_DEFAULT;

	// recents------------------------

	// navbar------------------------
	public static int NAVBAR_HEIGHT_DEFAULT;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		STATUS_BAR_HEADER_COLOR_DEFAULT = getResources().getColor(R.color.statusBarHeaderColorDefault);
		QUICKSETTINGS_BACKGROUND_COLOR_DEFAULT = getResources().getColor(R.color.statusBarHeaderColorDefault);

		NOTIFICATION_MIN_HEIGHT_DEFAULT = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) 64.0,
				getResources().getDisplayMetrics());
		NAVBAR_HEIGHT_DEFAULT = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) 64.0,
				getResources().getDisplayMetrics());

		getPreferenceManager().setSharedPreferencesMode(Context.MODE_WORLD_READABLE);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.settings);
	}
}

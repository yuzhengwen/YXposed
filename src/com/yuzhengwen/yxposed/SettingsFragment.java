package com.yuzhengwen.yxposed;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceFragment;

public class SettingsFragment extends PreferenceFragment {

	// DEFAULTS
	public static boolean AM_TO_CLOCK_DEFAULT = true;
	public static boolean CLOCK_SHOW_DAY_DEFAULT = true;

	// KEYS
	public static String AM_TO_CLOCK_KEY = "amToClock";
	public static String CLOCK_SHOW_DAY_KEY = "clockShowDay";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getPreferenceManager().setSharedPreferencesMode(Context.MODE_WORLD_READABLE);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.settings);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}
}

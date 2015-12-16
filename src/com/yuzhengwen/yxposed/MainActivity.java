package com.yuzhengwen.yxposed;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class MainActivity extends Activity {

	public static SharedPreferences PREF;

	public static String SYSTEM_UI_PACKAGE_NAME = "com.android.systemui";

	public static String AM_TO_CLOCK = "hook_system_ui_blurred_status_bar_expanded_enabled_pref";
	public static boolean AM_TO_CLOCK_DEFAULT = true;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Display the fragment as the main content.
		getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
	}

}

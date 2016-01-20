package com.yuzhengwen.yxposed;

import java.io.DataOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	public static String SYSTEM_UI_PACKAGE_NAME = "com.android.systemui";
	protected DataOutputStream dos;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Display the fragment as the main content.
		getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
	}

	// mount /system as ro on close
	protected void onStop(Bundle savedInstanceState) throws IOException {
		try {
			dos.writeBytes("\n" + "mount -o remount,ro /system" + "\n");
			dos.writeBytes("\n" + "exit" + "\n");
			dos.flush();
			dos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

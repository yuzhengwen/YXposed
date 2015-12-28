package com.yuzhengwen.yxposed.hooks;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.yuzhengwen.yxposed.SettingsFragment;
import com.yuzhengwen.yxposed.Xposed;

import android.view.View;
import android.widget.TextView;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class SystemUI_StatusBar {

	private static boolean amToClock, clockShowDay, clockShow;

	public static void hook(LoadPackageParam lpparam) {
		try {
			updatePreferences();
			findAndHookMethod("com.android.systemui.statusbar.policy.Clock", lpparam.classLoader, "updateClock",
					new XC_MethodHook() {

						@Override
						protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
							// TODO Auto-generated method stub
							super.beforeHookedMethod(param);
						}

						@Override
						protected void afterHookedMethod(MethodHookParam param) throws Throwable {
							TextView tv = (TextView) param.thisObject;
							if (!clockShow)
								tv.setVisibility(View.GONE);
							else {
								StringBuffer clock = new StringBuffer("");
								SimpleDateFormat format = new SimpleDateFormat("h:mm");
								clock.append(format.format(new Date()));
								if (amToClock) {
									SimpleDateFormat am = new SimpleDateFormat("a");
									clock.append(am.format(new Date()));
								}
								if (clockShowDay) {
									SimpleDateFormat day = new SimpleDateFormat("EEE");
									clock.insert(0, day.format(new Date()) + ", ");
								}
								tv.setText(clock);
							}
						}
					});
		} catch (Exception e) {
			XposedBridge.log(e);
		}
	}

	public static void updatePreferences() {
		clockShow = Xposed.mXSharedPreferences.getBoolean(SettingsFragment.CLOCK_SHOW_KEY,
				SettingsFragment.CLOCK_SHOW_DEFAULT);
		amToClock = Xposed.mXSharedPreferences.getBoolean(SettingsFragment.AM_TO_CLOCK_KEY,
				SettingsFragment.AM_TO_CLOCK_DEFAULT);
		clockShowDay = Xposed.mXSharedPreferences.getBoolean(SettingsFragment.CLOCK_SHOW_DAY_KEY,
				SettingsFragment.CLOCK_SHOW_DAY_DEFAULT);

	}
}

package com.yuzhengwen.yxposed.hooks;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.yuzhengwen.yxposed.Constants;
import com.yuzhengwen.yxposed.SettingsFragment;
import com.yuzhengwen.yxposed.Xposed;

import android.annotation.SuppressLint;
import android.content.res.XModuleResources;
import android.content.res.XResources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class SystemUIHook implements Constants {

	// status bar
	private static boolean amToClock, clockShowDay, clockShow, showTicker;
	private static int clockColor;

	// status bar header
	private static int quickSettingsColumnNo, statusBarHeaderColor, quickSettingsBackgroundColor;

	// recents
	private static boolean showCloseAllButton;

	// notifications
	private static int notificationMinHeight;

	// navbar
	private static boolean menuAlwaysShow, disableSwipe;

	// lockscreen
	private static boolean showAlbumArt;

	// misc
	private static boolean enableAmbientDisplay;

	@SuppressLint("SimpleDateFormat")
	public static void hook(LoadPackageParam lpparam) {
		try {
			updatePreferences();

			// status bar
			XposedHelpers.findAndHookMethod("com.android.systemui.statusbar.policy.Clock", lpparam.classLoader,
					"updateClock", new XC_MethodHook() {
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

			// navigation bar
			/*XposedHelpers.findAndHookMethod("com.android.systemui.statusbar.phone.NavigationBarView",
					lpparam.classLoader, "setMenuVisibility", new XC_MethodHook() {
						@Override
						protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
							param.setResult(menuAlwaysShow);
						}
					});
			 */
			XposedHelpers.findAndHookMethod("com.android.systemui.statusbar.phone.PhoneStatusBar", lpparam.classLoader,
					"shouldDisableNavbarGestures", new XC_MethodHook() {
						@Override
						protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
							param.setResult(disableSwipe);
						}
					});

			// lockscreen

			if (!showAlbumArt) {
				Class localClass = XposedHelpers.findClass("com.android.systemui.statusbar.phone.PhoneStatusBar",
						lpparam.classLoader);
				Class[] arrayOfClass = new Class[1];
				arrayOfClass[0] = Boolean.TYPE;
				XposedBridge.hookMethod(
						XposedHelpers.findMethodBestMatch(localClass, "updateMediaMetaData", arrayOfClass),
						new XC_MethodHook() {
							protected void beforeHookedMethod(
									XC_MethodHook.MethodHookParam paramAnonymousMethodHookParam) throws Throwable {
								XposedHelpers.setObjectField(paramAnonymousMethodHookParam.thisObject, "mMediaMetadata",
										null);
							}
						});
			}

		} catch (Exception e) {
			XposedBridge.log(e);
		}
	}

	public static void hookResources(InitPackageResourcesParam resparam, final XModuleResources modRes) {

		final String SYSTEMUI_PKG_NAME = Xposed.SYSTEMUI_PKG_NAME;

		updatePreferences();

		// status bar
		resparam.res.setReplacement(SYSTEMUI_PKG_NAME, "color", "status_bar_clock_color", clockColor);
		resparam.res.setReplacement(SYSTEMUI_PKG_NAME, "bool", "enable_ticker", showTicker);

		// status bar header
		resparam.res.setReplacement(SYSTEMUI_PKG_NAME, "integer", "quick_settings_num_columns", quickSettingsColumnNo);
		resparam.res.setReplacement(SYSTEMUI_PKG_NAME, "color", "system_primary_color", quickSettingsBackgroundColor);
		resparam.res.setReplacement(SYSTEMUI_PKG_NAME, "drawable", "notification_header_bg",
				new XResources.DrawableLoader() {
					@Override
					public Drawable newDrawable(XResources res, int id) throws Throwable {
						return new ColorDrawable(statusBarHeaderColor);
						// return
						// modRes.getDrawable(R.drawable.my_notification_header_bg);
					}
				});

		// notifications
		resparam.res.setReplacement(SYSTEMUI_PKG_NAME, "dimen", "notification_min_height", notificationMinHeight);

		// recents
		resparam.res.setReplacement(SYSTEMUI_PKG_NAME, "bool", "config_enableCloseAllButton", showCloseAllButton);

		// nav
		/*resparam.res.hookLayout(SYSTEMUI_PKG_NAME, "layout", "navigation_bar", new XC_LayoutInflated() {
			@Override
			public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
				KeyButtonView menu = (KeyButtonView) liparam.view
						.findViewById(liparam.res.getIdentifier("menu", "id", SYSTEMUI_PKG_NAME));
				menu.setVisibility(View.VISIBLE);
			}
		});*/

		// misc
		if (enableAmbientDisplay) {
			resparam.res.setReplacement(SYSTEMUI_PKG_NAME, "bool", "doze_display_state_supported", true);
			resparam.res.setReplacement(SYSTEMUI_PKG_NAME, "bool", "doze_pulse_on_pick_up", true);
		}
	}

	public static void updatePreferences() {

		XSharedPreferences p = Xposed.mXSharedPreferences;

		// status bar
		clockShow = p.getBoolean(CLOCK_SHOW_KEY, true);
		amToClock = p.getBoolean(AM_TO_CLOCK_KEY, true);
		clockShowDay = p.getBoolean(CLOCK_SHOW_DAY_KEY, true);
		showTicker = p.getBoolean(SHOW_TICKER_KEY, true);
		clockColor = p.getInt(CLOCK_COLOR_KEY, CLOCK_COLOR_DEFAULT);

		// status bar header
		quickSettingsColumnNo = Integer.parseInt(p.getString(QUICKSETTINGS_COLUMN_NO_KEY, "3"));
		statusBarHeaderColor = p.getInt(STATUS_BAR_HEADER_COLOR_KEY, SettingsFragment.STATUS_BAR_HEADER_COLOR_DEFAULT);
		quickSettingsBackgroundColor = p.getInt(QUICKSETTINGS_BACKGROUND_COLOR_KEY,
				SettingsFragment.QUICKSETTINGS_BACKGROUND_COLOR_DEFAULT);

		// notification
		notificationMinHeight = p.getInt(NOTIFICATION_MIN_HEIGHT_KEY, SettingsFragment.NOTIFICATION_MIN_HEIGHT_DEFAULT);

		// recents
		showCloseAllButton = p.getBoolean(SHOW_CLOSEALL_BUTTON_KEY, true);

		// navigation bar
		menuAlwaysShow = p.getBoolean(MENU_ALWAYS_SHOW_KEY, true);
		disableSwipe = p.getBoolean(DISABLE_SWIPE_KEY, false);

		// lockscreen
		showAlbumArt = p.getBoolean(SHOW_ALBUM_ART, true);

		// misc
		enableAmbientDisplay = p.getBoolean(ENABLE_AMBIENT_DISPLAY_KEY, true);
	}
}

package org.android10.viewgroupperformance.activity;

import android.content.Context;
import android.content.SharedPreferences;

import org.android10.gintonic.annotation.NoTrace;
import org.android10.gintonic.aspect.Constants;

/**
 * Utilities and constants related to app preferences.
 */
public class PrefUtils  {
    public final static String SHARED_PREFERENCE="MyPreferences";
	private static final String DEBUG_PREF = "DebugPref";

    @NoTrace
    public static String getDebugPref(final Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return sp.getString(DEBUG_PREF, Constants.OFF);
    }

    @NoTrace
    public static void setDebugPref(final Context context, String value) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        sp.edit().putString(DEBUG_PREF, value).apply();
    }
}
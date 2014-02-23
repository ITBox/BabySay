package cc.itbox.babysay.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * 
 * @author malinkang 2014-2-22 下午2:38:05
 * 
 */
public class PreferenceUtil {
	
	private static SharedPreferences.Editor editor = null;
	private static SharedPreferences sharedPreferences = null;

	private final static String FIRST_START = "first_start";

	private PreferenceUtil() {

	}

	public static boolean isFirstStart(Context context) {
		return getSharedPreferences(context, FIRST_START, true);
	}

	public static void editFirstStart(Context context) {
		setEditor(context, FIRST_START, false);
	}

	// ======================================================
	// ========================================================
	public static int getSharedPreferences(Context context, String key,
			int defValue) {
		return getSharedPreferencesObject(context).getInt(key, defValue);
	}

	public static long getSharedPreferences(Context context, String key,
			long defValue) {
		return getSharedPreferencesObject(context).getLong(key, defValue);
	}

	public static Boolean getSharedPreferences(Context context, String key,
			Boolean defValue) {
		return getSharedPreferencesObject(context).getBoolean(key, defValue);
	}

	public static String getSharedPreferences(Context context, String key,
			String defValue) {
		return getSharedPreferencesObject(context).getString(key, defValue);
	}

	private static SharedPreferences getSharedPreferencesObject(
			Context paramContext) {
		if (sharedPreferences == null)
			sharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(paramContext);
		return sharedPreferences;
	}

	// /=====================================================

	// /=====================================================

	private static SharedPreferences.Editor getEditorObject(Context context) {
		if (editor == null)
			editor = PreferenceManager.getDefaultSharedPreferences(context)
					.edit();
		return editor;
	}

	public static void setEditor(Context context, String key, int value) {
		getEditorObject(context).putInt(key, value).commit();
	}

	public static void setEditor(Context context, String key, long value) {
		getEditorObject(context).putLong(key, value).commit();
	}

	public static void setEditor(Context context, String key, Boolean value) {
		getEditorObject(context).putBoolean(key, value).commit();
	}

	public static void setEditor(Context context, String key, String value) {
		getEditorObject(context).putString(key, value).commit();
	}

}

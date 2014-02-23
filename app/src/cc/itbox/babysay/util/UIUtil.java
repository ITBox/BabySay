package cc.itbox.babysay.util;

import android.app.Activity;
import android.view.View;

public class UIUtil {
	/**
	 * http://android-activities.blogspot.jp/2013/11/cleaner-view-casting-with-
	 * generics.html
	 * 
	 * @param activity
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final <E extends View> E getView(Activity activity, int id) {
		try {
			return (E) activity.findViewById(id);
		} catch (ClassCastException ex) {
			throw ex;
		}
	}
 
	@SuppressWarnings("unchecked")
	public static final <E extends View> E getView(View view, int id) {
		try {
			return (E) view.findViewById(id);
		} catch (ClassCastException ex) {
			throw ex;
		}
	}

}

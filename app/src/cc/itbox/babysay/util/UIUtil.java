package cc.itbox.babysay.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

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
	public final static <E extends View> E getView(Activity activity, int id) {
		try {
			return (E) activity.findViewById(id);
		} catch (ClassCastException ex) {
			throw ex;
		}
	}

	@SuppressWarnings("unchecked")
	public final static <E extends View> E getView(View view, int id) {
		try {
			return (E) view.findViewById(id);
		} catch (ClassCastException ex) {
			throw ex;
		}
	}

	public final static void showLongToast(Context context, String str) {
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}

	public final static void showShortToast(Context context, String str) {
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}

}

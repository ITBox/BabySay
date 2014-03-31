package cc.itbox.babysay.iface;

import android.content.Intent;

/**
 * RegisterFragment can't call onActivity ,so define this interface deliver
 * result from RegisterOrLoginActivity
 * 
 * @author malinkang 2014-3-28
 * 
 */

public interface OnActivityResultReturnListener {
	public void onActivityresultReturn(int requestCode, int resultCode,
			Intent data);
}

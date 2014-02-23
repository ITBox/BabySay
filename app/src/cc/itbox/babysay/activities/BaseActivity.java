package cc.itbox.babysay.activities;

import cc.itbox.babysay.R;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

/**
 * 基类Activity
 * 
 * @author malinkang 2014-2-22 下午3:54:20
 * 
 */

public class BaseActivity extends ActionBarActivity {

	protected ActionBar mActionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActionBar = getSupportActionBar();
		mActionBar.setBackgroundDrawable(
				getResources().getDrawable(
						R.drawable.ab_solid_custom_pink_inverse_holo));
	}
}

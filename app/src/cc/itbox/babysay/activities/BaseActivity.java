package cc.itbox.babysay.activities;

import cc.itbox.babysay.R;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * 基类Activity
 * 
 * @author malinkang 2014-2-22 下午3:54:20
 * 
 */

public class BaseActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置ActionBar背景
		getSupportActionBar().setBackgroundDrawable(
				getResources().getDrawable(
						R.drawable.ab_solid_custom_pink_inverse_holo));
	}
}

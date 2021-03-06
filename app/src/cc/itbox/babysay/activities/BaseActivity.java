package cc.itbox.babysay.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import cc.itbox.babysay.R;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 基类Activity
 * 
 * @author malinkang 2014-2-22 下午3:54:20
 * 
 */

public class BaseActivity extends ActionBarActivity {

	protected ActionBar mActionBar;
	protected FragmentManager mSupportFragmentManager;
	protected FragmentTransaction mFragmentTransaction;
	protected ImageLoader mLoader;
	protected Context mCtx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCtx = this;
		mLoader = ImageLoader.getInstance();
		mActionBar = getSupportActionBar();
		mActionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.ab_solid_custom_pink_inverse_holo));
		mSupportFragmentManager = getSupportFragmentManager();
		mFragmentTransaction = mSupportFragmentManager.beginTransaction();
	}

	public void startActivity(Class<? extends Activity> activity) {
		Intent intent = new Intent(mCtx, activity);
		startActivity(intent);
	}
}

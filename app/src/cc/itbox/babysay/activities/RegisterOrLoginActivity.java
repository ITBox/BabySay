package cc.itbox.babysay.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import cc.itbox.babysay.R;
import cc.itbox.babysay.fragments.LoginFragment;
import cc.itbox.babysay.fragments.RegisterFragment;
import cc.itbox.babysay.util.UIUtil;

/**
 * 
 * @author malinkang 2014-2-27
 * 
 */

public class RegisterOrLoginActivity extends BaseActivity implements
		ActionBar.TabListener {

	private ViewPager mViewPager;

	private final String[] mTitles = { "登陆", "注册" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_or_login);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mViewPager = UIUtil.getView(this, R.id.pager);

		// Specify that we will be displaying tabs in the action bar.
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		LoginOrRegisterPagerAdapter mAdapter = new LoginOrRegisterPagerAdapter(
				mSupportFragmentManager);
		mViewPager.setAdapter(mAdapter);
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						mActionBar.setSelectedNavigationItem(position);
					}
				});
		for (int i = 0; i < mAdapter.getCount(); i++) {

			mActionBar.addTab(mActionBar.newTab()
					.setText(mAdapter.getPageTitle(i)).setTabListener(this));
		}

	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the primary sections of the app.
	 */
	public class LoginOrRegisterPagerAdapter extends FragmentPagerAdapter {

		public LoginOrRegisterPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			switch (i) {
			case 0:

				return new LoginFragment();

			default:

				return new RegisterFragment();
			}
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return mTitles[position];
		}
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

}

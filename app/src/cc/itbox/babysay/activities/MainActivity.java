package cc.itbox.babysay.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import cc.itbox.babysay.R;
import cc.itbox.babysay.fragments.DiscoverFragment;
import cc.itbox.babysay.fragments.MainPageFragment;
import cc.itbox.babysay.fragments.NavigationDrawerFragment;
import cc.itbox.babysay.fragments.SettingFragment;
import cc.itbox.babysay.fragments.UserCenterFragment;

/**
 * 主页面
 * 
 * @author baoyz
 * 
 * 2014-2-22 下午10:19:50
 * 
 */
public class MainActivity extends BaseActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * 导航菜单
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * 当前标题
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// 初始化导航菜单
		mNavigationDrawerFragment.init(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));

	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// 更换主页面的Fragment
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						BaseFragment.newInstance(position)).commit();
	}

	/**
	 * 当切换Fragment的时候
	 * 
	 * @param number
	 */
	public void onSectionAttached(int number) {
		mTitle = mNavigationDrawerFragment.getItemTitles()[number];
	}

	/**
	 * 恢复ActionBar，设置当前标题
	 */
	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// 创建Actionbar菜单
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Actionbar菜单选择事件
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 主页面Fragment基类
	 */
	public static class BaseFragment extends Fragment {

		/**
		 * 子页面
		 */
		private static BaseFragment[] mFragments;
		
		static{
			mFragments = new BaseFragment[] { new UserCenterFragment(),
					new MainPageFragment(), new DiscoverFragment(),
					new SettingFragment() };
		}

		/**
		 * 位置编号
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * 根据位置编号返回相应的Fragment
		 */
		public static BaseFragment newInstance(int sectionNumber) {
			BaseFragment fragment = mFragments[sectionNumber];
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public BaseFragment() {
			
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}
	}

}

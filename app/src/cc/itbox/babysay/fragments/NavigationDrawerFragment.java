package cc.itbox.babysay.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cc.itbox.babysay.R;

/**
 * 导航菜单
 * 
 * @author baoyz
 * 
 *         2014-2-22 下午11:10:20
 * 
 */
public class NavigationDrawerFragment extends Fragment {

	/**
	 * 选中的位置
	 */
	private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

	/**
	 * 用户是否使用过导航菜单，如果第一次打开软件，会默认显示导航菜单，如果用户关闭并打开过导航菜单，则下次打开的时候不显示导航菜单
	 */
	private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

	/**
	 * 导航回调
	 */
	private NavigationDrawerCallbacks mCallbacks;

	/**
	 * 与Actionbar交互
	 */
	private ActionBarDrawerToggle mDrawerToggle;

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerListView;
	private View mFragmentContainerView;

	private int mCurrentSelectedPosition = 1;
	private boolean mFromSavedInstanceState;
	private boolean mUserLearnedDrawer;
	private String[] mItemTitles;

	public NavigationDrawerFragment() {
		mItemTitles = new String[] { "个人中心", "主页", "发现", "消息", "设置" };
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 获取用户是否使用过导航菜单
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

		if (savedInstanceState != null) {
			mCurrentSelectedPosition = savedInstanceState
					.getInt(STATE_SELECTED_POSITION);
			mFromSavedInstanceState = true;
		}

		// 默认选择第1个，主页面
		selectItem(mCurrentSelectedPosition);

	}

	@Override
	public void onStart() {
		super.onStart();
		// 启用菜单
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mDrawerListView = (ListView) inflater.inflate(
				R.layout.fragment_navigation_drawer, container, false);
		mDrawerListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						selectItem(position);
					}
				});
		mDrawerListView.setAdapter(new NavigationItemAdapter());
		mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
		return mDrawerListView;
	}

	public boolean isDrawerOpen() {
		return mDrawerLayout != null
				&& mDrawerLayout.isDrawerOpen(mFragmentContainerView);
	}

	/**
	 * 初始化
	 * 
	 * @param fragmentId
	 * @param drawerLayout
	 */
	public void init(int fragmentId, DrawerLayout drawerLayout) {
		mFragmentContainerView = getActivity().findViewById(fragmentId);
		mDrawerLayout = drawerLayout;

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		// 与Actionbar交互
		mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout,
				R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				if (!isAdded()) {
					return;
				}
				// 更新ActionBar菜单
				getActivity().supportInvalidateOptionsMenu();
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				if (!isAdded()) {
					return;
				}

				if (!mUserLearnedDrawer) {
					// 用户已经使用过导航菜单
					mUserLearnedDrawer = true;
					SharedPreferences sp = PreferenceManager
							.getDefaultSharedPreferences(getActivity());
					sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true)
							.commit();
				}
				// 更新ActionBar菜单
				getActivity().supportInvalidateOptionsMenu();
			}
		};

		// 如果用户没有使用过导航菜单，默认打开导航菜单
		if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
			mDrawerLayout.openDrawer(mFragmentContainerView);
		}

		// Actionbar同步状态
		mDrawerLayout.post(new Runnable() {
			@Override
			public void run() {
				mDrawerToggle.syncState();
			}
		});

		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	/**
	 * 选择当前位置项
	 * 
	 * @param position
	 */
	private void selectItem(int position) {
		mCurrentSelectedPosition = position;
		if (mDrawerListView != null) {
			mDrawerListView.setItemChecked(position, true);
		}
		if (mDrawerLayout != null) {
			mDrawerLayout.closeDrawer(mFragmentContainerView);
		}
		if (mCallbacks != null) {
			mCallbacks.onNavigationDrawerItemSelected(position);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallbacks = (NavigationDrawerCallbacks) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(
					"Activity must implement NavigationDrawerCallbacks.");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// 创建显示导航菜单时的Actionbar菜单
		if (mDrawerLayout != null && isDrawerOpen()) {
			inflater.inflate(R.menu.global, menu);
			showGlobalContextActionBar();
		}
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Actionbar菜单选择事件
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 
	 */
	private void showGlobalContextActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setTitle(R.string.app_name);
	}

	private ActionBar getActionBar() {
		return ((ActionBarActivity) getActivity()).getSupportActionBar();
	}

	public String[] getItemTitles() {
		return mItemTitles;
	}

	/**
	 * 导航菜单回调
	 * 
	 * @author baoyz
	 * 
	 *         2014-2-22 下午11:47:10
	 * 
	 */
	public static interface NavigationDrawerCallbacks {
		/**
		 * 当导航切换之后
		 * 
		 * @param position
		 */
		void onNavigationDrawerItemSelected(int position);
	}

	/**
	 * 导航菜单项适配器
	 * 
	 * @author baoyz
	 * 
	 *         2014-2-22 下午11:47:49
	 * 
	 */
	private class NavigationItemAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mItemTitles.length;
		}

		@Override
		public Object getItem(int position) {
			return mItemTitles[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			switch (position) {
			case 0:
				View view = View.inflate(getActivity(),
						R.layout.navigation_user, null);
				return view;
			}
			View view = View.inflate(getActivity(),
					android.R.layout.simple_list_item_1, null);
			TextView tv = (TextView) view.findViewById(android.R.id.text1);
			tv.setText(mItemTitles[position]);
			tv.setPadding(20, 0, 0, 0);
			tv.setCompoundDrawablesWithIntrinsicBounds(
					R.drawable.ic_content_edit, 0, 0, 0);
			return view;
		}

	}
}

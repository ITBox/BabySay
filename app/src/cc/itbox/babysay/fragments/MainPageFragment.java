package cc.itbox.babysay.fragments;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.Toast;
import cc.itbox.babysay.R;
import cc.itbox.babysay.activities.MainActivity.BaseFragment;
import cc.itbox.babysay.adapter.DMListAdapter;
import cc.itbox.babysay.util.LogUtility;

/**
 * 主页页面
 * 
 * @author baoyz
 * 
 *         2014-2-22 下午10:46:35
 * 
 */
public class MainPageFragment extends BaseFragment implements
		OnRefreshListener, OnScrollListener {

	private PullToRefreshLayout mPullToRefreshLayout;
	private ListView mListView;
	private DMListAdapter mAdapter;
	private boolean hasMore;
	private boolean isLoad;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main_page, container,
				false);
		mListView = (ListView) view.findViewById(R.id.lv_dm);
		// 设置适配器
		mAdapter = new DMListAdapter(getActivity());
		mListView.setAdapter(mAdapter);
		hasMore = true;
		mListView.setOnScrollListener(this);
		mPullToRefreshLayout = new PullToRefreshLayout(getActivity());
		// 设置下拉刷新
		ActionBarPullToRefresh.from(getActivity())
				.insertLayoutInto((ViewGroup) view)
				.theseChildrenArePullable(R.id.lv_dm, android.R.id.empty)
				.listener(this).setup(mPullToRefreshLayout);
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// 创建显示导航菜单时的Actionbar菜单
		inflater.inflate(R.menu.main_page, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_refresh:
			// 刷新
			mPullToRefreshLayout.setRefreshing(true);
			break;
		case R.id.action_new_content:
			// 新建
			Toast.makeText(getActivity(), "new", 1).show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onRefreshStarted(View view) {

		// 开始下拉刷新
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (isLoad) {
					// 加载数据
				} else {
					// 刷新数据
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				// 设置下拉刷新完成
				mPullToRefreshLayout.setRefreshComplete();
				if (isLoad) {
					hasMore = false;
					isLoad = false;
				}
				if (getView() != null) {
				}
			}
		}.execute();
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		switch (scrollState) {
		case OnScrollListener.SCROLL_STATE_IDLE:
			// 停止滑动
			if (hasMore
					&& view.getLastVisiblePosition() == (view.getCount() - 1)) {
				// 滚动到最后一条记录，开始加载
				isLoad = true;
				mPullToRefreshLayout.setRefreshing(true);
			}
			break;
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

	}
}

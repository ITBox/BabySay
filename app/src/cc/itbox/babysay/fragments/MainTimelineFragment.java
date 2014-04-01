package cc.itbox.babysay.fragments;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import cc.itbox.babysay.R;
import cc.itbox.babysay.activities.MainActivity.BaseFragment;
import cc.itbox.babysay.adapter.TimelineListAdapter;

/**
 * 主页页面
 * 
 * @author baoyz
 * 
 *         2014-2-22 下午10:46:35
 * 
 */
public class MainTimelineFragment extends BaseFragment {

	private StickyListHeadersListView mListView;

	private TimelineListAdapter mAdapter;
	private SwipeRefreshLayout mSwipeRefreshLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main_timeline, container,
				false);
		mSwipeRefreshLayout = (SwipeRefreshLayout) view
				.findViewById(R.id.refreshLayout);

		mListView = (StickyListHeadersListView) view.findViewById(R.id.lv_dm);
		mAdapter = new TimelineListAdapter(getActivity());
		mListView.setAdapter(mAdapter);
		mSwipeRefreshLayout.setColorScheme(R.color.holo_green_dark,
				R.color.holo_orange_dark, R.color.holo_blue_dark,
				R.color.holo_red_dark);
		mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {

				new GetLinks().execute();
			}
		});
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main_page, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_refresh:
			break;
		case R.id.action_new_content:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public class GetLinks extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			mSwipeRefreshLayout.setRefreshing(false);
		}
	}
}

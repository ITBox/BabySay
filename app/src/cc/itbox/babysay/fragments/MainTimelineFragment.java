package cc.itbox.babysay.fragments;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import cc.itbox.babysay.AppContext;
import cc.itbox.babysay.R;
import cc.itbox.babysay.activities.MainActivity.BaseFragment;
import cc.itbox.babysay.adapter.TimelineListAdapter;
import cc.itbox.babysay.bean.Timeline;
import cc.itbox.babysay.util.ReadUtil;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.content.ContentProvider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 主页页面
 * 
 * @author baoyz
 * 
 *         2014-2-22 下午10:46:35
 * 
 */
public class MainTimelineFragment extends BaseFragment implements
		LoaderCallbacks<Cursor> {

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
		View view = inflater.inflate(R.layout.fragment_main_timeline,
				container, false);
		mSwipeRefreshLayout = (SwipeRefreshLayout) view
				.findViewById(R.id.refreshLayout);

		mListView = (StickyListHeadersListView) view.findViewById(R.id.lv_dm);
		mAdapter = new TimelineListAdapter(mActThis, null);
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
		mListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				mSwipeRefreshLayout
						.setEnabled(!(mListView.getListChildCount() > 0 && (mListView
								.getFirstVisiblePosition() > 0 || mListView
								.getListChildAt(0).getTop() < mListView
								.getPaddingTop())));
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

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		return new CursorLoader(getActivity(), ContentProvider.createUri(
				Timeline.class, null), null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		if (cursor != null && cursor.getCount() == 0) {
			String json = ReadUtil.readFile(mActThis, "timeline.json");
			Gson mGson = new Gson();
			ArrayList<Timeline> timelines = mGson.fromJson(json,
					new TypeToken<ArrayList<Timeline>>() {
					}.getType());
			ActiveAndroid.beginTransaction();
			try {
				for (int i = 0; i < timelines.size(); i++) {
					timelines.get(i).setUser_json(
							AppContext.getGson().toJson(
									timelines.get(i).getUser()));
					timelines.get(i).save();
				}
				ActiveAndroid.setTransactionSuccessful();
			} finally {
				ActiveAndroid.endTransaction();
			}
		} else {
			mAdapter.changeCursor(cursor);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		mAdapter.changeCursor(null);
	}
}

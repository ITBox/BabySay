package cc.itbox.babysay.adapter;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import cc.itbox.babysay.AppContext;
import cc.itbox.babysay.R;
import cc.itbox.babysay.bean.Timeline;
import cc.itbox.babysay.bean.User;
import cc.itbox.babysay.db.TimelineTable;
import cc.itbox.babysay.media.PlayerEngineImpl;
import cc.itbox.babysay.media.PlayerEngineListener;
import cc.itbox.babysay.ui.CircleImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 动态信息列表适配器
 * 
 * @author baoyz
 * 
 *         2014-2-23 下午4:42:54
 * 
 */
public class TimelineListAdapter extends CursorAdapter implements
		StickyListHeadersAdapter {

	private final LayoutInflater mInflater;
	private final Activity mActivity;
	private final ImageLoader mImageLoader;
	private final PlayerEngineImpl mPlayerEngineImpl;

	public TimelineListAdapter(Context context, Cursor cursor) {
		super(context, cursor);
		mActivity = (Activity) context;
		mImageLoader = ImageLoader.getInstance();
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mPlayerEngineImpl = AppContext.getInstance().getmPlayerEngineImpl();

	}

	class ViewHolder {
		ImageView contentIv;
		ImageView playIv;
		ProgressBar loading;

	}

	class HeaderViewHolder {
		CircleImageView avatarIv;
		TextView nicknameTv;
		TextView createTimeTv;
	}

	private User getUserForPosition(int position) {
		User user = null;
		Cursor cursor = getCursor();

		if (cursor != null) {
			cursor.moveToPosition(position);
			String json = cursor.getString(cursor
					.getColumnIndex(TimelineTable.COLUMN_USER_JSON));
			user = Timeline.getUserFromJson(json);
		}

		return user;
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		HeaderViewHolder holder = null;
		if (convertView == null) {
			holder = new HeaderViewHolder();
			convertView = mInflater.inflate(
					R.layout.layout_timeline_header_item, parent, false);
			holder.avatarIv = (CircleImageView) convertView
					.findViewById(R.id.iv_avatar);
			holder.nicknameTv = (TextView) convertView
					.findViewById(R.id.tv_nickname);
			holder.createTimeTv = (TextView) convertView
					.findViewById(R.id.tv_create_time);
			convertView.setTag(holder);
		} else {
			holder = (HeaderViewHolder) convertView.getTag();
		}

		User user = getUserForPosition(position);
		if (user != null) {
			mImageLoader.displayImage(user.getAvatar(), holder.avatarIv);
			holder.nicknameTv.setText(user.getNickname());
		}
		return convertView;
	}

	@Override
	public long getHeaderId(int position) {
		return position;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return mInflater.inflate(R.layout.layout_timeline_content_item, null);
	}

	@Override
	public void bindView(View view, Context context, final Cursor cursor) {
		ViewHolder holder = (ViewHolder) view.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.contentIv = (ImageView) view.findViewById(R.id.iv_content);

			holder.playIv = (ImageView) view.findViewById(R.id.iv_play);
			view.setTag(holder);
		}
		mImageLoader.displayImage(cursor.getString(cursor
				.getColumnIndex(TimelineTable.COLUMN_PIC)), holder.contentIv);
		final MyPlayerEngineListener listener = new MyPlayerEngineListener(
				holder);
		final String url = cursor.getString(cursor
				.getColumnIndex(TimelineTable.COLUMN_AUDIO));
		holder.playIv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPlayerEngineImpl.setListener(listener);
				mPlayerEngineImpl.play(url);
			}
		});

	}

	private class MyPlayerEngineListener implements PlayerEngineListener {
		private final ViewHolder mHolder;

		public MyPlayerEngineListener(ViewHolder holder) {
			// TODO Auto-generated constructor stub
			mHolder = holder;
		}

		@Override
		public void onTrackStreamError() {

		}

		@Override
		public void onTrackStop() {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean onTrackStart() {

			return true;
		}

		@Override
		public void onTrackProgress(int seconds) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTrackPause() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTrackChanged(int totalSeconds) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTrackBuffering(int percent) {
			// TODO Auto-generated method stub

		}
	};
}

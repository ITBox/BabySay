package cc.itbox.babysay.adapter;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import cc.itbox.babysay.R;
import cc.itbox.babysay.bean.Timeline;
import cc.itbox.babysay.bean.User;
import cc.itbox.babysay.db.TimelineTable;
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

	public TimelineListAdapter(Context context, Cursor cursor) {
		super(context, cursor);
		mActivity = (Activity) context;
		mImageLoader = ImageLoader.getInstance();
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	class ViewHolder {
		ImageView contentIv;
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
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = (ViewHolder) view.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.contentIv = (ImageView) view.findViewById(R.id.iv_content);
			LayoutParams params = (LayoutParams) holder.contentIv
					.getLayoutParams();
			params.height = (mActivity.getWindowManager().getDefaultDisplay()
					.getWidth() - 32);
			params.width = params.height;
			holder.contentIv.setLayoutParams(params);
			view.setTag(holder);
		}

		mImageLoader.displayImage(cursor.getString(cursor
				.getColumnIndex(TimelineTable.COLUMN_PIC)), holder.contentIv);

	}
}

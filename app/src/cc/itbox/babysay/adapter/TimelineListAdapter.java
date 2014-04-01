package cc.itbox.babysay.adapter;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import cc.itbox.babysay.R;

/**
 * 动态信息列表适配器
 * 
 * @author baoyz
 * 
 *         2014-2-23 下午4:42:54
 * 
 */
public class TimelineListAdapter extends BaseAdapter implements
		StickyListHeadersAdapter {

	private final Activity mContext;
	private final LayoutInflater mInflater;

	public TimelineListAdapter(Activity context) {
		mContext = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * 刷新
	 */
	public void refresh() {

	}

	public void load() {

	}

	@Override
	public int getCount() {
		return 100;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.layout_timeline_content_item, null);
			holder = new ViewHolder();
			holder.contentIv = (ImageView) convertView
					.findViewById(R.id.iv_content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		LayoutParams params = (LayoutParams) holder.contentIv.getLayoutParams();
		params.height = (mContext.getWindowManager().getDefaultDisplay()
				.getWidth());
		params.width = params.height;
		holder.contentIv.setLayoutParams(params);
		return convertView;
	}

	class ViewHolder {
		ImageView contentIv;
	}

	class HeaderViewHolder {

	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		HeaderViewHolder holder = null;
		if (convertView == null) {
			holder = new HeaderViewHolder();
			convertView = mInflater.inflate(
					R.layout.layout_timeline_header_item, parent, false);

			convertView.setTag(holder);
		} else {
			holder = (HeaderViewHolder) convertView.getTag();
		}
		return convertView;
	}

	@Override
	public long getHeaderId(int position) {

		return position;
	}

	// @Override
	// public void onClick(View v) {
	// mClickPlayBean = (DMBean) v.getTag();
	// if (mClickPlayBean == null) {
	// return;
	// }
	// switch (v.getId()) {
	// case R.id.iv_play:
	// // 播放音频
	//
	// break;
	// case R.id.iv_praise:
	// // 赞
	// mClickPlayBean.setPraise(!mClickPlayBean.isPraise());
	// notifyDataSetChanged();
	// break;
	// default:
	// break;
	// }
	// }

}

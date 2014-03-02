package cc.itbox.babysay.adapter;

import java.util.ArrayList;
import java.util.List;

import org.holoeverywhere.widget.ProgressBar;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cc.itbox.babysay.R;
import cc.itbox.babysay.bean.DMBean;
import cc.itbox.babysay.media.AudioPlayer;
import cc.itbox.babysay.media.AudioPlayer.AudioPlayerListener;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 动态信息列表适配器
 * 
 * @author baoyz
 * 
 *         2014-2-23 下午4:42:54
 * 
 */
public class DMListAdapter extends BaseAdapter implements OnClickListener,
		AudioPlayerListener {

	private List<DMBean> mList;
	private Activity mContext;
	private DMBean mCurrentPlayBean;
	private AudioPlayer mPlayer;

	public DMListAdapter(Activity context) {
		mContext = context;
		mPlayer = AudioPlayer.getInstance();
		mPlayer.setmListener(this);
		// 初始化数据
		mList = new ArrayList<DMBean>();
		for (int i = 0; i < 20; i++) {
			mList.add(new DMBean());
		}
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
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.list_item_dm, null);
			convertView.setTag(new ViewHolder(convertView));
		}
		// 绑定数据
		doBindView(mList.get(position), convertView);
		return convertView;
	}

	/**
	 * 绑定View数据
	 * 
	 * @param convertView
	 */
	private void doBindView(DMBean bean, View view) {
		ViewHolder holder = (ViewHolder) view.getTag();
		// 显示图片内容
		ImageLoader.getInstance().displayImage(bean.getImageUri(),
				holder.iv_image);
		// 设置用户信息
		// 设置操作栏信息
		holder.iv_praise
				.setImageResource(bean.isPraise() ? R.drawable.selector_dm_haspraise
						: R.drawable.selector_dm_nopraise);
		holder.iv_praise.setTag(bean);
	}

	/**
	 * View缓存
	 * 
	 * @author baoyz
	 * 
	 *         2014-3-2 下午12:14:41
	 * 
	 */
	class ViewHolder {

		public ImageView iv_head;
		public ImageView iv_image;
		public TextView tv_nickname;
		public TextView tv_time;
		public ProgressBar pb_play;
		public ImageView iv_play;
		public ImageView iv_praise;
		public ImageView iv_comment;
		public ImageView iv_share;
		public ImageView iv_more;

		public ViewHolder(View view) {
			this.iv_head = (ImageView) view.findViewById(R.id.iv_head);
			this.iv_image = (ImageView) view.findViewById(R.id.iv_image);
			this.tv_nickname = (TextView) view.findViewById(R.id.tv_nickname);
			this.tv_time = (TextView) view.findViewById(R.id.tv_time);
			this.iv_play = (ImageView) view.findViewById(R.id.iv_play);
			this.iv_praise = (ImageView) view.findViewById(R.id.iv_praise);
			this.iv_comment = (ImageView) view.findViewById(R.id.iv_comment);
			this.iv_share = (ImageView) view.findViewById(R.id.iv_share);
			this.iv_more = (ImageView) view.findViewById(R.id.iv_more);
			this.pb_play = (ProgressBar) view.findViewById(R.id.pd_play);

			// 设置图片内容为正方形
			LayoutParams params = this.iv_image.getLayoutParams();
			params.height = (int) (mContext.getWindowManager()
					.getDefaultDisplay().getWidth() - mContext.getResources()
					.getDimension(R.dimen.dm_horizontal_margin) * 2);
			params.width = params.height;
			this.iv_image.setLayoutParams(params);

			// 设置事件
			this.iv_play.setOnClickListener(DMListAdapter.this);
			this.iv_praise.setOnClickListener(DMListAdapter.this);
			this.iv_comment.setOnClickListener(DMListAdapter.this);
			this.iv_share.setOnClickListener(DMListAdapter.this);
			this.iv_more.setOnClickListener(DMListAdapter.this);
			this.iv_image.setOnClickListener(DMListAdapter.this);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_play:
			((ImageView) v).setImageResource(R.drawable.selector_dm_suspend);
			break;
		case R.id.iv_praise:
			DMBean bean = (DMBean) v.getTag();
			bean.setPraise(!bean.isPraise());
			notifyDataSetChanged();
			break;
		default:
			break;
		}
	}

	@Override
	public void onStateChanged(Uri uri, int state) {
		switch (state) {
		case AudioPlayerListener.STATE_DOWNLOAD_END:
			// 下载完毕
			break;
		case AudioPlayerListener.STATE_DOWNLOAD_ERROR:
			// 下载错误
			break;
		case AudioPlayerListener.STATE_DOWNLOAD_START:
			// 下载开始
			break;
		case AudioPlayerListener.STATE_PLAY_END:
			// 播放结束
			break;
		case AudioPlayerListener.STATE_PLAY_ERROR:
			// 播放错误
			break;
		case AudioPlayerListener.STATE_PLAY_START:
			// 播放开始
			break;
		}
	}

	@Override
	public void onProgress(int duration, int progress) {
		// 播放进度
	}

}

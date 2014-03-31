package cc.itbox.babysay.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

	private final List<DMBean> mList;
	private final Activity mContext;
	private DMBean mCurrentPlayBean;
	private DMBean mCurrentLoadBean;
	private DMBean mClickPlayBean;
	private final AudioPlayer mPlayer;
	private ProgressBar mPlayPb;

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

	public void destory() {
		mPlayer.stop();
		mPlayer.setmListener(null);
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
		holder.iv_play.setTag(bean);
		// 播放状态
		holder.pb_play.setVisibility(View.GONE);
		holder.pb_load.setVisibility(View.GONE);
		boolean isPlay = mCurrentPlayBean == bean;
		holder.iv_play.setImageResource(isPlay ? R.drawable.selector_dm_suspend
				: R.drawable.selector_dm_play);
		if (mCurrentLoadBean == bean) {
			// 正在下载
			holder.pb_load.setVisibility(View.VISIBLE);
		} else if (isPlay) {
			// 正在播放
			mPlayPb = holder.pb_play;
			holder.pb_play.setVisibility(View.VISIBLE);
		}
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
		public ProgressBar pb_load;
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
			this.pb_load = (ProgressBar) view.findViewById(R.id.pd_load);

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
		mClickPlayBean = (DMBean) v.getTag();
		if (mClickPlayBean == null) {
			return;
		}
		switch (v.getId()) {
		case R.id.iv_play:
			// 播放音频
			mPlayer.play(Uri
					.parse("http://music.baidu.com/data/music/file?link=http://zhangmenshiting.baidu.com/data2/music/34151069/1000860216000128.mp3?xcode=99cc4c5e331d6f8db2b2143597ac0366d1efeac24fa38ae3&song_id=1000860"));
			break;
		case R.id.iv_praise:
			// 赞
			mClickPlayBean.setPraise(!mClickPlayBean.isPraise());
			notifyDataSetChanged();
			break;
		default:
			break;
		}
	}

	@Override
	public void onStateChanged(Uri uri, int state) {
		switch (state) {
		case AudioPlayerListener.STATE_LOAD_END:
			// 下载完毕
			mCurrentLoadBean = null;
			break;
		case AudioPlayerListener.STATE_LOAD_ERROR:
			// 下载错误
			mCurrentLoadBean = null;
			break;
		case AudioPlayerListener.STATE_LOAD_START:
			// 下载开始
			mCurrentLoadBean = mClickPlayBean;
			break;
		case AudioPlayerListener.STATE_PLAY_END:
			// 播放结束
			mCurrentPlayBean = null;
			break;
		case AudioPlayerListener.STATE_PLAY_ERROR:
			// 播放错误
			mCurrentPlayBean = null;
			break;
		case AudioPlayerListener.STATE_PLAY_START:
			// 播放开始
			mCurrentPlayBean = mClickPlayBean;
			break;
		}
		this.notifyDataSetChanged();
	}

	@Override
	public void onProgress(int duration, int progress) {
		// 播放进度
		if (mPlayPb == null) {
			return;
		}
		mPlayPb.setMax(duration);
		mPlayPb.setProgress(progress);
	}

}

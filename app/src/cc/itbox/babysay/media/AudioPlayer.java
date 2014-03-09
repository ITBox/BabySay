package cc.itbox.babysay.media;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import cc.itbox.babysay.util.LogUtil;

/**
 * 音频播放器
 * 
 * @author baoyz
 * 
 *         2014-3-2 下午3:54:18
 * 
 */
@SuppressLint("HandlerLeak")
public class AudioPlayer {

	public static AudioPlayer audioPlayer = new AudioPlayer();

	private AudioPlayerListener mListener;
	private MediaPlayer mMediaPlayer;
	private Uri mUri;
	private static Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			audioPlayer.getmListener().onProgress(msg.arg1, msg.arg2);
		}
	};

	private AudioPlayer() {
	}

	public static AudioPlayer getInstance() {
		return audioPlayer;
	}

	/**
	 * 播放音频
	 * 
	 * @param uri
	 */
	public void play(Uri uri) {
		if (uri == null) {
			if (mListener != null) {
				mListener.onStateChanged(mUri,
						AudioPlayerListener.STATE_PLAY_ERROR);
			}
		}
		try {
			if (mMediaPlayer != null) {
				// 如果正在播放的话就停止
				if (mMediaPlayer.isPlaying()) {
					// 停止播放
					stop();
					if (uri.equals(mUri)) {
						return;
					}
				}
			}
			mUri = uri;
			// 判断Uri类型
			if ("http".equals(mUri.getScheme())) {
				// 网络文件
				playNetFile(mUri.toString());
			} else {
				// 本地文件
				playLocalFile(mUri.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (mListener != null)
				mListener.onStateChanged(mUri,
						AudioPlayerListener.STATE_PLAY_ERROR);
		}
	}

	/**
	 * 播放网络文件
	 * 
	 * @param path
	 */
	private void playNetFile(String path) {
		try {
			mMediaPlayer = new MediaPlayer();
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mMediaPlayer.setDataSource(path);
			mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					stop();
				}

			});
			mMediaPlayer
					.setOnBufferingUpdateListener(new OnBufferingUpdateListener() {
						@Override
						public void onBufferingUpdate(MediaPlayer mp,
								int percent) {
							LogUtil.i("mediaplayer buffering percent = "
									+ percent);
						}
					});
			mMediaPlayer.setOnPreparedListener(new OnPreparedListener() {

				@Override
				public void onPrepared(MediaPlayer mp) {
					// 缓冲完毕，开始播放
					if (mListener != null)
						mListener.onStateChanged(mUri,
								AudioPlayerListener.STATE_LOAD_END);
					mp.start();
					// 更新进度线程
					new Thread() {
						@Override
						public void run() {
							try {
								while (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
									if (mListener != null) {
										mHandler.sendMessage(Message.obtain(mHandler,
												0, mMediaPlayer.getDuration(),
												mMediaPlayer.getCurrentPosition()));
									}
									SystemClock.sleep(500);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}.start();
					if (mListener != null)
						mListener.onStateChanged(mUri,
								AudioPlayerListener.STATE_PLAY_START);
				}
			});
			// 异步缓冲
			mMediaPlayer.prepareAsync();
			if (mListener != null)
				mListener.onStateChanged(mUri,
						AudioPlayerListener.STATE_LOAD_START);
		} catch (Exception e) {
			e.printStackTrace();
			if (mListener != null)
				mListener.onStateChanged(mUri,
						AudioPlayerListener.STATE_LOAD_ERROR);
		}
	}

	/**
	 * 播放本地音频文件
	 * 
	 * @param path
	 */
	private void playLocalFile(String path) {
		try {
			mMediaPlayer = new MediaPlayer();
			mMediaPlayer.setDataSource(path);
			mMediaPlayer.prepare();
			mMediaPlayer.start();
			mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					stop();
				}

			});
			new Thread() {
				@Override
				public void run() {
					try {
						while (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
							if (mListener != null) {
								mHandler.sendMessage(Message.obtain(mHandler,
										0, mMediaPlayer.getDuration(),
										mMediaPlayer.getCurrentPosition()));
							}
							SystemClock.sleep(500);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
			mListener
					.onStateChanged(mUri, AudioPlayerListener.STATE_PLAY_START);
		} catch (Exception e) {
			e.printStackTrace();
			if (mListener != null)
				mListener.onStateChanged(mUri,
						AudioPlayerListener.STATE_PLAY_ERROR);
		}
	}

	public void stop() {
		if (mListener != null)
			mListener.onStateChanged(mUri, AudioPlayerListener.STATE_PLAY_END);
		if (mMediaPlayer == null)
			return;
		// 释放资源
		mMediaPlayer.stop();
		mMediaPlayer.release();
		mMediaPlayer = null;
	}

	public AudioPlayerListener getmListener() {
		return mListener;
	}

	public void setmListener(AudioPlayerListener mListener) {
		this.mListener = mListener;
	}

	public static interface AudioPlayerListener {
		public static final int STATE_LOAD_START = 1;
		public static final int STATE_LOAD_END = 2;
		public static final int STATE_LOAD_ERROR = 3;
		public static final int STATE_PLAY_START = 4;
		public static final int STATE_PLAY_END = 5;
		public static final int STATE_PLAY_ERROR = 6;

		public void onStateChanged(Uri uri, int state);

		public void onProgress(int duration, int progress);
	}
}

/*
 * Copyright (C) 2009 Teleca Poland Sp. z o.o. <android@teleca.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cc.itbox.babysay.media;

import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;

/**
 * Player core engine allowing playback, in other words, a wrapper around
 * 
 * 
 * @author Lukasz Wisniewski
 */
public class PlayerEngineImpl implements PlayerEngine {

	private static final long FAIL_TIME_FRAME = 1000;

	private static final int ACCEPTABLE_FAIL_NUMBER = 2;

	private long mLastFailTime;

	private long mTimesFailed;

	private class InternalMediaPlayer extends MediaPlayer {

		private String audioUrl;

		public boolean preparing = false;

	}

	private InternalMediaPlayer mCurrentMediaPlayer;

	private PlayerEngineListener mPlayerEngineListener;

	private final Handler mHandler;

	private final Runnable mUpdateTimeTask = new Runnable() {
		@Override
		public void run() {

			if (mPlayerEngineListener != null) {
				if (mCurrentMediaPlayer != null)
					mPlayerEngineListener.onTrackProgress(mCurrentMediaPlayer
							.getCurrentPosition());
				mHandler.postDelayed(this, 1000);
			}
		}
	};

	// 构造函数
	public PlayerEngineImpl() {
		mLastFailTime = 0;
		mTimesFailed = 0;
		mHandler = new Handler();
	}

	// 暂停
	@Override
	public void pause() {
		if (mCurrentMediaPlayer != null) {
			// 正在准备
			if (mCurrentMediaPlayer.preparing) {
				return;
			}
			if (mCurrentMediaPlayer.isPlaying()) {
				mCurrentMediaPlayer.pause();
				if (mPlayerEngineListener != null)
					mPlayerEngineListener.onTrackPause();
				return;
			}
		}
	}

	// 播放
	@Override
	public void play(String url) {
		if (mPlayerEngineListener.onTrackStart() == false) {
			return;
		}
		if (url != null) {

			if (mCurrentMediaPlayer == null) {
				mCurrentMediaPlayer = build(url);
			}

			// check if current media player is set to our song
			if (mCurrentMediaPlayer != null
					&& !mCurrentMediaPlayer.audioUrl.equals(url)) {
				cleanUp(); // this will do the cleanup job
				mCurrentMediaPlayer = build(url);
			}
			if (mCurrentMediaPlayer == null)
				return;
			// check if current media player is not still buffering
			if (!mCurrentMediaPlayer.preparing) {
				// prevent double-press
				if (!mCurrentMediaPlayer.isPlaying()) {
					// starting timer
					mHandler.removeCallbacks(mUpdateTimeTask);
					mHandler.postDelayed(mUpdateTimeTask, 1000);
					mCurrentMediaPlayer.start();
				} else {
					mCurrentMediaPlayer.pause();
				}
			} else {
				// 正在准备
				cleanUp();

			}
		}
	}

	@Override
	public void stop() {
		cleanUp();
		if (mPlayerEngineListener != null) {
			mPlayerEngineListener.onTrackStop();
		}
	}

	// 销毁对象
	private void cleanUp() {
		// nice clean-up job
		if (mCurrentMediaPlayer != null) {
			try {
				mCurrentMediaPlayer.stop();
			} catch (IllegalStateException e) {

			} finally {
				mCurrentMediaPlayer.release();
				mCurrentMediaPlayer = null;
			}
		}
	}

	// 构建MediaPlayer
	private InternalMediaPlayer build(final String url) {
		final InternalMediaPlayer mediaPlayer = new InternalMediaPlayer();

		String path = url;
		try {
			// 设置数据源
			mediaPlayer.setDataSource(path);
			mediaPlayer.audioUrl = path;
			// 播放监听期
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					stop();
				}
			});

			// 准备监听器
			mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
				@Override
				public void onPrepared(MediaPlayer mp) {
					mediaPlayer.preparing = false;
					play(url);
					if (mPlayerEngineListener != null) {
						mPlayerEngineListener.onTrackChanged(mediaPlayer
								.getDuration());
					}

				}

			});

			mediaPlayer
					.setOnBufferingUpdateListener(new OnBufferingUpdateListener() {
						@Override
						public void onBufferingUpdate(MediaPlayer mp,
								int percent) {
							if (mPlayerEngineListener != null) {
								mPlayerEngineListener.onTrackBuffering(percent);
							}
						}

					});

			mediaPlayer.setOnErrorListener(new OnErrorListener() {

				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {

					if (what == MediaPlayer.MEDIA_ERROR_UNKNOWN) {
						// we probably lack network
						if (mPlayerEngineListener != null) {
							mPlayerEngineListener.onTrackStreamError();
						}
						stop();
						return true;
					}

					if (what == -1) {
						long failTime = System.currentTimeMillis();
						if (failTime - mLastFailTime > FAIL_TIME_FRAME) {
							// outside time frame
							mTimesFailed = 1;
							mLastFailTime = failTime;

						} else {
							// inside time frame
							mTimesFailed++;
							if (mTimesFailed > ACCEPTABLE_FAIL_NUMBER) {
								stop();
								return true;
							}
						}
					}
					return false;
				}
			});

			mediaPlayer.preparing = true;
			mediaPlayer.prepareAsync();

			return mediaPlayer;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean isPlaying() {
		if (mCurrentMediaPlayer == null)
			return false;
		if (mCurrentMediaPlayer.preparing)
			return false;
		return mCurrentMediaPlayer.isPlaying();
	}

	@Override
	public void setListener(PlayerEngineListener playerEngineListener) {
		mPlayerEngineListener = playerEngineListener;
	}

	@Override
	public void seekTo(int progress) {
		mCurrentMediaPlayer.seekTo(progress);
	}

}

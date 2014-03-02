package cc.itbox.babysay;

import org.holoeverywhere.app.Application;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class AppContext extends Application {
	private static AppContext mContext;

	public static AppContext getInstance() {
		return mContext;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;

		// 初始化图片加载器
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.aaa)
				.showImageForEmptyUri(R.drawable.aaa)
				.showImageOnFail(R.drawable.aaa).cacheInMemory(true)
				.cacheOnDisc(true).build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.defaultDisplayImageOptions(options).writeDebugLogs()
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);
	}
}

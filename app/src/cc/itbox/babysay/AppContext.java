package cc.itbox.babysay;

import android.app.Application;
import cc.itbox.babysay.bean.User;
import cc.itbox.babysay.db.UserAdapter;
import cc.itbox.babysay.media.PlayerEngineImpl;

import com.activeandroid.ActiveAndroid;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class AppContext extends Application {

	public static AppContext mContext;

	private PlayerEngineImpl mPlayerEngineImpl;

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

		ActiveAndroid.initialize(this);
		mPlayerEngineImpl = new PlayerEngineImpl();
	}

	public PlayerEngineImpl getmPlayerEngineImpl() {
		return mPlayerEngineImpl;
	}

	public void setmPlayerEngineImpl(PlayerEngineImpl mPlayerEngineImpl) {
		this.mPlayerEngineImpl = mPlayerEngineImpl;
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		ActiveAndroid.dispose();
	}

	public static Gson getGson() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.registerTypeAdapter(User.class,
				new UserAdapter()).create();
		return gson;
	}
}

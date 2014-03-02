package cc.itbox.babysay;

import org.holoeverywhere.app.Application;

public class AppContext extends Application {
	private static AppContext mContext;

	public static AppContext getInstance() {
		return mContext;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
	}
}

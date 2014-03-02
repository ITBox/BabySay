package cc.itbox.babysay.api;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;

public class BaseApi {
	protected AsyncHttpClient client;
	protected Context mContext;

	public BaseApi(Context context) {
		// TODO Auto-generated constructor stub
		client = new AsyncHttpClient();
		this.mContext = context;
	}
}

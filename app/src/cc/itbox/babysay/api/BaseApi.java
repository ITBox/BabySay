package cc.itbox.babysay.api;

import android.content.Context;

import cc.itbox.babysay.CatchInfo;
import cc.itbox.babysay.ErrorInfo;

import com.loopj.android.http.AsyncHttpClient;

public class BaseApi {
	protected AsyncHttpClient client;
	protected Context mContext;
	protected OnCatchHandler mCatchHandler;
	protected int type;
	protected CatchInfo ci;
	protected ErrorInfo ei = new ErrorInfo();
	
	public BaseApi(Context context) {
		client = new AsyncHttpClient();
		this.mContext = context;
	}
	
	public BaseApi(Context context, int type, CatchInfo ci,OnCatchHandler onCatchHandler){
		this.mContext = context;
		this.type = type;
		this.ci = ci;
		this.mCatchHandler = onCatchHandler;
		client = new AsyncHttpClient();
	}
}

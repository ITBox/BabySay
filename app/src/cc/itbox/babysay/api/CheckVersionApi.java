package cc.itbox.babysay.api;

import java.util.ArrayList;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpResponseHandler;

import cc.itbox.babysay.CatchInfo;
import cc.itbox.babysay.WebDefine;
import cc.itbox.babysay.bean.VersionBean;
import android.content.Context;

public class CheckVersionApi extends BaseApi {
	private ArrayList<VersionBean> mVersionBeanList = new ArrayList<VersionBean>();
	
	public CheckVersionApi(Context context, int type, CatchInfo ci, OnCatchHandler onCatchHandler) {
		super(context, type, ci, onCatchHandler);
	}

	public void requestVersion(){
		client.get(WebDefine.CHECK_VERSION_URL, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				super.onSuccess(arg0, arg1, arg2);
				// JSON解析
				
				mCatchHandler.onCatchSucc(type, ci, mVersionBeanList);
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				super.onFailure(arg0, arg1, arg2, arg3);
				ei.mErrorCode = arg3.hashCode();
				ei.mErrorReservedText = arg3.getLocalizedMessage();
				mCatchHandler.onCatchFail(type, ci, ei);
			}
			
		});
	}

}

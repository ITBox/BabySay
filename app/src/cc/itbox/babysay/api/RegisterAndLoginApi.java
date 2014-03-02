package cc.itbox.babysay.api;

import org.apache.http.Header;

import android.content.Context;
import cc.itbox.babysay.util.Constants;
import cc.itbox.babysay.util.LogUtil;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class RegisterAndLoginApi extends BaseApi {

	public RegisterAndLoginApi(Context context) {
		super(context);
	}

	public void register(RequestParams params) {
		client.post(Constants.REGISTER_URL, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1, arg2);
						LogUtil.e("注册成功" + new String(arg2));
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
						super.onFailure(arg0, arg1, arg2, arg3);
						LogUtil.e("注册失败" + arg3.toString());
					}
				});
	}

	public void login(RequestParams params) {
		client.post(Constants.LOGIN_URL, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1, arg2);
						LogUtil.e("登陆成功" + new String(arg2));
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
						super.onFailure(arg0, arg1, arg2, arg3);
						LogUtil.e("登陆失败" + arg3.toString());
					}
				});
	}

}

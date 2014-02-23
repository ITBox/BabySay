package cc.itbox.babysay.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public abstract class BaseFragment extends Fragment {
    protected FragmentActivity mActThis = null;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActThis = getActivity();
	}
	
	@SuppressLint("HandlerLeak")
	public final Handler mHandlerEx = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			onHandleMessage(msg);
		}
	};
	
	protected abstract void onHandleMessage(Message message);
	
	protected void sendMessageWithArg(int what, int arg) {
		Message msg = mHandlerEx.obtainMessage(what);
		msg.what = what;
		msg.arg1 = arg;
		mHandlerEx.sendMessage(msg);
	}
	
	protected void sendMessageWithObj(int what, Object obj) {
		Message msg = mHandlerEx.obtainMessage(what);
		msg.what = what;
		msg.obj = obj;
		mHandlerEx.sendMessage(msg);
	}
}

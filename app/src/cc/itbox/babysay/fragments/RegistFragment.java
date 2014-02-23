package cc.itbox.babysay.fragments;

/**
 * 注册
 */
import cc.itbox.babysay.R;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RegistFragment extends BaseFragment {

	@Override
	protected void onHandleMessage(Message message) {
		// TODO Auto-generated method stub

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layout = null;
		if (container != null) {
			LayoutInflater layoutInflater = (LayoutInflater) mActThis.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			layout = layoutInflater.inflate(R.layout.fragment_regist, container, false);
		}
		return layout;
	}
}

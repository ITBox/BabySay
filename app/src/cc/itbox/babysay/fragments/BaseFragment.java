package cc.itbox.babysay.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View.OnClickListener;

/**
 * 
 * @author Youzh 2014-2-23 下午6:06:28
 */

public abstract class BaseFragment extends Fragment implements OnClickListener {
	protected FragmentActivity mActivity = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = getActivity();
	}

}

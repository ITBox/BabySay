package cc.itbox.babysay.fragments;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

/**
 * 
 * @author malinkang 2014-2-27
 * 
 */

public abstract class BaseFragment extends Fragment implements OnClickListener {
	/**被context替代*/
	@Deprecated
	protected FragmentActivity mActivity = null;
	protected FragmentActivity context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
		mActivity = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	public void startActivity(Class<? extends Activity> activity) {
		Intent intent = new Intent(context, activity);
		startActivity(intent);
	}
	
}

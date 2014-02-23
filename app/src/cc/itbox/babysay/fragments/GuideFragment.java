package cc.itbox.babysay.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import cc.itbox.babysay.R;
import cc.itbox.babysay.util.UIUtil;

/**
 * 
 * @author malinkang 2014-2-22 下午2:37:29
 * 
 */
public class GuideFragment extends Fragment {

	private final static String RES_ID = "res_id";

	private int resId;

	public static final GuideFragment newInstance(int resId) {
		GuideFragment fragment = new GuideFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(RES_ID, resId);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		resId = getArguments().getInt(RES_ID);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_guide, container, false);
		ImageView guideImageView = UIUtil.getView(view, R.id.iv_guide);
		guideImageView.setImageResource(resId);
		return view;
	}
}

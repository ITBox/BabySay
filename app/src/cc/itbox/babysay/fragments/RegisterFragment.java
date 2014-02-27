package cc.itbox.babysay.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import cc.itbox.babysay.R;
import cc.itbox.babysay.util.UIUtil;

/**
 * 
 * @author Youzh 2014-2-23 下午6:06:05
 */
public class RegisterFragment extends BaseFragment {

	// private FloatLabel mETRegName, mETRegEmail, mETRegPass, mETRegPassAgain;
	private EditText mTVRegBirthday;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = null;
		if (container != null) {
			view = inflater.inflate(R.layout.fragment_register, container,
					false);
			/*
			 * mETRegName = UIUtil.getView(layout, R.id.fragment_register_name);
			 * mETRegEmail = UIUtil.getView(layout,
			 * R.id.fragment_register_email); mETRegPass =
			 * UIUtil.getView(layout, R.id.fragment_register_pass);
			 */
			// mETRegPassAgain = UIUtil.getView(layout,
			// R.id.fragment_register_pass_again);
			UIUtil.getView(view, R.id.fragment_register_girl);
			UIUtil.getView(view, R.id.fragment_register_boy);
			mTVRegBirthday = UIUtil.getView(view,
					R.id.fragment_register_birthday);
			// mETRegName.setLabelAnimator(new FloatLabelAnimator());
			// mETRegEmail.setLabelAnimator(new FloatLabelAnimator());
			// mETRegPass.setLabelAnimator(new FloatLabelAnimator());
			// mETRegPassAgain.setLabelAnimator(new FloatLabelAnimator());
			mTVRegBirthday.setOnClickListener(OnETClickListener);
		}
		return view;
	}

	private OnClickListener OnETClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Toast.makeText(mActivity, "点击了生日", Toast.LENGTH_SHORT).show();
		}
	};

	@Override
	public void onClick(View v) {

	}
}

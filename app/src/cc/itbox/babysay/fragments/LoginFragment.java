package cc.itbox.babysay.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cc.itbox.babysay.R;
import cc.itbox.babysay.ui.dialog.ForgotPasswordDialogFragment;

/**
 * 
 * @author yanchenling
 *
 */
public class LoginFragment extends BaseFragment {

	// private FloatLabel emailFloatLabel, passwordFloatLabel;
	private TextView forgetTv, cancleTv, confirmTv;
	private ImageView sinaLoginIv, qqLoginIv;
	private View alertView;
	private EditText emailEt;
	private AlertDialog alertDialog;
	private ForgotPasswordDialogFragment forgotPasswordDialogFragment;

	@Override
	public View onCreateView(org.holoeverywhere.LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_login, container, false);
		LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
		alertView = layoutInflater.inflate(R.layout.alertdialog, null);
		emailEt = (EditText) alertView.findViewById(R.id.edit_email);
		cancleTv = (TextView) alertView.findViewById(R.id.tv_cancle);
		confirmTv = (TextView) alertView.findViewById(R.id.tv_confirm);

		initUI(view);
		setClick();

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		forgotPasswordDialogFragment = new ForgotPasswordDialogFragment();
	}

	private void initUI(View view) {
		// emailFloatLabel = UIUtil.getView(view, R.id.et_email);
		// passwordFloatLabel = UIUtil.getView(view, R.id.et_password);
		forgetTv = (TextView) view.findViewById(R.id.tv_forget);
		sinaLoginIv = (ImageView) view.findViewById(R.id.iv_sina);
		qqLoginIv = (ImageView) view.findViewById(R.id.iv_tencent);

	}

	private void setClick() {
		// emailFloatLabel.setLabelAnimator(new FloatLabelAnimator());
		// passwordFloatLabel.setLabelAnimator(new FloatLabelAnimator());
		forgetTv.setOnClickListener(this);
		sinaLoginIv.setOnClickListener(this);
		qqLoginIv.setOnClickListener(this);
		confirmTv.setOnClickListener(this);
		cancleTv.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_forget:
			alertDialog = new AlertDialog.Builder(getActivity()).setView(
					alertView).create();
			alertDialog.show();
			// forgotPasswordDialogFragment.show(getFragmentManager(),
			// "dialog");
			break;
		case R.id.iv_sina:

			break;
		case R.id.iv_tencent:
			break;
		case R.id.tv_cancle:
			if (alertDialog != null)
				alertDialog.dismiss();
			break;
		case R.id.tv_confirm:
			String emailStr = emailEt.getText().toString().trim();
			if (emailStr != null && emailStr.length() == 0) {
				Toast.makeText(getActivity(), "邮件地址不能为空", 0).show();
				return;
			}
			if (alertDialog != null)
				alertDialog.dismiss();
				alertDialog=null;
			break;
		default:
			break;
		}

	}
}

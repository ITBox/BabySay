package cc.itbox.babysay.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cc.itbox.babysay.R;
import cc.itbox.babysay.ui.FloatLabel;
import cc.itbox.babysay.ui.FloatLabelAnimator;
import cc.itbox.babysay.util.UIUtil;

public class LoginFragment extends BaseFragment implements OnClickListener {

	
	private FloatLabel et_email,et_password;
	private TextView tv_forget,tv_cancle,tv_confirm;
	private ImageView iv_sina,iv_tencent;
	private View alertView;
	private EditText edit_email;
	private AlertDialog alertDialog;
	
	@Override
	protected void onHandleMessage(Message message) {
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_login, container, false);
		LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
		alertView = layoutInflater.inflate(R.layout.alertdialog,null);
		edit_email=(EditText) alertView.findViewById(R.id.edit_email);
		tv_cancle=(TextView) alertView.findViewById(R.id.tv_cancle);
		tv_confirm=(TextView) alertView.findViewById(R.id.tv_confirm);
		
		initUI(view);
		setClick();
		
		return view;
	}

	private void initUI(View view) {
		
		et_email=UIUtil.getView(view, R.id.et_email);
		et_password=UIUtil.getView(view, R.id.et_password);
		tv_forget=(TextView) view.findViewById(R.id.tv_forget);
		iv_sina=(ImageView) view.findViewById(R.id.iv_sina);
		iv_tencent=(ImageView) view.findViewById(R.id.iv_tencent);
		
		
		
	}
	private void setClick() {
		et_email.setLabelAnimator(new FloatLabelAnimator());
		et_password.setLabelAnimator(new FloatLabelAnimator());
		
		tv_forget.setOnClickListener(this);
		iv_sina.setOnClickListener(this);
		iv_tencent.setOnClickListener(this);
		tv_confirm.setOnClickListener(this);
		tv_cancle.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_forget:

			alertDialog = new AlertDialog.Builder(getActivity())
			.setView(alertView)
			.create();
			alertDialog.show();
			
			break;
		case R.id.iv_sina:
			
			break;
		case R.id.iv_tencent:
			
			break;
		case R.id.tv_cancle:
			if(alertDialog!=null)
				alertDialog.dismiss();
			break;
		case R.id.tv_confirm:
			String email = edit_email.getText().toString().trim();
			if(email!=null && email.length()==0){
				Toast.makeText(getActivity(), "邮件地址不能为空", 0).show();
				return;
			}
			
			
			
			
			if(alertDialog!=null)
				alertDialog.dismiss();
			break;
		default:
			break;
		}
		
	}




	

}

package cc.itbox.babysay.fragments;

/**
 * 注册
 */
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.MonthDisplayHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import cc.itbox.babysay.R;
import cc.itbox.babysay.ui.FloatLabel;
import cc.itbox.babysay.ui.FloatLabelAnimator;
import cc.itbox.babysay.util.UIUtil;

public class RegisterFragment extends BaseFragment {

	private FloatLabel mETRegName, mETRegEmail, mETRegPass, mETRegPassAgain;
    private EditText mTVRegBirthday;
    private RadioButton mRBRegGirl, mRBRegBoy;
    
	@Override
	protected void onHandleMessage(Message message) {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layout = null;
		if (container != null) {
			LayoutInflater layoutInflater = (LayoutInflater) mActThis.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			layout = layoutInflater.inflate(R.layout.fragment_register, container, false);
			mETRegName = UIUtil.getView(layout, R.id.fragment_register_name);
			mETRegEmail = UIUtil.getView(layout, R.id.fragment_register_email);
			mETRegPass = UIUtil.getView(layout, R.id.fragment_register_pass);
			mETRegPassAgain = UIUtil.getView(layout, R.id.fragment_register_pass_again);
			mRBRegGirl = UIUtil.getView(layout, R.id.fragment_register_girl);
			mRBRegBoy = UIUtil.getView(layout, R.id.fragment_register_boy);
			mTVRegBirthday = UIUtil.getView(layout, R.id.fragment_register_birthday);
			mETRegName.setLabelAnimator(new FloatLabelAnimator());
			mETRegEmail.setLabelAnimator(new FloatLabelAnimator());
			mETRegPass.setLabelAnimator(new FloatLabelAnimator());
			mETRegPassAgain.setLabelAnimator(new FloatLabelAnimator());
			mTVRegBirthday.setOnClickListener(OnETClickListener);
		}
		return layout;
	}
	
	private OnClickListener OnETClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Toast.makeText(mActThis, "点击了生日", Toast.LENGTH_SHORT).show();
			
		}
	};
}

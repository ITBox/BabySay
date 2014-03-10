package cc.itbox.babysay.activities;

import cc.itbox.babysay.R;
import cc.itbox.babysay.ui.dialog.OnSetDateListener;
import cc.itbox.babysay.ui.dialog.PickersDatePickerFragment;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class UserInfoEditActivity extends BaseActivity implements OnClickListener, OnSetDateListener{

	private TextView tvBirthday;

	@Override
	@SuppressLint("CommitTransaction")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_userinfo_edit);
		initView();
	}

	private void initView() {
		findViewById(R.id.user_ll_birthday).setOnClickListener(this);
		tvBirthday = (TextView)findViewById(R.id.user_tv_birthday);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.user_ll_birthday:
			PickersDatePickerFragment datePickerFragment = new PickersDatePickerFragment();
			datePickerFragment.setOnSetDateListener(this);
			datePickerFragment.show(getSupportFragmentManager());
			break;

		default:
			break;
		}
	}

	@Override
	public void setDate(String date) {
		tvBirthday.setText(date);
	}
	
	
	
	
}

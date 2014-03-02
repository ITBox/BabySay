package cc.itbox.babysay.fragments;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.widget.EditText;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import cc.itbox.babysay.R;
import cc.itbox.babysay.api.RegisterAndLoginApi;
import cc.itbox.babysay.ui.CircleImageView;
import cc.itbox.babysay.util.UIUtil;

import com.loopj.android.http.RequestParams;

public class RegisterFragment extends BaseFragment {
	private CircleImageView avatarIV;
	private EditText nicknameEt;
	private EditText emailEt;
	private EditText passwordEt;
	private EditText confirmPasswordEt;
	private EditText birthdayEt;
	private RadioGroup sexRg;
	private MenuItem registerOrLoginItem;
	private String nicknameStr;
	private String emailStr;
	private String passwordStr;
	private String birthdayStr;
	private String sexStr;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_register, container,
				false);
		avatarIV = UIUtil.getView(view, R.id.iv_avatar);
		nicknameEt = UIUtil.getView(view, R.id.et_nickname);
		emailEt = UIUtil.getView(view, R.id.et_email);
		passwordEt = UIUtil.getView(view, R.id.et_password);
		confirmPasswordEt = UIUtil.getView(view, R.id.et_confirm_password);
		birthdayEt = UIUtil.getView(view, R.id.et_birthday);
		sexRg = UIUtil.getView(view, R.id.rg_sex);
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.actionbar_register_or_login, menu);
		registerOrLoginItem = menu.findItem(R.id.action_register_or_login);
		registerOrLoginItem.setTitle(R.string.register);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			mActivity.finish();
			return false;
		case R.id.action_register_or_login:
			nicknameStr = nicknameEt.getText().toString();
			emailStr = emailEt.getText().toString();
			passwordStr = passwordEt.getText().toString();
			birthdayStr = birthdayEt.getText().toString();
			RequestParams params = new RequestParams();
			params.put("nickname", nicknameStr);
			params.put("login", emailStr);
			params.put("password", passwordStr);
			params.put("birthday", birthdayStr);
			params.put("sex", sexStr);
			new RegisterAndLoginApi(mActivity).login(params);
			return false;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onClick(View arg0) {

	}

}

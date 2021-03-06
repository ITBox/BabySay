package cc.itbox.babysay.fragments;

import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import butterknife.InjectView;
import cc.itbox.babysay.R;
import cc.itbox.babysay.activities.PhotoViewerActivity;
import cc.itbox.babysay.api.RegisterAndLoginApi;
import cc.itbox.babysay.iface.OnActivityResultReturnListener;
import cc.itbox.babysay.ui.CircleImageView;
import cc.itbox.babysay.ui.dialog.OnSetDateListener;
import cc.itbox.babysay.util.UIUtil;

import com.loopj.android.http.RequestParams;

import eu.inmite.android.lib.validations.form.FormValidator;
import eu.inmite.android.lib.validations.form.annotations.MaxLength;
import eu.inmite.android.lib.validations.form.annotations.MinLength;
import eu.inmite.android.lib.validations.form.annotations.NotEmpty;
import eu.inmite.android.lib.validations.form.annotations.RegExp;
import eu.inmite.android.lib.validations.form.callback.SimpleErrorPopupCallback;

public class RegisterFragment extends BaseFragment implements
		OnSetDateListener, OnCheckedChangeListener,
		OnActivityResultReturnListener {
	private int mYear;
	private int mMonth;
	private int mDay;

	private static final int CHOOSER_IMAGE_REQUEST_CODE = 1;
	// 头像 昵称
	@InjectView(R.id.iv_avatar)
	private CircleImageView avatarIV;
	// 昵称
	@NotEmpty(messageId = R.string.validation_name_empty, order = 1)
	@MinLength(value = 3, messageId = R.string.validation_name_min_length, order = 2)
	@MaxLength(value = 12, messageId = R.string.validation_name_max_length, order = 3)
	@InjectView(R.id.et_nickname)
	private EditText nicknameEt;
	// 邮箱
	@NotEmpty(messageId = R.string.validation_valid_email_empty, order = 4)
	@RegExp(value = RegExp.EMAIL, messageId = R.string.validation_valid_email_format, order = 5)
	private EditText emailEt;
	@NotEmpty(messageId = R.string.validation_password_empty, order = 6)
	private EditText passwordEt;
	@NotEmpty(messageId = R.string.validation_password_empty, order = 7)
	private EditText confirmPasswordEt;
	// @DateInFuture(messageId = R.string.validation_date, order = 8)
	private EditText birthdayEt;
	private RadioGroup sexRg;
	private MenuItem registerOrLoginItem;
	private String nicknameStr;
	private String emailStr;
	private String passwordStr;
	private String birthdayStr;
	// 默认是女
	private String sexStr = "2";

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
		sexRg.setOnCheckedChangeListener(this);
		avatarIV.setOnClickListener(this);
		birthdayEt.setOnClickListener(this);
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
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
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			mActivity.finish();
			return false;
		case R.id.action_register_or_login:
			final boolean isValid = FormValidator.validate(this,
					new SimpleErrorPopupCallback(mActivity, true));
			if (isValid) {
				nicknameStr = nicknameEt.getText().toString();
				emailStr = emailEt.getText().toString();
				passwordStr = passwordEt.getText().toString();
				// 判断两次密码是否一直
				if (!passwordStr.equals(confirmPasswordEt.getText().toString())) {
					UIUtil.showShortToast(mActivity, "两次密码不一致");
					return false;
				}
				birthdayStr = birthdayEt.getText().toString();
				RequestParams params = new RequestParams();
				params.put("nickname", nicknameStr);
				params.put("login", emailStr);
				params.put("password", passwordStr);
				params.put("birthday", birthdayStr);
				params.put("sex", sexStr);
				new RegisterAndLoginApi(mActivity).register(params);
			}
			return false;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.et_birthday:
			// new DatePickerDialog(mActivity, mDateSetListener, mYear, mMonth,
			// mDay).show();
			// break;
			// case R.id.iv_avatar:
			// GeneralImageChooser mGeneralImageChooser = new
			// GeneralImageChooser(
			// this, CHOOSER_IMAGE_REQUEST_CODE);
			// GetPictureDialogListFragment getPictureDialogListFragment =
			// GetPictureDialogListFragment
			// .newInstance(mGeneralImageChooser);
			// getPictureDialogListFragment.show(getFragmentManager());
			// break;
		}
	}

	@Override
	public void setDate(String date) {
		birthdayEt.setText(date);

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (checkedId == R.id.rb_male) {
			sexStr = "1";
		} else if (checkedId == R.id.rb_female) {
			sexStr = "2";
		}

	}

	@Override
	public void onActivityresultReturn(int requestCode, int resultCode,
			Intent data) {
		if (data != null) {
			// Bitmap bitmap = null;
			// if (data.getExtras() != null) {
			// bitmap = (Bitmap) data.getExtras().get("data");
			// } else {
			// bitmap = ImageUtils.getBitmapFromUri(getActivity(),
			// data.getData());
			// }
			// 跳转到图片处理界面
			Intent intent = new Intent(mActivity, PhotoViewerActivity.class);
			intent.setData(data.getData());
			startActivity(intent);
		}

	}

}

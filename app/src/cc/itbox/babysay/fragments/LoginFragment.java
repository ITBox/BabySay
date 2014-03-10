package cc.itbox.babysay.fragments;

import org.holoeverywhere.widget.EditText;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import cc.itbox.babysay.R;
import cc.itbox.babysay.activities.MainActivity;
import cc.itbox.babysay.util.UIUtil;

/**
 * 登陆Fragment
 * 
 * @author malinkang 2014-3-2
 * 
 */

/**
 * 
 * @author yanchenling
 * 
 */
public class LoginFragment extends BaseFragment {
	private MenuItem registerOrLoginItem;
	private EditText emailEt;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(org.holoeverywhere.LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_login, container, false);
		emailEt = UIUtil.getView(view, R.id.et_email);
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.actionbar_register_or_login, menu);
		registerOrLoginItem = menu.findItem(R.id.action_register_or_login);
		registerOrLoginItem.setTitle(R.string.login);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_forget_password:
			break;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			mActivity.finish();
			return false;
		case R.id.action_register_or_login:
			startActivity(new Intent(mActivity, MainActivity.class));
			return false;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}

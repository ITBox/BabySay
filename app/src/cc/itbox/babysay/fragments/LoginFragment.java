package cc.itbox.babysay.fragments;

import org.holoeverywhere.widget.Toast;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import cc.itbox.babysay.R;
import cc.itbox.babysay.activities.MainActivity;

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
	private AlertDialog alertDialog;
	private View alertView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(org.holoeverywhere.LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_login, container, false);
		alertView = inflater.inflate(R.layout.alertdialog, null);
		
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
		case R.id.tv_forget:
			alertDialog = new AlertDialog.Builder(getActivity()).setView(
					alertView).create();
			alertDialog.show();
			// forgotPasswordDialogFragment.show(getFragmentManager(),
			// "dialog");
			break;
//		case R.id.iv_sina:
//
//			break;
//		case R.id.iv_tencent:
//			break;
		case R.id.tv_cancle:
			if (alertDialog != null)
				alertDialog.dismiss();
			break;
		case R.id.tv_confirm:
//			String emailStr = emailEt.getText().toString().trim();
//			if (emailStr != null && emailStr.length() == 0) {
//				Toast.makeText(getActivity(), "邮件地址不能为空", 0).show();
//				return;
//			}
//			if (alertDialog != null)
//				alertDialog.dismiss();
				alertDialog=null;
			break;
		}
		}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			mActivity.finish();
			return false;
		case R.id.action_register_or_login:
			Toast.makeText(mActivity, R.string.login, Toast.LENGTH_SHORT)
					.show();
			startActivity(new Intent(mActivity, MainActivity.class));
			return false;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}

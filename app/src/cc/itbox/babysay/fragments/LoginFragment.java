package cc.itbox.babysay.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import cc.itbox.babysay.R;
import cc.itbox.babysay.activities.MainActivity;
import cc.itbox.babysay.activities.WeiboOAuthActivity;
import cc.itbox.babysay.util.UIUtil;

/**
 * 
 * @author malinkang
 * 
 */
public class LoginFragment extends BaseFragment implements OnClickListener {
	private MenuItem registerOrLoginItem;
	private TextView weiboLoginTv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_login, container, false);
		UIUtil.getView(view, R.id.et_email);
		weiboLoginTv = UIUtil.getView(view, R.id.tv_weibo_login);
		weiboLoginTv.setOnClickListener(this);
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
		Intent intent = null;
		switch (v.getId()) {
		case R.id.tv_forget_password:
			break;
		case R.id.tv_weibo_login:
			intent = new Intent(mActivity, WeiboOAuthActivity.class);
			startActivity(intent);
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

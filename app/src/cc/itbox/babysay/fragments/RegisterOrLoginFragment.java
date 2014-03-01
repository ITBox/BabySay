package cc.itbox.babysay.fragments;

import org.holoeverywhere.app.TabSwipeFragment;
import org.holoeverywhere.app.TabSwipeInterface.OnTabSelectedListener;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import cc.itbox.babysay.R;

public class RegisterOrLoginFragment extends TabSwipeFragment implements
		OnTabSelectedListener {

	private MenuItem registerOrLoginItem;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.actionbar_register_or_login, menu);
		registerOrLoginItem = menu.findItem(R.id.action_register_or_login);

	}

	@Override
	public void onHandleTabs() {
		addTab(R.string.loading, LoginFragment.class, null);
		addTab("注册", RegisterFragment.class, null);
		setOnTabSelectedListener(this);
	}

	@Override
	public void onTabSelected(int position) {
		switch (position) {
		case 0:

			registerOrLoginItem.setTitle("登陆");
			break;

		case 1:
			registerOrLoginItem.setTitle("注册");
			break;
		}

	}
}

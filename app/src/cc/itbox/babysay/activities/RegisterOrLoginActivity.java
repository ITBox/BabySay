package cc.itbox.babysay.activities;

import android.os.Bundle;
import cc.itbox.babysay.R;
import cc.itbox.babysay.fragments.RegisterOrLoginFragment;

/**
 * 
 * @author malinkang 2014-2-27
 * 
 */

public class RegisterOrLoginActivity extends BaseActivity {

	private RegisterOrLoginFragment mRegisterOrLoginFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_or_login);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mRegisterOrLoginFragment = new RegisterOrLoginFragment();
		mFragmentTransaction.replace(R.id.container, mRegisterOrLoginFragment)
				.commit();
	}
}

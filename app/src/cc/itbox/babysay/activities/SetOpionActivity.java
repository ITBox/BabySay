package cc.itbox.babysay.activities;

import cc.itbox.babysay.R;
import android.annotation.SuppressLint;
import android.os.Bundle;
/**
 * 
 * @author youzh
 * 2014-3-2 下午5:27:08
 */
public class SetOpionActivity extends BaseActivity {

	@Override
	@SuppressLint("CommitTransaction")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_opion);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		
	}
}

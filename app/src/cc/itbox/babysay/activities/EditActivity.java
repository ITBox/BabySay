package cc.itbox.babysay.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import cc.itbox.babysay.R;

public class EditActivity extends BaseActivity {

	
	private ImageView iv_pic;
	private EditText et_content;
	
	@Override
	@SuppressLint("CommitTransaction")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		
		initUI();
		setClick();
		initData();
		
	}

	private void initUI() {
	
		iv_pic=(ImageView) findViewById(R.id.iv_pic);
		et_content=(EditText) findViewById(R.id.et_content);
		
	}

	private void setClick() {
		
	}

	private void initData() {
		Intent intent = getIntent();
		String imagePath = intent.getStringExtra("path");
		Bitmap bitmap = BitmapFactory.decodeFile(imagePath, null);
		iv_pic.setImageBitmap(bitmap);
		
		
	}
	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuItem add = menu.add(0, 0, 0, "发布");
		add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
			
		}
		
		
	}
	
}

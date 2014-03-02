package cc.itbox.babysay.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import cc.itbox.babysay.R;
import cc.itbox.babysay.util.BitmapUtils;
import cc.itbox.babysay.util.MediaUtils;
import cc.itbox.babysay.util.PhotoProcessing;

/**
 * 滤镜界面
 * @author yanchenling
 *
 */
public class PhotoProcessingActivity extends BaseActivity {

	private ImageView iv_photo;
	private Bitmap mBitmap;
	
	@Override
	@SuppressLint("CommitTransaction")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photoprocessing);
		
		initUI();
		setClick();
		initData();
		
	}
	private void initUI() {
		iv_photo=(ImageView) findViewById(R.id.iv_photo);
		
	}

	private void setClick() {
		
	}
	private void initData() {
		Intent intent = getIntent();
		String imagePath = intent.getStringExtra("image_path");
		loadPhoto(imagePath);
		iv_photo.setImageBitmap(mBitmap);
		
		
	}
	
	private void loadPhoto(String path) {
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		
		if (mBitmap != null) {
			mBitmap.recycle();
		}
		
		mBitmap = BitmapUtils.getSampledBitmap(path, displayMetrics.widthPixels, displayMetrics.heightPixels);
		
//		if (mBitmap != null && !mBitmap.isMutable()) {
//			mBitmap = PhotoProcessing.makeBitmapMutable(mBitmap);
//		}
		
		int angle = MediaUtils.getExifOrientation(path);
//		mBitmap = PhotoProcessing.rotate(mBitmap, angle);
		
//		enableFilterEditAndSaveButtons();
	}
}

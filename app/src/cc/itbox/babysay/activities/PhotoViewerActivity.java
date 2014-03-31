package cc.itbox.babysay.activities;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import cc.itbox.babysay.R;
import cc.itbox.babysay.util.UIUtil;

/**
 * 图片处理Activity
 * 
 * @author malinkang 2014-3-28
 * 
 */
public class PhotoViewerActivity extends BaseActivity {
	private ImageView mPhotoIv;
	private Bitmap mBitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_viewer);
		mPhotoIv = UIUtil.getView(this, R.id.iv_photo);
		// mBitmap = getIntent().getParcelableExtra("photo");
		Uri uri = getIntent().getData();
		mPhotoIv.setImageURI(uri);
		mPhotoIv.setImageBitmap(mBitmap);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.actionbar_photo_viewer, menu);
		return super.onCreateOptionsMenu(menu);
	}
}

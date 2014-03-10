package cc.itbox.babysay.activities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import cc.itbox.babysay.R;
import cc.itbox.babysay.util.BitmapUtils;
import cc.itbox.babysay.util.MediaUtils;
import cc.itbox.babysay.util.PhotoProcessing;

/**
 * 滤镜界面
 * @author yanchenling
 *
 */
public class PhotoProcessingActivity extends BaseActivity implements OnClickListener {

	private ImageView iv_photo;
	private Bitmap mBitmap;
	private ImageView iv_backg1,iv_backg2,iv_backg3,iv_backg4,iv_backg5,iv_backg6,iv_backg7,iv_backg8,iv_backg9,iv_backg10,iv_backg11;
	private TextView tv_original,tv_sentimental,tv_permeate,tv_dawn,tv_black,tv_xpro,tv_latte,tv_turmeric,tv_glaze,tv_wormwood,tv_subside;
	private static FilterTask sFilterTask;
	private ProgressDialog mProgressDialog;
	private String imagePath;	
	private ArrayList<Integer> mEditActions = new ArrayList<Integer>();
	private HorizontalScrollView hsv_item;
	
//	原图 青涩 渗透 破晓 黑白 X-pro 拿铁 姜黄 琉璃 艾草 沉降
	
	@Override
	@SuppressLint("CommitTransaction")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photoprocessing);
		
		initUI();
		setClick();
		initData();
		
	}
	
	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuItem add = menu.add(0, 0, 0, "确定");
		add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			Intent intent = new Intent();
			intent.setClass(this, EditActivity.class);
			String path = getCacheDir()+"/cached.jpg";
			
			intent.putExtra("path", path);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
			
		}
		
		
	}
	
	private void initUI() {
		iv_photo=(ImageView) findViewById(R.id.iv_photo);
		iv_backg1=(ImageView) findViewById(R.id.iv_backg1);
		iv_backg2=(ImageView) findViewById(R.id.iv_backg2);
		iv_backg3=(ImageView) findViewById(R.id.iv_backg3);
		iv_backg4=(ImageView) findViewById(R.id.iv_backg4);
		iv_backg5=(ImageView) findViewById(R.id.iv_backg5);
		iv_backg6=(ImageView) findViewById(R.id.iv_backg6);
		iv_backg7=(ImageView) findViewById(R.id.iv_backg7);
		iv_backg8=(ImageView) findViewById(R.id.iv_backg8);
		iv_backg9=(ImageView) findViewById(R.id.iv_backg9);
		iv_backg10=(ImageView) findViewById(R.id.iv_backg10);
		iv_backg11=(ImageView) findViewById(R.id.iv_backg11);
		
		tv_original=(TextView) findViewById(R.id.tv_original);
		tv_sentimental=(TextView) findViewById(R.id.tv_sentimental);
		tv_permeate=(TextView) findViewById(R.id.tv_permeate);
		tv_dawn=(TextView) findViewById(R.id.tv_dawn);
		tv_black=(TextView) findViewById(R.id.tv_black);
		tv_xpro=(TextView) findViewById(R.id.tv_xpro);
		tv_latte=(TextView) findViewById(R.id.tv_latte);
		tv_turmeric=(TextView) findViewById(R.id.tv_turmeric);
		tv_glaze=(TextView) findViewById(R.id.tv_glaze);
		tv_wormwood=(TextView) findViewById(R.id.tv_wormwood);
		tv_subside=(TextView) findViewById(R.id.tv_subside);
		
		hsv_item=(HorizontalScrollView) findViewById(R.id.hsv_item);
		
		
		
	}

	private void setClick() {
		
		tv_original.setOnClickListener(this);
		tv_sentimental.setOnClickListener(this);
		tv_permeate.setOnClickListener(this);
		tv_dawn.setOnClickListener(this);
		tv_black.setOnClickListener(this);
		tv_xpro.setOnClickListener(this);
		tv_latte.setOnClickListener(this);
		tv_turmeric.setOnClickListener(this);
		tv_glaze.setOnClickListener(this);
		tv_wormwood.setOnClickListener(this);
		tv_subside.setOnClickListener(this);
		
		iv_photo.setOnClickListener(this);
		
	}
	private void initData() {
		Intent intent = getIntent();
		imagePath = intent.getStringExtra("image_path");
		Bitmap bitmap = BitmapFactory.decodeFile(imagePath, null);
		saveToCache(bitmap);
		loadPhoto(imagePath);
		iv_photo.setImageBitmap(mBitmap);
		
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_original:
			v.setTag(v.getId(), iv_backg1);
			setBackground(v);
			sFilterTask = new FilterTask(PhotoProcessingActivity.this);
			sFilterTask.execute(0);
			break;
		case R.id.tv_sentimental:
			v.setTag(v.getId(),iv_backg2);
			setBackground(v);
			sFilterTask = new FilterTask(PhotoProcessingActivity.this);
			sFilterTask.execute(1);
			break;
		case R.id.tv_permeate:
			v.setTag(v.getId(), iv_backg3);
			setBackground(v);
			sFilterTask = new FilterTask(PhotoProcessingActivity.this);
			sFilterTask.execute(2);
			break;
		case R.id.tv_dawn:
			v.setTag(v.getId(), iv_backg4);
			setBackground(v);
			sFilterTask = new FilterTask(PhotoProcessingActivity.this);
			sFilterTask.execute(3);
			break;
		case R.id.tv_black:
			v.setTag(v.getId(), iv_backg5);
			setBackground(v);
			sFilterTask = new FilterTask(PhotoProcessingActivity.this);
			sFilterTask.execute(4);
			break;
		case R.id.tv_xpro:
			v.setTag(v.getId(), iv_backg6);
			setBackground(v);
			sFilterTask = new FilterTask(PhotoProcessingActivity.this);
			sFilterTask.execute(5);
			break;
		case R.id.tv_latte:
			v.setTag(v.getId(),iv_backg7);
			setBackground(v);
			sFilterTask = new FilterTask(PhotoProcessingActivity.this);
			sFilterTask.execute(6);
			break;
		case R.id.tv_turmeric:
			v.setTag(v.getId(),iv_backg8);
			setBackground(v);
			sFilterTask = new FilterTask(PhotoProcessingActivity.this);
			sFilterTask.execute(7);
			break;
		case R.id.tv_glaze:
			v.setTag(v.getId(), iv_backg9);
			setBackground(v);
			sFilterTask = new FilterTask(PhotoProcessingActivity.this);
			sFilterTask.execute(8);
			break;
		case R.id.tv_wormwood:
			v.setTag(v.getId(), iv_backg10);
			setBackground(v);
			sFilterTask = new FilterTask(PhotoProcessingActivity.this);
			sFilterTask.execute(9);
			break;
		case R.id.tv_subside:
			v.setTag(v.getId(), iv_backg11);
			setBackground(v);
			sFilterTask = new FilterTask(PhotoProcessingActivity.this);
			sFilterTask.execute(10);
			break;
		case R.id.iv_photo:
			if(hsv_item.isShown()){
			TranslateAnimation animation = new TranslateAnimation(0, 0, 0, 150);
			animation.setDuration(100);
			hsv_item.startAnimation(animation);
			hsv_item.setVisibility(View.INVISIBLE);
			}else{
				TranslateAnimation animation = new TranslateAnimation(0, 0, 150, 0);
				animation.setDuration(100);
				hsv_item.startAnimation(animation);
				hsv_item.setVisibility(View.VISIBLE);
			}
			
			break;
		default:
			break;
		}
		
		
		
	}
	
	private void setBackground(View v){
		
		iv_backg1.setVisibility(View.INVISIBLE);
		iv_backg2.setVisibility(View.INVISIBLE);
		iv_backg3.setVisibility(View.INVISIBLE);
		iv_backg4.setVisibility(View.INVISIBLE);
		iv_backg5.setVisibility(View.INVISIBLE);
		iv_backg6.setVisibility(View.INVISIBLE);
		iv_backg7.setVisibility(View.INVISIBLE);
		iv_backg8.setVisibility(View.INVISIBLE);
		iv_backg9.setVisibility(View.INVISIBLE);
		iv_backg10.setVisibility(View.INVISIBLE);
		iv_backg11.setVisibility(View.INVISIBLE);
		
		ImageView view = (ImageView) v.getTag(v.getId());
		
		view.setVisibility(View.VISIBLE);
		
		
	}
	private void showFilterProgressDialog() {
		mProgressDialog=null;
		mProgressDialog = ProgressDialog.show(PhotoProcessingActivity.this, "", "请稍后...");
		
	}
	private void showTempPhotoInImageView() {
		if (mBitmap != null) {
			Bitmap bitmap = Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth()/4, mBitmap.getHeight()/4, true);
			iv_photo.setImageBitmap(bitmap);
		}
	}
	private static class FilterTask extends AsyncTask<Integer, Void, Bitmap> {
		WeakReference<PhotoProcessingActivity> mActivityRef;
		
		public FilterTask(PhotoProcessingActivity activity) {
			mActivityRef = new WeakReference<PhotoProcessingActivity>(activity);
		}
		
		public void reattachActivity(PhotoProcessingActivity activity) {
			mActivityRef = new WeakReference<PhotoProcessingActivity>(activity);
			if (getStatus().equals(Status.RUNNING)) {
				activity.showFilterProgressDialog();
			}
		}
		
		private PhotoProcessingActivity getActivity() {
			if (mActivityRef == null) {
				return null;
			}
			
			return mActivityRef.get();
		}
		
		@Override
		protected void onPreExecute() {
			PhotoProcessingActivity activity = getActivity();
			if (activity != null) {
				activity.showFilterProgressDialog();
				activity.showTempPhotoInImageView();
			}
		}
		
		@Override
		protected Bitmap doInBackground(Integer... params) {		
			PhotoProcessingActivity activity = getActivity();
			
			if (activity != null) {
				activity.loadPhoto(activity.imagePath);
				int position = params[0];
				Bitmap bitmap = PhotoProcessing.filterPhoto(activity.mBitmap, position);
				for (Integer editAction : activity.mEditActions) {
					bitmap = PhotoProcessing.applyEditAction(bitmap, editAction);
				}
				activity.saveToCache(bitmap);
				
				return bitmap;
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {	
			PhotoProcessingActivity activity = getActivity();
			if (activity != null) {
				activity.mBitmap = result;
				activity.iv_photo.setImageBitmap(result);
				activity.hideProgressDialog();
			}
		}
	}
	private void hideProgressDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}

	
	private void saveToCache(Bitmap bitmap) {
		if (bitmap == null || bitmap.isRecycled()) {
			return;
		}
		
		File cacheFile = new File(getCacheDir(), "cached.jpg");
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(cacheFile);
		} catch (FileNotFoundException e) {
			// do nothing
		} finally {
			if (fos != null) {
				bitmap.compress(CompressFormat.JPEG, 100, fos);
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					// Do nothing
				}
			}
		}
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

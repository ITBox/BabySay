package cc.itbox.babysay.fragments;

import java.io.File;
import java.io.IOException;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.TextView;
import cc.itbox.babysay.R;
import cc.itbox.babysay.activities.MainActivity.BaseFragment;
import cc.itbox.babysay.activities.CropImageActivity;
import cc.itbox.babysay.activities.PhotoProcessingActivity;
import cc.itbox.babysay.adapter.DMListAdapter;
import cc.itbox.babysay.util.Constants;
import cc.itbox.babysay.util.ImageUtils;

/**
 * 主页页面
 * 
 * @author baoyz
 * 
 *         2014-2-22 下午10:46:35
 * 
 */
public class MainPageFragment extends BaseFragment implements
		OnRefreshListener, OnScrollListener {

	private PullToRefreshLayout mPullToRefreshLayout;
	private ListView mListView;
	private View alertView;
	private AlertDialog dialog;
	private TextView tv_photo,tv_pic;
	private Uri imageFileUri;
	
	
	
	private final String TEMP_FEED_IMAGE_PATH = Environment
			.getExternalStorageDirectory() + "/BabySay/TEMP_FEED_IMAGE.jpg";
	private DMListAdapter mAdapter;
	private boolean hasMore;
	private boolean isLoad;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main_page, container,
				false);
		mListView = (ListView) view.findViewById(R.id.lv_dm);
		// 设置适配器
		mAdapter = new DMListAdapter(getActivity());
		mListView.setAdapter(mAdapter);
		hasMore = true;
		mListView.setOnScrollListener(this);
		mPullToRefreshLayout = new PullToRefreshLayout(getActivity());
		// 设置下拉刷新
		ActionBarPullToRefresh.from(getActivity())
				.insertLayoutInto((ViewGroup) view)
				.theseChildrenArePullable(R.id.lv_dm, android.R.id.empty)
				.listener(this).setup(mPullToRefreshLayout);
		
		alertView=inflater.inflate(R.layout.dialog_takephoto, null);
		
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// 创建显示导航菜单时的Actionbar菜单
		inflater.inflate(R.menu.main_page, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_refresh:
			// 刷新
			mPullToRefreshLayout.setRefreshing(true);
			break;
		case R.id.action_new_content:
//			if(dialog==null)
//			dialog = new AlertDialog.Builder(getActivity()).setView(alertView).create();
//			dialog.show();
			
			getPic();
			
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	

	@Override
	public void onRefreshStarted(View view) {

		// 开始下拉刷新
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (isLoad) {
					// 加载数据
				} else {
					// 刷新数据
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				// 设置下拉刷新完成
				mPullToRefreshLayout.setRefreshComplete();
				if (isLoad) {
					hasMore = false;
					isLoad = false;
				}
				if (getView() != null) {
				}
			}
		}.execute();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==getActivity().RESULT_OK){
			Intent intent;
			switch (requestCode) {
			case Constants.Request_Code.TAKE_PICTURE_FROM_CAMERA:
				
				intent = new Intent(getActivity(), CropImageActivity.class);
				// x方向的比例
				intent.putExtra("aspectX", 1);
				// y方向的比例
				intent.putExtra("aspectY", 1);
				// 输出图片大小
				intent.putExtra("outputX", 400);

				intent.putExtra("outputY", 400);
				// 是否保存比例
				intent.putExtra("scale", true);

				intent.setData(imageFileUri);

				startActivityForResult(intent,
						Constants.Request_Code.CROP_CAMERA_PICTURE);
				
				break;
			case Constants.Request_Code.TAKE_PICTURE_FROM_GALLERY:
				// 进入裁剪页面
				intent = new Intent(getActivity(), CropImage.class);
				// x方向的比例
				intent.putExtra("aspectX", 1);
				// y方向的比例
				intent.putExtra("aspectY", 1);
				// 输出图片大小
				intent.putExtra("outputX", 400);

				intent.putExtra("outputY", 400);
				// 是否保存比例
				intent.putExtra("scale", true);

				intent.setDataAndType(data.getData(), "image/jpeg");

				startActivityForResult(intent,
						Constants.Request_Code.CROP_GALLERY_PICTURE);
				
				
				break;
			case Constants.Request_Code.CROP_CAMERA_PICTURE:
				
				File dir = new File(Environment.getExternalStorageDirectory()+"/BabySay");
				if(!dir.exists())
					dir.mkdirs();
				File file = new File(TEMP_FEED_IMAGE_PATH);
				if(!file.exists())
					try {
						file.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				
//				// 裁剪相机拍摄的照片完成，进入滤镜界面
				intent = new Intent(getActivity(), PhotoProcessingActivity.class);
//				// 将bitmap转换为file
				ImageUtils.Bitmap2File(
						(Bitmap) data.getParcelableExtra("data"),
						TEMP_FEED_IMAGE_PATH);
				intent.putExtra("image_path", TEMP_FEED_IMAGE_PATH);
//				startActivityForResult(intent,Constants.Request_Code.CAMERA_PICTURE_RECORD);
				startActivity(intent);
				
				break;
			case Constants.Request_Code.CROP_GALLERY_PICTURE:
				// 裁剪相册的照片完成，进入录音界面
				intent = new Intent(getActivity(), PhotoProcessingActivity.class);
				// 将bitmap转换为file
				ImageUtils.Bitmap2File(
						(Bitmap) data.getParcelableExtra("data"),
						TEMP_FEED_IMAGE_PATH);
				intent.putExtra("image_path", TEMP_FEED_IMAGE_PATH);
//				startActivityForResult(intent,Constants.Request_Code.GALLERY_PICTURE_RECORD);
				startActivity(intent);
				break;

			default:
				break;
			}
			
			
		}
		
	}

	
	private void getPic() {
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setItems(new String[] { "拍照", "从图库选择" },
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						switch (which) {
						case 0:
							imageFileUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
											new ContentValues());
							if (imageFileUri != null) {
								Intent intent = new Intent(
										android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
								intent.putExtra(
										android.provider.MediaStore.EXTRA_OUTPUT,
										imageFileUri);
								// 开启系统拍照的Activity
								startActivityForResult(
										intent,
										Constants.Request_Code.TAKE_PICTURE_FROM_CAMERA);
							}
							break;
						case 1:
							Intent intent = new Intent(
									"android.intent.action.PICK");
							intent.setType("image/*");
							startActivityForResult(
									intent,
									Constants.Request_Code.TAKE_PICTURE_FROM_GALLERY);
							break;
						}

					}
				}).show();
	}
	
	

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		switch (scrollState) {
		case OnScrollListener.SCROLL_STATE_IDLE:
			// 停止滑动
			if (hasMore
					&& view.getLastVisiblePosition() == (view.getCount() - 1)) {
				// 滚动到最后一条记录，开始加载
				isLoad = true;
				mPullToRefreshLayout.setRefreshing(true);
			}
			break;
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

	}
}

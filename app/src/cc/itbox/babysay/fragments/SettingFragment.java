package cc.itbox.babysay.fragments;

import java.util.ArrayList;

import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.app.ProgressDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cc.itbox.babysay.CatchInfo;
import cc.itbox.babysay.ErrorInfo;
import cc.itbox.babysay.R;
import cc.itbox.babysay.activities.MainActivity.BaseFragment;
import cc.itbox.babysay.activities.SetOpionActivity;
import cc.itbox.babysay.api.CheckVersionApi;
import cc.itbox.babysay.api.OnCatchHandler;
import cc.itbox.babysay.bean.VersionBean;
import cc.itbox.babysay.util.Constants;
import cc.itbox.babysay.util.UIUtil;

/**
 * 设置页面
 * 
 * @author youzh 2014-3-2 下午5:27:18
 */
public class SettingFragment extends BaseFragment implements OnCatchHandler {

	private ListView mLVSet;
	private SetAdapter adapter;
	private ProgressDialog progressDialog;
	private ArrayList<VersionBean> mVersionList = new ArrayList<VersionBean>();
	private VersionBean versionBean;

	private String[] titleStr = new String[] { "清理缓存", "检查更新", "反馈意见" };

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Constants.MSG_DATASET_NOTIFY_1:
				progressDialog.dismiss();
				if(UIUtil.compareVersion(versionBean.getVersionName())){
					// 弹框
					showUpdateDialog(versionBean.getVersionDescribe());
				} else {
					UIUtil.showShortToast(mActThis, "已经是最新版");
				}
				break;
			case Constants.MSG_TOAST_SHORT:
				progressDialog.dismiss();
				UIUtil.showShortToast(mActThis, "获取版本信息失败");
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		progressDialog = new ProgressDialog(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = null;
		if (container != null) {
			view = inflater.inflate(R.layout.fragment_setting, container, false);
			mLVSet = UIUtil.getView(view, R.id.fragment_set_list);
			adapter = new SetAdapter();
			mLVSet.setAdapter(adapter);
			mLVSet.setOnItemClickListener(mSetItemClickListener);
		}
		return view;
	}

	private class SetAdapter extends BaseAdapter {

		private int selectedposition = -1;

		public void setSelectedposition(int selectedposition) {
			this.selectedposition = selectedposition;
		}

		@Override
		public int getCount() {
			return titleStr.length;
		}

		@Override
		public Object getItem(int arg0) {
			return titleStr[arg0];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			SetHolder holder = null;
			if (convertView == null) {
				holder = new SetHolder();
				convertView = View.inflate(getActivity(), R.layout.fragment_item_setting, null);
				holder.line = UIUtil.getView(convertView, R.id.set_item_clear_line);
				holder.tv = UIUtil.getView(convertView, R.id.set_item_clear_tv);
				convertView.setTag(holder);
			} else {
				holder = (SetHolder) convertView.getTag();
			}
			holder.tv.setText(titleStr[position]);

			if (selectedposition == position) {
				holder.line.setBackgroundResource(R.drawable.common_red_line);
				holder.tv.setTextColor(getResources().getColor(R.color.red_line));
			} else {
				holder.line.setBackgroundResource(R.drawable.common_gray_line);
				holder.tv.setTextColor(Color.rgb(0, 0, 0));
			}
			return convertView;
		}
	}

	class SetHolder {
		TextView tv;
		TextView line;
	}

	private AdapterView.OnItemClickListener mSetItemClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			adapter.setSelectedposition(position);
			adapter.notifyDataSetChanged();
			switch (position) {
			case 0:
				progressDialog.setMessage("清理中...");
				progressDialog.setCanceledOnTouchOutside(true);
				progressDialog.show();
				mLoader.clearDiscCache();
				mLoader.clearMemoryCache();
				break;
			case 1:
				showUpdateDialog("dfdfdsfsd");
//				progressDialog.setMessage("检查中...");
//				progressDialog.setCanceledOnTouchOutside(true);
//				progressDialog.show();
				// 连接服务器
				CatchInfo ci = new CatchInfo();
//				CheckVersionApi versionApi = new CheckVersionApi(mActThis, Constants.VISIT_SERVER_GET_VERSION, ci, SettingFragment.this);
//				versionApi.requestVersion();
				break;
			case 2:
				startActivity(new Intent(getActivity(), SetOpionActivity.class));
				break;
			default:
				break;
			}
		}
	};

	private void showUpdateDialog(String versionDescribe) {
		// TODO Auto-generated method stub
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(mActThis);
		alertDialog.setTitle("发现新版本");
		alertDialog.setMessage(versionDescribe);
		alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		alertDialog.setPositiveButton("更新", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		alertDialog.show();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onCatchSucc(int type, CatchInfo ci, Object result) {
		switch (type) {
		case Constants.VISIT_SERVER_GET_VERSION:
			mVersionList.clear();
			ArrayList<VersionBean> itemList = (ArrayList<VersionBean>) result;
			if (itemList != null && itemList.size() > 0) {
				versionBean = itemList.get(0);
				itemList.clear();
			}
			mHandler.sendEmptyMessage(Constants.MSG_DATASET_NOTIFY_1);
			break;

		default:
			break;
		}

	}

	@Override
	public void onCatchFail(int type, CatchInfo ci, ErrorInfo ei) {
		switch (type) {
		case Constants.VISIT_SERVER_GET_VERSION:
			mHandler.sendEmptyMessage(Constants.MSG_TOAST_SHORT);
			break;

		default:
			break;
		}

	}
}

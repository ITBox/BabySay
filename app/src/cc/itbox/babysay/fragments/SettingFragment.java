package cc.itbox.babysay.fragments;

import org.holoeverywhere.app.ProgressDialog;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cc.itbox.babysay.R;
import cc.itbox.babysay.activities.MainActivity.BaseFragment;
import cc.itbox.babysay.activities.SetOpionActivity;
import cc.itbox.babysay.util.UIUtil;

/**
 * 设置页面
 * @author youzh
 * 2014-3-2 下午5:27:18
 */
public class SettingFragment extends BaseFragment {

    private ListView mLVSet;
    private SetAdapter adapter;
    private ProgressDialog progressDialog;
    
    private String [] titleStr = new String []{"清理缓存", "检查更新", "反馈意见"}; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		progressDialog = new ProgressDialog(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = null;
		if(container != null) {
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
            if(convertView == null) {
            	holder = new SetHolder();
            	convertView = View.inflate(getActivity(), R.layout.fragment_item_setting, null);
            	holder.line = UIUtil.getView(convertView, R.id.set_item_clear_line);
    			holder.tv = UIUtil.getView(convertView, R.id.set_item_clear_tv);
    			convertView.setTag(holder);
            } else {
            	holder = (SetHolder) convertView.getTag();
            }
            holder.tv.setText(titleStr[position]);
            
            if(selectedposition == position) {
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
				break;
			case 1:
				progressDialog.setMessage("检查中...");
				progressDialog.setCanceledOnTouchOutside(true);
				progressDialog.show();
				break;
			case 2:
				startActivity(new Intent(getActivity(), SetOpionActivity.class));
				break;
			default:
				break;
			}
		}
	};
}

package cc.itbox.babysay.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import cc.itbox.babysay.R;
import cc.itbox.babysay.bean.DMBean;

/**
 * 动态信息列表适配器
 * 
 * @author baoyz 
 * 
 * 2014-2-23 下午4:42:54
 *
 */
public class DMListAdapter extends BaseAdapter {
	
	private List<DMBean> mList;
	private Context mContext;
	
	public DMListAdapter(Context context) {
		mContext = context;
		// 初始化数据
		mList = new ArrayList<DMBean>();
		for (int i = 0; i < 20; i++) {
			mList.add(new DMBean());
		}
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.list_item_dm, null);
			convertView.setTag(new ViewHolder(convertView));
		}
		// 绑定数据
		doBindView(convertView);
		return convertView;
	}

	/**
	 * 绑定View数据
	 * @param convertView
	 */
	private void doBindView(View convertView) {
		
	}

	/**
	 * View缓存
	 * @author baoyz 
	 * 
	 * 2014-3-2 下午12:14:41
	 *
	 */
	static class ViewHolder{
		public ViewHolder(View view) {
		}
	}
}

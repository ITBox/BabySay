package cc.itbox.babysay.fragments;

import org.holoeverywhere.widget.Toast;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import cc.itbox.babysay.R;
import cc.itbox.babysay.activities.BaseActivity;
import cc.itbox.babysay.activities.MainActivity.BaseFragment;
import cc.itbox.babysay.activities.UserInfoEditActivity;
import cc.itbox.babysay.ui.ElasticScrollView;

/**
 * 用户中心页面
 * 
 * @author baoyz
 * 
 *         2014-2-22 下午10:42:28
 * 
 */
public class UserCenterFragment extends BaseFragment implements OnClickListener {

	private View contentView;
	private ImageView mIvHeader;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(contentView == null){
			contentView = inflater.inflate(R.layout.fragment_user_center, container, false);
			initView();
		}
		return contentView;
	}

	private void initView() {
		ElasticScrollView mScrollview = (ElasticScrollView)contentView.findViewById(R.id.user_scrollview);
		mScrollview.setElasticView(contentView.findViewById(R.id.user_iv_background));
		mIvHeader = (ImageView)contentView.findViewById(R.id.user_iv_head);
		mIvHeader.setOnClickListener(this);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// 创建显示导航菜单时的Actionbar菜单
		inflater.inflate(R.menu.main_page, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.user_iv_head:
			Intent intent = new Intent(getActivity(), UserInfoEditActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}

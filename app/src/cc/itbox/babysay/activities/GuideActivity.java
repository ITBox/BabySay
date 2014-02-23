package cc.itbox.babysay.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import cc.itbox.babysay.R;
import cc.itbox.babysay.fragments.GuideFragment;
import cc.itbox.babysay.ui.GuideIndicator;
import cc.itbox.babysay.util.PreferenceUtil;
import cc.itbox.babysay.util.UIUtil;

/**
 * 
 * @author malinkang 2014-2-22 下午2:37:16
 * 
 */

public class GuideActivity extends FragmentActivity implements OnClickListener {

	private int[] imageIds = { R.drawable.biz_audio_bg_am,
			R.drawable.biz_audio_bg_pm, R.drawable.biz_audio_bg_night };
	private Context mContext;
	private ViewPager mViewPager;
	private GuidePagerAdapter mPagerAdapter;
	private GuideIndicator mGuideIndicator;
	private LinearLayout mLayout;
	private ImageButton mRegisterBtn;
	private ImageButton mLoginBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.activity_guide);
		mViewPager = UIUtil.getView(this, R.id.viewpager);
		mGuideIndicator = UIUtil.getView(this, R.id.indicator);
		mRegisterBtn = UIUtil.getView(this, R.id.btn_register);
		mRegisterBtn.setOnClickListener(this);
		mLoginBtn = UIUtil.getView(this, R.id.btn_login);
		mLoginBtn.setOnClickListener(this);
		mLayout = UIUtil.getView(this, R.id.layout);
		if (!PreferenceUtil.isFirstStart(mContext)) {
			mLayout.setVisibility(View.VISIBLE);
		}
		mPagerAdapter = new GuidePagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mPagerAdapter);
		mGuideIndicator.setPageCount(imageIds.length);
		mGuideIndicator
				.setOnPageSelectedListener(new GuideIndicator.OnPageSelectedListener() {
					@Override
					public void onPageStripSelected(int position) {
						position = Math.min(mPagerAdapter.getCount() - 1,
								position);
						if (mViewPager.getCurrentItem() != position) {
							mViewPager.setCurrentItem(position);
						}
					}
				});

		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						mGuideIndicator.setCurrentPage(position);
						// 最后一页
						if (position == imageIds.length - 1) {
							if (mLayout.getVisibility() == View.GONE
									&& PreferenceUtil.isFirstStart(mContext)) {
								mLayout.setVisibility(View.VISIBLE);
								mLayout.startAnimation(AnimationUtils
										.loadAnimation(mContext, R.anim.push_up));
								mGuideIndicator.startAnimation(AnimationUtils
										.loadAnimation(mContext, R.anim.push_up));
								PreferenceUtil.editFirstStart(mContext);
							}
						}
					}
				});

	}

	private class GuidePagerAdapter extends FragmentPagerAdapter {

		public GuidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			GuideFragment fragment = GuideFragment
					.newInstance(imageIds[position]);
			return fragment;
		}

		@Override
		public int getCount() {
			return imageIds.length;
		}

	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn_login:
		case R.id.btn_register:
			intent = new Intent(mContext, RegisterOrLoginActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

}

package com.test.viewdraghelper.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.test.viewdraghelper.R;
import com.test.viewdraghelper.drag.CircleViewPager;
import com.test.viewdraghelper.drag.CommonPageAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * name: MainFragment
 * desc: 包装viewpager 和指示器，循环也其他业务逻辑在这里处理
 * 这里应该有一个长按回调的接口
 * author:
 * date: 2018-03-27 11:00
 * remark:
 */
public class MainFragment extends Fragment implements IBaseFragment, ViewPager.OnPageChangeListener {

	protected View view;

	private String test;

//	private TextView tvTest;

	private CircleViewPager vpAction;

	private LinearLayout llytIndicator;

	protected CommonPageAdapter mAdapter;

	/**
	 * 当前位置
	 */
	private int currentIndex = 1 ;

	IBaseFragment callback;

	List<String> mUrls;

	private int currentPos;

	/**
	 * 指示器集合
	 */
	private ArrayList<ImageView> mIndicators;

	private List<Fragment> fragments = new ArrayList<>();

	public static MainFragment newInstance(String[] urls) {
		Bundle args = new Bundle();
		MainFragment fragment = new MainFragment();
		args.putStringArray("urls", urls);
		fragment.setArguments(args);
		return fragment;
	}

	public void setCallback(IBaseFragment callback) {
		this.callback = callback;
	}

	public void addPage(int index, Fragment fragment){
		if(mAdapter != null) {
			mAdapter.addPage(index, fragment);
		}
	}

	public void addPage(Fragment fragment){
		if(mAdapter != null) {
			mAdapter.addPage(fragment);
		}
	}

	public void delPage(int index) {
		if(mAdapter != null) {
			mAdapter.delPage(index);
		}
	}

	public void updatePage(String[] urls) {
		if(mAdapter != null) {
			handlerData(urls);
			mAdapter.updatePage(fragments);
			initIndicator(getItemCount());
			selectIndicator(0);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Bundle bundle = getArguments();
		if (bundle != null) {
			test = bundle.getString("test");
		}
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_main, null);
		Bundle bundle = getArguments();
		String[] urls = bundle.getStringArray("urls");//person 是之前设置好的key，此处为取出person对应的内容
		handlerData(urls);
		initView(view);
//		view.setOnLongClickListener(new View.OnLongClickListener() {
//			@Override
//			public boolean onLongClick(View v) {
//				Toast.makeText(getActivity(), "long click", Toast.LENGTH_SHORT).show();
//				boolean result = false;
//				if(callback != null)
//					result = callback.onLongClick(v);
//				return result;
//			}
//		});
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
	}


	/**
	 * 重新设置数据
	 * @param
	 * @return
	 */
	public void loadingData() {

	}

	/**
	 * 初始化界面
	 * @param
	 * @return
	 */
	private void initView(View view) {
		if(view != null) {
			vpAction = view.findViewById(R.id.vp_action);
			llytIndicator = view.findViewById(R.id.llyt_indicator);
			initIndicator(getItemCount()); // 减去首尾2个元素
//			for(int i = 0; i < mUrls.size(); i ++) {
//				TestFragment0 fragment = TestFragment0.newInstance(mUrls.get(i), i);
//				fragment.setCallback(this);
//				fragments.add(fragment);
//			}

			if (mAdapter == null) {
				mAdapter = new CommonPageAdapter(getChildFragmentManager(), fragments);
			}
//			vpAction.setOffscreenPageLimit(5);
			vpAction.setAdapter(mAdapter);
//			vpAction.setCurrentItem(currentIndex); // 初始显示在第二个位置
			vpAction.setPageChangeListener(this);
//			vpAction.setAutoPlay(true);
		}
	}

	@Override
	public boolean onLongClick(View v) {
		return callback != null && callback.onLongClick(v);
	}

	/**
	 * 处理无线循环，需要在数据的前后各插入一个item
	 */
	private void handlerData(String[] urls) {
		mUrls = new ArrayList<>(Arrays.asList(urls));
		mUrls.add(urls[0]);
		mUrls.add(0, urls[urls.length - 1]); // 在0的位置插入最后一条数据
		fragments.clear();
		for(int i = 0; i < mUrls.size(); i ++) {
			TestFragment0 fragment = TestFragment0.newInstance(mUrls.get(i), i);
			fragment.setCallback(this);
			fragments.add(fragment);
		}

	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//		System.out.println("===============> onPageScrolled");
	}

	@Override
	public void onPageSelected(int position) {
		selectIndicator(position);
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	/**
	 * 获取ViewPager显示item的数量
	 * @return
	 */
	public int getItemCount() {
		return fragments != null ? fragments.size() - 2 : 0;
	}

	/**
	 * 显示指示器
	 */
	/**
	 * 初始化指示器
	 */
	private void initIndicator(int count) {
		if(llytIndicator == null || count < 1)
			return ;
		if(mIndicators ==  null) {
			mIndicators = new ArrayList<>();
		}
		llytIndicator.removeAllViews();
		mIndicators.clear();
		llytIndicator.setVisibility(View.VISIBLE);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		for (int i = 0; i < count; i++) { // 2个页面才显示，1个页面不显示
			int dots_margin = dip2px(2.5f);
			ImageView indicator = new ImageView(getActivity());
			indicator.setLayoutParams(params);
			indicator.setImageResource(R.drawable.shape_gift_dot_unselected);
			indicator.setPadding(dots_margin, 0, dots_margin, 0);
			mIndicators.add(indicator);
			llytIndicator.addView(indicator);
		}
		currentPos = 0;
		selectIndicator(0);
	}

	/**
	 * 切换指示器
	 */
	private void selectIndicator(int index) {
		try {
			if (mIndicators != null && mIndicators.size() > index) {
				mIndicators.get(currentPos).setImageResource(R.drawable.shape_gift_dot_unselected);
				if (index >= 0 && index < mIndicators.size()) {
					currentPos = index;
					mIndicators.get(currentPos).setImageResource(R.drawable.shape_gift_dot_selected);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public int dip2px(float dpValue) {
		return (int) (dpValue * getDensity(getActivity()) + 0.5f);
	}


	/**
	 * 返回屏幕密度
	 */
	public float getDensity(Context context) {
		try {
			return context.getResources().getDisplayMetrics().density;
		} catch (Exception e){
			e.printStackTrace();
		}
		return 2.0f;
	}

}

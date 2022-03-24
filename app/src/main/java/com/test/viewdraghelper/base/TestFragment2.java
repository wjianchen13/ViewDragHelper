package com.test.viewdraghelper.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.test.viewdraghelper.R;


/**
 * name: TestFragment2
 * desc:
 * author:
 * date: 2018-03-27 11:00
 * remark:
 */
public class TestFragment2 extends Fragment {

	protected View view;

	private String test;

	private TextView tvTest;

	IBaseFragment callback;

	public void setCallback(IBaseFragment callback) {
		this.callback = callback;
	}

	public static TestFragment2 newInstance(String test) {
		Bundle args = new Bundle();
		TestFragment2 fragment = new TestFragment2();
		args.putString("test", test);
		fragment.setArguments(args);
		return fragment;
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
		view = inflater.inflate(R.layout.fragment_test2, null);
		initView(view);
		view.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				Toast.makeText(getActivity(), "long click", Toast.LENGTH_SHORT).show();
				boolean result = false;
				if(callback != null)
					result = callback.onLongClick(v);
				return result;
			}
		});
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	/**
	 * 清除界面状态
	 * @param
	 * @return
	 */
	public void clearState() {
		if(tvTest != null) {
			tvTest.setText("clear");
		}
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
			tvTest = view.findViewById(R.id.tv_test);
			tvTest.setText(test);
		}
	}

}

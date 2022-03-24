package com.test.viewdraghelper.base;

import android.os.Bundle;

/**
 * name: TestFragment0
 * desc:
 * author:
 * date: 2018-03-27 11:00
 * remark:
 */
public class TestFragment0 extends BaseWebViewFragment {

	public static TestFragment0 newInstance(String url, int num) {
		Bundle args = new Bundle();
		TestFragment0 fragment = new TestFragment0();
		args.putString("url", url);
		args.putString("num", "页面" + num);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	protected String getUrl() {
		Bundle bundle = getArguments();
		String url = bundle.getString("url");
		num = bundle.getString("num");
//		return "https://tapi.95xiu.com/web/new_active_web_view_match.php?v=3";
		return url;
	}

}

package com.test.viewdraghelper.drag;

import android.app.Service;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.test.viewdraghelper.R;
import com.test.viewdraghelper.base.IBaseFragment;
import com.test.viewdraghelper.base.Live2InterceptFrameLayout;
import com.test.viewdraghelper.base.MainFragment;
import com.test.viewdraghelper.base.TestFragment0;
import com.test.viewdraghelper.base.TestFragment1;
import com.test.viewdraghelper.base.TestFragment2;

import java.util.ArrayList;
import java.util.List;

public class DragActivity extends AppCompatActivity implements ViewPagerAdapter.CallBack, IBaseFragment {

    private DragLayout dlTest;
//    private AutoScrollViewPager viewPager ;
    private ViewPagerAdapter adapter ;
//    protected CommonPageAdapter mAdapter;
    private List<Fragment> fragments = new ArrayList<>();
    private Live2InterceptFrameLayout flytContest;

    private MainFragment mainFragment;

    private String[] urls = new String[]{"https://tapi.95xiu.com/web/new_active_web_view_match.php?v=3",
            "https://tapi.95xiu.com/web/new_active_web_view_annualgala.php"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);
//        initData();
//        viewPager = findViewById(R.id.viewpager);
        dlTest = findViewById(R.id.dl_test);
        flytContest = findViewById(R.id.flyt_contest);
        mainFragment = MainFragment.newInstance(urls);
        mainFragment.setCallback(this);
        //  commit方法是在Activity的onSaveInstanceState()之后调用的，这样会出错，改为commitAllowingStateLoss()
        getSupportFragmentManager().beginTransaction().add(R.id.flyt_contest, mainFragment).commitAllowingStateLoss();

//        if (mAdapter == null) {
//            mAdapter = new CommonPageAdapter(getSupportFragmentManager(), fragments);
//        }
//        viewPager.setAdapter(mAdapter);
//        viewPager.setAutoPlay(true);
    }

    public void onAdd(View v) {
        if(mainFragment != null) {
//            TestFragment0 testFragment0 = TestFragment0.newInstance("https://tapi.95xiu.com/web/new_active_web_view_annualgala.php", 101);
//            testFragment0.setCallback(this);
//            mainFragment.addPage(testFragment0);
//            urls = new String[]{"https://tapi.95xiu.com/web/new_active_web_view_match.php?v=3",
//                    "https://tapi.95xiu.com/web/new_active_web_view_annualgala.php",
//                    "https://tapi.95xiu.com/web/new_active_web_view_match.php?v=3",
//                    "https://tapi.95xiu.com/web/new_active_web_view_annualgala.php",
//                    "https://tapi.95xiu.com/web/new_active_web_view_match.php?v=3"};
            urls = new String[]{"https://tapi.95xiu.com/web/new_active_web_view_annualgala.php",
                    "https://tapi.95xiu.com/web/new_active_web_view_match.php?v=3",
                    "https://tapi.95xiu.com/web/new_active_web_view_annualgala.php"};
            mainFragment.updatePage(urls);
        }

    }

    public void onDelete(View v) {

        urls = new String[]{"https://tapi.95xiu.com/web/new_active_web_view_match.php?v=3",
                "https://tapi.95xiu.com/web/new_active_web_view_annualgala.php"};
        mainFragment.updatePage(urls);
//        mAdapter.delPage(3);
    }

    private void initData() {
//        fragments = new ArrayList<>();
//        fragments.add(TestFragment0.newInstance("0"));
        fragments.clear();
//        TestFragment1 testFragment1 = TestFragment1.newInstance("3");
//        testFragment1.setCallback(this);
//        TestFragment1 testFragment2 = TestFragment1.newInstance("1");
//        testFragment2.setCallback(this);
//        TestFragment2 testFragment3 = TestFragment2.newInstance("2");
//        testFragment3.setCallback(this);
//        TestFragment1 testFragment4 = TestFragment1.newInstance("3");
//        testFragment4.setCallback(this);
//        TestFragment1 testFragment5 = TestFragment1.newInstance("1");
//        testFragment5.setCallback(this);

//
//        fragments.add(testFragment1);
//        fragments.add(testFragment2);
//        fragments.add(testFragment3);
//        fragments.add(testFragment4);
//        fragments.add(testFragment5);
    }

    @Override
    public boolean onLongClick(View v) {

        Vibrator vib = (Vibrator)this.getSystemService(Service.VIBRATOR_SERVICE);
//			vibrator.vibrate(1000);//只震动一秒，一次
        if(vib != null) {
            long[] pattern = {0, 200};
            vib.vibrate(pattern, -1);
        }
        flytContest.setStatus(false);
        dlTest.setInterceptTouchEvent(true);
        return true;
    }
}

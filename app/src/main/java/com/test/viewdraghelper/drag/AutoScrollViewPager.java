package com.test.viewdraghelper.drag;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.test.viewdraghelper.R;

/**
 * 封装的viewpager
 * Created by WZH on 2017/12/14.
 */

public class AutoScrollViewPager<T extends PagerAdapter> extends FrameLayout {
    private ViewPager viewPager ;
    private PagerAdapter mAdapter ;
//    private LinearLayout mLinearLayout ;
    private Context mContext ;
    private int pageMargin = 0 ;
    private int oldPosition = 0 ; //上一个位置
    private int currentIndex = 1 ; //当前位置
    private static long time = 3000 ; //自动播放时间
    private static boolean autoPlay = true ; //是否自动播放

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            play();
        }
    };
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            viewPager.setCurrentItem(++currentIndex);
        }
    };

    public AutoScrollViewPager(@NonNull Context context) {
        this(context,null);
    }

    public AutoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AutoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        init1();
    }

    /**
     * 动态添加viewpager和小圆点
     */
    private void init() {
        mContext = getContext() ;
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        setClipChildren(false);
//        pageMargin = getResources().getDimensionPixelSize(R.dimen.page_margin);

        viewPager = new ViewPager(mContext);
        viewPager.setId(R.id.view_pager);
        LayoutParams vparams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        vparams.leftMargin = pageMargin * 2;
        vparams.rightMargin = pageMargin * 2 ;
        viewPager.setLayoutParams(vparams);
        addView(viewPager);

//        mLinearLayout = new LinearLayout(mContext);
//        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.gravity = Gravity.BOTTOM ;
//        mLinearLayout.setGravity(Gravity.CENTER);
//        mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
//        addView(mLinearLayout,params);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch(action) {
            case MotionEvent.ACTION_DOWN:
                setAutoPlay(false);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        play();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancel();
    }

    /**
     * 初始化viewpager
     */
    private void initViewPager() {
        if (mAdapter==null){
            return;
        }
        viewPager.setCurrentItem(currentIndex);
        //设置红缓存的页面数
        viewPager.setOffscreenPageLimit(1);
        // 设置2张图之前的间距。
        viewPager.setPageMargin(pageMargin);
//        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,int positionOffsetPixels) {
                System.out.println("===============> onPageScrolled");
            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    currentIndex = mAdapter.getCount()-2 ;
                }else if (position == mAdapter.getCount()-1){
                    currentIndex = 1 ;
                }else {
                    currentIndex = position ;
                }
                //此处currentIndex是从1开始的，要注意
//                mLinearLayout.getChildAt(oldPosition).setEnabled(false);
//                mLinearLayout.getChildAt(currentIndex-1).setEnabled(true);
                oldPosition = currentIndex-1 ;
                Log.e("wenzhihao","position=="+position+",currentIndex="+currentIndex);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state==ViewPager.SCROLL_STATE_IDLE){
                    viewPager.setCurrentItem(currentIndex,false); // 切换到首尾的时候自动改变
                    play();
                }else if(state==ViewPager.SCROLL_STATE_DRAGGING){
                    cancel();
                }
            }
        });
        setIndicatorDot();
//        mLinearLayout.getChildAt(0).setEnabled(true);
    }

    /**
     * 播放，根据autoplay
     */
    public void play(){
        if (autoPlay){
            handler.postDelayed(runnable,time);
        }else {
            handler.removeCallbacks(runnable);
        }
    }
    /**
     * 取消播放
     */
    public void cancel(){
        handler.removeCallbacks(runnable);
    }

    /**
     * 设置小圆点
     */
    private void setIndicatorDot() {
        for (int i = 0;i<mAdapter.getCount()-2;i++){
            //添加底部灰点
            View v = new View(mContext);
            v.setBackgroundResource(R.drawable.bg_dot);
            v.setEnabled(false);
            //指定其大小
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20,20);
            if (i != 0)
                params.leftMargin = 20;
            params.bottomMargin = 20;
            v.setLayoutParams(params);
//            mLinearLayout.addView(v);
        }
    }

    /**
     * 设置适配器
     * @param adpter
     */
    public void setAdapter(T adpter){
        mAdapter = adpter ;
        viewPager.setAdapter(mAdapter);
        initViewPager();
    }

    /**
     * 设置是否自动播放
     * @param autoPlay
     */
    public void setAutoPlay(boolean autoPlay) {
        AutoScrollViewPager.autoPlay = autoPlay;
        if (!autoPlay){
            handler.removeCallbacks(runnable);
        }
    }


    //

    private void init1() {
        setStatus1(true);
    }

    public void setStatus1(boolean flag){
        setClickable(flag);
        setFocusable(flag);
        setFocusableInTouchMode(flag);
        isTouchEvent = flag;
    }

    public void setStatus(boolean flag){
        setClickable(flag);
        setFocusable(flag);
        setFocusableInTouchMode(flag);
        getParent().requestDisallowInterceptTouchEvent(flag);
        isTouchEvent = flag;
    }

    boolean isTouchEvent = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return isTouchEvent ? true : super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_SCROLL:
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                getParent().requestDisallowInterceptTouchEvent(true);
                isTouchEvent = true;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                isTouchEvent = false;
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


}
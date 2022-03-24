package com.test.viewdraghelper.drag;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
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
 * 循环 viewpager
 *
 */
public class CircleViewPager extends ViewPager {

    private PagerAdapter mAdapter ;
    private Context mContext ;
    private int pageMargin = 0 ;
    private int currentIndex = 1 ; //当前位置
    private static long time = 3000 ; //自动播放时间
    private static boolean autoPlay = true ; //是否自动播放

    private ViewPager.OnPageChangeListener mOnPageChangeListener;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            play();
        }
    };
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            setCurrentItem(++ currentIndex);
        }
    };


    public CircleViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
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
                play();
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

    public void setPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener;
    }

    /**
     * 初始化viewpager
     */
    private void init() {
        if (mAdapter == null){
            return;
        }
        setCurrentItem(currentIndex);
        setOffscreenPageLimit(6); // 设置红缓存的页面数
        setPageMargin(pageMargin); // 设置2张图之前的间距。

//        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,int positionOffsetPixels) {
                if(mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                System.out.println("================> onPageSelected position: " + position);
                if (position == 0){
                    currentIndex = mAdapter.getCount() - 2;
                    if(currentIndex != getCurrentItem()) {
                        onPageScrollStateChanged(ViewPager.SCROLL_STATE_IDLE);
                    }
                }else if (position == mAdapter.getCount() - 1){
                    currentIndex = 1 ;
                    if(currentIndex != getCurrentItem()) {
                        onPageScrollStateChanged(ViewPager.SCROLL_STATE_IDLE);
                    }
                }else {
                    currentIndex = position ;
                }
//                setCurrentItem(currentIndex,false);  // 切换到首尾的时候自动改变
                System.out.println("================> onPageSelected currentIndex: " + currentIndex);
                System.out.println("================> onPageSelected currentItem: " + getCurrentItem());
//                if(currentIndex != getCurrentItem()) { // 说明到第一个或最后一个item
//
//                    setCurrentItem(currentIndex,false);  // 切换到首尾的时候自动改变
//                    System.out.println("================> onPageSelected force currentItem: " + getCurrentItem());
//                }
                if(mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageSelected(currentIndex - 1); // 因为头部插入了一个，所以-1
                }
                Log.e("wenzhihao","position=="+position+",currentIndex="+currentIndex);
            }

            @Override
            public void onPageScrollStateChanged(int state) { // 这个方法有坑呀，如果viewpager 很小，每次移动到viewpager范围之外，这个方法不毁掉
                System.out.println("================> onPageScrollStateChanged state:" + state);
                if(state == ViewPager.SCROLL_STATE_IDLE){ // 动画还没有结束就回调了这个方法，所以加个延时
                    System.out.println("================> onPageScrollStateChanged currentIndex: " + currentIndex);
//                    setCurrentItem(currentIndex,false);  // 切换到首尾的时候自动改变
                    setCurrentIndexDelay();
                    play();
                }else if(state == ViewPager.SCROLL_STATE_DRAGGING){
                    cancel();
                }
                if(mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageScrollStateChanged(state);
                }
            }
        });
    }

    private void setCurrentIndexDelay() {
        if(handler != null)
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setCurrentItem(currentIndex,false);  // 切换到首尾的时候自动改变
                }
            }, 200);
    }

    /**
     * 加了延时之后，不用这个方法，指示器也是对的，因为延时的时候一并处理了currentIndex
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(getLeft() > ev.getX() || getTop() > ev.getY() || getRight() < ev.getX() || getBottom() < ev.getY()) {
            ev.setAction(MotionEvent.ACTION_UP);
            super.onTouchEvent(ev);
            return false;
        }
        return super.onTouchEvent(ev);
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
     * 设置适配器
     * @param adpter
     */
    public void setAdapter(PagerAdapter adpter){
        super.setAdapter(adpter);
        mAdapter = adpter;
        init();
    }

    /**
     * 设置是否自动播放
     * @param autoPlay
     */
    public void setAutoPlay(boolean autoPlay) {
        CircleViewPager.autoPlay = autoPlay;
        if (!autoPlay){
            handler.removeCallbacks(runnable);
        }
    }


}
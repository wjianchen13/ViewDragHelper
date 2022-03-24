package com.test.viewdraghelper.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2017/8/2 0002.
 */

public class Live2InterceptFrameLayout extends FrameLayout {
    public Live2InterceptFrameLayout(Context context) {
        super(context);
        init();
    }

    public Live2InterceptFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Live2InterceptFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setClickable(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
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
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public void setStatus(boolean flag){
        setClickable(flag);
        setFocusable(flag);
        setFocusableInTouchMode(flag);
        getParent().requestDisallowInterceptTouchEvent(flag);
        isTouchEvent = flag;
    }
}

package com.test.viewdraghelper.drag;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.customview.widget.ViewDragHelper;

/**
 * Created by zhy on 15/6/3.
 * 第一次拖动可以拖动，第二次就不能拖动了，也不能滑动viewpager，onTouchEvent返回true就可以了
 * 动态添加了fragemnt之后，重新切换导致切换到前面的部分页面不显示，因为Viewpager销毁了item需要重新加载
 * 设置destroyItem，不销毁viewpager
 * 循环滑动Viepager，有时候下个页面为空，设置setOffscreenPageLimit
 * 动态添加fragment后，如果注释掉destroyItem，viewpager上面的item会重复，因为上一个没有销毁
 * 每次更新比较慢，注释掉getResponseCode
 * 自定Viewpager onTouchEvent，处理划出Viewpager区域不响应
 *
 *
 */
public class DragLayout extends RelativeLayout {
    private ViewDragHelper mDragger;

    private View mDragView;
//    private View mAutoBackView;
//    private View mEdgeTrackerView;

    private boolean isDrag = false;

    private int mLeft;
    private int mTop;

    private boolean isInterceptTouchEvent = false;

    private Point mAutoBackOriginPos = new Point();

    public void setInterceptTouchEvent(boolean interceptTouchEvent) {
        isInterceptTouchEvent = interceptTouchEvent;
    }

    public DragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDragger = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                //mEdgeTrackerView禁止直接移动
                println("tryCaptureView: child == mDragView " + (child == mDragView));
                return child == mDragView /*|| child == mAutoBackView*/;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
//                return left;
                println("clampViewPositionHorizontal: ");
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - mDragView.getWidth() - leftBound;

                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);

                return newLeft;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
//                return top;
                println("clampViewPositionVertical: ");
                final int topBound = getPaddingTop();
                final int bottomBound = getHeight() - mDragView.getHeight() - topBound;

                final int newTop = Math.min(Math.max(top, topBound), bottomBound);

                return newTop;
            }


            //手指释放的时候回调
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                println("onViewReleased: ");
                //mAutoBackView手指释放时可以自动回去
//                if (releasedChild == mAutoBackView)
//                {
//                    mDragger.settleCapturedViewAt(mAutoBackOriginPos.x, mAutoBackOriginPos.y);
//                    invalidate();
//                }

            }

            @Override
            public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
                println("onViewPositionChanged: ");
                mLeft = left;
                mTop = top;
                invalidate();
            }

            //在边界拖动时回调
            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
//                mDragger.captureChildView(mEdgeTrackerView, pointerId);
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                println("getViewHorizontalDragRange: ");
                return getMeasuredWidth()-child.getMeasuredWidth();
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                println("getViewVerticalDragRange: ");
                return getMeasuredHeight()-child.getMeasuredHeight();
            }
        });
//        mDragger.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        println("onInterceptTouchEvent isInterceptTouchEvent: " + isInterceptTouchEvent);
        if(isInterceptTouchEvent) {
            return true;
        }
        return mDragger.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                println("onTouchEvent isInterceptTouchEvent = false");
                isInterceptTouchEvent = false;
                break;
        }
        return super.dispatchTouchEvent(ev);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        println("onTouchEvent");
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                println("onTouchEvent isInterceptTouchEvent = false");
//                isInterceptTouchEvent = false;
//                break;
//        }
        mDragger.processTouchEvent(event);
//        return super.onTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        println("computeScroll: ");
        if(mDragger.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        println("onLayout");
        if(mDragView != null) {
            int childLeft = mLeft;
            int childTop = mTop;
            mDragView.layout(childLeft, childTop, childLeft + mDragView.getMeasuredWidth(), childTop + mDragView.getMeasuredHeight());
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mDragView = getChildAt(3);
        println("mDragView: " + mDragView);
//        mAutoBackView = getChildAt(1);
//        mEdgeTrackerView = getChildAt(2);
    }

    private void println(String str) {
//        System.out.println("===============> " + str);
    }
}

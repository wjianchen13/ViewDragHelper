package com.test.viewdraghelper.vdh;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.customview.widget.ViewDragHelper;

/**
 * Created by zhy on 15/6/3.
 */
public class VDHLayout extends RelativeLayout {
    private ViewDragHelper mDragger;

    private View mDragView;
//    private View mAutoBackView;
//    private View mEdgeTrackerView;

    private boolean isDrag = false;

    private Point mAutoBackOriginPos = new Point();

    public VDHLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDragger = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                //mEdgeTrackerView禁止直接移动
                return child == mDragView /*|| child == mAutoBackView*/;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
//                return left;
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - mDragView.getWidth() - leftBound;

                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);

                return newLeft;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
//                return top;

                final int topBound = getPaddingTop();
                final int bottomBound = getHeight() - mDragView.getHeight() - topBound;

                final int newTop = Math.min(Math.max(top, topBound), bottomBound);

                return newTop;
            }


            //手指释放的时候回调
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                //mAutoBackView手指释放时可以自动回去
//                if (releasedChild == mAutoBackView)
//                {
//                    mDragger.settleCapturedViewAt(mAutoBackOriginPos.x, mAutoBackOriginPos.y);
//                    invalidate();
//                }
            }

            //在边界拖动时回调
            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
//                mDragger.captureChildView(mEdgeTrackerView, pointerId);
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return getMeasuredWidth()-child.getMeasuredWidth();
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight()-child.getMeasuredHeight();
            }

            @Override
            public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
                mLeft = left;
                mTop = top;
                invalidate();
            }

            @Override
            public int getOrderedChildIndex(int index) {
                return indexOfChild(mDragView);
            }
        });
//        mDragger.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    private int mLeft;
    private int mTop;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return mDragger.shouldInterceptTouchEvent(event);
//        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragger.processTouchEvent(event);
        return true;
    }

//    @Override
//    public void computeScroll() {
//        if(mDragger.continueSettling(true)) {
//            invalidate();
//        }
//    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(mDragView != null) {
            int childLeft = mLeft;
            int childTop = mTop;

            mDragView.layout(childLeft, childTop, childLeft + mDragView.getMeasuredWidth(), childTop + mDragView.getMeasuredHeight());
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mDragView = getChildAt(0);
//        mAutoBackView = getChildAt(1);
//        mEdgeTrackerView = getChildAt(2);
    }
}



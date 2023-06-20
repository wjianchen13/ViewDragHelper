package com.test.viewdraghelper.attach;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.customview.widget.ViewDragHelper;

/**
 * 音乐播放器拖动功能
 */
public class AttachLayout extends LinearLayout {

    private Context mContext;
    private final ViewDragHelper mDragHelper;

    private View mDragView;

    private int mLeft;
    private int mTop;

    private Point mAutoBackOriginPos = new Point();

    public AttachLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {

            /**
             * 捕获可拖动View
             */
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                log("mDragView start x: " + mDragView.getX() + "  y: " + mDragView.getY());
                return child == mDragView;
            }

            /**
             * 限定View只能在ViewGroup内部移动
             */
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - mDragView.getWidth() - leftBound;
                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
                return newLeft;
            }

            /**
             * 限定View只能在ViewGroup内部移动
             */
            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                final int topBound = getPaddingTop();
                final int bottomBound = getHeight() - mDragView.getHeight() - topBound;
                final int newTop = Math.min(Math.max(top, topBound), bottomBound);
                return newTop;
            }


            @Override
            public int getViewHorizontalDragRange(View child) {
                return 1;
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return 1;
            }

            @Override
            public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
                mLeft = left;
                mTop = top;
                log("onViewPositionChanged  mLeft: " + mLeft + "  mTop: " + mTop);
                invalidate();
            }

            /**
             * 手指释放的时候回调
             * @param releasedChild
             * @param xvel
             * @param yvel
             */
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                log("mDragView end x: " + mDragView.getX() + "  y: " + mDragView.getY());
                log("mDragView end Left: " + mDragView.getLeft() + "  top: " + mDragView.getTop());
                handleReleased(releasedChild, xvel, yvel);
            }

            private void handleReleased(View releasedChild, float xvel, float yvel) {
                int screenWidth = getWidth();
                if(mDragView.getLeft() < (screenWidth - mDragView.getWidth()) / 2) { // 在左半区域
                    if (releasedChild == mDragView) {
                        mDragHelper.settleCapturedViewAt(0, mDragView.getTop());
                        invalidate();
                    }
                } else { // 右半区域
                    if (releasedChild == mDragView) {
                        int right = getWidth() - mDragView.getWidth() - getPaddingRight();
                        mDragHelper.settleCapturedViewAt(right, mDragView.getTop());

                        invalidate();
                    }
                }
            }

        });
    }

    /**
     * 让 child 平滑地滑动到某个位置
     * @param finalLeft
     * @param finalTop
     */
    public void smoothSlideViewTo(int finalLeft, int finalTop) {
        if(mDragHelper != null)
            mDragHelper.smoothSlideViewTo(mDragView, finalLeft, finalTop);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return mDragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if(mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mAutoBackOriginPos.x = mDragView.getLeft();
        mAutoBackOriginPos.y = mDragView.getTop();
        log("onLayout x: " + mAutoBackOriginPos.x + "  y: " + mAutoBackOriginPos.y);
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
    }

    private static void log(String str) {
        System.out.println("==========================> " + str);
    }
}





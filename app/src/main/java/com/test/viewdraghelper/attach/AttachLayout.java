package com.test.viewdraghelper.attach;

import static androidx.customview.widget.ViewDragHelper.STATE_IDLE;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.customview.widget.ViewDragHelper;

/**
 * 音乐播放器拖动功能
 */
public class AttachLayout extends ConstraintLayout {

    private Context mContext;
    private final ViewDragHelper mDragHelper;

    private View mDragView1;
    private View mDragView2;
    /**
     * 最后一次操作的View
     */
    private View mLastView;

    private int mLeft1;
    private int mTop1;

    private int mLeft2;
    private int mTop2;

    private boolean isChangePosition;

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
                log("mDragView start x: " + mDragView1.getX() + "  y: " + mDragView1.getY());
                return child == mDragView1 || child == mDragView2;
            }

            /**
             * 限定View只能在ViewGroup内部移动
             */
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - child.getWidth() - leftBound;
                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
                return newLeft;
            }

            /**
             * 限定View只能在ViewGroup内部移动
             */
            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                final int topBound = getPaddingTop();
                final int bottomBound = getHeight() - child.getHeight() - topBound;
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
                isChangePosition = true;
                mLeft1 = left;
                mTop1 = top;
                mLeft2 = left;
                mTop2 = top;
                log("onViewPositionChanged  mLeft: " + mLeft1 + "  mTop: " + mTop1);
            }

            /**
             * 2个View 拖动其中一个的时候，结束之后改变另外一个的位置
             * @param state
             */
            @Override
            public void onViewDragStateChanged(int state) {
                log("onViewDragStateChanged  state: " + state);
                if(state == STATE_IDLE && mLastView != null) {
                    if(mLastView == mDragView1 && mDragView2 != null) { // 拖动flag
                        if(mDragView1.getLeft() == 0) { // 在左边
                            mDragView2.layout(mDragView1.getLeft(), mDragView1.getTop(), mDragView1.getLeft() + mDragView2.getWidth(), mDragView1.getTop() + mDragView2.getHeight());
                        } else { // 在右边
                            mDragView2.layout(mDragView1.getRight() - mDragView2.getWidth(), mDragView1.getTop(), mDragView1.getRight(), mDragView1.getTop() + mDragView2.getHeight());
                        }
                    } else if(mLastView == mDragView2 && mDragView1 != null) { // 拖动的是box
                        if(mDragView2.getLeft() == 0) { // 在左边
                            mDragView1.layout(mDragView2.getLeft(), mDragView2.getTop(), mDragView2.getLeft() + mDragView1.getWidth(), mDragView2.getTop() + mDragView1.getHeight());
                        } else {
                            mDragView1.layout(mDragView2.getRight() - mDragView1.getWidth(), mDragView2.getTop(), mDragView2.getRight(), mDragView2.getTop() + mDragView1.getHeight());
                        }
                    }
                    mLastView = null; // 动画也会回调这个方法，需要区分拖动释放还是动画停止
                }
            }

            /**
             * 手指释放的时候回调
             * @param releasedChild
             * @param xvel
             * @param yvel
             */
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                log("mDragView end x: " + releasedChild.getX() + "  y: " + releasedChild.getY());
                log("mDragView end Left: " + releasedChild.getLeft() + "  top: " + releasedChild.getTop());
                mLastView = releasedChild;
                handleReleased(releasedChild, xvel, yvel);
            }

            private void handleReleased(View releasedChild, float xvel, float yvel) {
                int screenWidth = getWidth();
                if(releasedChild.getLeft() < (screenWidth - releasedChild.getWidth()) / 2) { // 在左半区域
//                    if (releasedChild == mDragView1) {
                        mDragHelper.settleCapturedViewAt(0, releasedChild.getTop());
                        invalidate();
//                    }
                } else { // 右半区域
//                    if (releasedChild == mDragView1) {
                        int right = getWidth() - releasedChild.getWidth() - getPaddingRight();
                        mDragHelper.settleCapturedViewAt(right, releasedChild.getTop());

                        invalidate();
//                    }
                }
            }
        });
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
        mAutoBackOriginPos.x = mDragView1.getLeft();
        mAutoBackOriginPos.y = mDragView1.getTop();
        log("onLayout x: " + mAutoBackOriginPos.x + "  y: " + mAutoBackOriginPos.y);
        if(mDragView1 != null && isChangePosition) {
            int childLeft = mLeft1;
            int childTop = mTop1;
            mDragView1.layout(childLeft, childTop, childLeft + mDragView1.getMeasuredWidth(), childTop + mDragView1.getMeasuredHeight());
        }
        if(mDragView2 != null && isChangePosition) {
            int childLeft = mLeft2;
            int childTop = mTop2;
            mDragView2.layout(childLeft, childTop, childLeft + mDragView2.getMeasuredWidth(), childTop + mDragView2.getMeasuredHeight());
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDragView1 = getChildAt(0);
        mDragView2 = getChildAt(1);
    }

    public void setTest1() {
        int dy = 50;
        mDragView2.layout(mDragView2.getLeft(), mDragView2.getTop() + dy, mDragView2.getRight(), mDragView2.getBottom() + dy);
    }

    public void setTest2() {
        smoothSlideViewTo(mDragView2, getWidth() - mDragView2.getWidth(), mDragView2.getTop() + 200);
    }

    public void setTest3() {
//        adjustViewLayout(mDragView1, 2, 200, true);
        hideFlag(2);
    }

    private int testDx = 20;

    /**
     * 调整View 的位置
     * @param v 调整的View
     * @param position 方位 1 左侧 2 右侧
     * @param top 上边界
     * @param visibility 是否显示
     */
    private void adjustViewLayout(View v, int position, int top, boolean visibility) {
        if(v != null) {
            if(position == 1) { // 左侧
                if(visibility) {
                    v.layout(0, top, v.getWidth(), v.getHeight() + top);
                } else {
                    v.layout(-v.getWidth() + testDx, top, testDx, v.getHeight() + top);
                }
            } else { // 右侧
                if(visibility) {
                    v.layout(getWidth() - v.getWidth(), top, getWidth(), v.getHeight() + top);
                } else {
                    v.layout(getWidth() - testDx, top, getWidth() + v.getWidth() - testDx, v.getHeight() + top);
                }
            }
        }

    }

    /**
     * 让 child 平滑地滑动到某个位置
     * @param finalLeft
     * @param finalTop
     */
    public void smoothSlideViewTo(View v, int finalLeft, int finalTop) {
        if(mDragHelper != null && v != null) {
            mDragHelper.smoothSlideViewTo(v, finalLeft, finalTop);
            invalidate();
        }
    }

    /**
     * 显示详情
     */
    public void showMusic() {
        if(mDragView1.getLeft() < testDx || mDragView2.getLeft() < testDx) {
            hideFlag(1);
            showBox(1);
        } else {
            hideFlag(2);
            showBox(2);
        }
    }

    /**
     * 隐藏详情
     */
    public void hideMusic() {
        if(mDragView1.getLeft() < testDx || mDragView2.getLeft() < testDx) {
            showFlag(1);
            hideBox(1);
        } else {
            showFlag(2);
            hideBox(2);
        }
    }

    /**
     * 隐藏标志
     * @param position 方位 1 左侧 2 右侧
     */
    private void hideFlag(int position) {
        if(position == 1) {
            smoothSlideViewTo(mDragView1, -mDragView1.getWidth() + testDx, mDragView1.getTop());
        } else {
            smoothSlideViewTo(mDragView1, getWidth() - testDx, mDragView1.getTop());
        }
    }

    /**
     * 显示标志
     * @param position 方位 1 左侧 2 右侧
     */
    private void showFlag(int position) {
        if(position == 1) {
            smoothSlideViewTo(mDragView1, testDx, mDragView1.getTop());
        } else {
            smoothSlideViewTo(mDragView1, getWidth() - mDragView1.getWidth() - testDx, mDragView1.getTop());
        }
    }

    /**
     * 隐藏box
     * @param position 方位 1 左侧 2 右侧
     */
    private void hideBox(int position) {
        if(position == 1) {
            smoothSlideViewTo(mDragView2, -mDragView2.getWidth() + testDx, mDragView2.getTop());
        } else {
            smoothSlideViewTo(mDragView2, getWidth() - testDx, mDragView2.getTop());
        }
    }

    /**
     * 显示box
     * @param position 方位 1 左侧 2 右侧
     */
    private void showBox(int position) {
        if(position == 1) {
            smoothSlideViewTo(mDragView2, testDx, mDragView2.getTop());
        } else {
            smoothSlideViewTo(mDragView2, getWidth() - mDragView2.getWidth() - testDx, mDragView2.getTop());
        }
    }

    private static void log(String str) {
        System.out.println("==========================> " + str);
    }
}





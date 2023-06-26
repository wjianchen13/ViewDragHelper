package com.test.viewdraghelper.normal;

import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * 这个工具可以使任何一个view进行拖动。
 */

public class DragViewUtil {


    /**
     * 拖动View方法
     *
     * @param v     view
     */
    public static void registerDragAction(View v) {
        v.setOnTouchListener(new TouchListener(v));
    }

    private static class TouchListener implements View.OnTouchListener {

        private View mView;
        private float downX;
        private float downY;
        private boolean isMove;
        private boolean canDrag;
        private int mMarginStart;
        private int mMarginTop;

        private TouchListener(View v) {
            this.mView = v;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = event.getRawX();
                    downY = event.getRawY();
                    isMove = false;
                    if(mView != null) {
                        RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) mView.getLayoutParams();
                        if (p != null) {
                            mMarginStart = p.getMarginStart();
                            mMarginTop = p.topMargin;
                        }
                    }
                    canDrag = true;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (!canDrag) {
                        break;
                    }

                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mView.getLayoutParams();
                    int width = layoutParams.width;
                    int height = layoutParams.height;
                    final float xDistance = event.getRawX() - downX;
                    final float yDistance = event.getRawY() - downY;
                    System.out.println("=======================================> event.getY(): " + event.getRawY() + "   yDistance: " + yDistance  + "   downY: " + downY);
                    if (xDistance != 0 && yDistance != 0) {
                        int marginStart = mMarginStart + (int)xDistance;
                        int marginTop = mMarginTop + (int)yDistance;
//                        layoutParams.setMarginStart(marginStart);
                        layoutParams.topMargin = marginTop;
                        mView.setLayoutParams(layoutParams);
                        isMove = true;
                    }
                    break;
                default:
                    break;
            }
            return isMove;
        }

    }
}

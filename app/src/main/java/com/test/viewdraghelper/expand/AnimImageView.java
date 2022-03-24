package com.test.viewdraghelper.expand;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

@SuppressLint("AppCompatCustomView")
public class AnimImageView extends ImageView {

    private Context mContext;
    private static final int IMAGE_HEIGHT = 200;

    public AnimImageView(Context context) {
        super(context);
        this.mContext = context;
    }


    public AnimImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        this.mContext = context;
    }


    /**
     * 设置的高度最好是bitmap的高度，这样不好压缩变形。这里写死了
     * @param widthMeasureSpec 宽度
     * @param heightMeasureSpec 高度
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, dip2px(IMAGE_HEIGHT));
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(float dpValue) {
        return (int) (dpValue * getDensity(mContext) + 0.5f);
    }


    /**
     * 返回屏幕密度
     */
    public float getDensity(Context context) {
        try {
            return context.getResources().getDisplayMetrics().density;
        } catch (Exception e){
            e.printStackTrace();
        }
        return 2.0f;
    }
}


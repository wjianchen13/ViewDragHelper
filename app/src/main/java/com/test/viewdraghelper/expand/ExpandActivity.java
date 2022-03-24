package com.test.viewdraghelper.expand;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.test.viewdraghelper.BuildConfig;
import com.test.viewdraghelper.R;

public class ExpandActivity extends AppCompatActivity implements View.OnClickListener{
    private RelativeLayout mContentLayout;
    private RelativeLayout.LayoutParams mLayoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand);
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll()
                    .penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog()
                    .build());
        }
        findIds();
    }

    private void findIds() {
        mContentLayout = findViewById(R.id.ll_content);
        findViewById(R.id.bt_scale_big).setOnClickListener(this);
        findViewById(R.id.bt_scale_small).setOnClickListener(this);
        mLayoutParams = (RelativeLayout.LayoutParams)
                mContentLayout.getLayoutParams();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_scale_big:
                animScaleBig();
                break;
            case R.id.bt_scale_small:
                animScaleSmall();
                break;
            default:
                break;
        }
    }

    private void animScaleBig() {
        ValueAnimator scaleBig = ValueAnimator.ofFloat(100, 200);
        scaleBig.setInterpolator(new LinearInterpolator());
        scaleBig.setDuration(1000);
        scaleBig.addUpdateListener(mListener);
        scaleBig.start();
    }

    private ValueAnimator.AnimatorUpdateListener mListener = new ValueAnimator
            .AnimatorUpdateListener() {

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            mLayoutParams.height = dip2px((float) animation.getAnimatedValue());
            mContentLayout.setLayoutParams(mLayoutParams);
        }
    };

    private void animScaleSmall() {
        ValueAnimator scaleBig = ValueAnimator.ofFloat(200, 100);
        scaleBig.setInterpolator(new LinearInterpolator());
        scaleBig.setDuration(1000);
        scaleBig.addUpdateListener(mListener);
        scaleBig.start();
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(float dpValue) {
        return (int) (dpValue * getDensity(this) + 0.5f);
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

package com.test.viewdraghelper.attach;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.test.viewdraghelper.R;

/**
 * Created by zhy on 15/6/3.
 */
public class TestLayout extends ConstraintLayout implements View.OnClickListener{

    private Context mContext;
    private RelativeLayout musicFlagLayout;
    private ConstraintLayout musicBoxLayout;

    public TestLayout(Context context) {
        super(context);
        init(context);
    }

    public TestLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TestLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        LayoutInflater.from(getContext()).inflate(R.layout.view_test_layout, this, true);
        musicFlagLayout = findViewById(R.id.music_flag_layout);
        musicBoxLayout = findViewById(R.id.music_box_layout);

        musicFlagLayout.setOnClickListener(this);
        musicBoxLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.music_flag_layout) {
            showMusicLayout();
        } else if(view.getId() == R.id.music_box_layout) {
            hideMusicLayout();
        }
    }


    /**
     * 显示音乐详情
     */
    public void showMusicLayout() {
        hideTipAnim();
        showBoxAnim(true);
    }

    /**
     * 隐藏音乐详情
     */
    public void hideMusicLayout() {
        showTipAnim(true);
        hideBoxAnim();
    }

    /**
     * 隐藏提示
     */
    public void hideTipAnim() {
        if(musicFlagLayout == null)
            return ;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(musicFlagLayout, "translationX",
                0, musicFlagLayout.getWidth()).setDuration(150);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationStart(animation);
                musicFlagLayout.setVisibility(GONE);
            }
        });
        objectAnimator.start();
    }

    /**
     * 显示提示
     */
    private void showTipAnim(boolean isAnim) {
        if(musicFlagLayout == null)
            return ;
        if(isAnim) {
            ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(musicFlagLayout, "translationX",
                    musicFlagLayout.getWidth(), 0).setDuration(150);
            objectAnimator1.setInterpolator(new AccelerateDecelerateInterpolator());
            objectAnimator1.setStartDelay(260);
            objectAnimator1.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    musicFlagLayout.setVisibility(VISIBLE);
                }
            });
            objectAnimator1.start();
        } else {
            musicFlagLayout.setTranslationX(0);
            musicFlagLayout.setTranslationY(0);
            musicFlagLayout.setVisibility(VISIBLE);
        }
    }

    /**
     * 隐藏箱体
     */
    public void hideBoxAnim() {
        if(musicBoxLayout == null)
            return ;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(musicBoxLayout, "translationX",
                        0, musicBoxLayout.getWidth()).setDuration(300);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.setStartDelay(100);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationStart(animation);
                musicBoxLayout.setVisibility(GONE);
            }
        });
        objectAnimator.start();
    }

    /**
     * 显示箱体
     */
    private void showBoxAnim(boolean isAnim) {
        if(musicBoxLayout == null)
            return ;
        if(isAnim) {
            ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(musicBoxLayout, "translationX",
                            musicBoxLayout.getWidth(), 0).setDuration(300);
            objectAnimator1.setInterpolator(new AccelerateDecelerateInterpolator());
            objectAnimator1.setStartDelay(150);
            objectAnimator1.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    musicBoxLayout.setVisibility(VISIBLE);
                }
            });
            objectAnimator1.start();
        } else {
            musicBoxLayout.setTranslationX(0);
            musicBoxLayout.setTranslationY(0);
            musicBoxLayout.setVisibility(VISIBLE);
        }
    }
}





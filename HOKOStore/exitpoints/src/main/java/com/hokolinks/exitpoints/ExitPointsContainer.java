package com.hokolinks.exitpoints;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

/**
 * Created by ivanbruel on 28/07/15.
 */
class ExitPointsContainer /*extends FrameLayout */{

   /* private static final int ANIMATION_DURATION = 300;

    private AnimationSet mOutAnimationSet;
    private AnimationSet mInAnimationSet;

    private float mPreviousY;

    public ExitPointsContainer(Context context) {
        super(context);
        init();
    }

    public ExitPointsContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    ExitPointsContainer(ViewGroup container) {
        super(container.getContext());

        container.addView(this, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setVisibility(View.GONE);
        setId(R.id.snackContainer);
        init();
    }

    private void init() {
        mInAnimationSet = new AnimationSet(false);

        TranslateAnimation mSlideInAnimation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT, 0.0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0.0f,
                TranslateAnimation.RELATIVE_TO_SELF, 1.0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0.0f);

        AlphaAnimation mFadeInAnimation = new AlphaAnimation(0.0f, 1.0f);

        mInAnimationSet.addAnimation(mSlideInAnimation);
        mInAnimationSet.addAnimation(mFadeInAnimation);

        mOutAnimationSet = new AnimationSet(false);

        TranslateAnimation mSlideOutAnimation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT, 0.0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0.0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0.0f,
                TranslateAnimation.RELATIVE_TO_SELF, 1.0f);

        AlphaAnimation mFadeOutAnimation = new AlphaAnimation(1.0f, 0.0f);

        mOutAnimationSet.addAnimation(mSlideOutAnimation);
        mOutAnimationSet.addAnimation(mFadeOutAnimation);

        mOutAnimationSet.setDuration(ANIMATION_DURATION);
        mOutAnimationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                removeAllViews();
                setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mInAnimationSet.cancel();
        mOutAnimationSet.cancel();
        removeCallbacks(mHideRunnable);
    }


    public void show(boolean animated) {
        setVisibility(View.VISIBLE);

        addView();
    }*/

}

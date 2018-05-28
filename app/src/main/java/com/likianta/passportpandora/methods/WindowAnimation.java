package com.likianta.passportpandora.methods;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

import com.likianta.passportpandora.beans.ViewWrapper;
import com.likianta.passportpandora.data.DataCenter;
import com.likianta.passportpandora.utils.PropertyAnimation;
import com.orhanobut.logger.Logger;

/**
 * Created by Likianta_Dodoora on 2018/5/10 0010.
 */
public class WindowAnimation {
    
    private View cover;
    private View target;
    private View refer;
    private View fragCover;
    
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    private ObjectAnimator coverAnimator;
    private ObjectAnimator discoverAnimator;
    private PropertyAnimation animation = new PropertyAnimation();
    
    private ObjectAnimator fragCoverAnimator;
    private ObjectAnimator fragDiscoverAnimator;
    
    public WindowAnimation(View cover, View target, View fragCover) {
        this.cover = cover;
        this.target = target;
        this.fragCover = fragCover;
        
        coverAnimator = darkWindowBackground(true);
        discoverAnimator = darkWindowBackground(false);
        fragCoverAnimator = popupFragment(true);
        fragDiscoverAnimator = popupFragment(false);
        
    }
    
    public void setRefer(View refer) {
        this.refer = refer;
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    
    public void activateAnimation() {
        beforeVisibilities(true);
        AnimatorSet set = getMainAnimation();
        
        set.playTogether(new ObjectAnimator[]{
                coverAnimator,
                fragCoverAnimator
        });

//        set.setDuration(DURATION);
        set.start();
        
        afterVisibilities(set, true);
    }
    
    public void deactivateAnimation() {
        beforeVisibilities(false);
        AnimatorSet set = getRetrievedAnimation();
        
        set.playTogether(new ObjectAnimator[]{
                discoverAnimator,
                fragDiscoverAnimator
        });
        set.start();
        
        afterVisibilities(set, false);
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    
    private ObjectAnimator darkWindowBackground(boolean activate) {
        ObjectAnimator animator;
        if (activate) {
            animator = ObjectAnimator.ofFloat(cover, "alpha", 0, 1);
        } else {
            animator = ObjectAnimator.ofFloat(cover, "alpha", 1, 0);
        }
        
        return animator;
    }
    
    private ObjectAnimator popupFragment(boolean activate) {
        ObjectAnimator animator;
        if (activate) {
            fragCover.setAlpha(1.0f);
            animator = ObjectAnimator.ofFloat(fragCover, "alpha", 1, 0);
        } else {
            animator = ObjectAnimator.ofFloat(fragCover, "alpha", 0, 1);
        }
        
        return animator;
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    
    private void beforeVisibilities(boolean activate) {
        if (activate) {
            cover.setVisibility(View.VISIBLE);
            target.setVisibility(View.VISIBLE);
//            fragCover.setVisibility(View.VISIBLE);
            refer.setVisibility(View.INVISIBLE);
        } else {
            fragCover.setVisibility(View.VISIBLE);
        }
    }
    
    private AnimatorSet getMainAnimation() {
        
        float[] refer = new ViewWrapper(this.refer).getBounds();
        float[] x = {10, 0, 20};
        float[] y = {refer[1] - DataCenter.STATUS_HEIGHT, refer[1] + 50 - DataCenter
                .STATUS_HEIGHT, 100};
        float[] w = {340, 360, 320};
        float[] h = {refer[3] - 20, 160, 360, 335};
        
        final int DURATION = 800;
        return animation.calculateMultiAnimation(target, x, y, w, h, DURATION);
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    
    private AnimatorSet getRetrievedAnimation() {
        return animation.playbackMultiAnimation();
    }
    
    private void afterVisibilities(AnimatorSet set, boolean activate) {
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (activate) {
                    fragCover.setVisibility(View.GONE);
                } else {
                    cover.setVisibility(View.GONE);
                    target.setVisibility(View.GONE);
                    refer.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}

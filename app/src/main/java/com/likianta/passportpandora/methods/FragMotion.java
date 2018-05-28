package com.likianta.passportpandora.methods;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;

import com.likianta.passportpandora.R;
import com.likianta.passportpandora.beans.CardWrapper;
import com.likianta.passportpandora.utils.AnimatorUtils;

/**
 * Created by Likianta_Dodoora on 2018/5/9 0009.
 */
public class FragMotion {
    private ImageView cover;
    private ObjectAnimator coveredAnimator;
    private ObjectAnimator discoveredAnimator;
    private AnimatorSet animatorSet = new AnimatorSet();
    private int[] referBounds;
    private int[] targetBounds;
    private ObjectAnimator[] extraAnimators;
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // activate and deactivate methods
    private ObjectAnimator[] extraAnimatorsIn;
    private ObjectAnimator[] extraAnimatorsOut;
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    
    public FragMotion() {
        /*this.cover = cover;
        coveredAnimator = new CoverMotion().getCoveredAnimator(cover);
        discoveredAnimator = new CoverMotion().getDiscoveredAnimator(cover);*/
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    
    public void activateFrag(View target, int[] start) {
        final int duration = 1000;
        
        referBounds = start;
//        targetBounds = end;
        
        ObjectAnimator[] animators = new AnimatorUtils().getAnimators(target, referBounds,
                targetBounds);
        
        
        cover.setVisibility(View.VISIBLE);
        target.setVisibility(View.VISIBLE);
        
        animatorSet.playTogether(animators);
        if (extraAnimatorsIn != null) {
            animatorSet.playTogether(extraAnimatorsIn);
        }
        
        animatorSet.setDuration(duration);
        animatorSet.start();
    }

    public void deactivateFrag(View target) {
        final int duration = 1000;
        
        // Before animation
        // hide keyboard
        
        // On animation
        ObjectAnimator[] animators = new AnimatorUtils().getAnimators(target, targetBounds,
                referBounds);
        
        animatorSet.playTogether(animators);
        if (extraAnimatorsOut != null) {
            animatorSet.playTogether(extraAnimatorsOut);
        }
        
        animatorSet.setDuration(duration);
        animatorSet.start();
        
        
        // After animation
        animatorSet.addListener(new AnimatorListenerAdapter() {
            
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
            
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                
                cover.setVisibility(View.GONE);
                target.setVisibility(View.GONE);
            }
            
        });
        
        
    }
    
    public void setExtraAnimators(View view) {
        extraAnimatorsIn = new ObjectAnimator[]{
                coveredAnimator,
                ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        };
        
        extraAnimatorsOut = new ObjectAnimator[]{
                discoveredAnimator,
                ObjectAnimator.ofFloat(view, "alpha", 1f, 0f)
        };
    }
    
    public void setTargetBounds(int[] targetBounds) {
        this.targetBounds = targetBounds;
    }
    
    /*public void setExtraAnimators(View[] views, boolean isActivate) {
        ObjectAnimator[] animators = new ObjectAnimator[views.length];
        if (isActivate) {
            for (int i = 0, n = views.length; i < n; i++) {
                animators[i] = ObjectAnimator.ofFloat(views[i], "alpha", 0f, 1f);
            }
        } else {
            for (int i = 0, n = views.length; i < n; i++) {
                animators[i] = ObjectAnimator.ofFloat(views[i], "alpha", 1f, 0f);
            }
        }
        
        this.extraAnimators = animators;
    }*/
    
    
}

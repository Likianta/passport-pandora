package com.likianta.passportpandora.methods;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.likianta.passportpandora.data.DataCenter;
import com.likianta.passportpandora.beans.CardWrapper;
import com.likianta.passportpandora.utils.AnimatorUtils;
import com.orhanobut.logger.Logger;

/**
 * Created by Likianta_Dodoora on 2018/5/6 0006.
 */
public class CardMotion {
    // Card index code
    private static final int MEMO_CARD = 0;
    private static final int SOCIAL_CARD = 1;
    
    // Property name code
    /*private final int X = 0;
    private final int Y = 1;
    private final int W = 2;
    private final int H = 3;*/
    
    // Animation related
    private int currentIndex;
    private ImageView cover;
    private Button cardBtnDone;
    private TextView cardMemoHeading;
    private AnimatorSet animatorSet;
    private ObjectAnimator coveredAnimator;
    private ObjectAnimator discoveredAnimator;
    
    // □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
    
    public CardMotion(ImageView cover) {
        this.cover = cover;
        this.coveredAnimator = new CoverMotion().getCoveredAnimator(cover);
        this.discoveredAnimator = new CoverMotion().getDiscoveredAnimator(cover);
    }
    
    // □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
    
    public void activeCard(int cardIndex, View target, View refer) {
        this.currentIndex = cardIndex;
        int duration = 500;
        
        ObjectAnimator[] animators = null;
        ObjectAnimator[] extraAnimators = null;
        
        switch (cardIndex) {
            case MEMO_CARD:
                animators = getMemoCardAnimators(target, refer, true);
                extraAnimators = getMemoCardExtra(true);
                break;
            
            case SOCIAL_CARD:
                animators = getSocialCardAnimators(target, refer);
//                extraAnimators = null;
                break;
        }
        
        doAnimators(animators, extraAnimators, duration);
    }
    
    public void deactivateCard(View target, View refer) {
        int duration = 500;
        
        ObjectAnimator[] animators = null;
        ObjectAnimator[] extraAnimators = null;
        
        switch (this.currentIndex) {
            case MEMO_CARD:
                animators = getMemoCardAnimators(target, refer, false);
                extraAnimators = getMemoCardExtra(false);
                break;
        }
        
        doAnimators(animators, extraAnimators, duration);
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    
    private void doAnimators(ObjectAnimator[] animators,
                             ObjectAnimator[] extraAnimators, int duration) {
        
        // Init animation params
        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        
        // Main animations
        animatorSet.playTogether(animators);
        
        // Extra animations
        if (extraAnimators != null) {
            animatorSet.playTogether(extraAnimators);
        }
        
        // ----------------------------------------------------------------
        // Already, let's play
        animatorSet.setDuration(duration);
        animatorSet.start();
        
        // Return animator set
        this.animatorSet = animatorSet;
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    
    private ObjectAnimator[] getMemoCardAnimators(View target, View refer, boolean isActivate) {
        final int X = 0;
        final int Y = 1;
        final int W = 2;
        final int H = 3;
        
        int[] startBounds;
        int[] endBounds;
        int[] x;
        int[] y;
        int[] w;
        int[] h;
        
        if (isActivate) {
            startBounds = new CardWrapper(refer).getBoundaries();
            x = new int[]{startBounds[X], 20};
            y = new int[]{startBounds[Y], 40};
            w = new int[]{startBounds[W], 320};
            h = new int[]{startBounds[H], 320, 280};
        } else {
            startBounds = new CardWrapper(target).getBoundaries();
            endBounds = new CardWrapper(refer).getBoundaries();
            endBounds[Y] -= DataCenter.STATUS_HEIGHT;
            x = new int[]{startBounds[X], endBounds[X]};
            y = new int[]{startBounds[Y], endBounds[Y]};
            w = new int[]{startBounds[W], endBounds[W]};
            h = new int[]{startBounds[H], endBounds[H]};
        }
        
        return new AnimatorUtils().getAnimators(target, x, y, w, h);
    }
    
    private ObjectAnimator[] getMemoCardExtra(boolean isActivate) {
        if (cardMemoHeading == null || cardBtnDone == null) {
            return null;
        }
        
        ObjectAnimator[] animators = new ObjectAnimator[3];
        
        if (isActivate) {
            animators[0] = this.coveredAnimator;
            animators[1] = ObjectAnimator.ofFloat(cardBtnDone, "alpha", 0.0f, 1.0f);
            animators[2] = ObjectAnimator.ofFloat(cardMemoHeading, "alpha", 0.0f, 1.0f);
//            animators[2] = ObjectAnimator.ofFloat(cardMemoEdt, "alpha", 0.0f, 0.2f, 0.4f, 1.0f);
        } else {
            animators[0] = this.discoveredAnimator;
            animators[1] = ObjectAnimator.ofFloat(cardBtnDone, "alpha", 1.0f, 0.0f);
            animators[2] = ObjectAnimator.ofFloat(cardMemoHeading, "alpha", 1.0f, 0.0f);
//            animators[2] = ObjectAnimator.ofFloat(cardMemoEdt, "alpha", 1.0f, 0.4f, 0.2f, 0.0f);
        }
        
        return animators;
    }
    
    
    public void setCardMemoExtra(TextView cardMemoHeading, Button cardBtnDone) {
        this.cardMemoHeading = cardMemoHeading;
//        this.cardMemoEdt = cardMemoEdt;
        this.cardBtnDone = cardBtnDone;
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    private ObjectAnimator[] getSocialCardAnimators(View target, View refer) {
        int[] start = new CardWrapper(refer).getBoundaries();
        int[] end = {10, 300, 340, 300};
        
        return new AnimatorUtils().getAnimators(target, start, end);
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    
    public AnimatorSet getAnimatorSet() {
        return animatorSet;
    }
    
    public int getCurrentIndex() {
        return currentIndex;
    }
}

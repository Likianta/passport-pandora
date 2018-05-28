package com.likianta.passportpandora.methods;

import android.animation.ObjectAnimator;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;

/**
 * Created by Likianta_Dodoora on 2018/5/9 0009.
 */
public class CoverMotion {
    
    public ObjectAnimator getCoveredAnimator(ImageView cover) {
        if (cover == null) {
            Logger.d("(20-2453) >> cover is null!");
            return null;
        } else {
            return ObjectAnimator.ofFloat(cover, "alpha", 0.0f, 1.0f);
        }
    }
    
    public ObjectAnimator getDiscoveredAnimator(ImageView cover) {
        if (cover == null) {
            return null;
        } else {
            return ObjectAnimator.ofFloat(cover, "alpha", 1.0f, 0.0f);
        }
    }
}

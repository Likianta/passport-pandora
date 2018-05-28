package com.likianta.passportpandora.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.likianta.passportpandora.beans.ViewWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Likianta_Dodoora on 2018/5/10 0010.
 */
public class PropertyAnimation {
    
    private final String[] properties = {"x", "y", "scaleX", "scaleY"};
    /*private final int X = 0;
    private final int Y = 1;
    private final int W = 2;
    private final int H = 3;*/
    private View view;
    private int currentDuration;
    private float[] currentStart;
    private float[] currentEnd;
    private List<float[]> list = new ArrayList<>(4);
    
    public AnimatorSet calculateAnimation(View view, float[] start, float[] end, int
            duration) {
        
        ObjectAnimator[] animators = new ObjectAnimator[4];
        ViewWrapper wrapper = new ViewWrapper(view);
        
        for (int i = 0; i < 4; i++) {
            animators[i] = ObjectAnimator.ofFloat(wrapper, properties[i], start[i], end[i]);
        }
        
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animators);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.setDuration(duration);
        
        this.view = view;
        this.currentDuration = duration;
        this.currentStart = start;
        this.currentEnd = end;
        
        return set;
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    
    public AnimatorSet playbackAnimation() {
//        Logger.d("(17-0721) >> 3000ms >> currentDuration = " + currentDuration);
        return
                calculateAnimation(view, currentEnd, currentStart, currentDuration);
    }
    
    public AnimatorSet calculateMultiAnimation(View view, float[] x, float[] y, float[] w,
                                               float[] h, int duration) {
        list.clear();
        list.add(x);
        list.add(y);
        list.add(w);
        list.add(h);
        
        return calculateMultiAnimation(view, list, duration);
    }
    
    private AnimatorSet calculateMultiAnimation(View view, List<float[]> list, int duration) {
        ObjectAnimator[] animators = new ObjectAnimator[4];
        ViewWrapper wrapper = new ViewWrapper(view);
        
        for (int i = 0; i < 4; i++) {
            animators[i] = ObjectAnimator.ofFloat(wrapper, properties[i], list.get(i));
        }
        
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animators);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.setDuration(duration);
        
        this.view = view;
        this.currentDuration = duration;
//        list.clear();
        this.list = reverseArrays(list);
        
        return set;
    }
    
    public AnimatorSet playbackMultiAnimation() {
        return calculateMultiAnimation(view, list, currentDuration);
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // reverse array method
    
    private List<float[]> reverseArrays(List<float[]> list) {
        // REFERENCE: 2018-05-10
        // https://blog.csdn.net/u011699931/article/details/52554787
        
        for (int i = 0, n = list.size(); i < n; i++) {
            
            for (int start = 0, end = list.get(i).length -1; start < end; start++, end--) {
                float temp = list.get(i)[end];
                list.get(i)[end] = list.get(i)[start];
                list.get(i)[start] = temp;
            }
        }
        
        /*Logger.d("(19-5521) >> 10, 0, 20; reverse >> Arrays.toString(list.get(0)) = " + Arrays
                .toString(list.get(0)));*/
        return list;
    }
    
    
}

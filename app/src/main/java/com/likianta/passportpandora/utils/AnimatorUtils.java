package com.likianta.passportpandora.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.likianta.passportpandora.beans.CardWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Likianta_Dodoora on 2018/5/3 0003.
 */
public class AnimatorUtils {
    // Request code
    private static final int TEST_MODE = 0;
    private static final int START_NOW = 1;
    private static final int RETURN_SET = 2;
    private static final int RETURN_ANIMATORS = 3;
    
    // NOTE: 2018-05-04
    //
    // # Animator工具介绍
    //
    // ## 自动计算属性并执行动画
    //
    // 1. 如果我们已知View的开始状态、结束状态（指x，y，width，height四个属性）
    // 并且只需要执行位移和缩放动画
    // 且缩放动画的中心为View的中心位置
    // 那么我们可以用：
    //     multiFramesAnim(view,     new Card(this, 20, 40, 320, 280), 5000);
    //                    our_view  target_view_properties            duration
    //
    // 2. 如果我们已知View的开始状态、中间的关键帧（多个）、结束状态
    // 并且只需要执行位移和缩放动画
    // 且缩放动画的中心始终为View的中心位置
    // 那么我们可以用：
    //     multiFramesAnim(view, new Card[]{new Card(this, 0, 0, 360, 100),
    //             new Card(this, 10, 20, 180, 140),
    //             new Card(this, 20, 40, 360, 550)},
    //             new int[]{10, 20});
    //             notice_int[].length_must_equal_to_Card[].length-1,
    //             if_not,this_function_will_return_false_and_do_nothing.
    //
    // ## 手动开始动画
    //
    // 如果我们需要执行透明度动画、旋转动画，甚至更多更复杂的动画，自动计算属性并不能完成这类需求；
    // 我们需要这样做：
    //
    // 1. 获取AnimatorSet：
    //     AnimatorSet set = new AnimatorUtils().getAnimatorSet(view, new Card(this, 20, 40,
    //     320, 280));
    // 该set将包含自动计算属性，接下来我们可以对这个set手动添加更多属性，并在最后执行动画：
    //     new AnimatorUtils().multiFramesAnim(set, duration);
    //
    // 2. 如果我们不希望执行位移和缩放动画，那么我们不需要使用到AnimatorUtils
    // 即使如此，为了代码的美观性和一致性，AnimatorUtils仍然提供了最简易的获取方式：
    //     public AnimatorSet getAnimatorSet() {
    //         return new AnimatorSet();
    //     }
    //
    
    private final int X = 0;
    private final int Y = 1;
    private final int W = 2;
    private final int H = 3;
    
    // ----------------------------------------------------------------
    // This is custom properties
    private String[] properties = {"x", "y", "width", "height"};
    private ObjectAnimator[] animators = new ObjectAnimator[4];
    private AnimatorSet animatorSet = new AnimatorSet();
    
    // □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
    // Start animators
    
    public void startAnimator(View target, View refer, int[] end, int duration) {
        CardWrapper wrapper = new CardWrapper(refer);
        int[] start = wrapper.getBoundaries();
    
        simpleAnim(new CardWrapper(target), start, end, duration, START_NOW);
    }
    
    public void startAnimator(View view, int[] start, int[] end, int duration) {
        simpleAnim(new CardWrapper(view), start, end, duration, START_NOW);
    }
    
    public void startAnimator(View view, int[] x, int[] y, int[] w, int[] h, int duration) {
        List<int[]> list = new ArrayList<>(4);
        list.add(x);
        list.add(y);
        list.add(w);
        list.add(h);
        
        multiFramesAnim(new CardWrapper(view), list, duration, START_NOW);
    }
    
    private void multiFramesAnim(
            CardWrapper wrapper, List<int[]> valueList, int duration, int request) {
        
        ObjectAnimator[] animators = new ObjectAnimator[4];
        
        // dp px conversion
        int[] x = DpToPxTool.dip2px(valueList.get(X));
        int[] y = DpToPxTool.dip2px(valueList.get(Y));
        int[] w = DpToPxTool.dip2px(valueList.get(W));
        int[] h = DpToPxTool.dip2px(valueList.get(H));
        
        List<int[]> finalValueList = new ArrayList<>(4);
        finalValueList.add(x);
        finalValueList.add(y);
        finalValueList.add(w);
        finalValueList.add(h);
        
        for (int i = 0; i < animators.length; i++) {
            animators[i] = ObjectAnimator.ofInt(wrapper, properties[i], finalValueList.get(i));
        }
        
        switch (request) {
            case RETURN_ANIMATORS:
                this.animators = animators;
                return;
            case RETURN_SET:
                this.animatorSet.playTogether(animators);
                return;
            case TEST_MODE:
                
                break;
        }
        
        // Here is START_NOW or TEST_MODE
        // TODO custom interpolator
        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new DecelerateInterpolator());
        set.playTogether(animators);
        set.setDuration(duration);
        set.start();
    }
    
    private void simpleAnim(CardWrapper wrapper, int[] start, int[] end, int duration, int
            request) {
        // simpleAnim的主要参数为目标view，初始状态和结束状态
        // 其中初始状态只包含四个int值（x，y，width，height），同样结束状态也是
        // 也就是说，在开始和结束之间，不包括额外的过渡帧
        
        start = DpToPxTool.dip2px(start);
        end = DpToPxTool.dip2px(end);
        
        ObjectAnimator[] animators = new ObjectAnimator[4];
        
        for (int i = 0; i < 4; i++) {
            animators[i] = ObjectAnimator.ofInt(wrapper, properties[i], start[i], end[i]);
        }
        
        final AnimatorSet set = new AnimatorSet();
        set.playTogether(animators);
        switch (request) {
            case RETURN_ANIMATORS:
                this.animators = animators;
                return;
            case START_NOW:
                this.animatorSet = set;
                return;
            case RETURN_SET:
                set.setDuration(duration);
                set.start();
                break;
        }
        
    }
    
    // □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
    
    public ObjectAnimator[] getAnimators(View target, int[] start, int[] end) {
        simpleAnim(new CardWrapper(target), start, end, 0, RETURN_ANIMATORS);
        return this.animators;
    }
    
    public ObjectAnimator[] getAnimators(View view, int[] x, int[] y, int[] w, int[] h) {
        List<int[]> list = new ArrayList<>(4);
        list.add(x);
        list.add(y);
        list.add(w);
        list.add(h);
        
        multiFramesAnim(new CardWrapper(view), list, 0, RETURN_ANIMATORS);
        return animators;
    }
    
    public ObjectAnimator getAnAnimator(View view, String property, int[] values) {
        values = DpToPxTool.dip2px(values);
        
        CardWrapper wrapper = new CardWrapper(view);
        
        return ObjectAnimator.ofInt(wrapper, property, values);
    }
    
    // □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
    
    public AnimatorSet getAnimatorSet(View view, int[] start, int[] end) {
        simpleAnim(new CardWrapper(view), start, end, 0, RETURN_SET);
        return this.animatorSet;
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Custom interpolator (for smooth animations)
    
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Custom tools
    
    
}

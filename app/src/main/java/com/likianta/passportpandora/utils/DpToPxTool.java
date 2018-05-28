package com.likianta.passportpandora.utils;

import android.content.res.Resources;

/**
 * Created by Likianta_Dodoora on 2018/5/3 0003.
 */
public class DpToPxTool {
    
    // REFERENCE: 2018-05-05
    // Dp2Px methods without CONTEXT
    // https://blog.csdn.net/weizongwei5/article/details/43667899
    
    public static int px2dip(int pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    
    public static int[] px2dip(int[] values) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        int[] result = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = values[i] / (int) scale;
        }
        return result;
    }
    
    // □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
    
    public static float dip2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (dipValue * scale + 0.5f);
    }
    
    public static int[] dip2px(int[] values) {
        final int scale = (int) Resources.getSystem().getDisplayMetrics().density;
        int[] result = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = values[i] * scale;
        }
        return result;
    }
}

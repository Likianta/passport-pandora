package com.likianta.passportpandora.beans;

import android.view.View;

import com.likianta.passportpandora.utils.DpToPxTool;
import com.orhanobut.logger.Logger;

/**
 * Created by Likianta_Dodoora on 2018/5/10 0010.
 */
public class ViewWrapper {
    
    private View view;
    private float width;
    private float height;
    
    
    public ViewWrapper(View view) {
        this.view = view;
        
        view.setPivotX(0f);
        view.setPivotY(0f);
        this.width = view.getWidth();
        this.height = view.getHeight();
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    
    public void setX(float x) {
        x = DpToPxTool.dip2px(x);
        view.setX(x);
    }
    
    public void setY(float y) {
        y = DpToPxTool.dip2px(y);
        view.setY(y);
    }
    
    public void setScaleX(float width) {
        width = DpToPxTool.dip2px(width);
        view.setScaleX(width / this.width);
    }
    
    public void setScaleY(float height) {
        height = DpToPxTool.dip2px(height);
        view.setScaleY(height / this.height);
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // bounds
    
    public float[] getBounds() {
        int[] positions = new int[2];
        view.getLocationInWindow(positions);
        positions = DpToPxTool.px2dip(positions);
        
        return new float[]{
                positions[0],
                positions[1],
                DpToPxTool.px2dip((int) width),
                DpToPxTool.px2dip((int) height)
        };
    }
    
    
}

package com.likianta.passportpandora.beans;

import android.view.View;

import com.likianta.passportpandora.utils.DpToPxTool;

/**
 * Created by Likianta_Dodoora on 2018/5/5 0005.
 */
public class CardWrapper {
    
    private View view;
    
    public CardWrapper(View view) {
        this.view = view;
    }
    
    // □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
    
    public void setWidth(int width) {
        view.getLayoutParams().width = width;
        view.requestLayout();
    }
    
    public void setHeight(int height) {
        view.getLayoutParams().height = height;
        view.requestLayout();
    }
    
    public void setX(int x) {
        view.setX(x);
    }
    
    public void setY(int y) {
        view.setY(y);
    }
    
    // □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
    
    public int[] getBoundaries() {
        // Return top-left x,y positions in dp values
        int[] positions = new int[2];
        view.getLocationInWindow(positions);
        
        int[] boundaries = new int[4];
        boundaries[0] = DpToPxTool.px2dip(positions[0]);
        boundaries[1] = DpToPxTool.px2dip(positions[1]);
        boundaries[2] = DpToPxTool.px2dip(view.getWidth());
        boundaries[3] = DpToPxTool.px2dip(view.getHeight());
        
        return boundaries;
    }
    
}

package com.likianta.passportpandora.methods;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * 实现RecyclerView的item被点击事件
 * Created by DevWiki on 2016/7/16.
 * http://blog.devwiki.net/index.php/2016/07/24/three-ways-click-recyclerview-item.html
 */

public class ItemClickListener extends RecyclerView.SimpleOnItemTouchListener {
    
    private OnItemClickListener clickListener;
    private GestureDetectorCompat gestureDetector;
    
    public ItemClickListener(final RecyclerView recyclerView,
                             OnItemClickListener listener) {
        this.clickListener = listener;
        gestureDetector = new GestureDetectorCompat(recyclerView.getContext(),
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                        if (childView != null && clickListener != null) {
                            clickListener.onItemClick(childView, recyclerView
                                    .getChildAdapterPosition(childView));
                        }
                        return true;
                    }
                    
                    @Override
                    public void onLongPress(MotionEvent e) {
                        View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                        if (childView != null && clickListener != null) {
                            clickListener.onItemLongClick(childView,
                                    recyclerView.getChildAdapterPosition(childView));
                        }
                    }
                });
    }
    
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);
        return false;
    }
    
    public interface OnItemClickListener {
        
        void onItemClick(View view, int position);
        
        void onItemLongClick(View view, int position);
    }
}
package com.likianta.passportpandora.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.orhanobut.logger.Logger;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Likianta_Dodoora on 2018/5/2 0002.
 */
public class KeyboardManager {
    
    public static void hideKeyboard(Activity activity) {
        InputMethodManager manager = (InputMethodManager) activity.getSystemService
                (Context.INPUT_METHOD_SERVICE);
        
        if (manager != null && activity.getCurrentFocus() != null) {
            manager.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getWindowToken(), 0);
        }
    }
    
    public static void showKeyboard(Activity activity, final EditText view) {
        showKeyboard(activity, view, 300);
    }
    
    public static void showKeyboard(Activity activity, final EditText view, int delay) {
        final InputMethodManager manager = (InputMethodManager) activity.getSystemService
                (Context.INPUT_METHOD_SERVICE);
        
        if (manager != null) {
            // REFERENCE: 2018-05-04 https://blog.csdn.net/cshichao/article/details/8536961
            view.requestFocus();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    manager.showSoftInput(view, 0);
                }
            }, delay);
        }
    }
    
    
}

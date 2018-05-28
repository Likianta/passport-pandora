package com.likianta.passportpandora;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Likianta_Dodoora on 2018/4/27 0027.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button login;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        initBinding();
        initListeners();
    }
    
    private void initListeners() {
        login.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                startActivity(new Intent(this, MainActivity.class));
                break;
            default:
        }
    }
    
    private void initBinding() {
        login = findViewById(R.id.btn_login);
        
        View rootView = getWindow().getDecorView();
        ImageView bottomView = findViewById(R.id.login_bg);
        adjustLayoutWithSoftKeyboard(rootView, bottomView);
    }
    
    /**
     * 作者：瘟疫幽魂
     * 链接：https://www.jianshu.com/p/733759fd87bf
     * 來源：简书
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     *
     * @param rootView   根布局
     * @param bottomView 需要显示的最下方View
     */
    public void adjustLayoutWithSoftKeyboard(final View rootView, final View bottomView) {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                .OnGlobalLayoutListener() {
            
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                rootView.getWindowVisibleDisplayFrame(rect); // 获取rootView的可视区域
                int invisibleHeight = rootView.getRootView().getHeight() - rect.bottom; //
                // 获取rootView的不可视区域高度
                if (invisibleHeight > 150) { // 键盘显示
                    int[] location = new int[2];
                    bottomView.getLocationInWindow(location); // 获取bottomView的坐标
                    int scrollHeight = (location[1] + bottomView.getHeight()) - rect.bottom; //
                    // 算出需要滚动的高度
                    if (scrollHeight != 0) { // 防止界面元素改变调用监听，使界面上下跳动，如验证码倒计时
                        rootView.scrollTo(0, scrollHeight);
                    }
                } else {
                    rootView.scrollTo(0, 0);
                }
            }
        });
    }
    
}

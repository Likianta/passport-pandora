package com.likianta.passportpandora.utils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.likianta.passportpandora.R;
import com.orhanobut.logger.Logger;


/**
 * Created by Likianta_Dodoora on 2018/5/4 0004.
 */
public class TestRoom extends AppCompatActivity implements View.OnClickListener {
    
    
    private TextView testImg;
    private TextView testImg1;
    private RelativeLayout testImg2;
    
    private EditText testEdt;
    private TextView testTxt;
    private TextView testTxt1;
    private Button testBtn;
    
    private Button testBtn1;
    private Button testBtn2;
    private Button testBtn3;
    private Button testBtn4;
    private Button testBtn5;
    private Button testBtn6;
    private Button testBtn7;
    private Button testBtn8;
    private Button testBtn9;
    
    
    private PropertyAnimation propertyAnimation = new PropertyAnimation();
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // onCreate() >>
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_room);
        
        initBinding();
        initListeners();
    }
    
    private void initBinding() {
        testImg = findViewById(R.id.test_img);
        testImg1 = findViewById(R.id.test_img1);
        testImg2 = findViewById(R.id.test_img2);
        
        testEdt = findViewById(R.id.test_edt);
        testTxt = findViewById(R.id.test_txt);
        testTxt1 = findViewById(R.id.test_txt1);
        testBtn = findViewById(R.id.test_btn);
        
        testBtn1 = findViewById(R.id.test_btn1);
        testBtn2 = findViewById(R.id.test_btn2);
        testBtn3 = findViewById(R.id.test_btn3);
        testBtn4 = findViewById(R.id.test_btn4);
        testBtn5 = findViewById(R.id.test_btn5);
        testBtn6 = findViewById(R.id.test_btn6);
        testBtn7 = findViewById(R.id.test_btn7);
        testBtn8 = findViewById(R.id.test_btn8);
        testBtn9 = findViewById(R.id.test_btn9);
        
    }
    
    private void initListeners() {
        testBtn.setOnClickListener(this);
        testBtn1.setOnClickListener(this);
        testBtn2.setOnClickListener(this);
        testBtn3.setOnClickListener(this);
        testBtn4.setOnClickListener(this);
        testBtn5.setOnClickListener(this);
        testBtn6.setOnClickListener(this);
        testBtn7.setOnClickListener(this);
        testBtn8.setOnClickListener(this);
        testBtn9.setOnClickListener(this);

//        testEdt.setOnClickListener(this);

//        testImg.setOnClickListener(this);
//        testImg1.setOnClickListener(this);
//        testImg2.setOnClickListener(this);
    
    
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // onClick() >>
    
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test_btn:
                switch (testEdt.getText().toString()) {
                    case "1":
                        demo1();
                        break;
                    case "2":
                        demo2();
                        break;
                    case "4":
                        demo4();
                        break;
                    case "5":
                        demo5();
                        break;
                    case "6":
                        demo6();
                        break;
                    case "7":
                        demo7();
                        break;
                    case "8":
                        demo8();
                        break;
                    default:
                        /* card = new Card(testImg);
                        testTxt.setText("this is img0 info."
                                + "\nimg x = " + card.getCenterX()
                                + "\nimg y = " + card.getCenterY()
                                + "\nimg width = " + card.getWidth()
                                + "\nimg height = " + card.getHeight());*/
                        
                        // ----------------------------------------------------------------
                        int[] positions = new int[2];
//                        testImg2.getLocationInWindow(positions);
                        testImg2.getLocationOnScreen(positions);
                        /*testTxt1.setText("this is img2 info."
                                + "\nimg x = " + positions[0]
                                + "\nimg y = " + positions[1]
                                + "\nimg width = " + testImg1.getWidth()
                                + "\nimg height = " + testImg1.getHeight());*/
                        
                        // ----------------------------------------------------------------
//                        testImg2.getLocationInWindow(positions);
                        
                        
                        break;
                }
                break;
            // ----------------------------------------------------------------
            default:
                testEdt.setText(((TextView) view).getText().toString());
                break;
        }
    }
    
    private void demo1() {
        // testImg: width=100, height=200, marginStart=20, marginTop=350
        // start array is: x, y, width, height
        
//        Logger.d("(16-3300) >> you played demo1() ");
        
        float[] start = {20, 350, 100, 200};
        float[] end = {20, 350, 200, 200};
        
        propertyAnimation.calculateAnimation(testImg2, start, end, 3000).start();
    }
    
    // □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
    
    private void demo2() {
        propertyAnimation.playbackAnimation().start();
        
    }
    
    // □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
    
    private void demo4() {
        testImg1.setVisibility(View.INVISIBLE);
        
        testImg.setX(testImg1.getX());
        testImg.setY(testImg1.getY());
    }
    
    // □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
    
    private void demo5() {
    
    }
    
    // □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
    
    private void demo6() {
       /* new AnimatorUtils().multiFramesAnim(
                testImg2,
                new Card(testImg),
                new Card(20, 40, 320, 280),
                5000
        );*/
    }
    
    // □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
    
    private void demo7() {
//        CardWrapper wrapper = new CardWrapper(testImg);
//        int[] start = {20, 350, 200, 200};
        /*int[] start = {60, 1050, 600, 600};
        int[] end =  {60, 1050, 300, 600};
        
        new AnimatorUtils().multiFramesAnim(testImg, start, end, 1000);*/
        
    }
    
    // □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
    
    private void demo8() {
//        CardWrapper wrapper = new CardWrapper(testImg);
//        int[] values = {20, 350, 100, 200};
        /*int[] end = {60, 1050, 600, 600};
        int[] start =  {60, 1050, 300, 600};
    
        new AnimatorUtils().multiFramesAnim(testImg, start, end, 1000);*/
    }
    
    
    // □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
    // □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
    
    /*private void demo2(View view) {
        new AnimatorUtils().multiFramesAnim(
                view,
                new Card[]{
                        new Card(this, 20, 400, 50, 180),
                        new Card(this, 20, 400, 100, 180),
                        new Card(this, 20, 400, 200, 180),
                        new Card(this, 20, 400, 150, 180),
                        new Card(this, 20, 400, 50, 180),
                        new Card(this, 20, 400, 100, 180),
                },
                3000,
                true
        );
    }
    
    private void demo3(View view) {
        view.setX(testImg1.getX());
        view.setY(testImg1.getY());
        view.refreshDrawableState();
    }*/
    
}

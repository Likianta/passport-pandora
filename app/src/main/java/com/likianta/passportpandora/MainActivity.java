package com.likianta.passportpandora;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.likianta.passportpandora.beans.GroupHeader;
import com.likianta.passportpandora.beans.GroupItem;
import com.likianta.passportpandora.data.DataCenter;
import com.likianta.passportpandora.data.QuickDataExchange;
import com.likianta.passportpandora.data.TempNewAccount;
import com.likianta.passportpandora.frag.DetailFragment;
import com.likianta.passportpandora.methods.GroupSection;
import com.likianta.passportpandora.methods.ItemClickListener;
import com.likianta.passportpandora.methods.MainRecyclerMethod;
import com.likianta.passportpandora.utils.DpToPxTool;
import com.likianta.passportpandora.utils.TestRoom;
import com.likianta.passportpandora.utils.XykeyMigrationTool;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.util.List;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DetailFragment detailFragment;
    private ImageView bgCover;
    private ImageView btnPlus;
    private ImageView btnTest;
    private RecyclerView mainRecycler;
    private View currentItem;
    private MainRecyclerMethod mainRecyclerMethod;
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setImmersiveStatusBar(true, getResources().getColor(R.color.bg_accent_ffffff));
        
        initLoggerConfig();
        initRealmConfig();
        
        initBinding();
        initListeners();
        
        setUpQuickDataExchanger();
        showRecyclerList(QuickDataExchange.headerList);
        
    }
    
    // ----------------------------------------------------------------
    
    private void setUpQuickDataExchanger() {
        Realm realm = Realm.getDefaultInstance();
        
        // TODO TEST: load data from xykey backup
        QuickDataExchange.accountList = new XykeyMigrationTool(this).getAccountList();
//        QuickDataExchange.accountList.addAll(realm.where(Account.class).findAll());
        
        QuickDataExchange.headerList.addAll(realm.where(GroupHeader.class).findAll());
        
        /*// uuid list
        for (int i = 0, j = QuickDataExchange.accountList.size(); i < j; i++) {
            QuickDataExchange.uuidList.append(
                    i, QuickDataExchange.accountList.get(i).getUuid()
            );
        }*/
    }
    
    private void showRecyclerList(List<GroupHeader> headerList) {
        // TODO TEST
        mainRecyclerMethod = new MainRecyclerMethod();
        mainRecyclerMethod.load(headerList);
        mainRecyclerMethod.show(this, mainRecycler);
        
        addItemClickListener(mainRecycler);
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // init logger and realm configurations.
    // the initializations just need once per application.
    
    private void initLoggerConfig() {
        
        // REFERENCE: 2018-05-14
        // https://github.com/orhanobut/logger
        
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false) // Whether to show thread info or not
                .build();
        
        /*FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false) // Whether to show thread info or not
                .methodCount(0)        // How many method line to show
                .methodOffset(7)       // Hides internal method calls up to offset
                .tag("LK LOG")         // Global tag for every log
                .build();*/
        
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }
    
    private void initRealmConfig() {
        // REFERENCE: 2018-05-13
        // https://realm.io/docs/java/latest/#
        
        Realm.init(this);
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    
    private void initBinding() {
        
        btnPlus = findViewById(R.id.main_btn_plus);
        bgCover = findViewById(R.id.main_layer_cover);
        mainRecycler = findViewById(R.id.main_recycler);
        
        btnTest = findViewById(R.id.main_btn_test);
        
        detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id
                .main_frag_detail);
    }
    
    private void initListeners() {
        btnPlus.setOnClickListener(this);
        bgCover.setOnClickListener(this);
        
        btnTest.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // ----------------------------------------------------------------
            case R.id.main_btn_plus:
                Intent intent = new Intent(this, EditActivity.class);
                startActivityForResult(intent, 1);
                break;
            // ----------------------------------------------------------------
            case R.id.main_layer_cover:
                retrieveFragAnimation();
                break;
            // ----------------------------------------------------------------
            case R.id.main_btn_test:
                startActivity(new Intent(this, TestRoom.class));
                break;
        }
    }
    
    // ----------------------------------------------------------------
    
    private void addItemClickListener(final RecyclerView recyclerView) {
        
        recyclerView.addOnItemTouchListener(new ItemClickListener(recyclerView,
                new ItemClickListener.OnItemClickListener() {
                    
                    @Override
                    public void onItemClick(View view, int position) {
                        if (view.getTag() == "copy") {
                            ((TextView) view).setText(getResources().getString(R.string
                                    .item_btn_copied));
                            // TODO copy this account's password
                            
                        } else {
                            // frag activity
                            currentItem = view;
                            detailFragment.startAnimation();
                            detailFragment.loadFragInfo(QuickDataExchange.uuidList
                                    .get(position));
                        }
                    }
                    
                    @Override
                    public void onItemLongClick(View view, int position) {
                    
                    }
                }));
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    
    private void retrieveFragAnimation() {
        detailFragment.retrieveAnimation();
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Rect frame = new Rect();
        this.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        
        DataCenter.STATUS_HEIGHT = DpToPxTool.px2dip(frame.top);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    // TODO TEST
                    /*mainRecyclerMethod.update(
                            new GroupSection(TempNewAccount.groupItem),
                            TempNewAccount.groupName
                    );*/
                    mainRecyclerMethod.update(
                            new GroupSection(
                                    new GroupItem(
                                            TempNewAccount.title,
                                            TempNewAccount.username
                                    )
                            ),
                            TempNewAccount.groupName
                    );
                    mainRecycler.smoothScrollToPosition(0);
                    break;
            }
        }
        
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // get and set
    
    public View getCurrentItem() {
        return currentItem;
    }
    
    public ImageView getBgCover() {
        return bgCover;
    }
    
}

package com.likianta.passportpandora.frag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.likianta.passportpandora.MainActivity;
import com.likianta.passportpandora.R;
import com.likianta.passportpandora.beans.Account;
import com.likianta.passportpandora.beans.AccountPrimary;
import com.likianta.passportpandora.data.DataCenter;
import com.likianta.passportpandora.methods.WindowAnimation;
import com.likianta.passportpandora.utils.PropertyAnimation;
import com.orhanobut.logger.Logger;

import java.util.Objects;


/**
 * Created by Likianta_Dodoora on 2018/5/8 0008.
 */
public class DetailFragment extends Fragment implements View.OnClickListener {
    
    private View rootView;
    private TextView title;
    private TextView username;
    private TextView password;
    private TextView note;
    private ImageButton btnPlane;
    private Button btnStar;
    private Button btnDelete;
    private Button btnEdit;
    
    private WindowAnimation windowAnimation;
    private boolean hasAnimationInitialized = false;
    private PropertyAnimation animation = new PropertyAnimation();
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_detail, container, false);
        
        
        // 初始化绑定
        initBinding(rootView);
        // 初始化加载
        initLoading();
        
        // TODO TEST
        rootView.setVisibility(View.GONE);
        return rootView;
    }
    
    private void initBinding(View view) {
//        title = view.findViewById(R.id.detail);
        username = view.findViewById(R.id.detail_username);
        password = view.findViewById(R.id.detail_password);
        note = view.findViewById(R.id.detail_note);
        
        // child elements
        btnPlane = view.findViewById(R.id.detail_btn_plane);
        btnStar = view.findViewById(R.id.detail_btn_star);
        btnDelete = view.findViewById(R.id.detail_btn_delete);
        btnEdit = view.findViewById(R.id.detail_btn_edit);
        
        /*View[] views = {*/
        /*        title,*/
        /*        username,*/
        /*        password,*/
        /*        note*/
        /*};*/
        
        /*addListeners(views);*/
        
    }
    
    private void addListeners(View[] views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                break;
            
            
        }
    }
    
    public void startAnimation() {
//        Logger.d("(11-5639) >> true >> hasAnimationInitialized = " + hasAnimationInitialized);
        if (hasAnimationInitialized) {
            windowAnimation.setRefer(((MainActivity) Objects.requireNonNull(getActivity())).getCurrentItem());
            windowAnimation.activateAnimation();
        } else {
            initAnimation();
            startAnimation();
        }
    }
    
    private void initAnimation() {
        MainActivity activity = (MainActivity) getActivity();
        if (activity == null) {
            Logger.d("(17-3600) >> getActivity is null! ");
            hasAnimationInitialized = false;
            return;
        }

        ImageView cover = activity.getBgCover();
        View fragCover = rootView.findViewById(R.id.detail_layer_cover);
        View item = activity.getCurrentItem();
        windowAnimation = new WindowAnimation(cover, rootView, fragCover);

        hasAnimationInitialized = true;
    }
    
    public void retrieveAnimation() {
        if (hasAnimationInitialized) {
            windowAnimation.deactivateAnimation();
        }
    }
    
    private void initLoading() {
        /*String uid = getIntent().getStringExtra("uid");
        Logger.d("(16-1033) >> uid-0 >> uid = " + uid);
        
        Account account = DataCenter.accountMap.get(uid);
        Logger.d("(15-4127) >> uid-0 >> account.getUid() = " + account.getUid());
        
        AccountPrimary primary = account.getPrimary();
        if (primary != null) {
            title.setText(primary.getAccountTitle());
            username.setText(primary.getUsername());
            password.setText(primary.getPassword());
        }
        
        note.setText(account.getSecondary().getPassword2());*/
        
        
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    
    public void loadFragInfo(String uid) {
        Account account = DataCenter.accountMap.get(uid);
//        Logger.d("(15-4127) >> uid-0 >> account.getUid() = " + account.getUid());
    
        AccountPrimary primary = account.getPrimary();
        if (primary != null) {
//            title.setText(primary.getAccountTitle());
            username.setText(primary.getUsername());
            password.setText(primary.getPassword());
        }
        
    }
    
    
}

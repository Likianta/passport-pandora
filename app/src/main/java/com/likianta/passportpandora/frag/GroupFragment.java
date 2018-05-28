package com.likianta.passportpandora.frag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.likianta.passportpandora.EditActivity;
import com.likianta.passportpandora.R;
import com.likianta.passportpandora.data.DefaultDataGenerator;
import com.likianta.passportpandora.data.TempNewAccount;
import com.likianta.passportpandora.methods.GroupListAdapter;
import com.likianta.passportpandora.methods.ItemClickListener;

import java.util.List;

/**
 * Created by Likianta_Dodoora on 2018/5/11 0011.
 */
public class GroupFragment extends Fragment implements View.OnClickListener {
    
    // member views
    private Button btnConfirm;
    private RecyclerView recyclerGroup;
    private RecyclerView recyclerTag;
    
    // data
    private String selectedGroup;
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // on create
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_group, container, false);
        
        initBinding(view);
        
        initLoading();
        
        addRecyclerListeners();
        addClickListeners();
        
        return view;
    }
    
    private void initBinding(View view) {
        btnConfirm = view.findViewById(R.id.group_btn_confirm);
        
        recyclerGroup = view.findViewById(R.id.group_recycler_group);
        recyclerTag = view.findViewById(R.id.group_recycler_tag);
    }
    
    private void initLoading() {
        
        // TODO TEST load inner groups and tags.
        
        List<String> groups = DefaultDataGenerator.generateDefaultGroups();
        List<String> tags = DefaultDataGenerator.generateDefaultTags();
        
        GroupListAdapter GroupListAdapter = new GroupListAdapter(groups);
        GroupListAdapter tagAdapter = new GroupListAdapter(tags);
        
//        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        
        recyclerGroup.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerGroup.setHasFixedSize(true);
        recyclerGroup.setAdapter(GroupListAdapter);
        
        recyclerTag.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerTag.setHasFixedSize(true);
        recyclerTag.setAdapter(tagAdapter);
        
        
    }
    
    private void addRecyclerListeners() {
        
        recyclerGroup.addOnItemTouchListener(new ItemClickListener(
                recyclerGroup, new ItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                selectedGroup = ((TextView) view).getText().toString();
            }
            
            @Override
            public void onItemLongClick(View view, int position) {
            
            }
        }
        ));
        
        /*recyclerTag.addOnItemTouchListener(new ItemClickListener(
                recyclerTag, new ItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
            
            @Override
            public void onItemLongClick(View view, int position) {
            
            }
        }
        ));*/
        
        
    }
    
    private void addClickListeners() {
        btnConfirm.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.group_btn_confirm:
                TempNewAccount.groupName = this.selectedGroup;
    
                EditActivity activity = (EditActivity) getActivity();
                assert activity != null;
                activity.removeFragment(this);
                break;
        }
    }
    
}

package com.likianta.passportpandora.methods;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.likianta.passportpandora.R;

import java.util.List;

/**
 * Created by Likianta_Dodoora on 2018/5/11 0011.
 */
public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.ViewHolder> {
    
    private List<String> groupList;
    
    public GroupListAdapter(List<String> groupList) {
        this.groupList = groupList;
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    
    @NonNull
    @Override
    public GroupListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_group_item,
                parent, false);
        return new GroupListAdapter.ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull GroupListAdapter.ViewHolder holder, int position) {
        holder.groupName.setText(groupList.get(position));
    }
    
    @Override
    public int getItemCount() {
        return groupList.size();
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView groupName;
        
        ViewHolder(View view) {
            super(view);
            groupName = (TextView) view;
        }
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Add and remove methods
    
    public void addItem(int position, String item) {
        groupList.add(position, item);
        notifyItemInserted(position);
    }
    
}

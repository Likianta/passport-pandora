package com.likianta.passportpandora.beans;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Likianta_Dodoora on 2018/5/1 0001.
 */
public class GroupHeader extends RealmObject {
    
    @PrimaryKey
    private String id;
    private String groupName;
    private RealmList<GroupItem> itemList;
    
    
    public GroupHeader() {
    
    }
    
    public GroupHeader(String id, String groupName, GroupItem groupItem) {
        this.id = id;
        this.groupName = groupName;
        this.itemList.add(groupItem);
    }
    
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getGroupName() {
        return groupName;
    }
    
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
    public RealmList<GroupItem> getItemList() {
        return itemList;
    }
    
    public void setItemList(RealmList<GroupItem> itemList) {
        this.itemList = itemList;
    }
}

package com.likianta.passportpandora.beans;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Likianta_Dodoora on 2018/5/1 0001.
 */
public class GroupItem extends RealmObject {
    
    private String accountTitle;
    private String username;
    
    
    public GroupItem() {
    
    }
    
    public GroupItem(String accountTitle, String username) {
        this.accountTitle = accountTitle;
        this.username = username;
    }
    
    
    public String getAccountTitle() {
        return accountTitle;
    }
    
    public void setAccountTitle(String accountTitle) {
        this.accountTitle = accountTitle;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
}

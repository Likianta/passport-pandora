package com.likianta.passportpandora.beans;

import io.realm.RealmObject;

/**
 * Created by Likianta_Dodoora on 2018/5/1 0001.
 */
public class AccountSecondary extends RealmObject {
    
    private String groupName;
    private String location;
    private String note;
    private String password2;
    private String updatedDate;
    
    
    public AccountSecondary() {
    
    }
    
    public AccountSecondary(String groupName, String note) {
//        this.id = uuid;
        this.groupName = groupName;
        this.note = note;
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // get and set
    
    public String getGroupName() {
        return groupName;
    }
    
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getNote() {
        return note;
    }
    
    public void setNote(String note) {
        this.note = note;
    }
    
    public String getPassword2() {
        return password2;
    }
    
    public void setPassword2(String password2) {
        this.password2 = password2;
    }
    
    public String getUpdatedDate() {
        return updatedDate;
    }
    
    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }
}

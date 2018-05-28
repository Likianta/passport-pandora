package com.likianta.passportpandora.beans;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Likianta_Dodoora on 2018/5/1 0001.
 */
public class AccountPrimary extends RealmObject {
    
    private String title;
    private String username;
    private String password;
    
    
    public AccountPrimary() {
    
    }
    
    public AccountPrimary(String title, String username, String password) {
        this.title = title;
        this.username = username;
        this.password = password;
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // get and set
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}

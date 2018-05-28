package com.likianta.passportpandora.beans;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Likianta_Dodoora on 2018/5/1 0001.
 */
public class AccountXyFormat {
    private String name;
    private String account;
    private String password;
    private String password2;
    private String url;
    private String note;
    private Map<String, String>[] extra;
    
    public AccountXyFormat(String name, String account, String password, String password2, String
            url, String note, Map<String, String>[] extra) {
        this.name = name;
        this.account = account;
        this.password = password;
        this.password2 = password2;
        this.url = url;
        this.note = note;
        this.extra = extra;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAccount() {
        return account;
    }
    
    public void setAccount(String account) {
        this.account = account;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPassword2() {
        return password2;
    }
    
    public void setPassword2(String password2) {
        this.password2 = password2;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getNote() {
        return note;
    }
    
    public void setNote(String note) {
        this.note = note;
    }
    
    public Map<String, String>[] getExtra() {
        return extra;
    }
    
    public void setExtra(Map<String, String>[] extra) {
        this.extra = extra;
    }
}

package com.likianta.passportpandora.beans;

import com.likianta.passportpandora.utils.UUIDGenerator;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Likianta_Dodoora on 2018/4/30 0030.
 */
public class Account extends RealmObject {
    
    @PrimaryKey
    private String uuid;
    
    // relations
    private AccountPrimary primary;
    private AccountSecondary secondary;
    
    
    public Account() {
        this.uuid = UUIDGenerator.getUUID();
    }
    
    /*public Account(AccountPrimary primary) {
        this.uuid = UUIDGenerator.getUUID();
        this.primary = primary;
    }*/
    
    public Account(AccountPrimary primary, AccountSecondary secondary) {
        // 使用专门的生成器来生成唯一标识码
        this.uuid = UUIDGenerator.getUUID();
        this.primary = primary;
        this.secondary = secondary;
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // get and set
    
    public String getUuid() {
        return uuid;
    }
    
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    public AccountPrimary getPrimary() {
        return primary;
    }
    
    public void setPrimary(AccountPrimary primary) {
        this.primary = primary;
    }
    
    public AccountSecondary getSecondary() {
        return secondary;
    }
    
    public void setSecondary(AccountSecondary secondary) {
        this.secondary = secondary;
    }
    
}

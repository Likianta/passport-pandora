package com.likianta.passportpandora.beans;

import java.util.Map;

/**
 * Created by Likianta_Dodoora on 2018/5/1 0001.
 */
public class AccountTertiary {
    
    private Map<String, String>[] extra;
    
    public AccountTertiary(Map<String, String>[] extra) {
        this.extra = extra;
    }
    
    public Map<String, String>[] getExtra() {
        return extra;
    }
    
    public void setExtra(Map<String, String>[] extra) {
        this.extra = extra;
    }
}

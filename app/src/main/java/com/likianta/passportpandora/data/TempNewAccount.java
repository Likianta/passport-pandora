package com.likianta.passportpandora.data;

import android.content.Context;

import com.likianta.passportpandora.R;
import com.likianta.passportpandora.beans.GroupItem;

/**
 * Created by Likianta_Dodoora on 2018/5/11 0011.
 */
public class TempNewAccount {
    
    // primary info
    public static String title;
    public static String username;
    public static String password;
    
    // secondary info
    public static String groupName;
    public static String note;
    
    // TODO: 2018/5/14 0014 what's the time format adapted in realm?
    
    public static GroupItem groupItem;
    
    // ----------------------------------------------------------------
    
    public static void init(Context context) {
        groupName = context.getResources().getString(R.string.default_group_name);
        
        // reset
        title = "";
        username = "";
        password = "";
        groupName = "";
        note = "";
        
    }
    
    public static GroupItem generateItem() {
        groupItem = new GroupItem(title, username);
        return groupItem;
    }
    
}

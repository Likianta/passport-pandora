package com.likianta.passportpandora.data;

import com.likianta.passportpandora.beans.Account;
import com.likianta.passportpandora.beans.AccountPrimary;
import com.likianta.passportpandora.beans.GroupItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Likianta_Dodoora on 2018/5/2 0002.
 */
public class DataCenter {
    /* Notice:
     * incorrect: new Group("No Group", null); - this will call an Initializer Error
     * workaround: you can use a "placeholder" in place of "null"
     * correct: new Group("No Group", new HashSet<GroupItem>()); */
    
//    protected static Group group = new Group("No Group", new HashSet<GroupItem>());
    
    public static Map<String, GroupItem> groupItemMap = new HashMap<>();
    
    public static Map<String, Account> accountMap = new HashMap<>();
    
    public static int STATUS_HEIGHT = 0;
    
//    public static Card currentCard = new Card(10, 10, 10, 10);
    
    // TODO: should load from local file
    private static String newUid = "";
    
    // □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
    
    public static boolean updateGroup(String groupName, GroupItem item) {
        /*Set<GroupItem> groupItemSet = group.getGroupItemSet(groupName);
        Logger.d("set size is " + groupItemSet.size());
        
        if (!groupItemSet.contains(group_item)) {
            groupItemSet.add(group_item);
            group.setGroup(groupName, groupItemSet);
            return true;
        } else {
            Logger.d("this group_item is already exists");
            return false;
        }*/
        return false;
        
    }
    
    public static GroupItem getLatestGroupItem() {
        return groupItemMap.get(newUid);
    }
    
    public static void addAccount(AccountPrimary primary) {
        Account account = new Account(primary, null);
        newUid = account.getUuid();
        accountMap.put(newUid, account);
//        groupItemMap.put(newUid, new GroupItem(account));
    }
}

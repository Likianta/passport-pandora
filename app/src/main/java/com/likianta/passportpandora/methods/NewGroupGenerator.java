package com.likianta.passportpandora.methods;

import com.likianta.passportpandora.beans.GroupHeader;
import com.likianta.passportpandora.beans.GroupItem;
import com.likianta.passportpandora.utils.UUIDGenerator;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Likianta_Dodoora on 2018/5/14 0014.
 */
public class NewGroupGenerator {
    
    public static void generateAll(String groupName, RealmList<GroupItem> itemList) {
        
        String id = UUIDGenerator.getUUID();
        
        // REFERENCE: 2018-05-14
        
        // A sample of "User.class" which taught me how to name "@PrimaryKey private String id...",
        // "don't create auto generated uuid in non-arg constructor" and so on
        // Realm: Create reactive mobile apps in a fraction of the time
        // https://realm.io/docs/java/latest/#
        
        // (Dropped) java - Only valid managed objects can be copied from Realm - Stack Overflow
        // https://stackoverflow.com/questions/45321325/only-valid-managed-objects-can-be-copied
        // -from-realm
        
        // Resolve unmanaged itemList
        // java - IllegalArgumentException Each element of 'value' must be a valid managed object
        // - Stack Overflow
        // https://stackoverflow.com/questions/44789494/illegalargumentexception-each-element-of
        // -value-must-be-a-valid-managed-object
        
        
        Realm realm = Realm.getDefaultInstance();
        
//        realm.beginTransaction();
        
        // Create an unmanaged Javabean and then make it managed
        // PS: Don't do this:
        //     GroupData groupData = realm.createObject(GroupData.class, id);
        //     groupData.setGroupName(groupName);
        //     groupData.setItemList(itemList);
        //     realm.copyToRealmOrUpdate(groupData);
        
        GroupHeader groupHeader = new GroupHeader();
        groupHeader.setId(id);
        groupHeader.setGroupName(groupName);
        groupHeader.setItemList(itemList);
        
        // Make it managed
        realm.insertOrUpdate(groupHeader);

//        realm.commitTransaction();
    
    }
    
    /*private static void generateGroup() {
    
    
    }
    
    private static void generateItem(AccountPrimary primary) {
    
    }*/
    
}

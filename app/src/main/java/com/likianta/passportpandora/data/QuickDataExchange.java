package com.likianta.passportpandora.data;

import android.util.SparseArray;

import com.likianta.passportpandora.beans.Account;
import com.likianta.passportpandora.beans.GroupHeader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Likianta_Dodoora on 2018/5/15 0015.
 */
public class QuickDataExchange {
    
    // QuickDataExchange.class定位于轻量级的数据交流与传递
    //
    // 不同于Realm，QuickData具有以下特点：
    //
    // 1. 无需存储到本地，保存在内存中，所以读取速度比Realm更快
    // 2. 使用静态方法和对象，因此在调用时不需要创建实例，只需一行代码就行了（比realm开销小）
    // 3. 用于在不同的类之间传递数据，可以不受生命周期和方法参数局限
    // 4. 用于短时间或几个相邻的活动之间的数据的快捷交换，一般来说不担心会丢失
    // 5. 随用随走，并且内置了持久化方法（与Realm连接）
    
    public static List<Account> accountList = new ArrayList<>();
    public static List<GroupHeader> headerList = new ArrayList<>();
    
    public static SparseArray<String> uuidList = new SparseArray<>();
    
    
}

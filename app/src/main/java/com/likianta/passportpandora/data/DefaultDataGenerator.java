package com.likianta.passportpandora.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Likianta_Dodoora on 2018/5/11 0011.
 */
public class DefaultDataGenerator {
    
    public static List<String> generateDefaultGroups() {
        List<String> list = new ArrayList<>(4);
        list.add("No group");
        list.add("Social");
        list.add("Game");
        list.add("Shopping");
        list.add("Tool");
        return list;
    }
    
    public static List<String> generateDefaultTags() {
        List<String> list = new ArrayList<>(4);
        list.add("Star");
        list.add("Disabled");
        list.add("Blocked");
        list.add("VIP");
        return list;
    }
    
}

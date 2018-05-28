package com.likianta.passportpandora.methods;

import com.chad.library.adapter.base.entity.SectionEntity;
import com.likianta.passportpandora.beans.GroupItem;

/**
 * Created by Likianta_Dodoora on 2018/5/15 0015.
 */
public class GroupSection extends SectionEntity<GroupItem> {

    // this is made for GroupHeader
    // 在这里，header被当作一个很简单的String来考虑了
    // 一般来说，header确实就是这么简单，绝大多数人拿header用来显示一个字符串就足够了
    // 但从长远角度来看，有人会担心如果自己的header不仅要显示一个String该怎么传参呢？
    // 这样：
    //     public GroupSection(boolean isHeader, CustomJavaBean header) {
    //         // do something you want, and don't use `super(isHeader, header)`
    //     }
    
    GroupSection(boolean isHeader, String header) {
        super(isHeader, header);
    }
    
    // this section is made for GroupItem
    public GroupSection(GroupItem groupItem) {
        super(groupItem);
    }
}

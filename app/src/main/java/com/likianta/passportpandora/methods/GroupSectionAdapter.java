package com.likianta.passportpandora.methods;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.likianta.passportpandora.R;

import java.util.List;

/**
 * Created by Likianta_Dodoora on 2018/5/15 0015.
 */
public class GroupSectionAdapter extends BaseSectionQuickAdapter<GroupSection, BaseViewHolder> {
    
    // REFERENCE: 2018-05-15
    // Add group · CymChad/BaseRecyclerViewAdapterHelper Wiki
    //     https://github.com/CymChad/BaseRecyclerViewAdapterHelper/wiki/Add-group
    // BRVAH 小白笔记之分组篇 - Android - 掘金
    //     https://juejin.im/entry/57af17fbc4c9710054773bdf
    
    GroupSectionAdapter(List<GroupSection> sectionList) {
        // the first argument is item layout res id
        // the second argument is header layout res id
        super(R.layout.group_item, R.layout.group_header, sectionList);
    }
    
    // item data will be loaded by this method
    @Override
    protected void convert(BaseViewHolder helper, GroupSection section) {
        helper.setText(R.id.item_title, section.t.getAccountTitle())
                .setText(R.id.item_username, section.t.getUsername());
    }
    
    // head data will be loaded by this method
    // we just need a String title to be a group's head
    @Override
    protected void convertHead(BaseViewHolder helper, GroupSection section) {
        helper.setText(R.id.header_text_title, section.header);
        // section.header comes from mGroupHeader.getGroupName()
    }
    
}

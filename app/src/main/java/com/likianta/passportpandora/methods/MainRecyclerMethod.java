package com.likianta.passportpandora.methods;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.likianta.passportpandora.beans.GroupHeader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Likianta_Dodoora on 2018/5/15 0015.
 */
public class MainRecyclerMethod {
    
    private GroupSectionAdapter sectionAdapter;
    private List<GroupSection> sectionList = new ArrayList<>();
    private Map<String, GroupSection> sectionMap = new HashMap<>();
    
    
    public void load(List<GroupHeader> groupHeaderList) {
        GroupSection section;
        
        for (int i = 0, j = groupHeaderList.size(); i < j; i++) {
            
            GroupHeader header = groupHeaderList.get(i);
            section = new GroupSection(true, header.getGroupName());
            
            sectionMap.put(header.getGroupName(), section);
            sectionList.add(section);
            
            for (int m = 0, n = header.getItemList().size(); m < n; m++) {
                
                sectionList.add(new GroupSection(header.getItemList().get(m)));
                
            }
        }
    }
    
    public void show(Context context, RecyclerView recycler) {
        
        sectionAdapter = new GroupSectionAdapter(sectionList);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        
        recycler.setLayoutManager(manager);
        recycler.setAdapter(sectionAdapter);
        
    }
    
    // ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    
    public void update(GroupSection newItemSection, String groupName) {
        
        // 如何将新增的一个 item 加入到 sectionList 中？
        //
        // 步骤如下：
        //
        // 1. 首先要知道我们这个类中的静态成员 sectionList 与 GroupSectionAdapter 中的列表是引用关系
        //    （也就是说二者用的是同一块内存），因此只要我们在 sectionList 中做出更改，Adapter 就能响应
        //    列表元素的变动了
        //
        // 2. 准备工作：
        //     1. 由于我们手中持有 sectionList，newItemSection 以及它所属的 groupName，我们需要根据
        //        groupName 查找到这个 header 在 sectionList 中的索引位置（同时也是 Adapter 中的
        //        position）
        //     2. 方法为：int position = sectionList.indexOf(...)
        //     3. 注意“...”处只能填已有的某个 GroupSection，不可以填 new Group(true, groupName)，更
        //        不可以填 groupName
        //     4. 这个已有的 GroupSection 我们从哪里获得呢？答案是在 load() 时候就装载好的：
        //            sectionMap<String groupName, GroupSection section>
        //     5. 利用 sectionMap 根据 groupName 获得对应的 section，再让 sectionList 查询这个 section
        //        并返回索引到的位置 position，最后在 position + 1 处加入 newItemSection 就完成了
        //
        // 3. 插入的方法有二：
        //     1. 使用 sectionList.add(position + 1, newItemSection)，然后 sectionAdapter
        //        .notifyItemInserted(position + 1)
        //     2. 使用 sectionAdapter.addData(position + 1, newItemSection) 即可（该方法自带 notify
        //        功能），另外值得强调的是 sectionList 与 sectionAdapter 中的列表保持着引用关系，因此
        //        后者的更改同时也会反应到前者身上
        //     3. 方法二的优点是省去了一行代码，缺点是看起来可能不太直观。这里推荐使用方法二
        //
        // 4. 如果该分组不存在……
        //     1. 那么需要先创建并插入 newHeaderSection，然后再插入 newItemSection
        //     2. 另外切记更新我们的 sectionMap，这个是需要手动维护更新的
        
        int position = sectionList.indexOf(sectionMap.get(groupName));
        
        if (position != -1) {
            sectionAdapter.addData(position + 1, newItemSection);
        } else {
            // this group does not exist, so we should create it first
            GroupSection newHeaderSection = new GroupSection(true, groupName);
            sectionMap.put(groupName, newHeaderSection); // don't forget update our map
            
            sectionAdapter.addData(newHeaderSection);
            sectionAdapter.addData(newItemSection);
    
            /*Logger.d("(11-1814) >> 6+ >> sectionList.indexOf(sectionMap.get(groupName)) = " +
                    sectionList.indexOf(sectionMap.get(groupName)));*/
        }
    }
    
}

package com.app.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//此类仅做测试用,测试完即删除
public class TaskFactory {

    public static List<Map<String, String>> getTask(){
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for(int i = 0; i < 20; i++){
            Map<String, String> map = new HashMap<String, String>();
            map.put("username", "wangxiangjun_" + i);
            map.put("password", "123456_" + i);
            list.add(map);
        }
        return list;
    }
}

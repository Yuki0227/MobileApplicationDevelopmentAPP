package com.app.task;

import android.util.JsonToken;
import android.util.Log;

import com.app.MyApplication;
import com.app.task.entity.TaskAssign;
import com.app.util.CommonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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


    //根据用户id来获得其所拥有的任务
    public static List<TaskAssign> getTask(Integer userId){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FormBody.Builder params = new FormBody.Builder();
                    params.add("creatorId", userId+"");
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://8.131.250.250/taskAssign/findAll")
                            .post(params.build())
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = Objects.requireNonNull(response.body()).string();
                    Log.d("responseData", responseData);
                    //JSONObject jsonObj = new JSONObject(responseData);
                    JSONArray jsonArray = new JSONArray(responseData);
                    //将json转成TaskAssign对象
                    //JSONArray jsonArray = jsonObj.optJSONArray("jsonArray");
                    List<TaskAssign> tmp = new ArrayList<TaskAssign>();
                    System.out.println("jsonArray --> " + jsonArray);
                    if(jsonArray != null && jsonArray.length() > 0){
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject ans = jsonArray.getJSONObject(i);
                            TaskAssign taskAssign = new TaskAssign();
                            taskAssign.setId(ans.optInt("id"));
                            taskAssign.setCreatorId(ans.optInt("creatorId"));
                            taskAssign.setAssigneeId(ans.optInt("assigneeId"));
                            taskAssign.setTask(ans.optString("task"));
                            String taskCreateTime = ans.optString("taskCreateTime");
                            String taskFinishTime = ans.optString("taskFinishTime");
                            if(taskCreateTime != null || taskCreateTime.length() > 0)
                                taskAssign.setTaskCreateTime(CommonUtils.StringToDate(taskCreateTime));
                            if(taskFinishTime != null || taskCreateTime.length() > 0)
                                taskAssign.setTaskFinishTime(CommonUtils.StringToDate(taskFinishTime));
                            //System.out.println("i --> " + i);
                            //System.out.println("taskAssign --> (only part)" + taskAssign);
                            //System.out.println("taskCreateTime --> " + taskCreateTime);
                            //System.out.println("taskFinishTime --> " + taskFinishTime);
                            tmp.add(taskAssign);
                        }
                        MyApplication.setTaskList(tmp);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return MyApplication.getTaskList();
    }
}

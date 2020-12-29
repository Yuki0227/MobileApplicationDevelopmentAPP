package com.app.task;

import android.util.JsonToken;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.app.MyApplication;
import com.app.task.entity.TaskAssign;
import com.app.util.CommonUtils;
import com.app.util.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Array;
import java.sql.Date;
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
import okhttp3.internal.concurrent.Task;


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


    //根据任务的id来删除对应的任务
    public static void deleteTask(Integer taskId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    FormBody.Builder params = new FormBody.Builder();
                    params.add("id",taskId+"");
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://8.131.250.250/taskAssign/delete")
                            .post(params.build())
                            .build();
                    Response response = client.newCall(request).execute();
                    if(response.isSuccessful()){
                        Log.d("response --> ", response.toString());
                    }else{
                        Log.d("note --> ", "删除失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //添加任务
    public static void addTask(TaskAssign taskAssign){
        new Thread(new Runnable() {
            @Override
            public void run() {
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                OkHttpClient client = new OkHttpClient();
                String json = com.alibaba.fastjson.JSON.toJSONString(taskAssign);
                Log.d("json --> ",json);
                RequestBody body = RequestBody.create(JSON, json);
                Request request = new Request.Builder()
                        .url("http://8.131.250.250/taskAssign/add")
                        .post(body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    if(response.isSuccessful()){
                        Log.d("response --> ", response.body().string());
                    }else{
                        Log.d("note --> ", "添加失败");
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //修改任务
    public static void updateTask(TaskAssign taskAssign){
        new Thread(new Runnable() {
            @Override
            public void run() {
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                OkHttpClient client = new OkHttpClient();
                String json = com.alibaba.fastjson.JSON.toJSONString(taskAssign);
                Log.d("json --> ", json);
                RequestBody body = RequestBody.create(JSON, json);
                Request request = new Request.Builder()
                        .url("http://8.131.250.250/taskAssign/update")
                        .post(body)
                        .build();
                try{
                    Response response = client.newCall(request).execute();
                    if(response.isSuccessful()){
                        Log.d("response --> ", response.body().string());
                    }else{
                        Log.d("note --> ", "修改失败");
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //更新任务列表
                getTask(MyApplication.getUser().getId());
            }
        }).start();
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
                 //   System.out.println("jsonArray --> " + jsonArray);
                    if(jsonArray != null && jsonArray.length() > 0){
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject ans = jsonArray.getJSONObject(i);
                            TaskAssign taskAssign = new TaskAssign();
                            taskAssign.setId(ans.optInt("id"));
                            taskAssign.setCreatorId(ans.optInt("creatorId"));
                            taskAssign.setAssigneeId(ans.optInt("assigneeId"));
                            taskAssign.setTaskTitle(ans.optString("taskTitle"));
                            taskAssign.setTaskContent(ans.optString("taskContent"));
                            String taskCreateTime = ans.optString("taskCreateTime");
                            String taskFinishTime = ans.optString("taskFinishTime");
                            if(taskCreateTime != null && taskCreateTime.length() > 0){
                                Date createTime = CommonUtils.StringToDate(taskCreateTime);
                                taskAssign.setTaskCreateTime(createTime);
                            }

                            if(taskFinishTime != null && taskCreateTime.length() > 0){
                                Date finishTime = CommonUtils.StringToDate(taskFinishTime);
                                if(finishTime != null)
                                    taskAssign.setTaskFinishTime(finishTime);
                            }

                            //System.out.println("i --> " + i);
                            //System.out.println("taskAssign --> (only part)" + taskAssign);
                            //System.out.println("taskCreateTime --> " + taskCreateTime);
                            //System.out.println("taskFinishTime --> " + taskFinishTime);
                            tmp.add(taskAssign);
                        }
                        MyApplication.setTaskList(tmp);
                    }else{
                        MyApplication.setTaskList(null);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    MyApplication.setTaskList(null);
                }
            }
        }).start();
        return MyApplication.getTaskList();
    }

    //根据用户id来返回被指派者是它的所有任务
    public static List<TaskAssign> findAllAssignedTask(Integer userId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    FormBody.Builder params = new FormBody.Builder();
                    params.add("assigneeId", userId+"");
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://8.131.250.250/taskAssign/findAllAssigned")
                            .post(params.build())
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = Objects.requireNonNull(response.body()).string();
                    Log.d("responseData", responseData);
                    //将JSON字符串转换成对象链表(这个地方有待进一步测试)
                    List<TaskAssign> taskAssignList = new ArrayList<TaskAssign>();
                    taskAssignList = JSON.parseArray(responseData,TaskAssign.class);
                    MyApplication.setAllAssignedTask(taskAssignList);
                } catch (Exception e) {
                    MyApplication.setAllAssignedTask(null);
                    e.printStackTrace();
                }
            }
        }).start();
        return MyApplication.getAllAssignedTask();
    }

    //根据用户id来返回创建者是它的所有任务
    public static List<TaskAssign> findAllCreatedTask(Integer userId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    FormBody.Builder params = new FormBody.Builder();
                    params.add("creatorId", userId+"");
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://8.131.250.250/taskAssign/findAllCreated")
                            .post(params.build())
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = Objects.requireNonNull(response.body()).string();
                    Log.d("responseData", responseData);
                    //将JSON字符串转换成对象链表(这个地方有待进一步测试)
                    List<TaskAssign> taskAssignList = new ArrayList<TaskAssign>();
                    taskAssignList = JSON.parseArray(responseData,TaskAssign.class);
                    MyApplication.setAllCreatedTask(taskAssignList);
                } catch (Exception e) {
                    MyApplication.setAllCreatedTask(null);
                    e.printStackTrace();
                }
            }
        }).start();
        return MyApplication.getAllCreatedTask();
    }


    //获得数据库中用户表中的所有用户
    public static List<User> getAllUsers() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    FormBody.Builder params = new FormBody.Builder();
                    Request request = new Request.Builder()
                            .url("http://8.131.250.250/user/findAll")
                            .post(params.build())
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = Objects.requireNonNull(response.body()).string();
                    //打印日志
                    Log.d("responseData", responseData);
                    JSONArray jsonArray = new JSONArray(responseData);
                    List<User> tmp = new ArrayList<User>();
                    if(jsonArray != null && jsonArray.length() > 0){
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject ans = jsonArray.getJSONObject(i);
                            User user = new User();
                            user.setId(ans.optInt("id"));
                            user.setName(ans.optString("name"));
                            user.setPassword(ans.optString("password"));
                            //System.out.println(user);
                            tmp.add(user);
                        }
                        MyApplication.setAllUsers(tmp);
                    }

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return MyApplication.getAllUsers();
    }
}

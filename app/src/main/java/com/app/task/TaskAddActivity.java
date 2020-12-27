package com.app.task;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.MyApplication;
import com.app.R;
import com.app.util.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.concurrent.Task;

public class TaskAddActivity extends AppCompatActivity {

    private Spinner spinner;
    private TextView task_assign_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_add);

        //初始化控件
        spinner = findViewById(R.id.task_assign_spinner);
        task_assign_username = findViewById(R.id.task_assign_username);
        //获得数据源
        List<User> userList = getAllUsers();
        if(userList != null){
            UserListAdapter userListAdapter = new UserListAdapter(userList,this);
            spinner.setAdapter(userListAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    task_assign_username.setText(userList.get(position).getName());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

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
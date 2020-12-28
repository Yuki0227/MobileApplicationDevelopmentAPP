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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.MyApplication;
import com.app.R;
import com.app.login.RegisterActivity;
import com.app.task.entity.TaskAssign;
import com.app.util.CommonUtils;
import com.app.util.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;
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
    private TextView btn_task_add;
    private EditText task_title;
    private EditText task_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_add);

        //初始化控件
        spinner = findViewById(R.id.task_assign_spinner);
        task_assign_username = findViewById(R.id.task_assign_username);
        btn_task_add = findViewById(R.id.btn_task_add);
        task_title = findViewById(R.id.task_title);
        task_content = findViewById(R.id.task_content);
        //获得数据源
        List<User> userList = TaskFactory.getAllUsers();
        if(userList != null){
            UserListAdapter userListAdapter = new UserListAdapter(userList,this);
            spinner.setAdapter(userListAdapter);
            //为spinner绑定选中监听器
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

        //为提交按钮绑定监听事件
        btn_task_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(task_title.getText() == null || task_title.getText().toString().isEmpty() || task_assign_username.getText().toString().isEmpty()){
                    CommonUtils.showShortMsg(v.getContext(),"修改失败,未填写任务标题或未指派人员");
                }else{
                    TaskAssign taskAssign = new TaskAssign();
                    taskAssign.setCreatorId(MyApplication.getUser().getId());
                    taskAssign.setTaskTitle(task_title.getText().toString());
                    if(task_content.getText() != null){
                        taskAssign.setTaskContent(task_content.getText().toString());
                    }
                    List<User> allUsers = MyApplication.getAllUsers();
                    for(int i = 0; i < allUsers.size(); i++){
                        if(allUsers.get(i).getName().equals(task_assign_username.getText().toString())){
                            taskAssign.setAssigneeId(allUsers.get(i).getId());
                            break;
                        }
                    }
                    //taskAssign.setTaskCreateTime(new Date(System.currentTimeMillis()));
                    TaskFactory.addTask(taskAssign);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            CommonUtils.showShortMsg(v.getContext(),"添加成功");
                            finish();
                        }
                    });
                }
            }
        });


    }

}
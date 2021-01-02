package com.app.task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.MyApplication;
import com.app.R;
import com.app.task.entity.TaskAssign;
import com.app.util.CommonUtils;
import com.app.util.User;

import java.util.List;

public class TaskUpdateActivity extends AppCompatActivity {

    private Spinner update_spinner;
    private TextView task_update_username;
    private TextView btn_task_update;
    private TextView btn_task_generate_code;
    private EditText et_title_update;
    private EditText et_content_update;
    TaskListViewAdapter taskListViewAdapter;
    private int task_position;

    private static final String Tag = "Task";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_update);

        //初始化控件
        update_spinner = findViewById(R.id.task_update_spinner);
        task_update_username = findViewById(R.id.task_update_username);
        btn_task_update = findViewById(R.id.btn_task_update);
        btn_task_generate_code = findViewById(R.id.btn_task_generate_code);
        et_title_update = findViewById(R.id.et_title_update);
        et_content_update = findViewById(R.id.et_content_update);

        //获取任务的内容
        Bundle bundle = getIntent().getExtras();
        int position = bundle.getInt("position");
        task_position = bundle.getInt("position");
        String str_title = bundle.getString("task_title");
        String str_content = bundle.getString("task_content");
        int assigneeId = bundle.getInt("assigneeId");
        et_title_update.setText(str_title);
        et_content_update.setText(str_content);

        //获得数据源
        List<User> userList = TaskFactory.getAllUsers();
        int user_number = 0;
        for(int i = 0;i < userList.size();i++){
            if(!userList.get(i).equals(null)){
                user_number++;
                if(userList.get(i).getId().equals(assigneeId)){
                    task_update_username.setText(userList.get(i).getName());
                    //Log.d(Tag,userList.get(i).getName());
                    //Log.d(Tag,String.valueOf(user_number));
                    break;
                }
            }
        }
        if(userList != null){
            UserListAdapter userListAdapter = new UserListAdapter(userList,this);
            update_spinner.setAdapter(userListAdapter);
            update_spinner.setSelection(user_number-1);

            //为update_spinner绑定选中监听器
            update_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    task_update_username.setText(userList.get(position).getName());
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        //为更新按钮绑定监听事件
        btn_task_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_title_update.getText() == null || et_title_update.getText().toString().isEmpty() || task_update_username.getText().toString().isEmpty()){
                    CommonUtils.showShortMsg(v.getContext(),"更新失败,未填写任务标题或未指派人员");
                }else{
                    TaskAssign taskAssign = new TaskAssign();
                    List<TaskAssign> taskAssignList = TaskFactory.getTask(MyApplication.getUser().getId());
                    if(taskAssignList != null || taskAssignList.size() > 0){
                        taskAssign = taskAssignList.get(position);
                        taskAssign.setCreatorId(MyApplication.getUser().getId());
                        taskAssign.setTaskTitle(et_title_update.getText().toString());
                        if(et_content_update.getText() != null){
                            taskAssign.setTaskContent(et_content_update.getText().toString());
                        }
                        List<User> allUsers = MyApplication.getAllUsers();
                        for(int i = 0; i < allUsers.size(); i++){
                            if(allUsers.get(i).getName().equals(task_update_username.getText().toString())){
                                taskAssign.setAssigneeId(allUsers.get(i).getId());
                                break;
                            }
                        }
                    }
                    //刷新listView
                    //taskListViewAdapter.notifyDataSetChanged();
                    TaskFactory.updateTask(taskAssign);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            CommonUtils.showShortMsg(v.getContext(),"更新成功");
                            finish();
                        }
                    });
                }
            }
        });

        btn_task_generate_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<TaskAssign> taskAssignList = TaskFactory.getTask(MyApplication.getUser().getId());
                TaskAssign taskAssign = taskAssignList.get(position);
                Integer taskId = taskAssign.getId();
                Intent intent = new Intent(TaskUpdateActivity.this,GenerateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("taskId", taskId);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}
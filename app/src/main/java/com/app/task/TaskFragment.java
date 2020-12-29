package com.app.task;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.app.MyApplication;
import com.app.R;
import com.app.task.entity.TaskAssign;
import com.app.util.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.internal.concurrent.Task;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFragment} factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends Fragment {

    private String fragmentText;
    private TextView fragmentTextView;
    private ListView listView;
    //private List<Map<String, String>> list = null;
    private TaskListViewAdapter taskListViewAdapter;
    private Toolbar task_toolbar;
    public static int search_mode = 0;      //查询模式,0表示查询所有、1表示只查询自己创建的任务、2表示只查询被指派人是自己的任务

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //让fragment有菜单
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container,false);

        listView = view.findViewById(R.id.task_item);
        taskListViewAdapter = new TaskListViewAdapter(getContext());
        task_toolbar = view.findViewById(R.id.task_toolbar);
        task_toolbar.inflateMenu(R.menu.menu_add_task);

        task_toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_task_add:
                        Toast.makeText(getContext(),"click",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(),TaskAddActivity.class);
                        startActivity(intent);
                        /*
                        Intent intent= new Intent(getActivity(),Web_Activity.class);
                    startActivity(intent);
                         */
                        break;
                    case R.id.menu_task_search_all:
                        Toast.makeText(getContext(),"点击了查询所有",Toast.LENGTH_SHORT).show();
                        search_mode = 0;
                        //List<TaskAssign> taskAssignList = TaskFactory.getTask(MyApplication.getUser().getId());
                        //MyApplication.setTaskList(taskAssignList);
                        break;
                    case R.id.menu_task_search_created:
                        Toast.makeText(getContext(),"点击了查询自己创建的",Toast.LENGTH_SHORT).show();
                        search_mode = 1;
                        //List<TaskAssign> taskAssignList1 = TaskFactory.findAllCreatedTask(MyApplication.getUser().getId());
                        //MyApplication.setTaskList(taskAssignList1);
                        break;
                    case R.id.menu_task_search_assigneed:
                        Toast.makeText(getContext(),"点击了查询被分配的",Toast.LENGTH_SHORT).show();
                        search_mode = 2;
                        //List<TaskAssign> taskAssignList2 = TaskFactory.findAllAssignedTask(MyApplication.getUser().getId());
                        //MyApplication.setTaskList(taskAssignList2);
                        break;
                }
                return false;
            }
        });

        /*
        list = new ArrayList<Map<String, String>>();
        for (int i = 0; i < 20; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("username", "wangxiangjun_" + i);
            map.put("password", "123456_" + i);
            list.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(getContext(), list, R.layout.task_list, new String[]{"username","password"},
            new int[]{
                R.id.tv_title,R.id.tv_content
            });
        listView.setAdapter(adapter);

         */


        taskListViewAdapter.setOnItemClickListener(new TaskListViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (view.getId()){
                    case R.id.iv_delete:
                        System.out.println("iv_delete --> ");
                        listView.setAdapter(taskListViewAdapter);
                        break;
                }
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"长按" + position,Toast.LENGTH_SHORT).show();

                //获取任务的各项内容
                TaskAssign taskAssign;
                List<TaskAssign> taskAssignList = TaskFactory.getTask(MyApplication.getUser().getId());
                taskAssign = taskAssignList.get(position);
                String task_title = taskAssign.getTaskTitle();
                String task_content = taskAssign.getTaskContent();
                int assigneeId = taskAssign.getAssigneeId();

                //将任务的各项内容传递到TaskUpdateActivity
                Intent intent = new Intent(getActivity(),TaskUpdateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position",position);
                bundle.putString("task_title",task_title);
                bundle.putString("task_content",task_content);
                bundle.putInt("assigneeId",assigneeId);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            }
        });

        listView.setAdapter(taskListViewAdapter);




     //   listView.setAdapter(taskListViewAdapter);
        return view;
    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add_task,menu);
    }

    public TaskFragment(String fragmentText) {
        this.fragmentText = fragmentText;
    }


}

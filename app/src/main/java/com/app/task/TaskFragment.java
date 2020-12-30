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
import androidx.appcompat.app.AppCompatActivity;
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
    private List<TaskAssign> taskAssignList;
    private Toolbar task_toolbar;
    public static int search_mode = 0;      //查询模式,0表示查询所有、1表示只查询自己创建的任务、2表示只查询被指派人是自己的任务
    //private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //让fragment有菜单
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_task, container,false);
        View view = inflater.inflate(R.layout.fragment_task, container,false);
        System.out.println("(debug) user -- > " + MyApplication.getUser());

        /*
        listView = view.findViewById(R.id.task_item);

        */
     //   initLayoutBind();

        task_toolbar = view.findViewById(R.id.task_toolbar);
        task_toolbar.inflateMenu(R.menu.menu_add_task);

        //数据源
     //   initData();

        task_toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_task_add:
                        //System.out.println("add --> ");
                        Toast.makeText(getContext(),"click",Toast.LENGTH_SHORT).show();
                        //System.out.println("add --> ***");
                        if(MyApplication.getUser() != null){
                            Intent intent = new Intent(getActivity(),TaskAddActivity.class);
                            startActivity(intent);
                        }else{
                            CommonUtils.showShortMsg(getContext(),"请先登录后再使用此功能");
                        }
                        break;
                    case R.id.menu_task_search_all:
                        System.out.println("all --> ");
                        Toast.makeText(getContext(),"点击了查询所有",Toast.LENGTH_SHORT).show();
                        if(MyApplication.getUser() == null){
                            CommonUtils.showShortMsg(getContext(),"请先登录后再使用此功能");
                            break;
                        }
                        search_mode = 0;
                        //List<TaskAssign> taskAssignList = TaskFactory.getTask(MyApplication.getUser().getId());
                        //MyApplication.setTaskList(taskAssignList);
                        /*
                        taskAssignList.clear();
                        taskAssignList.addAll(TaskFactory.getTask(MyApplication.getUser().getId()));
                         */
                        initLayoutBind();
                        initData();
                        //通知更新listview
                        taskListViewAdapter.notifyDataSetChanged();
                        break;
                    case R.id.menu_task_search_created:
                        System.out.println("created --> ");
                        Toast.makeText(getContext(),"点击了查询自己创建的",Toast.LENGTH_SHORT).show();
                        if(MyApplication.getUser() == null){
                            CommonUtils.showShortMsg(getContext(),"请先登录后再使用此功能");
                            break;
                        }
                        search_mode = 1;
                        //List<TaskAssign> taskAssignList1 = TaskFactory.findAllCreatedTask(MyApplication.getUser().getId());
                        //MyApplication.setTaskList(taskAssignList1);
                        /*
                        taskAssignList.clear();
                        taskAssignList.addAll(TaskFactory.findAllCreatedTask(MyApplication.getUser().getId()));

                         */
                        initLayoutBind();
                        initData();
                        //通知更新listview
                        taskListViewAdapter.notifyDataSetChanged();
                        break;
                    case R.id.menu_task_search_assigneed:
                        System.out.println("assigneed --> ");
                        Toast.makeText(getContext(),"点击了查询被分配的",Toast.LENGTH_SHORT).show();
                        if(MyApplication.getUser() == null){
                            CommonUtils.showShortMsg(getContext(),"请先登录后再使用此功能");
                            break;
                        }
                        search_mode = 2;
                        //List<TaskAssign> taskAssignList2 = TaskFactory.findAllAssignedTask(MyApplication.getUser().getId());
                        //MyApplication.setTaskList(taskAssignList2);
                        /*
                        taskAssignList.clear();
                        taskAssignList.addAll(TaskFactory.findAllAssignedTask(MyApplication.getUser().getId()));

                         */
                        initLayoutBind();
                        initData();
                        //通知更新listview
                        taskListViewAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });




     //   listView.setAdapter(taskListViewAdapter);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        if(MyApplication.getUser() == null) return ;
        Looper.myQueue().addIdleHandler(() -> {
            initLayoutBind();
            initData();
            return false;
        });
    }

    @Override
    public void onResume() {
        System.out.println("search_mode --> " + search_mode);

        super.onResume();
        if(MyApplication.getUser() == null) return ;
        Looper.myQueue().addIdleHandler(() -> {
            initLayoutBind();
            initData();
            return false;
        });
    }

    private void initLayoutBind() {

        listView = getView().findViewById(R.id.task_item);
        //if(MyApplication.getUser() == null) return ;
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"长按" + position,Toast.LENGTH_SHORT).show();

                //获取任务的各项内容
                TaskAssign taskAssign = taskAssignList.get(position);
                //List<TaskAssign> taskAssignList = TaskFactory.getTask(MyApplication.getUser().getId());
                //taskAssign = taskAssignList.get(position);
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
    }

    private void initData() {
        //System.out.println("User -- >  " + MyApplication.getUser());
        //if(MyApplication.getUser() == null) return ;
        //创建数据源
        taskAssignList = new ArrayList<TaskAssign>();
        if(search_mode == 0 && TaskFactory.getTask(MyApplication.getUser().getId()) != null){
            //taskAssignList = TaskFactory.getTask(MyApplication.getUser().getId());
            taskAssignList.addAll(TaskFactory.getTask(MyApplication.getUser().getId()));
            Log.d("allTask --> " , TaskFactory.getTask(MyApplication.getUser().getId()).toString());
        }else if(search_mode == 1 && TaskFactory.findAllCreatedTask(MyApplication.getUser().getId()) != null){
            //taskAssignList = TaskFactory.findAllCreatedTask(MyApplication.getUser().getId());
            taskAssignList.addAll(TaskFactory.findAllCreatedTask(MyApplication.getUser().getId()));
            Log.d("createdTask --> " , TaskFactory.findAllCreatedTask(MyApplication.getUser().getId()).toString());
        }else if(search_mode == 2 && TaskFactory.findAllAssignedTask(MyApplication.getUser().getId()) != null){
            //taskAssignList = TaskFactory.findAllAssignedTask(MyApplication.getUser().getId());
            taskAssignList.addAll(TaskFactory.findAllAssignedTask(MyApplication.getUser().getId()));
            Log.d("assigneedTask --> " , TaskFactory.findAllAssignedTask(MyApplication.getUser().getId()).toString());
        }
        taskListViewAdapter = new TaskListViewAdapter(getContext(), taskAssignList);
        listView.setAdapter(taskListViewAdapter);

        taskListViewAdapter.setOnItemClickListener(new TaskListViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (view.getId()){
                    case R.id.iv_delete:
                        System.out.println("iv_delete --> ");
                        Integer deleted_task_id = taskAssignList.get(position).getId();
                        taskAssignList.remove(position);
                        TaskFactory.deleteTask(deleted_task_id);
                        //通知更新listview
                        taskListViewAdapter.notifyDataSetChanged();
                        //listView.setAdapter(taskListViewAdapter);
                        break;
                    case R.id.iv_done:
                        System.out.println("iv_done --> ");
                        Integer status = taskAssignList.get(position).getStatus();
                        TaskAssign taskAssign = taskAssignList.get(position);
                        if(status == 0){
                            //未完成状态下点击它时会跳转到扫码界面,扫码成功后状态变成已完成,否则仍为未完成状态
                            //跳转到扫码页面(暂未实现)
                            /*......*/
                            taskAssign.setStatus(1);
                            TaskFactory.updateTask(taskAssign);
                            ((ImageView)view).setImageResource(R.mipmap.ic_task_complete);
                            CommonUtils.showShortMsg(getContext(),"已完成");
                        }else{
                            //已完成状态下再点击它时会取消已完成状态
                            taskAssign.setStatus(0);
                            TaskFactory.updateTask(taskAssign);
                            ((ImageView)view).setImageResource(R.mipmap.ic_task_not_complete);
                            CommonUtils.showShortMsg(getContext(),"已取消完成");
                        }
                        //通知更新listview
                        taskListViewAdapter.notifyDataSetChanged();
                        break;
                }
            }
        });
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

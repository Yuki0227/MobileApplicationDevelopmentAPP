package com.app.task;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

package com.app.task;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.MyApplication;
import com.app.R;
import com.app.task.entity.TaskAssign;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

import okhttp3.internal.concurrent.Task;

public class TaskListViewAdapter extends BaseAdapter {

    private Context context = null;
    private OnItemClickListener mOnItemClickListener;

    public TaskListViewAdapter(Context context) {
        this.context = context;
    }

    public interface OnItemClickListener{
        //子条目单击事件
        void onItemClick(View view, int position);
    }

    //回调方法 将接口传递进来
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener)
    {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public int getCount() {
        //根据对象的个数进行返回其值
        if(MyApplication.getUser() != null){
            if(TaskFactory.getTask(MyApplication.getUser().getId()) != null)
                return TaskFactory.getTask(MyApplication.getUser().getId()).size();
        }
        return 0;
        //return TaskFactory.getTask().size();
    }

    @Override
    public Object getItem(int position) {
        if(MyApplication.getUser() != null){
            if(TaskFactory.getTask(MyApplication.getUser().getId()) != null ){
                if(TaskFactory.getTask(MyApplication.getUser().getId()).size() > 0){
                    return TaskFactory.getTask(MyApplication.getUser().getId()).get(position);
                }
            }
        }
        return null;
        //return TaskFactory.getTask().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if(convertView == null){
            mHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.task_list, parent, false);
            mHolder.iv_done = convertView.findViewById(R.id.iv_done);
            mHolder.iv_delete = convertView.findViewById(R.id.iv_delete);
            mHolder.tv_content = convertView.findViewById(R.id.tv_content);
            mHolder.tv_title = convertView.findViewById(R.id.tv_title);
            convertView.setTag(mHolder);
        }else{
            mHolder = (ViewHolder) convertView.getTag();
        }


        if(MyApplication.getUser() != null){
            List<TaskAssign> taskAssignList = TaskFactory.getTask(MyApplication.getUser().getId());
            if(taskAssignList != null || taskAssignList.size() > 0){
                TaskAssign taskAssign = taskAssignList.get(position);
                String task_title = taskAssign.getTaskTitle();
                mHolder.tv_title.setText(task_title);
                if(taskAssign.getTaskContent() != null && !taskAssign.getTaskContent().isEmpty()){
                    String task_content = taskAssign.getTaskContent();
                    mHolder.tv_content.setText(task_content);
                }
            }
        }


        /*下面这块代码仅做测试用,具体如何写根据实际需求来
        String title = TaskFactory.getTask().get(position).get("username").toString();
        String content = TaskFactory.getTask().get(position).get("password").toString();
        mHolder.tv_title.setText(title);
        mHolder.tv_content.setText(content);

        if(MyApplication.getUser() != null){
            List<TaskAssign> task = TaskFactory.getTask(MyApplication.getUser().getId());
            System.out.println("你已经登录了,你的任务是");
            //System.out.println(task);
        }else{
            Toast.makeText(context, "你还未登录", Toast.LENGTH_SHORT).show();
        }

         */


        if(mOnItemClickListener != null){
            mHolder.iv_done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "iv_done" + position, Toast.LENGTH_SHORT).show();
                    mHolder.iv_done.setImageResource(R.mipmap.ic_task_complete);
                    TaskAssign taskAssign = MyApplication.getTaskList().get(position);
                    taskAssign.setStatus(1);        //将它的状态设为1,表示该任务已经完成
                    TaskFactory.updateTask(taskAssign); //调用方法去更新该任务
                    mOnItemClickListener.onItemClick(mHolder.iv_done,position);
                }
            });

            mHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer taskId = TaskFactory.getTask(MyApplication.getUser().getId()).get(position).getId();
                    TaskFactory.getTask(MyApplication.getUser().getId()).remove(position);
                    TaskFactory.deleteTask(taskId);
                    //删除后更新任务集合
                    TaskFactory.getTask(MyApplication.getUser().getId());
                    //TaskFactory.getTask().remove(position);
                    Toast.makeText(context, "iv_delete" + position, Toast.LENGTH_SHORT).show();
                    mOnItemClickListener.onItemClick(mHolder.iv_delete,position);
                }
            });
        }

        /*
        mHolder.iv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "iv_done" + position, Toast.LENGTH_SHORT).show();
                mHolder.iv_done.setImageResource(R.mipmap.ic_task_complete);
            }
        });

        mHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskFactory.getTask().remove(position);
                Toast.makeText(context, "iv_delete" + position, Toast.LENGTH_SHORT).show();

            }
        });
         */

        return convertView;
    }

    class ViewHolder{
        private ImageView iv_done;
        private ImageView iv_delete;
        private TextView tv_title;          //用于显示任务的标题
        private TextView tv_content;        //用于显示任务的内容
    }
}

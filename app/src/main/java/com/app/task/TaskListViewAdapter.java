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
import com.app.util.User;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

import okhttp3.internal.concurrent.Task;

public class TaskListViewAdapter extends BaseAdapter {

    private Context context = null;
    private OnItemClickListener mOnItemClickListener;
    private List<TaskAssign> mList;

    public TaskListViewAdapter(Context context, List<TaskAssign> list) {
        this.context = context;
        this.mList = list;
        //System.out.println("TaskListViewAdapter --> " + this.mList);
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
        if(mList == null){
            return 0;
        }
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        if(mList == null){
            return null;
        }
        return mList.get(position);
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

        mHolder.tv_title.setText(mList.get(position).getTaskTitle());
        mHolder.tv_content.setText(mList.get(position).getTaskContent());
        if(mList.get(position).getStatus() == 0){
            mHolder.iv_done.setImageResource(R.mipmap.ic_task_not_complete);
        }else{
            mHolder.iv_done.setImageResource(R.mipmap.ic_task_complete);
        }


        if(mOnItemClickListener != null){
            mHolder.iv_done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*
                    Toast.makeText(context, "iv_done" + position, Toast.LENGTH_SHORT).show();
                    mHolder.iv_done.setImageResource(R.mipmap.ic_task_complete);
                    TaskAssign taskAssign = MyApplication.getTaskList().get(position);
                    taskAssign.setStatus(1);        //将它的状态设为1,表示该任务已经完成
                    TaskFactory.updateTask(taskAssign); //调用方法去更新该任务
                    //更新后更新任务集合
                    Integer userId = MyApplication.getUser().getId();
                    if(TaskFragment.search_mode == 0){
                        TaskFactory.getTask(userId);
                    }else if(TaskFragment.search_mode == 1){
                        TaskFactory.findAllCreatedTask(userId);
                    }else if(TaskFragment.search_mode == 2){
                        TaskFactory.findAllAssignedTask(userId);
                    }
                     */
                    mOnItemClickListener.onItemClick(mHolder.iv_done,position);
                }
            });

            mHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(mHolder.iv_delete,position);
                }
            });
        }

        return convertView;
    }

    class ViewHolder{
        private ImageView iv_done;
        private ImageView iv_delete;
        private TextView tv_title;          //用于显示任务的标题
        private TextView tv_content;        //用于显示任务的内容
    }
}

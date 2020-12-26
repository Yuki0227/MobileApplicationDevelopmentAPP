package com.app.task;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.R;

import okhttp3.internal.concurrent.Task;

public class TaskListViewAdapter extends BaseAdapter {

    private Context context = null;

    public TaskListViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        //根据对象的个数进行返回其值
        return TaskFactory.getTask().size();
    }

    @Override
    public Object getItem(int position) {
        return TaskFactory.getTask().get(position);
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
            convertView = inflater.inflate(R.layout.fragment_task, null, true);
            mHolder.iv_done = convertView.findViewById(R.id.iv_done);
            mHolder.iv_delete = convertView.findViewById(R.id.iv_delete);
            mHolder.tv_content = convertView.findViewById(R.id.tv_content);
            mHolder.tv_title = convertView.findViewById(R.id.tv_title);
            convertView.setTag(mHolder);
        }else{
            mHolder = (ViewHolder) convertView.getTag();
        }

        //下面这块代码仅做测试用,具体如何写根据实际需求来
        String title = TaskFactory.getTask().get(position).get("username").toString();
        String content = TaskFactory.getTask().get(position).get("password").toString();
        mHolder.tv_title.setText(title);
        //mHolder.tv_content.setText(content);
        return convertView;
    }

    class ViewHolder{
        private ImageView iv_done;
        private ImageView iv_delete;
        private TextView tv_title;
        private TextView tv_content;
    }
}

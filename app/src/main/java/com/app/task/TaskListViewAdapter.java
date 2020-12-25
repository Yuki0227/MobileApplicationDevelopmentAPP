package com.app.task;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.R;

public class TaskListViewAdapter extends BaseAdapter {

    private Context context = null;

    public TaskListViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        //根据对象的个数进行返回其值
        return 20;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if(convertView == null){
            mHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.fragment_task, null, true);
            mHolder.iv_done = convertView.findViewById(R.id.iv_done);


        }



        return null;
    }

    class ViewHolder{
        private ImageView iv_done;
        private ImageView iv_delete;
        private TextView tv_title;
        private TextView tv_content;
    }
}

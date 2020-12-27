package com.app.task;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.app.R;
import com.app.util.User;

import java.util.List;

public class UserListAdapter extends BaseAdapter implements SpinnerAdapter {

    private List<User> mList;
    private Context mContext;

    public UserListAdapter(List<User> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater _LayoutInflater= LayoutInflater.from(mContext);
        convertView = _LayoutInflater.inflate(R.layout.task_assign_users_item,null);
        if(convertView != null){
            TextView users_item = convertView.findViewById(R.id.users_item);
            users_item.setText(mList.get(position).getName());

            /*
            users_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(parent.getContext(),"点击了" + position, Toast.LENGTH_SHORT).show();
                }
            });

             */

        }
        return convertView;
    }



}

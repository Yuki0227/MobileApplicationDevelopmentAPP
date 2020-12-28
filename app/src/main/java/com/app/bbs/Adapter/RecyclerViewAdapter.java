package com.app.bbs.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.app.R;
import com.app.bbs.Bean.ItemBean;

import java.text.BreakIterator;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<ItemBean> itemList;
    private OnItemClickListener onItemClickListener = null;

    public RecyclerViewAdapter(List<ItemBean> itemList, Context context) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bbs_line_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapter.ViewHolder holder, final int position) {
        ItemBean item = itemList.get(position);
        holder.itemName.setText(item.getTitle());
        holder.itemLabels.setText(item.getContent());


        if(null != onItemClickListener){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(holder.itemView,position);
                }
            });
        }

    }
    @Override
    public int getItemCount() {
        return itemList.size();
    }
    // 设置点击事件
    public void setOnItemClickListener(OnItemClickListener l) {
        this.onItemClickListener = l;
    }

    // 点击事件接口
    public interface OnItemClickListener {
        void onClick(View parent, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName;
        public TextView itemLabels;
        //public BreakIterator discussView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.tv_item_name);
            itemLabels = itemView.findViewById(R.id.tv_item_labels);

        }


    }
}

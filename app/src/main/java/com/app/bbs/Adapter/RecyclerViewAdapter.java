package com.app.bbs.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.R;
import com.app.bbs.entity.ArticleView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final List<ArticleView> itemList;
    private OnItemClickListener onItemClickListener = null;

    public RecyclerViewAdapter(List<ArticleView> itemList, Context context) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bbs_line_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapter.ViewHolder holder, final int position) {
        ArticleView item = itemList.get(position);

        holder.itemName.setText(item.getTitle());
        holder.itemAuthor.setText("作者:" + item.getAuthor());
        holder.itemLabels.setText(item.getBody());


        if(null != onItemClickListener){
            holder.itemView.setOnClickListener(v -> onItemClickListener.onClick(holder.itemView, position));
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
        public TextView itemAuthor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.tv_item_name);
            itemLabels = itemView.findViewById(R.id.tv_item_labels);
            itemAuthor = itemView.findViewById(R.id.tv_item_username);
        }


    }
}

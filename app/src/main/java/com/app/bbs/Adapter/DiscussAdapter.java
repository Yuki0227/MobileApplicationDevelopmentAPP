package com.app.bbs.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.R;
import com.app.bbs.entity.ArticleReview;

import java.util.List;

public class DiscussAdapter extends RecyclerView.Adapter<DiscussAdapter.ViewHolder> {


    private List<ArticleReview> discussList;

    public DiscussAdapter(List<ArticleReview> itemList, Context context) {
        this.discussList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bbs_line_discuss, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull DiscussAdapter.ViewHolder holder, int position) {
        ArticleReview articleReview = discussList.get(position);
        holder.discussView.setText(articleReview.getBody());

    }

    @Override
    public int getItemCount() {
        return discussList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView discussView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            discussView = itemView.findViewById(R.id.tv_discuss_content);
        }
    }
}


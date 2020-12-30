package com.app.exercise.hepler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.R;
import com.app.exercise.entity.Result;
import android.widget.AdapterView.OnItemClickListener;

import java.util.List;

public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Result> results;
    private OnItemClickListener onItemClickListener = null;

    public RecyclerViewAdapter(List<Result> results, Context context){
        this.results = results;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_line_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Result result = results.get(position);
        holder.exercise_history_tv_title.setText(result.getTitle());
        holder.exercise_history_tv_score.setText("得   分："+result.getScore());
        holder.exercise_history_tv_time.setText("答题时间："+result.getTime());
        holder.exercise_history_tv_date.setText("提交时间："+result.getDate());

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
        return results.size();
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
       private TextView exercise_history_tv_title;
       private TextView exercise_history_tv_score;
       private TextView exercise_history_tv_time;
       private TextView exercise_history_tv_date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            exercise_history_tv_title = itemView.findViewById(R.id.exercise_history_tv_title);
            exercise_history_tv_score = itemView.findViewById(R.id.exercise_history_tv_score);
            exercise_history_tv_time = itemView.findViewById(R.id.exercise_history_tv_time);
            exercise_history_tv_date = itemView.findViewById(R.id.exercise_history_tv_date);

        }


    }
}

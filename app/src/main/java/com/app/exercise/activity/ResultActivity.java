package com.app.exercise.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.app.R;


public class ResultActivity extends AppCompatActivity {
    private String title, date, time, score;
    private TextView tvTitle, tvScore, tvDate, tvTime;
    private Button bt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_activity_result);

        //ActionBar工具栏设置
        Toolbar toolbar = findViewById(R.id.exercise_toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        date = intent.getStringExtra("date");
        time = intent.getStringExtra("time");
        score = intent.getStringExtra("score");
        tvTitle = findViewById(R.id.exercise_result_tv_title1);
        tvScore = findViewById(R.id.exercise_result_tv_score);
        tvDate = findViewById(R.id.exercise_result_tv_date);
        tvTime = findViewById(R.id.exercise_result_tv_time);
        tvTitle.setText(title);
        tvScore.append(score);
        tvDate.append(date);
        tvTime.append(time);
        setTitle("测试成绩");
        bt = findViewById(R.id.exercise_result_bt_record);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent1=new Intent(ResultActivity.this,ExamActivity.class);
//                startActivity(intent1);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

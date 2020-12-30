package com.app.exercise.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.app.R;

import org.json.JSONObject;

import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


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
        result();
        bt = findViewById(R.id.exercise_result_bt_record);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(ResultActivity.this,HistoryActivity.class);
                startActivity(intent1);
            }
        });
    }
    private void result(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    FormBody.Builder params = new FormBody.Builder();
                    params.add("title", title);
                    params.add("date", date);
                    params.add("time", time);
                    params.add("score", score);
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://8.131.250.250/result/add")
                            .post(params.build())
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = Objects.requireNonNull(response.body()).string();
                    Log.d("responseData", responseData);
                    JSONObject jsonObj = new JSONObject(responseData);
                }catch (Exception e){
                    e.printStackTrace();;
                }
            }
        }).start();
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

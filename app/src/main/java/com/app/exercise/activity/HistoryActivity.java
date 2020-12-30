package com.app.exercise.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.app.R;
import com.app.bbs.Activity.ItemShowActivity;
import com.app.bbs.Bean.ItemBean;
import com.app.bbs.entity.ArticleView;
import com.app.exercise.hepler.RecyclerViewAdapter;
import com.app.exercise.entity.Result;
import com.app.exercise.entity.TestPaper;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView exercise_result_list;
    private RecyclerViewAdapter recyclerViewAdapter;
    List<Result> resultList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_activity_history);

        exercise_result_list = findViewById(R.id.exercise_result_list);

        initDate();

    }

    public Thread getResult() {
        return new Thread(() -> {
            try {
                FormBody.Builder params = new FormBody.Builder();
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://8.131.250.250/result/findAll")
                        .post(params.build())
                        .build();
                Response response = client.newCall(request).execute();
                String responseData = Objects.requireNonNull(response.body()).string();
                resultList = JSON.parseArray(responseData, Result.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void initDate() {
        Thread getResultThread = getResult();
        try {
            getResultThread.start();
            getResultThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HistoryActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        exercise_result_list.setLayoutManager(linearLayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(resultList, HistoryActivity.this);
        exercise_result_list.setAdapter(recyclerViewAdapter);

    }

}


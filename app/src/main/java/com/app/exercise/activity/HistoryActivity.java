package com.app.exercise.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.app.R;
import com.app.exercise.entity.Result;
import com.app.exercise.entity.TestPaper;

import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HistoryActivity extends AppCompatActivity {

    private Result result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_activity_history);

        try {
            Thread getResultThread = getResult();
            getResultThread.start();
            getResultThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("res",result.toString());
    }

    public Thread getResult(){
        return new Thread(()->{
            try{
                FormBody.Builder params = new FormBody.Builder();
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://8.131.250.250/result/finAll")
                        .post(params.build())
                        .build();
                Response response = client.newCall(request).execute();
                String responseData = Objects.requireNonNull(response.body()).string();
                result = JSON.parseObject(responseData, Result.class);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

    }
}
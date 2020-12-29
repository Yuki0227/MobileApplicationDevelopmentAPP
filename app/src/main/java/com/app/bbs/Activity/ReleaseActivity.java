package com.app.bbs.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.MyApplication;
import com.app.R;
import com.app.bbs.entity.Article;
import com.app.util.CommonUtils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ReleaseActivity extends AppCompatActivity {

    Article article;
    boolean releaseSucceed = false;

    private void release() {
        Log.e("", "发布");
        String artcletitle = ((TextView) findViewById(R.id.edt_relese_title)).getText().toString();
        String artclebody = ((TextView) findViewById(R.id.edt_relese_content)).getText().toString();
        article.setTitle(artcletitle);
        article.setBody(artclebody);

        Thread sendToServerThread = sendToServer();

        try {
            sendToServerThread.start();
            sendToServerThread.join();
            releaseSucceed = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private Thread sendToServer() {
        return new Thread(() -> {
            try {
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                String json = com.alibaba.fastjson.JSON.toJSONString(article);
                Log.e("json", json);
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(JSON, json);
                Request request = new Request.Builder()
                        .url("http://8.131.250.250/bbs/putArticle")
                        .post(body)
                        .build();
                client.newCall(request).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        article = new Article();
        Integer userId = MyApplication.getUser().getId();
        article.setUserId(userId);
        setContentView(R.layout.bbs_release);
        Button mBtnitemRelease = findViewById(R.id.btn_item_release);
        mBtnitemRelease.setOnClickListener(v -> {
            release();
            if (releaseSucceed) {
                CommonUtils.showLongMsg(ReleaseActivity.this, "发帖成功");
                finish();
            } else {
                CommonUtils.showLongMsg(ReleaseActivity.this, "发帖失败");
            }
        });
    }
}
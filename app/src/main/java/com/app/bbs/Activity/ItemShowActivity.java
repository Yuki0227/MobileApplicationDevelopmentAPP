package com.app.bbs.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.app.MyApplication;
import com.app.R;
import com.app.bbs.Adapter.DiscussAdapter;
import com.app.bbs.entity.ArticleReview;
import com.app.bbs.entity.ArticleReviewView;
import com.app.bbs.entity.ArticleView;

import java.util.List;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ItemShowActivity extends AppCompatActivity {

    private DiscussAdapter discussAdapter;
    private RecyclerView recyclerView;
    private EditText discusscontent;
    private long articleId;
    ArticleView article;
    private Boolean succed = false;
    List<ArticleReviewView> articleReviews;

    TextView title, content, username;

    private Button mBtnDiscuss;


    private Thread getDataFromServer() {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FormBody.Builder params = new FormBody.Builder();
                    params.add("articleId", String.valueOf(articleId));
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://8.131.250.250/bbs/getArticleById")
                            .post(params.build())
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = Objects.requireNonNull(response.body()).string();
                    article = JSON.parseObject(responseData, ArticleView.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    FormBody.Builder params = new FormBody.Builder();
                    params.add("articleId", String.valueOf(articleId));
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://8.131.250.250/bbs/getReviews")
                            .post(params.build())
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = Objects.requireNonNull(response.body()).string();
                    articleReviews = JSON.parseArray(responseData, ArticleReviewView.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


    private void init() {

        Thread getDataThread = getDataFromServer();
        try {
            getDataThread.start();
            getDataThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        content = findViewById(R.id.bbs_tv_article);
        title = findViewById(R.id.show_tv_item_title);
//        username=findViewById(R.id.tv_item_username);
//        username.setText(article.getAuthor());
        title.setText(article.getTitle());
        content.setText(article.getBody());
        init_discuss();
    }

    public void init_discuss() {

        recyclerView = findViewById(R.id.recyclerDiscussList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ItemShowActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        discussAdapter = new DiscussAdapter(articleReviews, ItemShowActivity.this);
        recyclerView.setAdapter(discussAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bbs_item_show);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        articleId = (long) bundle.get("ArticleId");

        init();

        mBtnDiscuss=findViewById(R.id.btn_discuss);
        mBtnDiscuss.setOnClickListener(v -> {

            discusscontent = findViewById(R.id.edt_discuss_content);

            Thread putArticleReviewThread = new Thread(() -> {
                try {
                    ArticleReview articleReview = new ArticleReview();
                    articleReview.setArticleId(article.getId());
                    articleReview.setUserId(MyApplication.getUser().getId());
                    articleReview.setBody(discusscontent.getText().toString());
                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    String json = com.alibaba.fastjson.JSON.toJSONString(articleReview);
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = RequestBody.create(JSON, json);
                    Request request = new Request.Builder()
                            .url("http://8.131.250.250/bbs/putArticleReview")
                            .post(body)
                            .build();
                    client.newCall(request).execute();
                    succed = true;
                } catch (Exception e) {

                    e.printStackTrace();
                }
            });

            try {
                putArticleReviewThread.start();
                putArticleReviewThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                //提交失败

            }

            init();
            discusscontent.setText("");
            if (succed) {
                Toast.makeText(ItemShowActivity.this, "发布成功", Toast.LENGTH_LONG).show();
            } else Toast.makeText(ItemShowActivity.this, "请先登录", Toast.LENGTH_LONG).show();


        });

    }


}
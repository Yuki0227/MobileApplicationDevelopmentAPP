package com.app.bbs;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.app.MyApplication;
import com.app.R;
import com.app.bbs.Activity.ItemShowActivity;
import com.app.bbs.Activity.ReleaseActivity;
import com.app.bbs.Adapter.RecyclerViewAdapter;
import com.app.bbs.Bean.ItemBean;
import com.app.bbs.entity.Article;
import com.app.bbs.entity.ArticleView;
import com.app.util.CommonUtils;
import com.app.util.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BBSFragment} factory method to
 * create an instance of this fragment.
 */
public class BBSFragment extends Fragment {

    private final String fragmentText;

    private TextView fragmentTextView;
    //private Button mBtnRelease;
    private LinearLayout mLayIine;
    private Article release;

    private EditText release_title;
    private EditText release_content;
    //private View view;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    FloatingActionButton mBtnRelease;

    public BBSFragment(String fragmentText) {
        this.fragmentText = fragmentText;
    }

    List<ArticleView> itemList = new ArrayList<>();

    private String id;
    private String name;
    private String userId;

    User user;
    private TextView username;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bbs, container, false);


        recyclerView = view.findViewById(R.id.recyclerItemList);
        mBtnRelease = view.findViewById(R.id.btn_release);

        mBtnRelease.setOnClickListener(v -> {
            if (MyApplication.getUser() != null) {
                Intent intent = new Intent(getActivity(), ReleaseActivity.class);
                startActivity(intent);
            } else {
                CommonUtils.showLongMsg(getActivity(), "请先登录");
            }
        });

        initData();

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    //@Override
    public void onClick(View parent, int position) {
        Intent intent = new Intent(getActivity(), ItemShowActivity.class);
        Bundle bundle = new Bundle();
        ArticleView item = itemList.get(position);

        intent.putExtra("ArticleId", item.getId());
        bundle.putSerializable(ItemBean.KEY, item);
        intent.putExtras(bundle);
        startActivityForResult(intent, 1);
    }

    public Thread getArticle() {
        return new Thread(() -> {
            try {
                FormBody.Builder params = new FormBody.Builder();
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://8.131.250.250/bbs/getAllArticles")
                        .post(params.build())
                        .build();
                Response response = client.newCall(request).execute();
                String responseData = Objects.requireNonNull(response.body()).string();
                itemList = JSON.parseArray(responseData, ArticleView.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void initData() {
        Thread getArticleThread = getArticle();
        try {
            getArticleThread.start();
            getArticleThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(itemList, getActivity());
        recyclerViewAdapter.setOnItemClickListener(this::onClick);
        recyclerView.setAdapter(recyclerViewAdapter);
    }


}
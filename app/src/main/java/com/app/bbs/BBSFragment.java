package com.app.bbs;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.app.R;
import com.app.bbs.Activity.ItemShowActivity;
import com.app.bbs.Adapter.RecyclerViewAdapter;
import com.app.bbs.Bean.ItemBean;
import com.app.bbs.entity.Article;

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

    private String fragmentText;

    private TextView fragmentTextView;
    private Button mBtnFaTie;
    private LinearLayout mLayIine;
    private Article release;

    private EditText release_title;
    private EditText release_content;
    //private View view;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    public BBSFragment(String fragmentText) {
        this.fragmentText = fragmentText;
    }

    List<Article> itemList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bbs, container, false);
        init(view);

        mBtnFaTie=view.findViewById(R.id.btn_item_ly_fatie);
        mBtnFaTie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                release_title=view.findViewById(R.id.edt_relese_title);
//                release_content=view.findViewById(R.id.edt_relese_content);
//                release= new Article(release_title.getText().toString(), release_content.getText().toString());
//                itemList.add(release);
//                init(view);
//                release_title.setText("");
//                release_content.setText("");
//                Toast.makeText(getContext(),"发布成功",Toast.LENGTH_LONG).show();
            }
        });




        return view;
    }



    //@Override
    public void onClick(View parent, int position) {
        Intent intent = new Intent(getActivity(), ItemShowActivity.class);
        Bundle bundle = new Bundle();
        Article item = itemList.get(position);

        intent.putExtra("ArticleId", item.getId());
        bundle.putSerializable(ItemBean.KEY, item);
        intent.putExtras(bundle);
        startActivityForResult(intent, 1);
    }

    public Thread getArticle() {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FormBody.Builder params = new FormBody.Builder();
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://8.131.250.250/bbs/getAllArticles")
                            .post(params.build())
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = Objects.requireNonNull(response.body()).string();
                    itemList = JSON.parseArray(responseData, Article.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void init(View view) {
        Thread getArticleThread = getArticle();
        try {
            getArticleThread.start();
            getArticleThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        recyclerView = view.findViewById(R.id.recyclerItemList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(itemList, getActivity());
        recyclerViewAdapter.setOnItemClickListener(this::onClick);
        recyclerView.setAdapter(recyclerViewAdapter);

    }





}
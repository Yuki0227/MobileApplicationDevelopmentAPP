package com.app.bbs.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.app.R;
import com.app.bbs.Adapter.DiscussAdapter;
import com.app.bbs.Bean.DiscussBean;
import com.app.bbs.Bean.ItemBean;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ItemShowActivity extends AppCompatActivity {

    private ItemBean item;
    private DiscussBean discuss;
    private DiscussAdapter discussAdapter;
    private RecyclerView recyclerView;
    private EditText discusscontent;

    TextView title,content;
    List<List<DiscussBean>> dis=new ArrayList<>();

    private Button mBtnDiscuss;


    public void load(){
        for (int i = 0; i < 1000; i++) {
            dis.add(new ArrayList<DiscussBean>());
        }
        dis.get(0).add(new DiscussBean(
                "棒棒棒！棒棒棒！棒棒棒！\n"+
                        "但还可以改进\n"+
                        "hhh"));
        dis.get(0).add(new DiscussBean("棒！"));
        dis.get(1).add(new DiscussBean("棒棒！"));
        dis.get(2).add(new DiscussBean("棒棒棒！"));
        dis.get(3).add(new DiscussBean("棒棒棒棒！"));
        dis.get(4).add(new DiscussBean("棒棒棒棒棒！"));
        dis.get(5).add(new DiscussBean("棒棒棒棒棒棒！"));
    }


    private void init(){
        Intent intant = getIntent();
        Bundle bundle = intant.getExtras();
        item = (ItemBean) bundle.getSerializable(ItemBean.KEY);
        if(item == null) return;
        content=findViewById(R.id.show_tv_item_content);
        title=findViewById(R.id.show_tv_item_title);
        title.setText(item.getTitle());
        content.setText(item.getContent());
    }

    public void init_discuss(int postion) {

        recyclerView = findViewById(R.id.recyclerDiscussList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ItemShowActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        discussAdapter = new DiscussAdapter(dis.get(postion),ItemShowActivity.this);
        //recyclerViewAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(discussAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bbs_item_show);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();

        init();
        load();
        init_discuss(bundle.getInt("pos"));

        mBtnDiscuss=findViewById(R.id.btn_discuss);
        mBtnDiscuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(ItemShowActivity.this, DiscussActivity.class);
                //startActivityForResult(intent,3);
                discusscontent=findViewById(R.id.edt_discuss_content);
                discuss=new DiscussBean(discusscontent.getText().toString());
                dis.get(bundle.getInt("pos")).add(discuss);
                init_discuss(bundle.getInt("pos"));
                discusscontent.setText("");
                Toast.makeText(ItemShowActivity.this,"发布成功",Toast.LENGTH_LONG).show();

            }
        });

    }


}
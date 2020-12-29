package com.app.bbs.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.R;

public class ReleaseActivity extends AppCompatActivity {

    private EditText artcletitle;
    private EditText artclebody;
    private Button mBtnitemRelease;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bbs_release);
        mBtnitemRelease=findViewById(R.id.btn_item_release);
        mBtnitemRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                artcletitle=findViewById(R.id.edt_relese_title);
                artclebody=findViewById(R.id.edt_relese_content);

            }
        });
    }
}
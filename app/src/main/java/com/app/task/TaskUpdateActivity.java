package com.app.task;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.app.R;

public class TaskUpdateActivity extends AppCompatActivity {

    private EditText et_title_update;
    private EditText et_content_update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_update);
        et_title_update = findViewById(R.id.et_title_update);
        et_content_update = findViewById(R.id.et_content_update);
        Bundle bundle = getIntent().getExtras();
        String str_title = bundle.getString("task_title");
        String str_content = bundle.getString("task_content");
        et_title_update.setText(str_title);
        et_content_update.setText(str_content);
    }
}
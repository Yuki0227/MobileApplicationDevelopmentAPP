package com.app.exercise.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.app.R;

public class SubmitActivity extends AppCompatActivity {

    private String submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_activity_submit);

        EditText et_submit = findViewById(R.id.et_submit);
        Button btn_submit = findViewById(R.id.btn_submit);
        submit = et_submit.getText().toString().trim();

        btn_submit.setOnClickListener(v -> {
            Intent intent = getIntent();
            intent.putExtra("submit", submit);
            finish();
        });
    }
}
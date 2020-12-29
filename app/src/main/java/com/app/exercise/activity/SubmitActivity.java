package com.app.exercise.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.R;

public class SubmitActivity extends AppCompatActivity {

    private EditText et_submit;
    private Button btn_submit;
    private String submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_activity_submit);

        et_submit = findViewById(R.id.et_submit);
        btn_submit  = findViewById(R.id.btn_submit);
        submit = et_submit.getText().toString().trim();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubmitActivity.this,QuestionActivity.class);
                intent.putExtra("submit",submit);
                startActivity(intent);
                finish();
            }
        });
    }
}
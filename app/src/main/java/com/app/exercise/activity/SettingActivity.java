package com.app.exercise.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.app.R;

public class SettingActivity extends AppCompatActivity {

    private Spinner choiceQuestionNum;
    private Spinner programmingQuestionNum;
    private Spinner judgementQuestionNum;

    private String choiceQuestion;
    private String programmingQuestion;
    private String judgementQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_activity_choice);

        choiceQuestionNum = (Spinner) findViewById(R.id.choice_question_number);
        programmingQuestionNum = findViewById(R.id.program_question_number);
        judgementQuestionNum = findViewById(R.id.judgment_question_number);

        Button btn_question = findViewById(R.id.exercise_btn_question);


        btn_question.setOnClickListener(v -> {
//            choiceQuestion =(String) choiceQuestionNum.getSelectedItem();
//            programmingQuestion = (String) programmingQuestionNum.getSelectedItem();
//            judgementQuestion = (String) judgementQuestionNum.getSelectedItem();
//            Intent intent = new Intent(SettingActivity.this, .class);
//            intent.putExtra("choiceQuestion", choiceQuestion);
//            intent.putExtra("programmingQuestion", programmingQuestion);
//            intent.putExtra("judgementQuestion", judgementQuestion);
//            startActivity(intent);
        });


    }
}
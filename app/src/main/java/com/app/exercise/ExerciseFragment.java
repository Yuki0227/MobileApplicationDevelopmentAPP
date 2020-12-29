package com.app.exercise;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.app.R;
import com.app.exercise.activity.SettingActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExerciseFragment} factory method to
 * create an instance of this fragment.
 */

public class ExerciseFragment extends Fragment {


    private final String fragmentText;
    private Button btn_do;
    private Button btn_history;

    private TextView fragmentTextView;

    public ExerciseFragment(String fragmentText) {
        this.fragmentText = fragmentText;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);
        btn_do = view.findViewById(R.id.exercise_btn_do);
        btn_history = view.findViewById(R.id.exercise_btn_history);

        btn_do.setOnClickListener(v -> startActivity(new Intent(getActivity(), SettingActivity.class)));

        return view;
    }

}
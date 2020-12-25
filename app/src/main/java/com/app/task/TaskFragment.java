package com.app.task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFragment} factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends Fragment {

    private String fragmentText;

    private TextView fragmentTextView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, null);
        return view;
    }

    public TaskFragment(String fragmentText) {
        this.fragmentText = fragmentText;
    }


}

package com.app.bbs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BBSFragment} factory method to
 * create an instance of this fragment.
 */
public class BBSFragment extends Fragment {

    private String fragmentText;

    private TextView fragmentTextView;

    public BBSFragment(String fragmentText) {
        this.fragmentText = fragmentText;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bbs, container, false);
        return view;
    }

}
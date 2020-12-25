package com.app.email;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmailFragment} factory method to
 * create an instance of this fragment.
 */
public class EmailFragment extends Fragment {

    private String fragmentText;

    private TextView fragmentTextView;

    public EmailFragment(String fragmentText) {
        this.fragmentText = fragmentText;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_email, container, false);
        return view;
    }

}

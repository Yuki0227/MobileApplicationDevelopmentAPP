package com.app.userPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.app.R;
import com.app.login.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    private String fragmentText;
    private Button btn_login;

    private TextView fragmentTextView;

    public UserFragment(String fragmentText) {
        this.fragmentText = fragmentText;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        btn_login = view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;

    }


}
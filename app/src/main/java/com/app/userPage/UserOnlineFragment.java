package com.app.userPage;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.MainActivity;
import com.app.R;
import com.app.login.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserOnlineFragment} factory method to
 * create an instance of this fragment.
 */
public class UserOnlineFragment extends Fragment {


    private String fragmentText;
    private TextView text1;
    private Button btn_exit;

    public UserOnlineFragment(String fragmentText) {
        this.fragmentText = fragmentText;
    }
    public UserOnlineFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_on, container, false);
        text1 = view.findViewById(R.id.Text1);
        text1.setText(fragmentText);

//        退出登录
        btn_exit =view.findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

                getActivity().finish();

            }
        });

        return view;
    }

}
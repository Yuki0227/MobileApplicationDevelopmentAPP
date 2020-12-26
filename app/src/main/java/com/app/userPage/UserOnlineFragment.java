package com.app.userPage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserOnlineFragment} factory method to
 * create an instance of this fragment.
 */
public class UserOnlineFragment extends Fragment {


    private String fragmentText;
    private TextView text1;


    public UserOnlineFragment(String fragmentText) {
        this.fragmentText = fragmentText;
    }
    public UserOnlineFragment() {

    }



//    public  UserOnlineFragment newInstance(String param1, String param2) {
//        UserOnlineFragment fragment = new UserOnlineFragment();
//        Bundle args = new Bundle();
//
//        fragment.setArguments(args);
//        return fragment;
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_on, container, false);
        text1 = view.findViewById(R.id.Text1);
        text1.setText(fragmentText);
        return view;
    }




}
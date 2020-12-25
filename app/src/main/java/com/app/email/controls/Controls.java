package com.app.email.controls;

import android.app.Activity;
import android.widget.Toast;

import com.app.MyApplication;


public class Controls {

    public static TitleBar getTitleBar() {
        return new TitleBar();
    }

    public static ProgressDialog getProgressDialog(Activity activity) {
        return new ProgressDialog(activity);
    }

    public static void toast(String s) {
        Toast.makeText(MyApplication.getContext(), s, Toast.LENGTH_SHORT).show();
    }

}

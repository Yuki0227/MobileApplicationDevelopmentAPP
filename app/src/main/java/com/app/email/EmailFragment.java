package com.app.email;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.app.MyApplication;
import com.app.R;
import com.app.email.activity.ConfigActivity;
import com.app.email.activity.ListActivity;
import com.app.email.activity.SendActivity;
import com.app.email.controls.Controls;
import com.app.util.CommonUtils;
import com.smailnet.emailkit.EmailKit;
import com.smailnet.microkv.MicroKV;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmailFragment} factory method to
 * create an instance of this fragment.
 */
public class EmailFragment extends Fragment {


    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_email, container, false);

        return view;
    }

    void initData() {
        if (MyApplication.getConfig() != null) {
            MicroKV kv = MicroKV.customize("config", true);
            if (kv.containsKV("folder_list")) {
                List<String> list = new ArrayList<>(kv.getStringSet("folder_list"));
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_list_item_1, list);
                listView.setAdapter(adapter);
            } else {
                EmailKit.useIMAPService(MyApplication.getConfig())
                        .getDefaultFolderList(new EmailKit.GetFolderListCallback() {
                            @Override
                            public void onSuccess(List<String> folderList) {
                                kv.setKV("folder_list", new HashSet<>(folderList)).save();
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                                        android.R.layout.simple_list_item_1, folderList);
                                listView.setAdapter(adapter);
                            }

                            @Override
                            public void onFailure(String errMsg) {
                                Controls.toast(errMsg);
                            }
                        });
            }
        } else {
            CommonUtils.showLongMsg(getActivity(), "邮箱功能无法使用，请设置邮箱信息");
        }
    }

    void initLayoutBind() {
        getView().findViewById(R.id.email_activity_main_edit_btn)
                .setOnClickListener(v -> {
                    if (MyApplication.getConfig() == null) {
                        CommonUtils.showLongMsg(getActivity(), "请先设置邮箱信息");
                    } else
                        startActivity(new Intent(getActivity(), SendActivity.class));
                });

        getView().findViewById(R.id.email_activity_main_settings_btn)
                .setOnClickListener(v -> startActivity(new Intent(getActivity(), ConfigActivity.class)));

        listView = getView().findViewById(R.id.email_activity_main_list);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String folderName = parent.getAdapter().getItem(position).toString();
            Intent intent = new Intent(getActivity(), ListActivity.class);
            intent.putExtra("folderName", folderName);
            startActivity(intent);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Looper.myQueue().addIdleHandler(() -> {
            initLayoutBind();
            initData();
            return false;
        });

    }
}

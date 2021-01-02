package com.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;

import com.app.task.entity.TaskAssign;
import com.app.util.User;
import com.smailnet.emailkit.EmailKit;
import com.smailnet.microkv.MicroKV;

import org.litepal.LitePal;

import java.util.List;

public class MyApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static EmailKit.Config config;
    private static User user;
    private static List<TaskAssign> taskList;
    //数据库中的所有用户
    private static List<User> allUsers;

    private static void restoreEmailStatus() {
        new Handler().postDelayed(() -> {
            MicroKV kv = MicroKV.customize("config", true);
            if (kv.containsKV("account")) {
                EmailKit.Config config = new EmailKit.Config()
                        .setAccount(kv.getString("account"))
                        .setPassword(kv.getString("password"))
                        .setSMTP(kv.getString("smtp_host"), kv.getInt("smtp_port"), kv.getBoolean("smtp_ssl"))
                        .setIMAP(kv.getString("imap_host"), kv.getInt("imap_port"), kv.getBoolean("imap_ssl"));
                MyApplication.setConfig(config);
            }
        }, 0);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EmailKit.initialize(this);
        MicroKV.initialize(this);
        LitePal.initialize(this);
        user = null;
        allUsers = null;
        taskList = null;
        context = getApplicationContext();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        try {
            restoreLoginStatus();
            restoreEmailStatus();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void restoreLoginStatus() {

        User user = null;
        MicroKV kv = MicroKV.defaultMicroKV();
        if (kv.containsKV("id")) {
            user = new User(kv.getInt("id"), kv.getString("name"), kv.getString("password"));
            Log.e("restoreUser", user.toString());
        }
        MyApplication.setUser(user);
    }

    public static Context getContext() {
        return context;
    }

    public static void setConfig(EmailKit.Config config) {
        MyApplication.config = config;
    }

    public static EmailKit.Config getConfig() {
        return config;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        if (user != null) {
            MicroKV.defaultMicroKV()
                    .setKV("id", user.getId())
                    .setKV("name", user.getName())
                    .setKV("password", user.getPassword())
                    .save();
        } else {
            MicroKV.defaultMicroKV().clear();
        }
        MyApplication.user = user;
    }

    public static void setUser(int id, String name, String password) {
        MyApplication.user = new User(id, name, password);
        MicroKV.defaultMicroKV()
                .setKV("id", user.getId())
                .setKV("name", user.getName())
                .setKV("password", user.getPassword())
                .save();
    }

    public static void setContext(Context context) {
        MyApplication.context = context;
    }

    public static List<TaskAssign> getTaskList() {
        return taskList;
    }

    public static void setTaskList(List<TaskAssign> taskList) {
        MyApplication.taskList = taskList;
    }


    public static List<User> getAllUsers() {
        return allUsers;
    }

    public static void setAllUsers(List<User> allUsers) {
        MyApplication.allUsers = allUsers;
    }
}

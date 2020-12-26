package com.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

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

    @Override
    public void onCreate() {
        super.onCreate();
        EmailKit.initialize(this);
        MicroKV.initialize(this);
        LitePal.initialize(this);
        user = null;
        taskList = null;
        context = getApplicationContext();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        //临时添加固定数据
        String account, password, nickname, smtpHost, imapHost, smtpPort, imapPort;
        boolean smtpSSL, imapSSL;
        account = "";
        password = "";
        smtpHost = "smtp.qq.com";
        imapHost = "imap.qq.com";
        smtpPort = "465";
        imapPort = "993";
        smtpSSL = true;
        imapSSL = true;
        EmailKit.Config config = new EmailKit.Config()
                .setSMTP(smtpHost, Integer.parseInt(smtpPort), smtpSSL)
                .setIMAP(imapHost, Integer.parseInt(imapPort), imapSSL)
                .setAccount(account)
                .setPassword(password);
        setConfig(config);

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
        MyApplication.user = user;
    }

    public static void setUser(int id, String name, String password) {
        MyApplication.user = new User(id, name, password);
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
}

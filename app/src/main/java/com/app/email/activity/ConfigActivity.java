package com.app.email.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Switch;

import com.app.MyApplication;
import com.app.R;
import com.app.email.BaseActivity;
import com.app.email.controls.Controls;
import com.app.email.table.LocalMsg;
import com.smailnet.emailkit.EmailKit;
import com.smailnet.microkv.MicroKV;

import org.litepal.LitePal;

public class ConfigActivity extends BaseActivity {

    private String account, password, nickname, smtpHost, imapHost, smtpPort, imapPort;
    private boolean smtpSSL, imapSSL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_activity_config);
        findViewById(R.id.email_activity_config_db_btn)
                .setOnClickListener(v -> {
                    LitePal.deleteAll(LocalMsg.class);
                    Controls.toast("已清除");
                });

        findViewById(R.id.email_activity_config_sp_btn)
                .setOnClickListener(v -> {
                    MicroKV.customize("config", true).removeKV("folder_name");
                    Controls.toast("已清除");
                });

        new Handler().postDelayed(() -> {
            MicroKV kv = MicroKV.customize("config", true);
            if (kv.containsKV("account")) {
                EmailKit.Config config = new EmailKit.Config()
                        .setAccount(kv.getString("account"))
                        .setPassword(kv.getString("password"))
                        .setSMTP(kv.getString("smtp_host"), kv.getInt("smtp_port"), kv.getBoolean("smtp_ssl"))
                        .setIMAP(kv.getString("imap_host"), kv.getInt("imap_port"), kv.getBoolean("imap_ssl"));
                MyApplication.setConfig(config);
                fillData();
            }
        }, 0);

        Controls.getTitleBar()
                .display(this, "服务器设置", R.drawable.email_confirm, () -> {
                    if (setData()) {
                        EmailKit.Config config = MyApplication.getConfig();
                        Controls.getProgressDialog(ConfigActivity.this)
                                .setMessage("验证中...")
                                .setCancelable(false)
                                .show(progressDialog -> EmailKit.auth(config, new EmailKit.GetAuthCallback() {
                                    @Override
                                    public void onSuccess() {
                                        saveData();
                                        progressDialog.dismiss();
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(String errMsg) {
                                        progressDialog.dismiss();
                                        Controls.toast(errMsg);
                                    }
                                }));
                    }
                });
    }


    private void fillData() {
        EmailKit.Config config = MyApplication.getConfig();


        ((EditText) findViewById(R.id.email_activity_config_account_et)).setText(config.getAccount());
        ((EditText) findViewById(R.id.email_activity_config_password_et)).setText(config.getPassword());
        ((EditText) findViewById(R.id.email_activity_config_smtp_host_et)).setText(config.getSMTPHost());
        ((EditText) findViewById(R.id.email_activity_config_imap_host_et)).setText(config.getIMAPHost());
        ((EditText) findViewById(R.id.email_activity_config_smtp_port_et)).setText(String.valueOf(config.getSMTPPort()));
        ((EditText) findViewById(R.id.email_activity_config_imap_port_et)).setText(String.valueOf(config.getIMAPPort()));
        ((Switch) findViewById(R.id.email_activity_config_smtp_ssl_switch)).setChecked(true);
        ((Switch) findViewById(R.id.email_activity_config_imap_ssl_switch)).setChecked(true);

        MicroKV kv = MicroKV.customize("config", true);
        if (kv.containsKV("nickname")) {
            ((EditText) findViewById(R.id.email_activity_config_nickname_et)).setText(kv.getString("nickname"));
        }

    }

    /**
     * 设置服务器配置参数
     */
    private boolean setData() {

        account = ((EditText) findViewById(R.id.email_activity_config_account_et)).getText().toString();
        password = ((EditText) findViewById(R.id.email_activity_config_password_et)).getText().toString();
        nickname = ((EditText) findViewById(R.id.email_activity_config_nickname_et)).getText().toString();
        smtpHost = ((EditText) findViewById(R.id.email_activity_config_smtp_host_et)).getText().toString();
        imapHost = ((EditText) findViewById(R.id.email_activity_config_imap_host_et)).getText().toString();
        smtpPort = ((EditText) findViewById(R.id.email_activity_config_smtp_port_et)).getText().toString();
        imapPort = ((EditText) findViewById(R.id.email_activity_config_imap_port_et)).getText().toString();
        smtpSSL = ((Switch) findViewById(R.id.email_activity_config_smtp_ssl_switch)).isChecked();
        imapSSL = ((Switch) findViewById(R.id.email_activity_config_imap_ssl_switch)).isChecked();

        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password) || TextUtils.isEmpty(nickname)
                || TextUtils.isEmpty(smtpHost) || TextUtils.isEmpty(imapHost)
                || TextUtils.isEmpty(smtpPort) || TextUtils.isEmpty(imapPort)) {
            Controls.toast("请填写完整上面的内容");
            return false;
        } else {
            EmailKit.Config config = new EmailKit.Config()
                    .setSMTP(smtpHost, Integer.parseInt(smtpPort), smtpSSL)
                    .setIMAP(imapHost, Integer.parseInt(imapPort), imapSSL)
                    .setAccount(account)
                    .setPassword(password);
            MyApplication.setConfig(config);
            return true;
        }
    }

    /**
     * 保存服务器配置参数到本地
     */
    private void saveData() {
        MicroKV.customize("config", true)
                .setKV("account", account)
                .setKV("password", password)
                .setKV("nickname", nickname)
                .setKV("smtp_host", smtpHost)
                .setKV("imap_host", imapHost)
                .setKV("smtp_port", Integer.parseInt(smtpPort))
                .setKV("imap_port", Integer.parseInt(imapPort))
                .setKV("smtp_ssl", smtpSSL)
                .setKV("imap_ssl", imapSSL)
                .save();
    }
}

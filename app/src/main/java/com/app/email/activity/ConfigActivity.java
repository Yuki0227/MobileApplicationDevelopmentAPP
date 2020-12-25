package com.app.email.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Switch;

import com.app.MyApplication;
import com.app.R;
import com.app.email.BaseActivity;
import com.app.email.controls.Controls;
import com.smailnet.emailkit.EmailKit;
import com.smailnet.microkv.MicroKV;

public class ConfigActivity extends BaseActivity {

    private String account, password, nickname, smtpHost, imapHost, smtpPort, imapPort;
    private boolean smtpSSL, imapSSL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_activity_config);
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
                                        //startActivity(new Intent(ConfigActivity.this, ));
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

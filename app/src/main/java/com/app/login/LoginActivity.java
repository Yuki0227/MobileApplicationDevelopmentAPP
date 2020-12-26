package com.app.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.app.MyApplication;
import com.app.R;
import com.app.util.CommonUtils;

import org.json.JSONObject;

import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private Button btn_login;
    private Button btn_register;
    private EditText et_name;
    private EditText et_password;
    private Handler mainHandler;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initLayoutBind();
        initListener();
    }



    private void initLayoutBind() {
        et_name = findViewById(R.id.et_name);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_signin);
        btn_register = findViewById(R.id.btn_register);
        mainHandler = new Handler();
        relativeLayout = findViewById(R.id.login_title);

    }

    private void initListener() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //主页图片修改
        et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    //获得焦点时，修改背景属性
                    //R.drawable.edit_text_bg_focus为背景资源
                    relativeLayout.setBackgroundResource(R.drawable.bg_inpassword);
                }
                else{
                    relativeLayout.setBackgroundResource(R.drawable.bg_denglu);
                }
            }
        });
    }

    private void login() {
        final String username = et_name.getText().toString().trim();
        final String password = et_password.getText().toString().trim();

        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean loginSucceed = false;
                final String id;
                final String name;
                try {
                    FormBody.Builder params = new FormBody.Builder();
                    params.add("name", username);
                    params.add("password", password);
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://8.131.250.250/user/find")
                            .post(params.build())
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = Objects.requireNonNull(response.body()).string();
                    Log.d("responseData", responseData);
                    JSONObject jsonObj = new JSONObject(responseData);
                    id = jsonObj.getString("id");
                    name = jsonObj.getString("name");

                    MyApplication.setUser(Integer.parseInt(id), name, "");

                    loginSucceed = true;
                } catch (Exception e) {
                    e.printStackTrace();

                }
                final boolean finalLoginSucceed = loginSucceed;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (finalLoginSucceed) {
                            CommonUtils.showDlgMsg(LoginActivity.this, "登录成功");
                        } else {
                            CommonUtils.showDlgMsg(LoginActivity.this, "用户名与密码不匹配或用户不存在！");
                        }
                    }
                });
            }
        }).start();

    }


}
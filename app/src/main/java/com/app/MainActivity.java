package com.app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.util.CommonUtils;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Button btn_login;
    private EditText et_username;
    private EditText et_password;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLayoutBind();
        initListener();
    }

    private void initLayoutBind() {
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        mainHandler = new Handler();
    }

    private void initListener(){
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login(){
        final  String username = et_username.getText().toString().trim();
        final String password = et_password.getText().toString().trim();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    FormBody.Builder params = new FormBody.Builder();
                    params.add("name",username);
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://8.131.250.250/user/findByName")
                            .post(params.build())
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    JSONObject jsonObj = new JSONObject(responseData);
                    final String a= jsonObj.getString("name");
                    final String b =jsonObj.getString("password");
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(a.equals(username)&&b.equals(password)){
                                CommonUtils.showDlgMsg(MainActivity.this,"登录成功");
                            }else {
                                CommonUtils.showDlgMsg(MainActivity.this,"用户名或密码错误");
                            }

                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            CommonUtils.showDlgMsg(MainActivity.this,"用户名不存在，请先注册！");
                        }
                    });
                }
            }
        }).start();

    }




}
package com.app.register;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.app.R;
import com.app.util.CommonUtils;

import org.json.JSONObject;

import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    private Button btn_register;
    private EditText et_name;
    private EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initLayoutBind();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }


    private void initLayoutBind() {
        et_name = findViewById(R.id.et_name1);
        et_password = findViewById(R.id.et_password1);
        btn_register = findViewById(R.id.btn_register1);
    }


    private void register() {
        final String username = et_name.getText().toString().trim();
        final String password = et_password.getText().toString().trim();
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean registerSucceed = false;
                try {
                    FormBody.Builder params = new FormBody.Builder();
                    if (username.isEmpty() || password.isEmpty()) throw new Exception();
                    params.add("name", username);
                    params.add("password", password);
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://8.131.250.250/user/add")
                            .post(params.build())
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = Objects.requireNonNull(response.body()).string();
                    Log.d("responseData", responseData);
                    JSONObject jsonObj = new JSONObject(responseData);
                    Integer idNum = Integer.parseInt(jsonObj.getString("id"));
                    registerSucceed = true;
                } catch (Exception e) {
                    e.printStackTrace();

                }


                final boolean finalRegisterSucceed = registerSucceed;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (finalRegisterSucceed) {
                            CommonUtils.showLongMsg(RegisterActivity.this, "注册成功");
                            finish();
                        } else
                            CommonUtils.showDlgMsg(RegisterActivity.this, "注册失败！");
                    }
                });
            }
        }).start();
    }

}
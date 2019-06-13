package com.example.zxdc;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.example.zxdc.adapter.MyBaseActivity;
import com.example.zxdc.pojo.CommonData;
import com.example.zxdc.pojo.User;
import com.example.zxdc.util.MyApplication;
import com.example.zxdc.util.MyToastUtil;
import com.example.zxdc.util.UserClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 用户登录
 */
public class login extends MyBaseActivity {
    EditText username, password;
    Button login, regist;
    Context con = login.this;


    @Override
    protected void initUI() {
        setContentView(R.layout.login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        regist = findViewById(R.id.regist);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestParams ps = new RequestParams();
                ps.add("username", username.getText().toString());
                ps.add("password", password.getText().toString());
                UserClient.post("user/login",ps,new AsyncHttpResponseHandler(){
                    @Override
                    public void onSuccess(String content) {
                        super.onSuccess(content);
                        CommonData data=JSON.parseObject(content,CommonData.class);
                        if (data.getCode().equals("200")) {
                            User u = JSON.parseObject(data.getData(), User.class);
                            MyApplication.getApp().setU(u);
                            openActivity(main.class);
                        } else {
                            MyToastUtil.ShowToast(con, data.getMsg());
                        }
                    }
                });

            }
            });
        regist.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View view){
                openActivity(Regist.class);
            }
            });
        }

    }

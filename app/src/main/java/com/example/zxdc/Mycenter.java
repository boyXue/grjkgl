package com.example.zxdc;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zxdc.adapter.MyBaseActivity;
import com.example.zxdc.util.MyApplication;
import com.example.zxdc.util.MyToastUtil;
import com.example.zxdc.util.Url;
import com.example.zxdc.util.UserClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

public class Mycenter extends MyBaseActivity {


    ImageView head, wdyy, tc,xgmm;
    TextView nickname;



    private void xgmm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Mycenter.this);
        final AlertDialog dialog = builder.create();

        View view = View.inflate(Mycenter.this, R.layout.xgmm, null);
        // dialog.setView(view);// 将自定义的布局文件设置给dialog
        dialog.setView(view, 0, 0, 0, 0);// 设置边距为0,保证在2.x的版本上运行没问题

        final EditText newpass = (EditText) view.findViewById(R.id.newpass);
        final EditText oldpass = (EditText) view.findViewById(R.id.oldpass);
        Button btnOK = (Button) view.findViewById(R.id.add);
        Button btnCancel = (Button) view.findViewById(R.id.qx);

        btnOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String names = newpass.getText().toString();
                String phones = oldpass.getText().toString();
                // password!=null && !password.equals("")
                if (names.equals(phones)) {
                    RequestParams ps = new RequestParams();
                    ps.add("id", MyApplication.getApp().getU().getId());
                    ps.add("pass", names);
                    UserClient.post("user/update", ps,
                            new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(String content) {
                                    super.onSuccess(content);
                                    MyToastUtil.ShowToast(Mycenter.this, "修改成功");
                                    finish();
                                }
                            });

                } else {
                    Toast.makeText(Mycenter.this, "两次密码输入不一致",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();// 隐藏dialog
            }
        });

        dialog.show();
    }

    @Override
    protected void initUI() {
        setContentView(R.layout.mycenter);
        head = findViewById(R.id.head);
        ImageLoader.getInstance().displayImage(Url.url() + "upload/" + MyApplication.getApp().getU().getId(), head);
        nickname = findViewById(R.id.nickname);
        nickname.setText(MyApplication.getApp().getU().getNickname());
        tc = findViewById(R.id.tc);
        tc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        wdyy = findViewById(R.id.wdyy);
        wdyy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mycenter.this.startActivity(new Intent(Mycenter.this,wddd.class));
            }
        });
        xgmm = findViewById(R.id.xgmm);
        xgmm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xgmm();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}

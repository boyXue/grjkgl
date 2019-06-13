package com.example.zxdc;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.zxdc.adapter.CommonAdapter;
import com.example.zxdc.adapter.MyBaseActivity;
import com.example.zxdc.adapter.ViewHolder;
import com.example.zxdc.pojo.zwyy;
import com.example.zxdc.util.MyApplication;
import com.example.zxdc.util.UserClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.List;

public class zwyy_list extends MyBaseActivity {
    ListView listView;
    List<zwyy> list;

    @Override
    protected void initUI() {
        setContentView(R.layout.zwyy_list);
        listView = findViewById(R.id.listView);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getlist();
    }

    public void getlist() {
        RequestParams ps = new RequestParams();
        ps.add("uid", MyApplication.getApp().getU().getId());
        UserClient.get("zwyy/list", ps, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                list = JSON.parseArray(content, zwyy.class);
                listView.setAdapter(new CommonAdapter<zwyy>(zwyy_list.this, list, R.layout.zwyy_item) {
                    @Override
                    public void convert(ViewHolder helper, zwyy item) {
                        helper.setText(R.id.zh, item.getZw());
                        helper.setText(R.id.state,item.getState());
                        helper.setText(R.id.time,item.getTime());
                        helper.setText(R.id.pj,item.getPj());

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        startActivity(new Intent(zwyy_list.this,cp_list.class).putExtra("zid",list.get(position).getId()));
                    }
                });
                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        pl(list.get(position).getId());
                        return true;
                    }
                });
            }
        });
    }

    private void pl(final String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();

        View view = View.inflate(this, R.layout.pinglun_add, null);
        // dialog.setView(view);// 将自定义的布局文件设置给dialog
        dialog.setView(view, 0, 0, 0, 0);// 设置边距为0,保证在2.x的版本上运行没问题

        final EditText msg = (EditText) view.findViewById(R.id.msg);

        Button btnOK = (Button) view.findViewById(R.id.add);
        Button btnCancel = (Button) view.findViewById(R.id.qx);

        btnOK.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                String msgs = msg.getText().toString();
                // password!=null && !password.equals("")
                if (!TextUtils.isEmpty(msgs)) {
                    RequestParams ps = new RequestParams();
                    ps.add("id", id);
                    ps.add("pj", msgs);
                    UserClient.post("zwyy/update", ps,
                            new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(String content) {
                                    super.onSuccess(content);
                                    getlist();
                                }
                            });

                } else {
                    Toast.makeText(zwyy_list.this, "输入框内容不能为空!",
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
}

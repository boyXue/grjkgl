package com.example.zxdc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.example.zxdc.adapter.CommonAdapter;
import com.example.zxdc.adapter.MyBaseActivity;
import com.example.zxdc.adapter.ViewHolder;
import com.example.zxdc.pojo.zw;
import com.example.zxdc.util.MyApplication;
import com.example.zxdc.util.Url;
import com.example.zxdc.util.UserClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class MainActivity extends MyBaseActivity {


    ListView listView;
    List<zw> list;
    ImageView yh, dd;

    @Override
    protected void initUI() {
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        yh = findViewById(R.id.yh);
        dd = findViewById(R.id.dd);
        yh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Mycenter.class));
            }
        });

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
        UserClient.get("zw/alllist", ps, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                list = JSON.parseArray(content, zw.class);
                listView.setAdapter(new CommonAdapter<zw>(MainActivity.this, list, R.layout.zw_item) {
                    @Override
                    public void convert(ViewHolder helper, zw item) {
                        helper.setText(R.id.name, item.getName());
                        helper.setText(R.id.address, "地址:" + item.getAddress());
                        ImageView pic=helper.getView(R.id.pic);
                        ImageLoader.getInstance().displayImage(Url.url()+"upload/"+item.getPic(),pic);
                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        Intent i = new Intent(MainActivity.this, cp_list.class).putExtra("zid", list.get(position).getId());                                         startActivity(i);
                    }
                });
            }
        });
    }
}

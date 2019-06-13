package com.example.zxdc;

import android.widget.ListView;

import com.example.zxdc.adapter.MyBaseActivity;
import com.example.zxdc.pojo.gwc;

import java.util.List;

public class yy_cp extends MyBaseActivity {

    ListView listView;
    List<gwc> list;

    @Override
    protected void initUI() {
        setContentView(R.layout.yy_cp);
        listView=findViewById(R.id.listView);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}

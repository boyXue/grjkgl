package com.example.zxdc;

import android.widget.TextView;

import com.example.zxdc.adapter.MyBaseActivity;
import com.example.zxdc.pojo.zs;

public class zs_des extends MyBaseActivity {

    zs o;
    TextView title,msg,date;

    @Override
    protected void initUI() {
        setContentView(R.layout.zs_des);
        title=findViewById(R.id.title);
        msg=findViewById(R.id.msg);
        date=findViewById(R.id.date);
        o= (zs) getIntent().getExtras().getSerializable("o");
        title.setText(o.getTitle());
        msg.setText(o.getMsg());
        date.setText(o.getDate());

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}

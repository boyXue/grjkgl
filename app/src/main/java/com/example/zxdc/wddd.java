package com.example.zxdc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.example.zxdc.adapter.CommonAdapter;
import com.example.zxdc.adapter.MyBaseActivity;
import com.example.zxdc.adapter.ViewHolder;
import com.example.zxdc.pojo.gwc;
import com.example.zxdc.util.MyApplication;
import com.example.zxdc.util.Url;
import com.example.zxdc.util.UserClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class wddd extends MyBaseActivity {

    ListView listView;
    List<gwc> list;
    @Override
    protected void initUI() {
        setContentView(R.layout.wdyy);
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
        UserClient.get("gwc/wddd", ps, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                list = JSON.parseArray(content, gwc.class);

                listView.setAdapter(new CommonAdapter<gwc>(wddd.this, list, R.layout.gwc_item) {
                    @Override
                    public void convert(ViewHolder helper, gwc item) {
                        ImageView pic = helper.getView(R.id.pic);
                        ImageLoader.getInstance().displayImage(Url.url() + "upload/" + item.getPic(), pic);
                        helper.setText(R.id.name, item.getCname() );
                        helper.setText(R.id.price, "售价:"+item.getPrice() );
                        helper.setText(R.id.count, "数量:" + item.getCount());
                        helper.setText(R.id.pj, "评价:" + item.getPj());
                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        if(list.get(position).getPj()==null||list.get(position).getPj().equals("")){
                        final EditText e=new EditText(wddd.this);
                        new AlertDialog.Builder(wddd.this).setTitle("评价").setView(e).setPositiveButton("评价", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RequestParams ps=new RequestParams();
                                ps.add("id",list.get(position).getId());
                                ps.add("pj",e.getText().toString());
                                UserClient.get("gwc/update",ps,new AsyncHttpResponseHandler(){
                                    @Override
                                    public void onSuccess(String content) {
                                        super.onSuccess(content);
                                        getlist();
                                    }
                                });
                            }
                        }).setNeutralButton("返回",null).show();
                        }
                    }
                });

            }
        });
    }
}

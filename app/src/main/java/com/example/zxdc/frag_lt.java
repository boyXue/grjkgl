package com.example.zxdc;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.zxdc.adapter.CommonAdapter;
import com.example.zxdc.adapter.ViewHolder;
import com.example.zxdc.pojo.jbxx;
import com.example.zxdc.util.Url;
import com.example.zxdc.util.UserClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import zuo.biao.library.base.BaseFragment;

public class frag_lt extends BaseFragment {


    ListView listView;
    List<jbxx> list;
    TextView add;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //类相关初始化，必须使用<<<<<<<<<<<<<<<<
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.frag_lt);

        //类相关初始化，必须使用>>>>>>>>>>>>>>>>
        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

        return view;
    }

    /**
     * 创建一个Fragment实例
     *
     * @return
     */
    public static frag_lt createInstance() {
        frag_lt fragment = new frag_lt();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initView() {
        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), lt_add.class));
            }
        });
        listView = findViewById(R.id.listView);

        getlist();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onResume() {
        super.onResume();
        getlist();
    }


    public void getlist() {
        RequestParams ps = new RequestParams();
        UserClient.get("jbxx/alllist", ps, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                list = JSON.parseArray(content, jbxx.class);
                listView.setAdapter(new CommonAdapter<jbxx>(getActivity(), list, R.layout.jbxx_item) {
                    @Override
                    public void convert(ViewHolder helper, jbxx item) {
                        ImageView head = helper.getView(R.id.head);
                        ImageLoader.getInstance().displayImage(Url.url() + "upload/" + item.getHead(), head);
                        helper.setText(R.id.title, item.getTitle());
                        helper.setText(R.id.uname, item.getUname());
                        helper.setText(R.id.time, item.getTime());
                        helper.setText(R.id.msg, "详情:" + item.getMsg());
                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(getActivity(), lt_des.class);
                        Bundle b = new Bundle();
                        b.putSerializable("o", list.get(position));
                        i.putExtras(b);
                        startActivity(i);
                    }
                });
            }
        });
    }


}

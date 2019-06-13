package com.example.zxdc;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.example.zxdc.adapter.CommonAdapter;
import com.example.zxdc.adapter.ViewHolder;
import com.example.zxdc.pojo.sp;
import com.example.zxdc.pojo.zs;
import com.example.zxdc.util.UserClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.List;

import zuo.biao.library.base.BaseFragment;

public class frag_zs extends BaseFragment {


    ListView listView;
    List<zs> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //类相关初始化，必须使用<<<<<<<<<<<<<<<<
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.frag_zs);

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
    public static frag_zs createInstance() {
        frag_zs fragment = new frag_zs();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initView() {

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
        UserClient.get("zs/alllist", ps, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                list = JSON.parseArray(content, zs.class);
                listView.setAdapter(new CommonAdapter<zs>(getActivity(), list, R.layout.zs_item) {
                    @Override
                    public void convert(ViewHolder helper, zs item) {
                        helper.setText(R.id.title, item.getTitle());
                        helper.setText(R.id.date, item.getDate());
                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(getActivity(), zs_des.class);
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

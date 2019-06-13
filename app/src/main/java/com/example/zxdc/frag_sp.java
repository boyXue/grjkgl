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
import com.example.zxdc.pojo.sp;
import com.example.zxdc.util.Url;
import com.example.zxdc.util.UserClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import zuo.biao.library.base.BaseFragment;

public class frag_sp extends BaseFragment {


    ListView listView;
    List<sp> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //类相关初始化，必须使用<<<<<<<<<<<<<<<<
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.frag_sp);

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
    public static frag_sp createInstance() {
        frag_sp fragment = new frag_sp();
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
        UserClient.get("sp/alllist", ps, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                list = JSON.parseArray(content, sp.class);
                listView.setAdapter(new CommonAdapter<sp>(getActivity(), list, R.layout.sp_item) {
                    @Override
                    public void convert(ViewHolder helper, sp item) {
                        helper.setText(R.id.title, item.getTitle());
                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(getActivity(), bofang.class);
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

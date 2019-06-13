package com.example.zxdc;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.zxdc.adapter.CommonAdapter;
import com.example.zxdc.adapter.ViewHolder;
import com.example.zxdc.pojo.cp;
import com.example.zxdc.pojo.lb;
import com.example.zxdc.util.MyApplication;
import com.example.zxdc.util.MyToastUtil;
import com.example.zxdc.util.Url;
import com.example.zxdc.util.UserClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import zuo.biao.library.base.BaseFragment;

public class frag_sy extends BaseFragment {


    ListView listView;
    List<cp> list;

    ImageView cx;
    EditText key;
    Button type;
    int sl = 1;
    String types="全部";

    List<lb> llist;

    private AlertDialog alertDialog1; //信息框

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //类相关初始化，必须使用<<<<<<<<<<<<<<<<
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.frag_sy);

        //类相关初始化，必须使用>>>>>>>>>>>>>>>>
        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 创建一个Fragment实例
     *
     * @return
     */
    public static frag_sy createInstance() {
        frag_sy fragment = new frag_sy();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initView() {
        listView = findViewById(R.id.listView);
        cx = findView(R.id.cx);
        cx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getlist();
            }
        });
        key = findView(R.id.key);
        type = findView(R.id.type);
        type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams ps=new RequestParams();
                UserClient.get("lb/alllist",ps,new AsyncHttpResponseHandler(){
                    @Override
                    public void onSuccess(String content) {
                        super.onSuccess(content);
                        llist=JSON.parseArray(content,lb.class);
                        final String[] items = new String[llist.size()];
                        for(int i=0;i<llist.size();i++){
                            items[i]=llist.get(i).getName();
                        }
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                        alertBuilder.setTitle("选择种类");
                        alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                alertDialog1.dismiss();
                                type.setText(items[i]);
                                types=items[i];
                                getlist();
                            }
                        });
                        alertDialog1 = alertBuilder.create();
                        alertDialog1.show();
                    }
                });

            }
        });
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
        if(key.getText().toString().length()>0)
            ps.add("key",key.getText().toString());
        ps.add("type",types);
        UserClient.get("cp/alllist", ps, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                list = JSON.parseArray(content, cp.class);
                listView.setAdapter(new CommonAdapter<cp>(getActivity(), list, R.layout.cp_item) {
                    @Override
                    public void convert(ViewHolder helper, cp item) {
                        ImageView pic = helper.getView(R.id.pic);
                        ImageLoader.getInstance().displayImage(Url.url() + "upload/" + item.getPic(), pic);
                        helper.setText(R.id.name, item.getName());
                        helper.setText(R.id.price, item.getPrice() + "元");
                        helper.setText(R.id.type, "种类:" + item.getType());
                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        xz(list.get(position));
                    }
                });
            }


        });
    }

    private void xz(final cp c) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog dialog = builder.create();

        View view = View.inflate(getActivity(), R.layout.xuanze, null);
        // dialog.setView(view);// 将自定义的布局文件设置给dialog
        dialog.setView(view, 0, 0, 0, 0);// 设置边距为0,保证在2.x的版本上运行没问题

        final TextView count = (TextView) view.findViewById(R.id.count);
        Button jia = (Button) view.findViewById(R.id.jia);
        Button jian = (Button) view.findViewById(R.id.jian);
        jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sl = sl + 1;
                count.setText(sl + "");
            }
        });
        jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sl != 1) {
                    sl = sl - 1;
                } else {
                    sl = 1;
                }
                count.setText(sl + "");
            }
        });

        Button btnOK = (Button) view.findViewById(R.id.add);
        Button btnCancel = (Button) view.findViewById(R.id.qx);

        btnOK.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                String msgs = count.getText().toString();
                // password!=null && !password.equals("")
                if (!TextUtils.isEmpty(msgs)) {
                    RequestParams ps = new RequestParams();
                    ps.add("uid", MyApplication.getApp().getU().getId());
                    ps.add("cid", c.getId());
                    ps.add("price", c.getPrice());
                    ps.add("count", msgs);
                    ps.add("cname", c.getName());
                    ps.add("pic", c.getPic());
                    ps.add("zk", c.getZk());
                    UserClient.post("gwc/add", ps,
                            new AsyncHttpResponseHandler() {

                                @Override
                                public void onSuccess(String content) {
                                    super.onSuccess(content);
                                    MyToastUtil.ShowToast(getActivity(), "成功");
                                    dialog.dismiss();
                                }
                            });

                } else {
                    Toast.makeText(getActivity(), "输入框内容不能为空!",
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

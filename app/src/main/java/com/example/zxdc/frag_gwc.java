package com.example.zxdc;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.zxdc.adapter.CommonAdapter;
import com.example.zxdc.adapter.ViewHolder;
import com.example.zxdc.pojo.cp;
import com.example.zxdc.pojo.gwc;
import com.example.zxdc.util.MyApplication;
import com.example.zxdc.util.MyToastUtil;
import com.example.zxdc.util.Url;
import com.example.zxdc.util.UserClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youth.banner.Banner;

import java.util.List;

import zuo.biao.library.base.BaseFragment;

public class frag_gwc extends BaseFragment {


    ListView listView;
    List<gwc> list;
    TextView js;
    int sl = 1;
    TextView zong;
    double zongs = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //类相关初始化，必须使用<<<<<<<<<<<<<<<<
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.frag_gwc);

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
    public static frag_gwc createInstance() {
        frag_gwc fragment = new frag_gwc();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initView() {
        zong = findViewById(R.id.zong);
        listView = findViewById(R.id.listView);
        js = findViewById(R.id.js);
        js.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams ps = new RequestParams();
                ps.add("uid", MyApplication.getApp().getU().getId());
                UserClient.get("gwc/js", ps, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String content) {
                        super.onSuccess(content);
                        MyToastUtil.ShowToast(getActivity(), "预定成功");
                        zongs = 0;
                        getlist();
                        new AlertDialog.Builder(getActivity()).setTitle("请支付").setMessage("选择付款方式").setPositiveButton("支付宝", null).setNegativeButton("微信", null)
                                .show();
                    }
                });
            }
        });
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        getlist();
    }

    public void getlist() {
        RequestParams ps = new RequestParams();
        ps.add("uid", MyApplication.getApp().getU().getId());
        UserClient.get("gwc/list", ps, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                list = JSON.parseArray(content, gwc.class);
                zongs = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getState().equals("未结算")) {

                        zongs = zongs + Double.parseDouble(list.get(i).getCount()) * Double.parseDouble(list.get(i).getPrice());


                    }
                }
                zong.setText("总价:" + zongs + "");
                listView.setAdapter(new CommonAdapter<gwc>(getActivity(), list, R.layout.gwc_item) {
                    @Override
                    public void convert(ViewHolder helper, gwc item) {
                        ImageView pic = helper.getView(R.id.pic);
                        ImageLoader.getInstance().displayImage(Url.url() + "upload/" + item.getPic(), pic);
                        helper.setText(R.id.name, item.getCname());
                        helper.setText(R.id.price, "售价:" + item.getPrice());
                        helper.setText(R.id.state, "支付状态:" + item.getState());
                        helper.setText(R.id.count, "数量:" + item.getCount());

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

    private void xz(final gwc c) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog dialog = builder.create();

        View view = View.inflate(getActivity(), R.layout.xuanze, null);
        // dialog.setView(view);// 将自定义的布局文件设置给dialog
        dialog.setView(view, 0, 0, 0, 0);// 设置边距为0,保证在2.x的版本上运行没问题
        sl = Integer.parseInt(c.getCount());

        final TextView count = (TextView) view.findViewById(R.id.count);
        count.setText(c.getCount());
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
                    ps.add("count", msgs);
                    ps.add("id", c.getId());
                    UserClient.post("gwc/update", ps,
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
                RequestParams ps = new RequestParams();
                ps.add("id", c.getId());
                UserClient.get("gwc/del", ps, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String content) {
                        super.onSuccess(content);
                        getlist();
                    }
                });
                dialog.dismiss();// 隐藏dialog
            }
        });

        dialog.show();
    }

}

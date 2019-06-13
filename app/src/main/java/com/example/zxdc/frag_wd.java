package com.example.zxdc;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.zxdc.adapter.CommonAdapter;
import com.example.zxdc.adapter.ViewHolder;
import com.example.zxdc.pojo.gwc;
import com.example.zxdc.util.MyApplication;
import com.example.zxdc.util.MyToastUtil;
import com.example.zxdc.util.Url;
import com.example.zxdc.util.UserClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import zuo.biao.library.base.BaseFragment;

public class frag_wd extends BaseFragment {


    ImageView head, wdyy, tc,xgmm;
    TextView nickname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //类相关初始化，必须使用<<<<<<<<<<<<<<<<
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.mycenter);

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
    public static frag_wd createInstance() {
        frag_wd fragment = new frag_wd();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initView() {
        head = findViewById(R.id.head);
        ImageLoader.getInstance().displayImage(Url.url() + "upload/" + MyApplication.getApp().getU().getHead(), head);
        nickname = findViewById(R.id.nickname);
        nickname.setText(MyApplication.getApp().getU().getNickname());
        tc = findViewById(R.id.tc);
        tc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        wdyy = findViewById(R.id.wdyy);
        wdyy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),wddd.class));
            }
        });
        xgmm = findViewById(R.id.xgmm);
        xgmm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xgmm();
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

    }

    private void xgmm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog dialog = builder.create();

        View view = View.inflate(getActivity(), R.layout.xgmm, null);
        // dialog.setView(view);// 将自定义的布局文件设置给dialog
        dialog.setView(view, 0, 0, 0, 0);// 设置边距为0,保证在2.x的版本上运行没问题

        final EditText newpass = (EditText) view.findViewById(R.id.newpass);
        final EditText oldpass = (EditText) view.findViewById(R.id.oldpass);
        Button btnOK = (Button) view.findViewById(R.id.add);
        Button btnCancel = (Button) view.findViewById(R.id.qx);

        btnOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String names = newpass.getText().toString();
                String phones = oldpass.getText().toString();
                // password!=null && !password.equals("")
                if (names.equals(phones)) {
                    RequestParams ps = new RequestParams();
                    ps.add("id", MyApplication.getApp().getU().getId());
                    ps.add("pass", names);
                    UserClient.post("user/update", ps,
                            new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(String content) {
                                    super.onSuccess(content);
                                    MyToastUtil.ShowToast(getActivity(), "修改成功");
                                   getActivity(). finish();
                                }
                            });

                } else {
                    Toast.makeText(getActivity(), "两次密码输入不一致",
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

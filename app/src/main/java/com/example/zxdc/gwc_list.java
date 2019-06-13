package com.example.zxdc;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.zxdc.adapter.CommonAdapter;
import com.example.zxdc.adapter.MyBaseActivity;
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

import java.util.List;

public class gwc_list extends MyBaseActivity {
    ListView listView;
    List<gwc> list;
    TextView js;
    int sl = 1;
    TextView zong;
    double zongs = 0;

    @Override
    protected void initUI() {
        setContentView(R.layout.gwc_list);
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
                        MyToastUtil.ShowToast(gwc_list.this,"预定成功");
                        getlist();
                    }
                });
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
        ps.add("zid", getIntent().getStringExtra("zid"));
        ps.add("uid", MyApplication.getApp().getU().getId());
        UserClient.get("gwc/list", ps, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                list = JSON.parseArray(content, gwc.class);
                zongs=0;
                for (int i = 0; i < list.size(); i++) {

                            zongs = zongs + Double.parseDouble(list.get(i).getCount()) * Double.parseDouble(list.get(i).getPrice());
                }
                zong.setText("总价:" + zongs + "");
                listView.setAdapter(new CommonAdapter<gwc>(gwc_list.this, list, R.layout.gwc_item) {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();

        View view = View.inflate(this, R.layout.xuanze, null);
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
                                    MyToastUtil.ShowToast(gwc_list.this, "成功");
                                    dialog.dismiss();
                                }
                            });

                } else {
                    Toast.makeText(gwc_list.this, "输入框内容不能为空!",
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

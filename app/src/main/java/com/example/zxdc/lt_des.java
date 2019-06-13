package com.example.zxdc;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.zxdc.adapter.CommonAdapter;
import com.example.zxdc.adapter.MyBaseActivity;
import com.example.zxdc.adapter.ViewHolder;
import com.example.zxdc.pojo.jbxx;
import com.example.zxdc.pojo.ly;
import com.example.zxdc.util.LvHeightUtil;
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

public class lt_des extends MyBaseActivity {
    TextView uname, title, time, msg;
    jbxx b;
    ImageView head;
    Banner banner;
    List<ly> list;
    TextView lys;
    ListView listView;

    @Override
    protected void initUI() {
        setContentView(R.layout.lt_des);
        listView=findViewById(R.id.listView);
        uname = findViewById(R.id.uname);
        time = findViewById(R.id.time);
        msg = findViewById(R.id.msg);
        title = findViewById(R.id.title);
        b = (jbxx) getIntent().getExtras().getSerializable("o");
        uname.setText(b.getUname());
        time.setText(b.getTime());
        msg.setText(b.getMsg());
        title.setText(b.getTitle());
        banner = findViewById(R.id.banner);
        lys = findViewById(R.id.ly);
        lys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lyadd("");
            }
        });
        List<String> tlist = new ArrayList<>();
        String[] dd = b.getPics().split("#");
        for (int i = 0; i < dd.length; i++) {
            tlist.add(dd[i]);
        }
        banner.setImages(tlist);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
//                        startActivity(new Intent(getActivity(), xw_des.class).putExtra("id", list.get(position).getId()));
            }
        });
        banner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {

                com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(Url.url() + "upload/" + path.toString(), imageView);

            }
        });
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        //设置图片集合
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(tlist);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        getlist();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    public void getlist() {
        RequestParams ps = new RequestParams();
        ps.add("jid", b.getId());
        UserClient.get("ly/list", ps, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                list = JSON.parseArray(content, ly.class);
                listView.setAdapter(new CommonAdapter<ly>(lt_des.this, list, R.layout.ly_item) {
                    @Override
                    public void convert(ViewHolder helper, ly item) {
                        ImageView head = helper.getView(R.id.head);
                        ImageLoader.getInstance().displayImage(Url.url() + "upload/" + item.getHead(), head);
                        helper.setText(R.id.uname, item.getUname());
                        helper.setText(R.id.msg, item.getMsg());


                    }
                });
                LvHeightUtil.setListViewHeightBasedOnChildren(listView);
            }
        });
    }



    private void lyadd(final String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();

        View view = View.inflate(this, R.layout.ly_add, null);
        // dialog.setView(view);// 将自定义的布局文件设置给dialog
        dialog.setView(view, 0, 0, 0, 0);// 设置边距为0,保证在2.x的版本上运行没问题

        final EditText msg = (EditText) view.findViewById(R.id.msg);

        Button btnOK = (Button) view.findViewById(R.id.add);
        Button btnCancel = (Button) view.findViewById(R.id.qx);

        btnOK.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                String msgs = msg.getText().toString();
                // password!=null && !password.equals("")
                if (!TextUtils.isEmpty(msgs)) {
                    RequestParams ps = new RequestParams();
                    ps.add("uid", MyApplication.getApp().getU().getId());
                    ps.add("uname", MyApplication.getApp().getU().getNickname());
                    ps.add("msg", msgs);
                    ps.add("jid", b.getId());
                    ps.add("head", MyApplication.getApp().getU().getHead());
                    UserClient.post("ly/add", ps,
                            new AsyncHttpResponseHandler() {


                                @Override
                                public void onSuccess(String content) {
                                    super.onSuccess(content);
                                    MyToastUtil.ShowToast(lt_des.this, "成功");
                                    dialog.dismiss();
                                    getlist();
                                }
                            });

                } else {
                    Toast.makeText(lt_des.this, "输入框内容不能为空!",
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

package com.example.zxdc;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.zxdc.adapter.CommonAdapter;
import com.example.zxdc.adapter.MyBaseActivity;
import com.example.zxdc.adapter.ViewHolder;
import com.example.zxdc.pojo.CommonData;
import com.example.zxdc.util.FileUtil;
import com.example.zxdc.util.LvHeightUtil;
import com.example.zxdc.util.MyApplication;
import com.example.zxdc.util.MyToastUtil;
import com.example.zxdc.util.Url;
import com.example.zxdc.util.UserClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class lt_add extends MyBaseActivity {
    TextView add;
    EditText title, msg;
    Button addpic;
    ListView listView;
    List<String> list = new ArrayList<>();
    String names = "", head_url = "";



    @Override
    protected void initUI() {
        setContentView(R.layout.lt_add);

        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams ps = new RequestParams();
                ps.add("uid", MyApplication.getApp().getU().getId());
                ps.add("uname", MyApplication.getApp().getU().getNickname());
                ps.add("title", title.getText().toString());
                ps.add("msg", msg.getText().toString());
                String pics = "";
                for (int i = 0; i < list.size(); i++) {
                    pics = pics + list.get(i) + "#";
                }
                ps.add("pics", pics);
                ps.add("head", MyApplication.getApp().getU().getHead());
                ps.add("state", "未审核");
                UserClient.get("jbxx/add", ps, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String content) {
                        super.onSuccess(content);
                        MyToastUtil.ShowToast(lt_add.this, "成功");
                    }
                });
            }
        });


        title = findViewById(R.id.title);
        msg = findViewById(R.id.msg);
        listView = findViewById(R.id.listView);
        addpic = findViewById(R.id.addpic);
        addpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(lt_add.this)
                        .setTitle("选择照片")
                        .setNegativeButton("拍照",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        SimpleDateFormat df = new SimpleDateFormat(
                                                "MMddHHmmssSSSS");
                                        names = df.format(new Date());
                                        Intent intent = new Intent(
                                                MediaStore.ACTION_IMAGE_CAPTURE);
                                        intent.putExtra(
                                                MediaStore.EXTRA_OUTPUT,
                                                Uri.fromFile(new File(
                                                        Environment
                                                                .getExternalStorageDirectory(),
                                                        names + ".jpg")));
                                        startActivityForResult(intent, 2);
                                    }
                                })
                        .setNeutralButton("相册",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Intent intent = new Intent(
                                                Intent.ACTION_PICK, null);
                                        intent.setDataAndType(
                                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                                "image/*");
                                        startActivityForResult(intent, 1);
                                    }
                                }).show();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (data != null) {
                    Uri uri = data.getData();
                    String picPath = FileUtil.getRealPathFromUri(lt_add.this, uri);
                    RequestParams ps = new RequestParams();
                    try {
                        ps.put("head", new File(picPath));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    UserClient.post("common/mobileupload", ps,
                            new AsyncHttpResponseHandler() {

                                @Override
                                public void onSuccess(String content) {
                                    super.onSuccess(content);
                                    CommonData msg = JSON.parseObject(content,
                                            CommonData.class);
                                    if (msg.getCode().equals("200")) {
                                        head_url = msg.getData();
                                        list.add(head_url);
                                        initPic();
                                    } else {
                                        MyToastUtil.ShowToast(lt_add.this, msg.getMsg());
                                    }
                                }
                            });
                }
                break;

            case 2:
                try {
                    if (resultCode != 0) {
                        Uri uris = Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), names + ".jpg"));
                        String picPath = FileUtil.getRealPathFromUri(lt_add.this, uris);
                        RequestParams ps = new RequestParams();
                        try {
                            ps.put("head", new File(picPath));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        UserClient.post("common/mobileupload", ps,
                                new AsyncHttpResponseHandler() {


                                    @Override
                                    public void onSuccess(String content) {
                                        super.onSuccess(content);
                                        CommonData msg = JSON.parseObject(content,
                                                CommonData.class);
                                        if (msg.getCode().equals("200")) {
                                            head_url = msg.getData();
                                            list.add(head_url);
                                            initPic();
                                        } else {
                                            MyToastUtil.ShowToast(lt_add.this, msg.getMsg());
                                        }
                                    }
                                });
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }

                break;

            default:
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void initPic() {
        listView.setAdapter(new CommonAdapter<String>(lt_add.this, list, R.layout.pic_item) {
            @Override
            public void convert(ViewHolder helper, String item) {
                ImageView pic = helper.getView(R.id.pic);
                ImageLoader.getInstance().displayImage(Url.url() + "upload/" + item, pic);
            }
        });
        LvHeightUtil.setListViewHeightBasedOnChildren(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(lt_add.this).setTitle("删除").setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        list.remove(position);
                        listView.setAdapter(new CommonAdapter<String>(lt_add.this, list, R.layout.pic_item) {
                            @Override
                            public void convert(ViewHolder helper, String item) {
                                ImageView pic = helper.getView(R.id.pic);
                                ImageLoader.getInstance().displayImage(Url.url() + "upload/" + item, pic);
                            }
                        });
                    }
                }).setNegativeButton("取消", null).show();
            }
        });
    }
}

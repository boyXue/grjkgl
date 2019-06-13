package com.example.zxdc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.zxdc.pojo.sp;
import com.example.zxdc.util.Url;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

public class bofang extends AppCompatActivity {

    NiceVideoPlayer mNiceVideoPlayer;
    sp s;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bofang);


        s = (sp) getIntent().getExtras().getSerializable("o");

        mNiceVideoPlayer = findViewById(R.id.shipin);
        mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK);  //NiceVideoPlayer.IJKPlayer    or   NiceVideoPlayer.TYPE_NATIVE
        mNiceVideoPlayer.setUp(Url.url() + "upload/" + s.getUrl(), null);  //设置播放地址
        TxVideoPlayerController controller = new TxVideoPlayerController(bofang.this);
        controller.setTitle("测试小窗播放视频标题");  // controller为蒙版层，用于设置视频标题
        controller.setLenght(98000);  //时间以mm为单位计算
        Glide.with(bofang.this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558085253162&di=7c116e41594f5c3fce862d74ea539b42&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20161104%2Fa6063dc8620d4b7590136122d8fca653_th.jpg")
                .into(controller.imageView()); //加载视频图片到蒙版上
        mNiceVideoPlayer.setController(controller);



    }


    @Override
    protected void onStop() {
        super.onStop();
        // 在 onStop 时释放掉播放器
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }
    @Override
    public void onBackPressed() {
        // 在全屏或者小窗口时按返回键要先退出全屏或小窗口，
        // 所以在 Activity 中 onBackPress 要交给 NiceVideoPlayer 先处理。
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }


}

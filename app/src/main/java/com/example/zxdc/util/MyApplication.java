package com.example.zxdc.util;

import android.app.Application;
import android.content.Context;

import com.example.zxdc.R;
import com.example.zxdc.pojo.User;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

public class MyApplication extends Application {
    public static Context applicationContext;
    public User u;

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }

    private static MyApplication app;

    public static MyApplication getApp() {
        if (app == null) {
            app = new MyApplication();
        }
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;

        @SuppressWarnings("deprecation")
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.ic_empty)
                .showImageForEmptyUri(R.mipmap.ic_empty)
                .showImageOnFail(R.mipmap.ic_error).cacheInMemory(true)
                .cacheOnDisc(true).build();
        File cacheDir = StorageUtils.getOwnCacheDirectory(this,
                "imageloader/Cache");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext()).defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
        ///////////////////////////////////////环信聊天/////////////////////////////////////
     /*   EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
//初始化
        EMClient.getInstance().init(getApplicationContext(), options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
// 默认添加好友时，是不需要验证的，改成需要验证
        EaseUI.getInstance().init(getApplicationContext(), null);*/


    }

}

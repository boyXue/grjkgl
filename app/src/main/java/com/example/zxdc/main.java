package com.example.zxdc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;


import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.base.BaseBottomTabActivity;

public class main extends BaseBottomTabActivity {


    private frag_sp shouye;

    /**启动这个Activity的Intent
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
        initData();
        initEvent();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void initView() {
        super.initView();
        exitAnim = R.anim.bottom_push_out;
        shouye = frag_sp.createInstance();
    }

    @Override
    protected void selectTab(int position) {

    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    protected int[] getTabClickIds() {
        return new int[]{R.id.llBottomTabTab0,R.id.llBottomTabTab4,R.id.llBottomTabTab5, R.id.llBottomTabTab1, R.id.llBottomTabTab2,R.id.llBottomTabTab3};
    }

    @Override
    protected int[][] getTabSelectIds() {
        return new int[][]{
                new int[]{R.id.ivBottomTabTab0,R.id.ivBottomTabTab4,R.id.ivBottomTabTab5, R.id.ivBottomTabTab1, R.id.ivBottomTabTab2, R.id.ivBottomTabTab3},//顶部图标
                new int[]{R.id.tvBottomTabTab0,R.id.tvBottomTabTab4,R.id.tvBottomTabTab5, R.id.tvBottomTabTab1, R.id.tvBottomTabTab2, R.id.tvBottomTabTab3}//底部文字
        };
    }

    @Override
    public int getFragmentContainerResId() {
        return R.id.flMainTabFragmentContainer;
    }

    @Override
    protected Fragment getFragment(int position) {
        switch (position) {
            case 0:
                return shouye;
            case 1:
                return frag_sy.createInstance();
            case 2:
                return frag_zs.createInstance();
            case 3:
                return frag_lt.createInstance();
            case 4:
                return frag_gwc.createInstance();
            case 5:
                return frag_wd.createInstance();
            default:
                return frag_sp.createInstance();
        }
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }

    //双击手机返回键退出<<<<<<<<<<<<<<<<<<<<<
    private long firstTime = 0;//第一次返回按钮计时
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch(keyCode){
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if(secondTime - firstTime > 2000){
                    showShortToast("再按一次退出");
                    firstTime = secondTime;
                } else {//完全退出
                    moveTaskToBack(false);//应用退到后台
                    System.exit(0);
                }
                return true;
        }

        return super.onKeyUp(keyCode, event);
    }
}

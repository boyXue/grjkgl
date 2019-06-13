package com.example.zxdc.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.Stack;

/**
 * BaseActivity
 * 
 * @author RaphetS
 * 
 */
public abstract class MyBaseActivity extends FragmentActivity {

    private static Stack<Activity> listActivity = new Stack<Activity>();

    private Toast mToast;

    private long lastClickTime;
    public final static int CLICK_TIME = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        listActivity.push(this);
        initUI();
        initData();
        initListener();

    }

    protected abstract void initUI();

    protected abstract void initData();

    protected abstract void initListener();

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    protected void saveInstanceState(Bundle outState) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public void onBack(View v) {
        finish();

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (listActivity.contains(this)) {
            listActivity.remove(this);
        }

    }

    public void openActivity(Class<?> targetActivityClass) {
        openActivity(targetActivityClass, null);
    }

    public void openActivity(Class<?> targetActivityClass, Bundle bundle) {
        Intent intent = new Intent(this, targetActivityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void openActivityAndCloseThis(Class<?> targetActivityClass) {
        openActivity(targetActivityClass);
        this.finish();
    }

    public void openActivityAndCloseThis(Class<?> targetActivityClass, Bundle bundle) {
        openActivity(targetActivityClass, bundle);
        this.finish();
    }

    /***************************************************************/
    public boolean verifyClickTime() {
        if (System.currentTimeMillis() - lastClickTime <= CLICK_TIME) {
            return false;
        }
        lastClickTime = System.currentTimeMillis();
        return true;
    }

    public void closeInputMethod() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     *
     * @param mRid
     * @return
     */
    public String getStringMethod(int mRid) {
        return this.getResources().getString(mRid);
    }

    /**
     *
     * @param mRid
     * @return
     */
    protected int getDemonIntegerMethod(int mRid) {
        return (int) this.getResources().getDimension(mRid);
    }

    /**
     */
    protected static void finishAll() {
        int len = listActivity.size();
        for (int i = 0; i < len; i++) {
            Activity activity = listActivity.pop();
            activity.finish();
        }
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

//        if (KeyEvent.KEYCODE_BACK == keyCode) {
//            // �ж��Ƿ�������֮�����������ؼ������˳��������˳�
//            if (System.currentTimeMillis() - exitTime > 2000) {
//                Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();
//                // ��ϵͳ��ǰ��ʱ�丳ֵ��exitTime
//                exitTime = System.currentTimeMillis();
//            } else {
//                finishAll();
//            }
//            return true;
//        }
        return super.onKeyDown(keyCode, event);
    }

    /*
     *
     */
    private Fragment currentFragment;

    public void fragmentReplace(int target, Fragment toFragment, boolean backStack) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        String toClassName = toFragment.getClass().getSimpleName();
        if (manager.findFragmentByTag(toClassName) == null) {
            transaction.replace(target, toFragment, toClassName);
            if (backStack) {
                transaction.addToBackStack(toClassName);
            }
            transaction.commit();
        }
    }

    public void smartFragmentReplace(int target, Fragment toFragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        String toClassName = toFragment.getClass().getSimpleName();
        if (manager.findFragmentByTag(toClassName) != null) {
            transaction.show(toFragment);
        } else {
            transaction.add(target, toFragment, toClassName);
        }
        transaction.commit();
        currentFragment = toFragment;
    }

}
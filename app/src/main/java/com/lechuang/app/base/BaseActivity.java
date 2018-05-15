package com.lechuang.app.base;

import android.content.Context;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.lechuang.app.App;
import com.lechuang.app.base.lisenters.IBaseView;
import com.lechuang.app.events.NetStateEvent;
import com.lechuang.app.lisenters.INetStateLisenter;
import com.lechuang.app.receiver.NetWorkChangReceiver;
import com.lechuang.app.utils.StatusBarUtil;
import com.lechuang.app.utils.Utils;

import org.greenrobot.eventbus.EventBus;


import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity implements INetStateLisenter, IBaseView {
    protected Context mContext;

    private NetWorkChangReceiver mNetWorkChangReceiver;

    private Unbinder mUnbind;
    protected BasePresenter mBasePresenter;
    protected boolean mNetState = false; //网络的连接状态


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.base_activity_main);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);// 使得音量键控制媒体声音
        mContext = this;
        //管理activity
        App.getInstance().addActivity(this);
        mBasePresenter = onCreatePresenter(savedInstanceState);
    }


    //没有presenter可以不用重写该方法
    protected BasePresenter onCreatePresenter(@Nullable Bundle savedInstanceState){
        return new BasePresenter(this);
    }

    @Override
    public void setContentView(View view) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        mBasePresenter.setContentView(view);
        addContentView(view, lp);
        StatusBarUtil.setTranslucentForImageViewInFragment(BaseActivity.this, 0,null);
        mUnbind = ButterKnife.bind(this, view);
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = mBasePresenter.setContentView(layoutResID);
        setContentView(view);
        //初始化数据
        initCreateContent();
    }

    public void setContentView(int layoutResID,boolean addRefresh) {
        View view = mBasePresenter.setContentView(layoutResID,addRefresh);
        setContentView(view);
        //初始化数据
        initCreateContent();
    }



    /**
     * 初始化数据
     */
    public void initCreateContent() {
        mBasePresenter.initCreateContent();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mBasePresenter.onResume();
        //实现类需要的时候调用
//        setNetReceiver();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mBasePresenter.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBasePresenter.onPause();
        //实现类需要的时候调用
//        removeNetReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBasePresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBasePresenter.onDestroy();
        EventBus.getDefault().unregister(this);
        if (mUnbind != null) {
            mUnbind.unbind();
        }
        App.getInstance().removeActivity(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mBasePresenter.onKeyDown(super.onKeyDown(keyCode, event),keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus() && event.getAction() == MotionEvent.ACTION_UP) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }


    public void toast(String message) {
        mBasePresenter.toast(message);
    }

    public void toast(@StringRes int message) {
        mBasePresenter.toast(message);
    }

    protected void setNetReceiver() {
        if (mNetWorkChangReceiver == null) {
            mNetWorkChangReceiver = new NetWorkChangReceiver(this);
        }

        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetWorkChangReceiver, filter);
    }

    protected void removeNetReceiver() {
        //网络连接监听广播
        if (mNetWorkChangReceiver != null) {
            unregisterReceiver(mNetWorkChangReceiver);
        }
    }

    @Override
    public void netStateLisenter(NetStateEvent netStateEvent) {
        mBasePresenter.netStateLisenter(netStateEvent);
        mNetState = netStateEvent.netState;
        if (mNetState) {
            Utils.showToast("您正在使用" + netStateEvent.connType);
        } else {
            Utils.showToast("网络连接断开！");
        }
    }

    @Override
    public Context getContext() {
        return BaseActivity.this;
    }

    @Override
    public void finishA() {
        finish();
    }
}

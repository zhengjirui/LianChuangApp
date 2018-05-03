package com.lechuang.app.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lechuang.app.App;
import com.lechuang.app.R;
import com.lechuang.app.base.lisenters.IBaseView;
import com.lechuang.app.events.NetStateEvent;
import com.lechuang.app.lisenters.INetStateLisenter;
import com.lechuang.app.receiver.NetWorkChangReceiver;
import com.lechuang.app.utils.Utils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity implements INetStateLisenter,IBaseView {
    protected Context mContext;

    protected RelativeLayout mHeadLayout;
    protected Button mBtnLeft;
    protected Button mBtnRight;
    protected TextView mTitle;
    protected TextView mHeadRightText;
    private Drawable mBtnBackDrawable;
    private Dialog mDialog;
    private NetWorkChangReceiver mNetWorkChangReceiver;

    private Unbinder mUnbind;
    private BasePresenter mBasePresenter;
    protected boolean mPremissiomState = false;//开启权限状态
    protected boolean mNetState = false; //网络的连接状态


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.base_activity_main);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);// 使得音量键控制媒体声音
        mContext = this;
        //管理activity
        App.getInstance().addActivity(this);
        mBasePresenter = new BasePresenter(this);


    }

    @Override
    public void setContentView(View view) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        // 初始化公共头部
        mHeadLayout = (RelativeLayout) view.findViewById(R.id.layout_base_head);
        mHeadRightText = (TextView) view.findViewById(R.id.text_right);
        mBtnLeft = (Button) view.findViewById(R.id.btn_left);
        mBtnRight = (Button) view.findViewById(R.id.btn_right);
        mTitle = (TextView) view.findViewById(R.id.tv_title);
        mBtnBackDrawable = getResources().getDrawable(R.drawable.header_back_icon);
        mBtnBackDrawable.setBounds(0, 0, mBtnBackDrawable.getMinimumWidth(),
                mBtnBackDrawable.getMinimumHeight());
        addContentView(view, lp);
        mUnbind = ButterKnife.bind(this,view);

    }

    @Override
    public void setContentView(int layoutResID) {
        mBasePresenter.setContentView(layoutResID);
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        setContentView(view);
        //设置加载等待框
        setLoadView();

        //初始化数据
        initCreateContent();
    }

    private void setLoadView() {
        // 获取Dialog布局
        if (mDialog == null) {
            mDialog = new Dialog(this, R.style.AlertDialogStyle);
            mDialog.setCanceledOnTouchOutside(false);
            View view = LayoutInflater.from(this).inflate(
                    R.layout.view_loading_alertdialog, null);
            mDialog.setContentView(view);
            mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    //拦截返回键的操作，点击返回键返回false会消失当前窗口，返回true待鉴定
//                    HttpManger.getHttpInstance().cancelAllRequest();
                    return false;
                }
            });
        }
    }


    /**
     * 初始化数据
     */
    public void initCreateContent() {
    }

    /**
     * 设置头部是否可见
     *
     * @param visibility
     */
    public void setHeadVisibility(int visibility) {
        mHeadLayout.setVisibility(visibility);
    }

    /**
     * 设置左边是否可见
     *
     * @param visibility
     */
    public void setHeadLeftButtonVisibility(int visibility) {
        mBtnLeft.setVisibility(visibility);
    }

    /**
     * 设置右边是否可见
     *
     * @param visibility
     */
    public void setHeadRightButtonVisibility(int visibility) {
        mBtnRight.setVisibility(visibility);
    }

    /**
     * 设置标题
     */
    public void setTitle(int titleId) {
        setTitle(getString(titleId), false);
    }

    /**
     * 设置标题
     */
    public void setTitle(int titleId, boolean flag) {
        setTitle(getString(titleId), flag);
    }

    /**
     * 设置标题
     */
    public void setTitle(String title) {
        setTitle(title, false);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title, boolean flag) {
        mTitle.setText(title);
        if (flag) {
            mBtnLeft.setCompoundDrawables(null, null, null, null);
        } else {
            mBtnLeft.setCompoundDrawables(mBtnBackDrawable, null, null, null);
        }
    }

    /**
     * 点击左按钮
     */
    public void onHeadLeftButtonClick(View v) {
        finish();
    }

    /**
     * 点击右按钮
     */
    public void onHeadRightButtonClick(View v) {

    }

    public Button getHeadLeftButton() {
        return mBtnLeft;
    }

    public void setHeadLeftButton(Button leftButton) {
        this.mBtnLeft = leftButton;
    }

    public Button getHeadRightButton() {
        return mBtnRight;
    }

    public void setHeadRightButton(Button rightButton) {
        this.mBtnRight = rightButton;
    }

    public Drawable getHeadBackButtonDrawable() {
        return mBtnBackDrawable;
    }

    public void setBackButtonDrawable(Drawable backButtonDrawable) {
        this.mBtnBackDrawable = backButtonDrawable;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //实现类需要的时候调用
//        setNetReceiver();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //实现类需要的时候调用
//        removeNetReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if(mUnbind != null){
            mUnbind.unbind();
        }
        App.getInstance().removeActivity(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus() && event.getAction() == MotionEvent.ACTION_UP) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

    /**
     * 显示加载框
     */
    public void show() {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
    }

    /**
     * 隐藏加载框
     */
    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    /**
     * 兼容Android 8.0权限申请
     * @param permissions   申请的权限组  https://github.com/yanzhenjie/AndPermission
     * @return
     */
    protected boolean openPremission(String... permissions){
        AndPermission.with(this)
                .permission(permissions)
//                .rationale(mRationale)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        mPremissiomState = true;
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        mPremissiomState = false;
                    }
                })
                .start();
        return mPremissiomState;
    }

    public void toast(String message) {
        Utils.showToast(message);
    }

    protected void setNetReceiver(){
        if (mNetWorkChangReceiver == null){
            mNetWorkChangReceiver = new NetWorkChangReceiver(this);
        }

        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetWorkChangReceiver, filter);
    }

    protected void removeNetReceiver(){
        //网络连接监听广播
        if(mNetWorkChangReceiver != null){
            unregisterReceiver(mNetWorkChangReceiver);
        }
    }

    @Override
    public void netStateLisenter(NetStateEvent netStateEvent) {
        mNetState = netStateEvent.netState;
        if(mNetState){
            Utils.showToast("您正在使用" + netStateEvent.connType);
        }else {
            Utils.showToast("网络连接断开！");
        }
    }
}

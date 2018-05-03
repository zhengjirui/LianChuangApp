package com.lechuang.app.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lechuang.app.R;
import com.lechuang.app.base.lisenters.IBasePresenter;
import com.lechuang.app.base.lisenters.IBaseView;
import com.lechuang.app.events.NetStateEvent;
import com.lechuang.app.utils.Utils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.List;

import butterknife.ButterKnife;

/**
 * @author: LGH
 * @since: 2018/4/26
 * @describe:
 */

public class BasePresenter<V extends IBaseView> implements IBasePresenter{

    protected V mIBaseView;
    protected Context mContext;
    protected RelativeLayout mHeadLayout;
    protected Button mBtnLeft;
    protected Button mBtnRight;
    protected TextView mTitle;
    protected TextView mHeadRightText;
    private Drawable mBtnBackDrawable;
    private Dialog mDialog;

    protected boolean mPremissiomState = false;//开启权限状态
    protected boolean mNetState = false; //网络的连接状态

    public BasePresenter(V mIBaseView) {
        this.mIBaseView = mIBaseView;
        this.mContext = mIBaseView.getContext();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void setContentView(View view) {
        // 初始化公共头部
        mHeadLayout = (RelativeLayout) view.findViewById(R.id.layout_base_head);
        mHeadRightText = (TextView) view.findViewById(R.id.text_right);
        mBtnLeft = (Button) view.findViewById(R.id.btn_left);
        mBtnRight = (Button) view.findViewById(R.id.btn_right);
        mTitle = (TextView) view.findViewById(R.id.tv_title);
        mBtnBackDrawable = mContext.getResources().getDrawable(R.drawable.header_back_icon);
        mBtnBackDrawable.setBounds(0, 0, mBtnBackDrawable.getMinimumWidth(),
                mBtnBackDrawable.getMinimumHeight());
    }

    @Override
    public View setContentView(int layoutResID) {
        View view = LayoutInflater.from(mIBaseView.getContext()).inflate(layoutResID, null);
        return view;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void initCreateContent() {

    }


    /**
     * 兼容Android 8.0权限申请
     *
     * @param permissions 申请的权限组  https://github.com/yanzhenjie/AndPermission
     * @return
     */
    @Override
    public boolean openPremission(String... permissions) {
        AndPermission.with(mContext)
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

    @Override
    public void toast(String message) {
        Utils.showToast(message);
    }

    @Override
    public void netStateLisenter(NetStateEvent netStateEvent) {

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
        setTitle(mContext.getString(titleId), false);
    }

    /**
     * 设置标题
     */
    public void setTitle(int titleId, boolean flag) {
        setTitle(mContext.getString(titleId), flag);
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
//        finish();  todo
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

    private void setLoadView() {
        // 获取Dialog布局
        if (mDialog == null) {
            mDialog = new Dialog(mContext, R.style.AlertDialogStyle);
            mDialog.setCanceledOnTouchOutside(false);
            View view = LayoutInflater.from(mContext).inflate(
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
}

package com.lechuang.app.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lechuang.app.R;
import com.lechuang.app.base.lisenters.IBasePresenter;
import com.lechuang.app.base.lisenters.IBaseView;
import com.lechuang.app.events.NetStateEvent;
import com.lechuang.app.lisenters.ISmartRefreshLisenter;
import com.lechuang.app.model.LocalSession;
import com.lechuang.app.utils.Utils;
import com.lechuang.app.view.TransChangeScrollView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: LGH
 * @since: 2018/4/26
 * @describe:
 */

public class BasePresenter<V extends IBaseView> implements IBasePresenter{

    protected V mIBaseView;
    protected Context mContext;
    private Dialog mDialog;
    protected LocalSession mSession;//用户信息bean

    protected boolean mPremissiomState = false;//开启权限状态
    protected boolean mNetState = false; //网络的连接状态
    protected View mLayoutStatus;//头部添加的沉浸式状态栏
    protected SmartRefreshLayout mSmartRefresh;//总体的刷新空间
    protected FrameLayout mScrollContent;//view装在的内容
    private ClassicsHeader mRefreshHeader;//头部刷新的控件
    private ClassicsFooter mRefreshFooter;//底部加载的控件
    private ISmartRefreshLisenter mISmartRefreshLisenter;
    protected boolean mInited = false;//是否已经初始化
    protected boolean mIsVisibleToUser = false;//是否显示给用户
    protected Bundle mBundleF;//fragment之间的传值
    private FragmentManager mChildFragmentManager;
    public Unbinder mUnbinder;


    public BasePresenter(V mIBaseView) {
        this.mIBaseView = mIBaseView;
        this.mContext = mIBaseView.getContext();
        this.mSession = LocalSession.get(mContext);
        setLoadView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootGlobalView =  inflater.inflate(R.layout.layout_global_view,null);
        mLayoutStatus = rootGlobalView.findViewById(R.id.layout_status);
        mSmartRefresh = rootGlobalView.findViewById(R.id.smart_refresh);
        mScrollContent = rootGlobalView.findViewById(R.id.scroll_content_view);
        mRefreshHeader = rootGlobalView.findViewById(R.id.refresh_header);
        mRefreshFooter = rootGlobalView.findViewById(R.id.refresh_footer);
        addLayoutView(inflater, mScrollContent,savedInstanceState);
        mUnbinder = ButterKnife.bind(this, mScrollContent);
        return rootGlobalView;
    }

    public void setArguments(Bundle mBundleF){
        this.mBundleF = mBundleF;
    }

    public Bundle getArguments(){
        return this.mBundleF;
    }

    public void setChildFragmentManager(FragmentManager childFragmentManager){
        this.mChildFragmentManager = childFragmentManager;
    }

    public FragmentManager getChildFragmentManager(){
        return this.mChildFragmentManager;
    }


    @Override
    public void addLayoutView(LayoutInflater inflater,FrameLayout scrollContentView, Bundle savedInstanceState) {
        initData();
    }

    /**
     * 在fragment里面初始化一些内容
     */
    private void initData() {
        setOnRefreshLisenter();
        setEnableRefresh(false);
        setEnableLoadMore(false);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void setContentView(View view) {
        //没有实现任何功能
    }

    View mView;//activity的布局
    @Override
    public View setContentView(int layoutResID) {
        mView = LayoutInflater.from(mContext).inflate(layoutResID,mScrollContent);
        return mView;
    }

    public View getActivityView(){
        return this.mView;
    }

    @Override
    public View setContentView(int layoutResID,boolean addRefresh) {
        if(addRefresh){
            View rootGlobalView =  LayoutInflater.from(mContext).inflate(R.layout.layout_global_view,null);
            mLayoutStatus = rootGlobalView.findViewById(R.id.layout_status);
            mSmartRefresh = rootGlobalView.findViewById(R.id.smart_refresh);
            mRefreshHeader = rootGlobalView.findViewById(R.id.refresh_header);
            mRefreshFooter = rootGlobalView.findViewById(R.id.refresh_footer);
            mScrollContent = rootGlobalView.findViewById(R.id.scroll_content_view);
            setContentView(layoutResID);
            initData();
            return rootGlobalView;
        }else {
            return setContentView(layoutResID);
        }

    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.mIsVisibleToUser = isVisibleToUser;
    }

    public void onResume(boolean isVisibleToUser) {
        this.mIsVisibleToUser = isVisibleToUser;
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onPause() {
//        this.mIsVisibleToUser = false;
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        if(mUnbinder != null){
            mUnbinder.unbind();
        }
    }

    public void finish(){
        mIBaseView.finishA();
    }

    @Override
    public boolean onKeyDown(boolean keyBoolean,int keyCode, KeyEvent event) {
        return keyBoolean;
    }

    @Override
    public void initCreateContent() {
    }

    @Override
    public void setEnableRefresh(boolean enableRefresh) {
        mSmartRefresh.setEnableRefresh(enableRefresh);
    }

    private void setOnRefreshLisenter(){
        if(mSmartRefresh != null){
            setOnSmartRefreshLisenter(this);
            mSmartRefresh.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    onRefreshListener();
                }
            });
            mSmartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    onLoadMoreListener();
                }
            });
        }
    }

    /**
     * 设置加载刷新的监听。在initCreateContent已经设置，也可以在子类中重新设置（建议不要重写设置）
     * @param iSmartRefreshLisenter
     */
    public void setOnSmartRefreshLisenter(ISmartRefreshLisenter iSmartRefreshLisenter){
        this.mISmartRefreshLisenter = iSmartRefreshLisenter;
    }

    /**
     * 下拉刷新监听
     */
    @Override
    public void onRefreshListener() {

    }

    /**
     * 上拉加载监听
     */
    @Override
    public void onLoadMoreListener() {

    }

    @Override
    public void setEnableLoadMore(boolean enableLoadMore) {
        mSmartRefresh.setEnableLoadMore(enableLoadMore);
    }

    @Override
    public void finishRefresh() {
        finishRefresh(0);
    }

    @Override
    public void finishLoadMore() {
        finishLoadMore(0);
    }

    @Override
    public void finishRefresh(int delayTime) {
        mSmartRefresh.finishRefresh(delayTime);
    }

    @Override
    public void finishLoadMore(int delayTime) {
        mSmartRefresh.finishLoadMore(delayTime);
    }

    @Override
    public void finishRefresh(boolean refreshState) {
        mSmartRefresh.finishRefresh(refreshState);
    }

    @Override
    public void finishLoadMore(boolean refreshState) {
        mSmartRefresh.finishLoadMore(refreshState);
    }

    @Override
    public void finishLoadMoreWithNoMoreData() {
        mSmartRefresh.finishLoadMoreWithNoMoreData();
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
    public void toast(@StringRes int message) {
        Utils.showToast(message);
    }

    @Override
    public void netStateLisenter(NetStateEvent netStateEvent) {

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

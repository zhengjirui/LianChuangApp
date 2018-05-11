package com.lechuang.app.base.lisenters;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.lechuang.app.events.NetStateEvent;
import com.lechuang.app.lisenters.ISmartRefreshLisenter;

/**
 * @author: LGH
 * @since: 2018/5/3
 * @describe:
 */

public interface IBasePresenter extends ISmartRefreshLisenter{


    /**
     * activity生命周期的绑定
     */

    void onCreate(Bundle savedInstanceState);


    /**
     * 用于给fragment添加根布局使用
     * @param scrollContentView    跟布局的内容view
     * @return
     */
    void addLayoutView(LayoutInflater inflater, FrameLayout scrollContentView, Bundle savedInstanceState );
    /**
     * fragment的生命周期
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState);

    /**
     * fragment的懒加载回调
     * @param isVisibleToUser
     */
    void setUserVisibleHint(boolean isVisibleToUser);

    void onStart();

    void setContentView(View view);

    View setContentView(@LayoutRes int layoutResID);
    /**
     * 为activity添加是否带刷新的layout，
     * 如果activity中嵌套fragment带有刷新功能，addRefresh可以设置为false。因为fragment有添加，这里避免过度绘制布局
     * @param layoutResID
     * @param addRefresh
     * @return
     */
    View setContentView(@LayoutRes int layoutResID,boolean addRefresh);

    void onResume();

    /**
     * fragment的获取焦点
     * @param isVisibleToUser
     */
    void onResume(boolean isVisibleToUser);

    void onRestart();

    void onPause();

    void onStop();

    void onDestroy();

    /**
     * 设置是否可以下拉刷新
     * @param enableRefresh
     */
    void setEnableRefresh(boolean enableRefresh);

    /**
     * 设置是否可以上拉加载更多
     * @param enableLoadMore
     */
    void setEnableLoadMore(boolean enableLoadMore);


    /**
     * 下拉刷新
     */
    void finishRefresh();

    /**
     * 上拉加载
     */
    void finishLoadMore();

    /**
     * 设置下拉刷新延迟
     * @param delayTime
     */
    void finishRefresh(int  delayTime);

    /**
     * 设置上拉加载延迟
     * @param delayTime
     */
    void finishLoadMore(int delayTime);

    /**
     * 刷新失败
     * @param refreshState
     */
    void finishRefresh(boolean refreshState);

    /**
     * 刷新成功
     * @param refreshState
     */
    void finishLoadMore(boolean refreshState);

    /**
     * 上拉加载没有更多数据
     */
    void finishLoadMoreWithNoMoreData();

    /**
     * 自定义回调方法的绑定
     */

    void initCreateContent();


    /**
     * 打开权限
     *
     * @param permissions 权限组
     * @return
     */
    boolean openPremission(String... permissions);

    /**
     * 弹出提示
     *
     * @param message
     */
    void toast(String message);

    /**
     * 弹出提示，传递资源文件
     * @param message
     */
    void toast(@StringRes int message);

    /**
     * 网络的连接监听
     *
     * @param netStateEvent
     */
    void netStateLisenter(NetStateEvent netStateEvent);

}

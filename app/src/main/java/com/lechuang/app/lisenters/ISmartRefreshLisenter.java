package com.lechuang.app.lisenters;

/**
 * Created by cmd on 2018/5/5.
 */

public interface ISmartRefreshLisenter {

    /**
     * 下拉刷新监听
     */
    void onRefreshListener();

    /**
     * 上拉加载监听
     */
    void onLoadMoreListener();

}

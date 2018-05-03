package com.lechuang.app.base.lisenters;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.RawRes;
import android.view.View;

import com.lechuang.app.events.NetStateEvent;

/**
 * @author: LGH
 * @since: 2018/5/3
 * @describe:
 */

public interface IBasePresenter {


    /**
     * activity生命周期的绑定
     */

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void setContentView(View view);

    void setContentView(@LayoutRes int view);

    void onResume();

    void onRestart();

    void onPause();

    void onStop();

    void onDestroy();


    /**
     * 自定义回调方法的绑定
     */


    /**
     * 打开权限
     * @param permissions      权限组
     * @return
     */
    boolean openPremission(String... permissions);

    /**
     * 弹出提示
     * @param message
     */
    void toast(String message);

    /**
     * 网络的连接监听
     * @param netStateEvent
     */
    void netStateLisenter(NetStateEvent netStateEvent);

}

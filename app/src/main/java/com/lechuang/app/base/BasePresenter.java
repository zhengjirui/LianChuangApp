package com.lechuang.app.base;

import android.os.Bundle;
import android.view.View;

import com.lechuang.app.base.lisenters.IBasePresenter;
import com.lechuang.app.base.lisenters.IBaseView;
import com.lechuang.app.events.NetStateEvent;

/**
 * @author: LGH
 * @since: 2018/4/26
 * @describe:
 */

public class BasePresenter implements IBasePresenter{

    protected IBaseView mIBaseView;

    public BasePresenter(IBaseView mIBaseView) {
        this.mIBaseView = mIBaseView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void setContentView(View view) {

    }

    @Override
    public void setContentView(int view) {

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
    public boolean openPremission(String... permissions) {
        return false;
    }

    @Override
    public void toast(String message) {

    }

    @Override
    public void netStateLisenter(NetStateEvent netStateEvent) {

    }
}

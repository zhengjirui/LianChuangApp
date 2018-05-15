package com.lechuang.app.presenter.activity;

import android.content.Intent;
import android.view.View;

import com.lechuang.app.base.BasePresenter;
import com.lechuang.app.base.lisenters.IBaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: LGH
 * @since: 2018/5/15
 * @describe:
 */

public class ProductDetailsShareA extends BasePresenter {

    private Intent mIntent;
    private Unbinder mUnbinder;

    public ProductDetailsShareA(IBaseView mIBaseView, Intent intent) {
        super(mIBaseView);
        this.mIntent = intent;
    }

    @Override
    public void initCreateContent() {
        super.initCreateContent();
        View activityView = getActivityView();
        mUnbinder = ButterKnife.bind(this, activityView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mUnbinder != null){
            mUnbinder.unbind();
        }

    }
}

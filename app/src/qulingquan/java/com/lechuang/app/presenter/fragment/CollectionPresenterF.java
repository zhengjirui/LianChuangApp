package com.lechuang.app.presenter.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.lechuang.app.R;
import com.lechuang.app.base.BasePresenter;
import com.lechuang.app.base.lisenters.IBaseView;

/**
 * @author: LGH
 * @since: 2018/5/3
 * @describe:
 */

public class CollectionPresenterF extends BasePresenter {


    public CollectionPresenterF(IBaseView mIBaseView) {
        super(mIBaseView);
    }


    @Override
    public void addLayoutView(LayoutInflater inflater, FrameLayout scrollContentView, Bundle savedInstanceState) {
        super.addLayoutView(inflater, scrollContentView, savedInstanceState);
        inflater.inflate(R.layout.fragment_fenlei,scrollContentView);
    }
}

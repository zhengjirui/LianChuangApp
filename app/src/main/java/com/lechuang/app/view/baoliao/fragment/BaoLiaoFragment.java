package com.lechuang.app.view.baoliao.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lechuang.app.base.BaseFragment;
import com.lechuang.app.base.BasePresenter;
import com.lechuang.app.presenter.fragment.BaoLiaoPresenterF;

/**
 * @author: LGH
 * @since: 2018/5/3
 * @describe:
 */

public class BaoLiaoFragment extends BaseFragment {


    @Override
    protected BasePresenter onCreatePresenter(@Nullable Bundle savedInstanceState) {
        return mBasePresenter = new BaoLiaoPresenterF(this);
    }
}

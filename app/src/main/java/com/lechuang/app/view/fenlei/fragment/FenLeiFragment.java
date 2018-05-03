package com.lechuang.app.view.fenlei.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lechuang.app.base.BaseFragment;
import com.lechuang.app.base.BasePresenter;
import com.lechuang.app.presenter.FenLeiPresenterF;

/**
 * @author: LGH
 * @since: 2018/5/3
 * @describe:
 */

public class FenLeiFragment  extends BaseFragment {


    @Override
    protected BasePresenter onCreatePresenter(@Nullable Bundle savedInstanceState) {
        return mBasePresenter = new FenLeiPresenterF(this);
    }
}

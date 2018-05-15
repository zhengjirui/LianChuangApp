package com.lechuang.app.view.zhuanmoney.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lechuang.app.base.BaseFragment;
import com.lechuang.app.base.BasePresenter;
import com.lechuang.app.presenter.fragment.HomePresenterF;
import com.lechuang.app.presenter.fragment.ZhuanMoneyPresenterF;
import com.lechuang.app.presenter.fragment.ZhuanMoneyProductPresenterF;

/**
 * @author: LGH
 * @since: 2018/5/3
 * @describe:
 */

public class ZhuanMoneyProductFragment extends BaseFragment {

    @Override
    protected BasePresenter onCreatePresenter(@Nullable Bundle savedInstanceState) {
        return mBasePresenter = new ZhuanMoneyProductPresenterF(this);
    }

}

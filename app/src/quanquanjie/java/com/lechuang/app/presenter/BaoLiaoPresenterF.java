package com.lechuang.app.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.lechuang.app.R;
import com.lechuang.app.base.BasePresenter;
import com.lechuang.app.base.lisenters.IBaseView;

/**
 * @author: LGH
 * @since: 2018/5/3
 * @describe:
 */

public class BaoLiaoPresenterF extends BasePresenter {


    public BaoLiaoPresenterF(IBaseView mIBaseView) {
        super(mIBaseView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.fragment_baoliao,null);
        return view;
    }
}

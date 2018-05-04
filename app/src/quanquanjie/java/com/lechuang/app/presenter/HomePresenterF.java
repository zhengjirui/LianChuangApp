package com.lechuang.app.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lechuang.app.R;
import com.lechuang.app.base.BasePresenter;
import com.lechuang.app.base.lisenters.IBaseView;

/**
 * @author: LGH
 * @since: 2018/5/3
 * @describe:
 */

public class HomePresenterF extends BasePresenter {


    private LinearLayout mLlHomeHeader;

    public HomePresenterF(IBaseView mIBaseView) {
        super(mIBaseView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        LinearLayout layoutHomeRoot = (LinearLayout) inflater.inflate(R.layout.fragment_home,null);
        mLlHomeHeader = (LinearLayout) inflater.inflate(R.layout.layout_home_header,layoutHomeRoot);

        return layoutHomeRoot;
    }
}

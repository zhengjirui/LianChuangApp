package com.lechuang.app.base;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lechuang.app.base.lisenters.IBaseView;


/**
 * @author: LGH
 * @since: 2018/5/3
 * @describe:
 */

public abstract class BaseFragment extends Fragment implements IBaseView{

    protected BasePresenter mBasePresenter;
    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
        mBasePresenter = onCreatePresenter(savedInstanceState);
    }

    protected abstract BasePresenter onCreatePresenter(@Nullable Bundle savedInstanceState);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return mBasePresenter.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mBasePresenter.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mBasePresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBasePresenter.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBasePresenter.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBasePresenter.onDestroy();
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void toast(String message) {
        mBasePresenter.toast(message);
    }
}

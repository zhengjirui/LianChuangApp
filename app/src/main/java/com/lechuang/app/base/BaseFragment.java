package com.lechuang.app.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lechuang.app.base.lisenters.IBaseView;
import com.lechuang.app.utils.Logger;


/**
 * @author: LGH
 * @since: 2018/5/3
 * @describe:
 */

public abstract class BaseFragment extends Fragment implements IBaseView{

    public BasePresenter mBasePresenter;
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
        View view = mBasePresenter.onCreateView(inflater, container, savedInstanceState);
        mBasePresenter.setArguments(getArguments());
        mBasePresenter.setChildFragmentManager(getChildFragmentManager());
        initCreateContent();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(mBasePresenter != null){
            mBasePresenter.setUserVisibleHint(getUserVisibleHint());
            Logger.e("tab_BaseFragment",isVisibleToUser + "");
        }
    }


    public void initCreateContent(){
        mBasePresenter.initCreateContent();

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
        if(mBasePresenter != null){
            mBasePresenter.setUserVisibleHint(getUserVisibleHint());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mBasePresenter.onPause();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mBasePresenter.onHiddenChanged(hidden);
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
        Logger.e("tag","onDestroy");
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void toast(String message) {
        mBasePresenter.toast(message);
    }

    @Override
    public void finishA() {

    }
}

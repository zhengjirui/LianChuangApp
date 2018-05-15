package com.lechuang.app.presenter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lechuang.app.App;
import com.lechuang.app.R;
import com.lechuang.app.base.BasePresenter;
import com.lechuang.app.base.lisenters.IBaseView;
import com.lechuang.app.model.ZhuanMoneyModels;
import com.lechuang.app.model.bean.BaseEventBean;
import com.lechuang.app.model.bean.GetBeanTabInfoList;
import com.lechuang.app.model.bean.GetBeanTablayout;
import com.lechuang.app.presenter.activity.adapter.ZhuanMoneyProductAdapter;
import com.lechuang.app.presenter.lisenters.IZhuanProductLisenter;
import com.lechuang.app.utils.Logger;
import com.lechuang.app.view.loadwebview.LoadWebViewActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * @author: LGH
 * @since: 2018/5/14
 * @describe:
 */

public class ZhuanMoneyProductPresenterF extends BasePresenter implements IZhuanProductLisenter{


    @BindView(R.id.rv_zhuan_money_product)
    RecyclerView mRvZhuanMoneyProduct;

    private ZhuanMoneyModels mZhuanMoneyModels;
    private int mClassTypeId = 1;
    private int page = 1;

    public ZhuanMoneyProductPresenterF(IBaseView mIBaseView) {
        super(mIBaseView);
    }

    @Override
    public void addLayoutView(LayoutInflater inflater, FrameLayout scrollContentView, Bundle savedInstanceState) {
        super.addLayoutView(inflater, scrollContentView, savedInstanceState);
        inflater.inflate(R.layout.fragment_zhuan_money_product, scrollContentView);
    }

    @Override
    public void initCreateContent() {
        super.initCreateContent();
        Bundle arguments = getArguments();
        mClassTypeId = arguments.getInt("classTypeId");

        mLayoutStatus.setVisibility(View.GONE);
        setEnableRefresh(true);
        setEnableLoadMore(true);
        initRvData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Logger.e("tab_ZhuanMoneyProductPresenterF", mClassTypeId + "");
        if (isVisibleToUser && !mInited) {
            loadData();
        } else {
            updateInfo();
            dismiss();
            mSmartRefresh.finishRefresh();
        }
    }

    @Override
    public void onResume(boolean isVisibleToUser) {
        super.onResume(isVisibleToUser);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }


    private void loadData() {

        if(!mInited){
            show();
        }
        mZhuanMoneyModels = new ZhuanMoneyModels(mContext,this);
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("classTypeId", mClassTypeId);
        mZhuanMoneyModels.getTabLayoutListData(map);
    }

    ZhuanMoneyProductAdapter mZhuanMoneyProductAdapter;

    private void initRvData() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
//        mRvZhuanMoneyProduct.setNestedScrollingEnabled(false);
        mRvZhuanMoneyProduct.setLayoutManager(gridLayoutManager);
        if (mGetBeanTabInfoList == null) {
            mGetBeanTabInfoList = new GetBeanTabInfoList();
        }
        if (mZhuanMoneyProductAdapter == null) {
            mZhuanMoneyProductAdapter = new ZhuanMoneyProductAdapter(R.layout.zhuan_product_item, mGetBeanTabInfoList.productList);
        }
        mRvZhuanMoneyProduct.setAdapter(mZhuanMoneyProductAdapter);
        mZhuanMoneyProductAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                toast(position + "");
                mContext.startActivity(new Intent(mContext, LoadWebViewActivity.class));
            }
        });
    }

    private GetBeanTabInfoList mGetBeanTabInfoList;

    public void updateInfo() {
        mZhuanMoneyProductAdapter.setNewData(mGetBeanTabInfoList.productList);
    }

    @Override
    public void onRefreshListener() {
        super.onRefreshListener();
        loadData();

    }

    @Override
    public void onSuccess(GetBeanTabInfoList getBeanTabInfoList) {
        this.mGetBeanTabInfoList = getBeanTabInfoList;
        updateInfo();
        this.mInited = true;
    }

    @Override
    public void on300Error(int errorCode, String s) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onCompleted() {
        mSmartRefresh.finishRefresh();
        dismiss();
    }
}

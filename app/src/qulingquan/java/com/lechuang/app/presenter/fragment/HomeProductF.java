package com.lechuang.app.presenter.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lechuang.app.R;
import com.lechuang.app.base.BasePresenter;
import com.lechuang.app.base.lisenters.IBaseView;
import com.lechuang.app.model.HomeModels;
import com.lechuang.app.model.bean.BaseEventBean;
import com.lechuang.app.model.bean.HomeLastProgramBean;
import com.lechuang.app.presenter.activity.adapter.HomeProductRecycleAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

/**
 * @author: LGH
 * @since: 2018/5/11
 * @describe:
 */

public class HomeProductF extends BasePresenter {
    @BindView(R.id.recycle_home_product)
    RecyclerView mRecycleHomeProduct;
    List<HomeLastProgramBean.ListBean> mProductList;

    public HomeProductF(IBaseView mIBaseView) {
        super(mIBaseView);
    }

    @Override
    public void addLayoutView(LayoutInflater inflater, FrameLayout scrollContentView, Bundle savedInstanceState) {
        super.addLayoutView(inflater, scrollContentView, savedInstanceState);
        inflater.inflate(R.layout.fragment_home_tab_product, scrollContentView);
        mLayoutStatus.setVisibility(View.GONE);
    }

    @Override
    public void initCreateContent() {
        super.initCreateContent();
        initProductRecycleData();
    }

    /**
     * 初始化Product商品数据
     */
    HomeProductRecycleAdapter mHomeProductRecycleAdapter;

    private void initProductRecycleData() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        mRecycleHomeProduct.setNestedScrollingEnabled(false);
        mRecycleHomeProduct.setLayoutManager(gridLayoutManager);
        HomeLastProgramBean homeLastProgramBean = new HomeLastProgramBean();
        mHomeProductRecycleAdapter = new HomeProductRecycleAdapter(R.layout.home_product_item, homeLastProgramBean.productList, 1);
        mRecycleHomeProduct.setAdapter(mHomeProductRecycleAdapter);
        mHomeProductRecycleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                toast(position + "");
            }
        });
    }

    /**
     * 更新Product
     */
    public void updataProductData(HomeLastProgramBean homeLastProgramBean,boolean isFreshing) {
        if(isFreshing){
            mProductList.clear();
        }
        mProductList.addAll(homeLastProgramBean.productList);
        mHomeProductRecycleAdapter.setNewData(mProductList);
    }

    public void setScroll(boolean isScroll){
        mRecycleHomeProduct.setNestedScrollingEnabled(isScroll);
    }


}

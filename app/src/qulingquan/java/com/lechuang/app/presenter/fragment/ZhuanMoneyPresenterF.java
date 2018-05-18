package com.lechuang.app.presenter.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.lechuang.app.R;
import com.lechuang.app.base.BasePresenter;
import com.lechuang.app.base.lisenters.IBaseView;
import com.lechuang.app.model.ZhuanMoneyModels;
import com.lechuang.app.model.bean.BaseEventBean;
import com.lechuang.app.model.bean.GetBeanTablayout;
import com.lechuang.app.view.zhuanmoney.fragment.ZhuanMoneyProductFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author: LGH
 * @since: 2018/5/3
 * @describe:
 */

public class ZhuanMoneyPresenterF extends BasePresenter {


    @BindView(R.id.tablayout_zhuan_money)
    TabLayout mTablayoutZhuanMoney;
    @BindView(R.id.vp_zhuan_money)
    ViewPager mVpZhuanMoney;

    private ZhuanMoneyModels mZhuanMoneyModels;

    public ZhuanMoneyPresenterF(IBaseView mIBaseView) {
        super(mIBaseView);
    }

    @Override
    public void addLayoutView(LayoutInflater inflater, FrameLayout scrollContentView, Bundle savedInstanceState) {
        super.addLayoutView(inflater, scrollContentView, savedInstanceState);
        inflater.inflate(R.layout.fragment_zhuan_money, scrollContentView);
        EventBus.getDefault().register(this);
    }

    @Override
    public void initCreateContent() {
        super.initCreateContent();
        initTablayoutData();

        loadAllData();
    }

    private void loadAllData() {
        mZhuanMoneyModels = new ZhuanMoneyModels(mContext,null);
        mZhuanMoneyModels.getTabLayoutData();
    }

    /**
     * 初始化顶部的tab数据
     */
    private ProductChildFragmentAdapter mChildFragmentAdapter;

    private void initTablayoutData() {
        //tab可滚动
        mTablayoutZhuanMoney.setTabMode(TabLayout.MODE_SCROLLABLE);
        //tab居中显示
        mTablayoutZhuanMoney.setTabGravity(TabLayout.GRAVITY_CENTER);

        mTablayoutZhuanMoney.setupWithViewPager(mVpZhuanMoney);
        mVpZhuanMoney.setOffscreenPageLimit(1);

        mChildFragmentAdapter = new ProductChildFragmentAdapter(getChildFragmentManager());
        mVpZhuanMoney.setAdapter(mChildFragmentAdapter);

        mTablayoutZhuanMoney.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    class ProductChildFragmentAdapter extends FragmentPagerAdapter {

        public ProductChildFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            super.getPageTitle(position);
            return tabList.get(position).rootName;
        }
    }

    /**
     * 更新TablayoutData
     */
    List<ZhuanMoneyProductFragment> fragments = new ArrayList<>();
    List<GetBeanTablayout.TopTab> tabList = new ArrayList<>();

    private void updataTablayoutData(GetBeanTablayout getBeanTablayout) {

        if (tabList.size() <= 0) {
            //添加第一个默认项
            GetBeanTablayout.TopTab tab = new GetBeanTablayout.TopTab();
            tab.rootId = -1;
            tab.rootName = "精选";
            tabList.add(tab);
            tabList.addAll(getBeanTablayout.tbclassTypeList);

            ZhuanMoneyProductFragment zhuanMoneyProductFragment;
            for (int i = 1; i < tabList.size() + 1; i++) {
                zhuanMoneyProductFragment = new ZhuanMoneyProductFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("classTypeId", i);
                zhuanMoneyProductFragment.setArguments(bundle);
                fragments.add(zhuanMoneyProductFragment);
            }
            mChildFragmentAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void zhuanMoneyTabData(BaseEventBean eventBean) {
        if (eventBean instanceof GetBeanTablayout) {
            GetBeanTablayout getBeanTablayout = (GetBeanTablayout) eventBean;
            updataTablayoutData(getBeanTablayout);
        }
    }
}

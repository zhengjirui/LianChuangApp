package com.lechuang.app.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.lechuang.app.R;
import com.lechuang.app.base.BasePresenter;
import com.lechuang.app.base.lisenters.IBaseView;
import com.lechuang.app.model.HomeModels;
import com.lechuang.app.model.bean.BaseEventBean;
import com.lechuang.app.model.bean.GetBean;
import com.lechuang.app.model.bean.HomeBannerBean;
import com.lechuang.app.model.bean.HomeDefaultKindBean;
import com.lechuang.app.model.bean.HomeGunDongTextBean;
import com.lechuang.app.model.bean.HomeKindBean;
import com.lechuang.app.model.bean.HomeLastProgramBean;
import com.lechuang.app.model.bean.HomeProgramBean;
import com.lechuang.app.model.bean.HomeTipoffListBean;
import com.lechuang.app.model.bean.HomeTodayProductBean;
import com.lechuang.app.view.TransChangeScrollView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: LGH
 * @since: 2018/5/3
 * @describe:
 */

public class HomePresenterF extends BasePresenter {

    @BindView(R.id.header)
    View mHeader;
    @BindView(R.id.refreshview)
    TransChangeScrollView mRefreshView;
    private View mFragmentHome;

    public HomePresenterF(IBaseView mIBaseView) {
        super(mIBaseView);
    }

    @Override
    public void addLayoutView(LayoutInflater inflater, FrameLayout scrollContentView, Bundle savedInstanceState) {
        super.addLayoutView(inflater, scrollContentView, savedInstanceState);
        mFragmentHome = inflater.inflate(R.layout.fragment_home,scrollContentView);
        EventBus.getDefault().register(this);
        mLayoutStatus.setVisibility(View.GONE);
        setEnableRefresh(true);
        setEnableLoadMore(true);
    }

    @Override
    public void initCreateContent() {
        super.initCreateContent();
//        mHeader = mFragmentHome.findViewById(R.id.header);
//        mRefreshView = mFragmentHome.findViewById(R.id.refreshview);
        mHeader.setAlpha(0);
        mRefreshView.setTransparentChange(mHeader);

        HomeModels homeModels = new HomeModels(mContext);
        homeModels.getHomeBannerData();
        homeModels.getHomeKindData_1();
        homeModels.getHomeKindData_2();
        homeModels.getHomeProgram();
        homeModels.getHomeScrollTextView_1();
        homeModels.getHomeScrollTextView_2();
        homeModels.getProductList("1","14");
        homeModels.getTabData();
        homeModels.getTodayProduct(1);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void homeData(BaseEventBean eventBean){
        if(eventBean instanceof HomeBannerBean){
            toast("1");
        }else if(eventBean instanceof HomeDefaultKindBean){
            toast("2");
        }else if(eventBean instanceof HomeKindBean){
            toast("3");
        }else if(eventBean instanceof HomeTipoffListBean){
            toast("4");
        }else if(eventBean instanceof HomeGunDongTextBean){
            toast("5");
        }else if(eventBean instanceof HomeProgramBean){
            toast("6");
        }else if(eventBean instanceof HomeTodayProductBean.ProudList){
            toast("7");
        }else if(eventBean instanceof GetBean){
            toast("8");
        }else if(eventBean instanceof HomeLastProgramBean){
            toast("9");
        }
    }
}

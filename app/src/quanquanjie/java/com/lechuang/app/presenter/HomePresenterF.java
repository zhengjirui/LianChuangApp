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
import com.lechuang.app.model.BaseModels;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

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
        EventBus.getDefault().register(this);
        initCreateContent();
        return layoutHomeRoot;
    }

    @Override
    public void initCreateContent() {
        super.initCreateContent();
        HomeModels homeModels = new HomeModels(mContext);
        homeModels.getHomeBannerData();
        homeModels.getHomeKindData_1();
        homeModels.getHomeKindData_2();
        homeModels.getHomeProgram();
        homeModels.getHomeScrollTextView_1();
        homeModels.getHomeScrollTextView_2();
//        homeModels.getProductList();
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
//        else if(eventBean instanceof List){
//
//        }
    }
}

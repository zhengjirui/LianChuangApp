package com.lechuang.app.events;

import com.lechuang.app.model.bean.GetBean;
import com.lechuang.app.model.bean.HomeBannerBean;
import com.lechuang.app.model.bean.HomeDefaultKindBean;
import com.lechuang.app.model.bean.HomeGunDongTextBean;
import com.lechuang.app.model.bean.HomeKindBean;
import com.lechuang.app.model.bean.HomeLastProgramBean;
import com.lechuang.app.model.bean.HomeProgramBean;
import com.lechuang.app.model.bean.HomeTipoffListBean;
import com.lechuang.app.model.bean.HomeTodayProductBean;

/**
 * @author: LGH
 * @since: 2018/5/8
 * @describe:
 */

public class HomeDataEvent {

    //获取首页轮播图数据
    public HomeBannerBean mHomeBannerBean;


    //首页分类数据,第一种固定的模式
    public HomeDefaultKindBean mHomeDefaultKindBean;
    //首页分类数据,第二种请求的模式
    public HomeKindBean mHomeKindBean;


    //首页滚动文字   爆料数据
    public HomeTipoffListBean mHomeTipoffListBean;
    //首页滚动文字   商品数据
    public HomeGunDongTextBean mHomeGunDongTextBean;


    //首页5个图片栏目数据
    public HomeProgramBean mHomeProgramBean;


    //今日爆款
    public HomeTodayProductBean.ProudList mProudList;


    //tab数据
    public GetBean mGetBean;


    //商品数据(默认'全部')
    public HomeLastProgramBean mHomeLastProgramBean;

}

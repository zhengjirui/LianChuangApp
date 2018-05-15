package com.lechuang.app.model;

import android.content.Context;

import com.lechuang.app.R;
import com.lechuang.app.model.bean.GetBeanTablayout;
import com.lechuang.app.model.bean.HomeBannerBean;
import com.lechuang.app.model.bean.HomeDefaultKindBean;
import com.lechuang.app.model.bean.HomeGunDongTextBean;
import com.lechuang.app.model.bean.HomeKindBean;
import com.lechuang.app.model.bean.HomeLastProgramBean;
import com.lechuang.app.model.bean.HomeProgramBean;
import com.lechuang.app.model.bean.HomeTipoffListBean;
import com.lechuang.app.model.bean.HomeTodayProductBean;
import com.lechuang.app.net.Netword;
import com.lechuang.app.net.ResultBack;
import com.lechuang.app.net.netApi.HomeApi;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author: LGH
 * @since: 2018/5/8
 * @describe:
 */

public class HomeModels implements Serializable{

    protected Context mContext;

    public HomeModels(Context context) {
        this.mContext = context;
    }

    /**
     * 获取首页轮播图数据
     */
    public void getHomeBannerData() {
        //首页轮播图数据
        Netword.getInstance().getApi(HomeApi.class)
                .homeBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultBack<HomeBannerBean>(mContext) {

                    @Override
                    public void successed(HomeBannerBean result) {
                        if (result != null) {
                            EventBus.getDefault().post(result);
                        }
                    }
                });
    }

    /**
     * 首页分类数据,第一种固定的模式
     */
    private int[] images = {R.mipmap.shouye_juhuasuan, R.mipmap.shouye_taoqianggou
            , R.mipmap.shouye_renqiremao, R.mipmap.shouye_chaozhi, R.mipmap.shouye_jinrixinpin};
    private String[] str = {"聚划算", "淘抢购", "人气热卖", "超值推荐", "今日新品"};

    public void getHomeKindData_1() {
        //首页分类数据，固定模式就是写死的
        HomeDefaultKindBean homeKindList = new HomeDefaultKindBean();
        for (int i = 0; i < images.length; i++) {
            HomeDefaultKindBean.ListBean bean = new HomeDefaultKindBean.ListBean();
            bean.img = images[i];
            bean.name = str[i];
            homeKindList.tbclassTypeList.add(bean);
        }
        EventBus.getDefault().post(homeKindList);
    }

    /**
     * 首页分类数据,第二种请求的模式
     */
    public void getHomeKindData_2() {
        //首页分类数据
        Netword.getInstance().getApi(HomeApi.class)
                .homeClassify()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultBack<HomeKindBean>(mContext) {
                    @Override
                    public void successed(HomeKindBean result) {

                        if (result != null) {
                            EventBus.getDefault().post(result);
                        }
                    }

                });
    }

    /**
     * 首页滚动文字   爆料数据
     */
    public void getHomeScrollTextView_1() {
        Netword.getInstance().getApi(HomeApi.class)
                .gunDongText()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultBack<HomeTipoffListBean>(mContext) {
                    @Override
                    public void successed(HomeTipoffListBean result) {
                        if (result != null) {
                            EventBus.getDefault().post(result);
                        }
                    }
                });
    }

    /**
     * 首页滚动文字   商品数据
     */
    public void getHomeScrollTextView_2() {
        Netword.getInstance().getApi(HomeApi.class)
                .gunDongTextProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultBack<HomeGunDongTextBean>(mContext) {
                    @Override
                    public void successed(HomeGunDongTextBean result) {
                        if (result != null) {
                            EventBus.getDefault().post(result);
                        }
                    }
                });
    }

    /**
     * 首页5个图片栏目数据
     */

    public void getHomeProgram() {
        Netword.getInstance().getApi(HomeApi.class)
                .homeProgramaImg()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultBack<HomeProgramBean>(mContext) {
                    @Override
                    public void successed(HomeProgramBean result) {
                        if (result != null) {
                            EventBus.getDefault().post(result);
                        }
                    }
                });
    }

    /**
     * 今日爆款
     */

    public void getTodayProduct(int page) {
        Netword.getInstance().getApi(HomeApi.class)
                .homeTodayProduct(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultBack<HomeTodayProductBean.ProudList>(mContext) {
                    @Override
                    public void successed(HomeTodayProductBean.ProudList result) {
                        if (result != null) {
                            EventBus.getDefault().post(result);
                        }
                    }

                });
    }

    /**
     * tab数据
     */
    public void getTabData() {
        Netword.getInstance().getApi(HomeApi.class)
                .topTabList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultBack<GetBeanTablayout>(mContext) {
                    @Override
                    public void successed(GetBeanTablayout result) {
                        if (result != null) {
                            EventBus.getDefault().post(result);
                        }
                    }
                });
    }

    /**
     * 商品数据(默认'全部')
     */
    public void getProductList(String page, String classTypeId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("page", page);
        map.put("classTypeId", classTypeId);
        Netword.getInstance().getApi(HomeApi.class)
                .homeLastProgram(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultBack<HomeLastProgramBean>(mContext) {
                    @Override
                    public void successed(HomeLastProgramBean result) {
                        if (result != null) {
                            EventBus.getDefault().post(result);
                        }
                    }
                });
    }
}

package com.lechuang.app.net.netApi;


import com.lechuang.app.model.bean.GetBeanTabInfoList;
import com.lechuang.app.model.bean.GetBeanTablayout;
import com.lechuang.app.model.bean.ResultBean;
import com.lechuang.app.net.QUrl;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Author: guoning
 * Date: 2017/10/2
 * Description:
 */

public interface ZhuanMoneyApi {

    /**
     * 顶部tab列表
     * @return
     */
    @POST(QUrl.getTopTabList)
    Observable<ResultBean<GetBeanTablayout>> topTabList();

    /**
     * 顶部广告图
     * @return
     */
    @POST(QUrl.getTopBanner)
    Observable<ResultBean<GetBeanTablayout>> topBanner();

    @FormUrlEncoded
    @POST(QUrl.getListInfo)
    Observable<ResultBean<GetBeanTabInfoList>> listInfo(@FieldMap Map<String, Object> map);


//    @FormUrlEncoded
    @POST("https://api.m.taobao.com/h5/mtop.taobao.detail.getdetail/6.0/")
//    Observable<ResultBean<GetBeanTablayout>> item(@Field("api") String api,
//                                         @Field("v")String v,
//                                         @Field("ttid")String ttid,
//                                         @Field("type")String type,
//                                         @Field("dataType")String dataType,
//                                         @Field("data")String data
//                                         );
    Observable<ResultBean<GetBeanTablayout>> item();
//    /**
//     * 首页轮播图接口
//     * 入参 无
//     */
//    @POST(QUrl.homePageBanner)
//    Observable<String> homeBanner();
//
//    /**
//     * 分类接口
//     * 入参 无
//     */
//    @POST(QUrl.home_classify)
//    Observable<String> homeClassify();
//
//
//    /**
//     * 滚动条接口(滚动的文字)
//     * 入参 无
//     */
//    @POST(QUrl.home_scrollTextView)
//    Observable<String> homeScrollTextView();
//
//    /**
//     * 首页四个栏目图片接口
//     * 入参 无
//     */
//    @POST(QUrl.home_programaImg)
//    Observable<String> homeProgramaImg();
//
//    /**
//     * 首页最下栏目接口
//     *zai tiao
//     * duan
//     * @param page 分页加载页数
//     */
//    @FormUrlEncoded
//    @POST(QUrl.home_lastPage)
//    Observable<ResultBean<GetBeanTablayout>> homeLastPage(@Field("page") int page);

}

package com.lechuang.app.presenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.AlibcTaokeParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.alibaba.fastjson.JSON;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.lechuang.app.R;
import com.lechuang.app.base.BasePresenter;
import com.lechuang.app.base.Constants;
import com.lechuang.app.base.lisenters.IBaseView;
import com.lechuang.app.model.DemoTradeCallback;
import com.lechuang.app.model.bean.AllProductBean;
import com.lechuang.app.model.bean.TaobaoUrlBean;
import com.lechuang.app.net.Netword;
import com.lechuang.app.net.QUrl;
import com.lechuang.app.net.ResultBack;
import com.lechuang.app.net.netApi.CommenApi;
import com.lechuang.app.utils.SpreUtils;
import com.lechuang.app.view.login.LoginActivity;
import com.lechuang.app.view.productdetails.ProductDetailsActivity;
import com.lechuang.app.view.productdetails.ProductDetailsShareActivity;
import com.zheng.webmanger.manger.CusWebViewClient;
import com.zheng.webmanger.manger.WebViewManger;
import com.zheng.webmanger.view.CusWebView;
import com.zheng.webmanger.view.LoadView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author: LGH
 * @since: 2018/5/15
 * @describe:
 */

public class ProductDetailsA extends BasePresenter{

    @BindView(R.id.webview_product_details)
    CusWebView mWebviewProductDetails;

    private Unbinder mUnbinder;
    private WebViewManger mWebViewManger;
    private Intent mIntent;
    private AllProductBean.ListInfo bean;
    private int t;
    //是否是代理
    private int isAgencyStatus;
    //用户id
    private String userId;
    private String loadUrl;
    private String productUrl;

    private AlibcShowParams alibcShowParams;//页面打开方式，默认，H5，Native
    private Map<String, String> exParams;//yhhpass参数

    public ProductDetailsA(IBaseView mIBaseView, Intent intent) {
        super(mIBaseView);
        this.mIntent = intent;
    }

    @Override
    public void initCreateContent() {
        super.initCreateContent();
        View activityView = getActivityView();
        mUnbinder = ButterKnife.bind(this, activityView);
        bean = JSON.parseObject(this.mIntent.getStringExtra("listInfo"), AllProductBean.ListInfo.class);
        t = this.mIntent.getIntExtra("t", 1);
        //代理
        isAgencyStatus = SpreUtils.getInt(mContext,"isAgencyStatus", 0);
        //用户id
        userId = SpreUtils.getString(mContext,"id", "");

        if (isAgencyStatus == 1) {
            //代理  a = 1
            loadUrl = QUrl.productDetails + "?i=" + bean.alipayItemId + "&t=" + t + "&a=1" + "&userId=" + userId;
        } else {
            loadUrl = QUrl.productDetails + "?i=" + bean.alipayItemId + "&t=" + t + "&a=0" + "&userId=" + userId;
        }

        initWebViewData();
    }

    private void initWebViewData() {
        mWebViewManger = new WebViewManger(mWebviewProductDetails);
        mWebviewProductDetails.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.equals("appfun:product:detail") && SpreUtils.getBoolean(mContext,Constants.KEY_HAS_LOGIN, false)) {
                    Netword.getInstance().getApi(CommenApi.class)
                            .tb_privilegeUrl(bean.alipayItemId, bean.alipayCouponId == null ? "" : bean.alipayCouponId, bean.imgs, bean.name)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new ResultBack<TaobaoUrlBean>(mContext) {
                                @Override
                                public void successed(TaobaoUrlBean result) {
                                    productUrl = result.productWithBLOBs.tbPrivilegeUrl;
                                    alibcShowParams = new AlibcShowParams(OpenType.Native, false);
                                    AlibcTaokeParams taoke = new AlibcTaokeParams(Constants.PID, "", "");
                                    exParams = new HashMap<>();
                                    exParams.put("isv_code", "appisvcode");
                                    exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改
                                    //Utils.E("url:"+url);
                                    //view.loadUrl(url);

                                    //Utils.E("cccccc:"+getIntent().getStringExtra("alipayItemId"));
                                    //AlibcBasePage alibcBasePage = new AlibcDetailPage(getIntent().getStringExtra("alipayItemId"));
                                    //实例化URL打开page
                                    //AlibcBasePage alibcBasePage = new AlibcPage("https://uland.taobao.com/coupon/edetail?activityId=91cdf70f6a944043b21c9dfca39a889c&pid=mm_116411007_36292444_142176907&itemId=544410512702&src=lc_tczs");
                                    AlibcBasePage alibcBasePage = new AlibcPage(productUrl);
                                    //AlibcTrade.show(ProductDetailsActivity.this, alibcBasePage, alibcShowParams, null, exParams , new DemoTradeCallback());

                                    //添加购物车
                                    //AlibcBasePage alibcBasePage = new AlibcAddCartPage(getIntent().getStringExtra("alipayItemId"));
                                    AlibcTrade.show((ProductDetailsActivity)mContext, alibcBasePage, alibcShowParams, taoke, exParams, new DemoTradeCallback());

                                }
                            });

                } else {
//                    startActivity(new Intent(ProductDetailsActivity.this, LoginActivity.class));
                }
                return true;

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mWebViewManger.setLoadUrl(loadUrl);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mWebViewManger != null){
            mWebViewManger.onDestroy();
        }
        if(mUnbinder != null){
            mUnbinder.unbind();
        }

    }

    @Override
    public boolean onKeyDown(boolean keyBoolean,int keyCode, KeyEvent event) {
        if(mWebViewManger.onKeyDown(keyCode)){
            return false;
        }
        return keyBoolean;
    }

    @OnClick({R.id.web_back, R.id.iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.web_back:
                finish();
                break;
            case R.id.iv_right:
                if (SpreUtils.getBoolean(mContext,Constants.KEY_HAS_LOGIN, false)) {
                    mContext.startActivity(new Intent(mContext, ProductDetailsShareActivity.class).putExtra(Constants.listInfo, bean));
                } else {
                    mContext.startActivity(new Intent(mContext, LoginActivity.class));
                }
                break;
        }
    }

}

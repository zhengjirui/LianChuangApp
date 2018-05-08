package com.lechuang.app;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.view.View;

import com.lechuang.app.base.BaseActivity;
import com.lechuang.app.base.Constants;
import com.lechuang.app.events.NetStateEvent;
import com.lechuang.app.model.bean.AdvertisementBean;
import com.lechuang.app.model.bean.LoadingImgBean;
import com.lechuang.app.net.Netword;
import com.lechuang.app.net.ResultBack;
import com.lechuang.app.net.netApi.CommenApi;
import com.lechuang.app.utils.SpreUtils;
import com.lechuang.app.view.Adver.FirstIntoAdverActivity;
import com.lechuang.app.view.Adver.NormalIntoActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.splash_content)
    ConstraintLayout splashContent;
    private boolean mIsFirst;//是否第一次进入
    private List<String> firstIntoData;//第一次进入的数据集合
    private AdvertisementBean.AdvertisingImgBean advertisementBean;//非第一次进入的数据对象
    private boolean currentRun = false;//当前网络请求和即时流程正在run的判断

    private Handler mDelay = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private Runnable mDelayRun = new Runnable() {
        @Override
        public void run() {
            Intent intent;
            if (mNetState && mIsFirst && firstIntoData != null && firstIntoData.size() > 0) {
                //启动第一次进入的引导view
                intent = new Intent(SplashActivity.this, FirstIntoAdverActivity.class);
                intent.putStringArrayListExtra("firstIntoData", (ArrayList<String>) firstIntoData);
                startActivity(intent);
            } else if (mNetState && advertisementBean != null) {
                //启动非第一次的倒计时广告图
                intent = new Intent(SplashActivity.this, NormalIntoActivity.class);
                intent.putExtra("adverBean", advertisementBean);
                startActivity(intent);
            } else if (mNetState) {
                intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                currentRun = false;//当前网络请求和即时流程完毕
                return;
            }
            finish();
            // 设置Activity的切换效果
            overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    public void initCreateContent() {
        super.initCreateContent();
    }

    private void getFirstOpenAppData() {
        Netword.getInstance().getApi(CommenApi.class)
                .loadingImg()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultBack<LoadingImgBean>(mContext) {
                    @Override
                    public void successed(LoadingImgBean result) {
                        firstIntoData = new ArrayList<>();
                        int size = result.list.size();
                        for (int i = 0; i < size; i++) {
                            firstIntoData.add(result.list.get(i).startImage);
                        }

                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        mDelay.postDelayed(mDelayRun, 1500);
                    }
                });
    }

    private void getAdverData() {
        Netword.getInstance().getApi(CommenApi.class)
                .advertisementInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultBack<AdvertisementBean>(mContext) {
                    @Override
                    public void successed(AdvertisementBean result) {
                        if (result.advertisingImg != null) {
                            advertisementBean = result.advertisingImg;
                        }

                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        mDelay.postDelayed(mDelayRun, 1500);
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setNetReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
        removeNetReceiver();
    }


    /**
     * 用于监听网络连接状态
     */
    @Override
    public void netStateLisenter(NetStateEvent netStateEvent) {
        super.netStateLisenter(netStateEvent);
        if (mNetState) {
            mIsFirst = SpreUtils.getBoolean(this, Constants.IS_FIRST_OPEN_APP, true);
//            mIsFirst = true;
            if (mIsFirst && !currentRun) {
                getFirstOpenAppData();
            } else if (!currentRun) {
                getAdverData();
            }
            currentRun = true;
//            mDelay.postDelayed(mDelayRun,3000); // todo 这里可以不用处理
        } else {
//            mDelay.removeCallbacks(mDelayRun);  // todo 这里可以不用处理
        }
    }
}

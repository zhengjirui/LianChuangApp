package com.lechuang.app.model;

import android.content.Context;

import com.lechuang.app.model.bean.GetBeanTabInfoList;
import com.lechuang.app.model.bean.GetBeanTablayout;
import com.lechuang.app.model.bean.RefreshLoadStateBean;
import com.lechuang.app.net.Netword;
import com.lechuang.app.net.ResultBack;
import com.lechuang.app.net.netApi.ZhuanMoneyApi;
import com.lechuang.app.presenter.lisenters.IZhuanProductLisenter;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author: LGH
 * @since: 2018/5/14
 * @describe:
 */

public class ZhuanMoneyModels {
    private final String TAG = "ZhuanMoneyModels";

    protected Context mContext;
    private IZhuanProductLisenter mIZhuanProductLisenter;

    public ZhuanMoneyModels(Context mContext,IZhuanProductLisenter iZhuanProductLisenter) {
        this.mContext = mContext;
        this.mIZhuanProductLisenter = iZhuanProductLisenter;
    }

    public void getTabLayoutData() {
        Netword.getInstance().getApi(ZhuanMoneyApi.class)
                .topTabList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultBack<GetBeanTablayout>(mContext) {
                    @Override
                    public void successed(GetBeanTablayout result) {
                        if (result != null) {
                            EventBus.getDefault().post(result);
                        }
                        EventBus.getDefault().post(new RefreshLoadStateBean(TAG,true));
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        EventBus.getDefault().post(new RefreshLoadStateBean(TAG,false,e.toString()));
                    }

                    @Override
                    protected void error300(int errorCode, String s) {
                        super.error300(errorCode, s);
                        EventBus.getDefault().post(new RefreshLoadStateBean(TAG,false,s));
                    }
                });
    }

    public void getTabLayoutListData(Map<String, Object> map) {
        Netword.getInstance().getApi(ZhuanMoneyApi.class)
                .listInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultBack<GetBeanTabInfoList>(mContext) {
                    @Override
                    public void successed(GetBeanTabInfoList result) {
                        if (result != null && mIZhuanProductLisenter != null) {
                            mIZhuanProductLisenter.onSuccess(result);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if( mIZhuanProductLisenter != null) {
                            mIZhuanProductLisenter.onError(e);
                        }
                    }

                    @Override
                    protected void error300(int errorCode, String s) {
                        super.error300(errorCode, s);
                        if( mIZhuanProductLisenter != null) {
                            mIZhuanProductLisenter.on300Error(errorCode,s);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if( mIZhuanProductLisenter != null) {
                            mIZhuanProductLisenter.onCompleted();
                        }
                    }
                });
    }
}

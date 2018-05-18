package com.lechuang.app.model;

import android.content.Context;
import android.view.View;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.lechuang.app.lisenters.IMineLisenter;
import com.lechuang.app.model.bean.OwnUserInfoBean;
import com.lechuang.app.net.Netword;
import com.lechuang.app.net.ResultBack;
import com.lechuang.app.net.netApi.OwnApi;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author: LGH
 * @since: 2018/5/17
 * @describe:
 */

public class MineModels {

    private final String TAG = "LoginModels";

    protected Context mContext;
    private IMineLisenter mIMineLisenter;

    public MineModels(Context mContext, IMineLisenter iMineLisenter) {
        this.mContext = mContext;
        this.mIMineLisenter = iMineLisenter;
    }

    public void getMineData(){
        Netword.getInstance().getApi(OwnApi.class)
                .userInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultBack<OwnUserInfoBean>(mContext) {
                    @Override
                    protected void error300(int errorCode, String s) {
                        super.error300(errorCode, s);
                        if (mIMineLisenter != null) {
                            mIMineLisenter.on300Error(errorCode, s);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (mIMineLisenter != null) {
                            mIMineLisenter.onError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if (mIMineLisenter != null) {
                            mIMineLisenter.onComplate();
                        }
                    }

                    @Override
                    public void successed(OwnUserInfoBean result) {  //代表之前绑定过手机号码
                        if (result != null && mIMineLisenter != null) {
                            mIMineLisenter.onSuccess(result);
                        }
                    }
                });
    }
}

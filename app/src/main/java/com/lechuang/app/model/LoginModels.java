package com.lechuang.app.model;

import android.content.Context;
import com.lechuang.app.lisenters.ILoginModelsLisenter;
import com.lechuang.app.model.bean.DataBean;
import com.lechuang.app.model.bean.TaobaoLoginBean;
import com.lechuang.app.net.Netword;
import com.lechuang.app.net.ResultBack;
import com.lechuang.app.net.netApi.CommenApi;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author: LGH
 * @since: 2018/5/16
 * @describe:
 */

public class LoginModels {

    private final String TAG = "LoginModels";

    protected Context mContext;
    private ILoginModelsLisenter mILoginModelsLisenter;

    public LoginModels(Context mContext, ILoginModelsLisenter iLoginModelsLisenter) {
        this.mContext = mContext;
        this.mILoginModelsLisenter = iLoginModelsLisenter;
    }

    /**
     * 正常登录
     *
     * @param number
     * @param pwd
     */
    public void normalLogin(String number, String pwd) {
        Netword.getInstance().getApi(CommenApi.class)
                .login(number, pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultBack<DataBean>(mContext) {
                    @Override
                    public void successed(DataBean data) {
                        if (data != null && mILoginModelsLisenter != null) {
                            mILoginModelsLisenter.onNormalLoginSuccess(data);
                        }
                    }

                    @Override
                    protected void error300(int errorCode, String s) {
                        super.error300(errorCode, s);
                        if (mILoginModelsLisenter != null) {
                            mILoginModelsLisenter.on300Error(errorCode, s);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (mILoginModelsLisenter != null) {
                            mILoginModelsLisenter.onError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if (mILoginModelsLisenter != null) {
                            mILoginModelsLisenter.onComplate();
                        }
                    }
                });
    }

    /**
     * 淘宝一键登录，验证是否可以登录后台系统
     */
    public void taobaoLogin(String params) {

        Netword.getInstance().getApi(CommenApi.class)
                .threeLogin(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultBack<TaobaoLoginBean>(mContext) {
                    @Override
                    protected void error300(int errorCode, String s) {
                        super.error300(errorCode, s);
                        if (mILoginModelsLisenter != null) {
                            mILoginModelsLisenter.on300Error(errorCode, s);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (mILoginModelsLisenter != null) {
                            mILoginModelsLisenter.onError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if (mILoginModelsLisenter != null) {
                            mILoginModelsLisenter.onComplate();
                        }
                    }

                    @Override
                    public void successed(TaobaoLoginBean result) {  //代表之前绑定过手机号码
                        if (result != null && mILoginModelsLisenter != null) {
                            mILoginModelsLisenter.onTaoBaoLoginSuccess(result);
                        }
                    }
                });

    }

    public void qqLogin(String qqOpenId){
        Netword.getInstance().getApi(CommenApi.class)
                .threeLogin(qqOpenId,"2")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultBack<TaobaoLoginBean>(mContext) {

                    @Override
                    protected void error300(int errorCode, String s) {
                        super.error300(errorCode, s);
                        if (mILoginModelsLisenter != null) {
                            mILoginModelsLisenter.on300Error(errorCode, s);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (mILoginModelsLisenter != null) {
                            mILoginModelsLisenter.onError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if (mILoginModelsLisenter != null) {
                            mILoginModelsLisenter.onComplate();
                        }
                    }

                    @Override
                    public void successed(TaobaoLoginBean result) {  //代表之前绑定过手机号码
                        if (result != null && mILoginModelsLisenter != null) {
                            mILoginModelsLisenter.onQQLoginSuccess(result);
                        }
                    }
                });
    }
}

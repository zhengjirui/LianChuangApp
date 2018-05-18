package com.lechuang.app.model;

import android.content.Context;

import com.lechuang.app.lisenters.ISetModelsLisenter;
import com.lechuang.app.model.bean.UpFileBean;
import com.lechuang.app.model.bean.UpdataInfoBean;
import com.lechuang.app.net.Netword;
import com.lechuang.app.net.ResultBack;
import com.lechuang.app.net.netApi.CommenApi;
import com.lechuang.app.net.netApi.TheSunApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author: LGH
 * @since: 2018/5/17
 * @describe:
 */

public class SetModels {

    private final String TAG = "SetModels";

    protected Context mContext;
    private ISetModelsLisenter mISetModelsLisenter;

    public SetModels(Context mContext, ISetModelsLisenter iSetModelsLisenter) {
        this.mContext = mContext;
        this.mISetModelsLisenter = iSetModelsLisenter;
    }


    public void updateInfoTaobao(String nick){
        Map<String, String> map = new HashMap<>();
        map.put("taobaoNumber", nick);
        Netword.getInstance().getApi(CommenApi.class)
                .updataInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultBack<UpdataInfoBean>(mContext) {

                    @Override
                    public void successed(UpdataInfoBean result) {
                        if (result != null && mISetModelsLisenter != null) {
                            mISetModelsLisenter.onUpDataInfoSuccess(result);
                        }
                    }

                    @Override
                    protected void error300(int errorCode, String s) {
                        super.error300(errorCode, s);
                        if (mISetModelsLisenter != null) {
                            mISetModelsLisenter.on300Error(errorCode, s);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (mISetModelsLisenter != null) {
                            mISetModelsLisenter.onError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if (mISetModelsLisenter != null) {
                            mISetModelsLisenter.onComplate();
                        }
                    }
                });
    }

    public void upHeadPhoto(List<MultipartBody.Part> parts){
        Netword.getInstance().getApi(TheSunApi.class)
                .fileUpload(parts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultBack<UpFileBean>(mContext) {
                    @Override
                    public void successed(UpFileBean result) {
                        if (result != null && mISetModelsLisenter != null) {
                            mISetModelsLisenter.onUpHeadPhotoSuccess(result);
                        }
                    }

                    @Override
                    protected void error300(int errorCode, String s) {
                        super.error300(errorCode, s);
                        if (mISetModelsLisenter != null) {
                            mISetModelsLisenter.on300Error(errorCode, s);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (mISetModelsLisenter != null) {
                            mISetModelsLisenter.onError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if (mISetModelsLisenter != null) {
                            mISetModelsLisenter.onComplate();
                        }
                    }
                });
    }

    public void loginOut(){
        Netword.getInstance().getApi(CommenApi.class)
                .logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultBack<String>(mContext) {
                    @Override
                    public void successed(String result) {
                        if (result != null && mISetModelsLisenter != null) {
                            mISetModelsLisenter.onLoginOutSuccess(result);
                        }
                    }

                    @Override
                    protected void error300(int errorCode, String s) {
                        super.error300(errorCode, s);
                        if (mISetModelsLisenter != null) {
                            mISetModelsLisenter.on300Error(errorCode, s);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (mISetModelsLisenter != null) {
                            mISetModelsLisenter.onError(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if (mISetModelsLisenter != null) {
                            mISetModelsLisenter.onComplate();
                        }
                    }
                });
    }
}

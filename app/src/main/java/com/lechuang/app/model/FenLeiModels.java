package com.lechuang.app.model;

import android.content.Context;

import com.lechuang.app.model.bean.ClassInfoBean;
import com.lechuang.app.model.bean.RefreshLoadStateBean;
import com.lechuang.app.net.Netword;
import com.lechuang.app.net.ResultBack;
import com.lechuang.app.net.netApi.CommenApi;

import org.greenrobot.eventbus.EventBus;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author: LGH
 * @since: 2018/5/12
 * @describe:
 */

public class FenLeiModels {

    protected Context mContext;

    public FenLeiModels(Context mContext) {
        this.mContext = mContext;
    }

    public void getFenLeiData(){
        Netword.getInstance().getApi(CommenApi.class)
                .getNextTabClass()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultBack<ClassInfoBean>(mContext) {
                    @Override
                    public void successed(ClassInfoBean result) {
                        if(result != null){
                            EventBus.getDefault().post(result);
                        }
                        EventBus.getDefault().post(new RefreshLoadStateBean("FenLeiModels",true));

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        EventBus.getDefault().post(new RefreshLoadStateBean("FenLeiModels",false,e.toString()));
                    }

                    @Override
                    protected void error300(int errorCode, String s) {
                        super.error300(errorCode, s);
                        EventBus.getDefault().post(new RefreshLoadStateBean("FenLeiModels",false,s));
                    }

                });
    }

}

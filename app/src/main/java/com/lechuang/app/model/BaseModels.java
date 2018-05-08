package com.lechuang.app.model;

import android.content.Context;

import com.lechuang.app.net.Netword;
import com.lechuang.app.net.netApi.HomeApi;

/**
 * @author: LGH
 * @since: 2018/5/8
 * @describe:
 */

public class BaseModels<T> {

    protected Context mContext;
    protected Netword mNetword;
    protected T t;

    public BaseModels(Context mContext,T t) {
        this.mContext = mContext;
        this.mNetword = Netword.getInstance();
        this.t = t;
    }


}

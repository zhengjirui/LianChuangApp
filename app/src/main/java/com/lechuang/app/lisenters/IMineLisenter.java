package com.lechuang.app.lisenters;

import com.lechuang.app.model.bean.OwnUserInfoBean;

/**
 * @author: LGH
 * @since: 2018/5/17
 * @describe:
 */

public interface IMineLisenter {


    void onSuccess(OwnUserInfoBean result);

    void on300Error(int errorCode, String s);

    void onError(Throwable e);

    void onComplate();
}

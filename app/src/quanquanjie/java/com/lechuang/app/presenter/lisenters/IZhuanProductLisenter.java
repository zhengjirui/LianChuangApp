package com.lechuang.app.presenter.lisenters;

import com.lechuang.app.model.bean.GetBeanTabInfoList;
import com.lechuang.app.model.bean.GetBeanTablayout;

/**
 * @author: LGH
 * @since: 2018/5/15
 * @describe:
 */

public interface IZhuanProductLisenter {

    void onSuccess(GetBeanTabInfoList getBeanTabInfoList);

    void on300Error(int errorCode, String s);

    void onError(Throwable e);

    void onCompleted();

}

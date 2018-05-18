package com.lechuang.app.lisenters;

import com.lechuang.app.model.bean.UpFileBean;
import com.lechuang.app.model.bean.UpdataInfoBean;

/**
 * @author: LGH
 * @since: 2018/5/17
 * @describe:
 */

public interface ISetModelsLisenter {

    void onUpDataInfoSuccess(UpdataInfoBean result);

    void onUpHeadPhotoSuccess(UpFileBean result);

    void onLoginOutSuccess(String result);

    void on300Error(int errorCode, String s);

    void onError(Throwable e);

    void onComplate();
}

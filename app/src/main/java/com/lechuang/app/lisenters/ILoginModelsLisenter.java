package com.lechuang.app.lisenters;

import com.lechuang.app.model.bean.DataBean;
import com.lechuang.app.model.bean.TaobaoLoginBean;

/**
 * @author: LGH
 * @since: 2018/5/16
 * @describe:
 */

public interface ILoginModelsLisenter {


    void onNormalLoginSuccess(DataBean data);

    void onTaoBaoLoginSuccess(TaobaoLoginBean result);

    void onWeiXinLoginSuccess();

    void onQQLoginSuccess(TaobaoLoginBean result);

    void on300Error(int errorCode, String s);

    void onError(Throwable e);

    void onComplate();

}

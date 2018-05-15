package com.lechuang.app.base.lisenters;

import android.content.Context;

/**
 * @author: LGH
 * @since: 2018/5/3
 * @describe:
 */

public interface IBaseView {

    Context getContext();

    void finishA();

    /**
     * 弹出提示
     * @param message
     */
    void toast(String message);

}

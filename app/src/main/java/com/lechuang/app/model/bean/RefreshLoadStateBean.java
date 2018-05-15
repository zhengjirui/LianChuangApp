package com.lechuang.app.model.bean;

/**
 * @author: LGH
 * @since: 2018/5/12
 * @describe:
 */

public class RefreshLoadStateBean extends BaseEventBean {

    public String className = "";
    public boolean requestState = false;
    public String info = "";

    public RefreshLoadStateBean(String className, boolean requestState,String info) {
        this.className = className;
        this.requestState = requestState;
        this.info = info;
    }

    public RefreshLoadStateBean(String className, boolean requestState) {
        this.className = className;
        this.requestState = requestState;
    }
}

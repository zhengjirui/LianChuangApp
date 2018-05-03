package com.lechuang.app.lisenters;

import com.lechuang.app.events.NetStateEvent;

/**
 * @author: LGH
 * @since: 2018/5/3
 * @describe:
 */

public interface INetStateLisenter {
    void netStateLisenter(NetStateEvent netStateEvent);
}

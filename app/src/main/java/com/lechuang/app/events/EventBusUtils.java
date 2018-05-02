package com.lechuang.app.events;

import org.greenrobot.eventbus.EventBus;

/**
 * @author: LGH
 * @since: 2018/5/2
 * @describe:
 */

public class EventBusUtils<T> {

    //静态内部类，既实现了线程安全，又避免了同步带来的性能影响。
    private static class LazyHolder {
        private static final EventBusUtils INSTANCE = new EventBusUtils();
    }

    private EventBus mEventBus = EventBus.getDefault();

    private EventBusUtils (){}

    public static final EventBusUtils getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void post(T t){
        mEventBus.post(t);
    }
}

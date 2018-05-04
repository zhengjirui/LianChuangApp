package com.lechuang.app;

import android.app.Activity;
import android.app.Application;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.wxlib.util.SysUtil;
import com.lechuang.app.base.Constants;
import com.lechuang.app.utils.Utils;
import com.umeng.commonsdk.UMConfigure;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * @author: LGH
 * @since: 2018/5/2
 * @describe:
 */

public class App extends Application {

    public static App myApplaction = null;
    //单例模式中获取唯一的MyApplication实例
    private List<Activity> activityList = new LinkedList<Activity>();

    /**
     * @author li
     * 邮箱：961567115@qq.com
     * @time 2017/9/26  11:22
     * @describe 初始化百川
     */

    @Override
    public void onCreate() {
        super.onCreate();
        myApplaction = this;
        Utils.init(this);

        //配置eventbus 只在DEBUG模式下，抛出异常，便于自测，同时又不会导致release环境的app崩溃
        EventBus.builder().throwSubscriberException(BuildConfig.DEBUG).installDefaultEventBus();

        AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
                //初始化成功，设置相关的全局配置参数
                // 是否使用支付宝
                AlibcTradeSDK.setShouldUseAlipay(true);
                // 设置是否使用同步淘客打点
                AlibcTradeSDK.setSyncForTaoke(true);
                // 是否走强制H5的逻辑，为true时全部页面均为H5打开
                AlibcTradeSDK.setForceH5(true);

            }

            @Override
            public void onFailure(int code, String msg) {
                //初始化失败，可以根据code和msg判断失败原因，详情参见错误说明
            }
        });


        //极光推送
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush

        //必须首先执行这部分代码, 如果在":TCMSSevice"进程中，无需进行云旺（OpenIM）和app业务的初始化，以节省内存;
        SysUtil.setApplication(this);
        if (SysUtil.isTCMSServiceProcess(this)) {
            return;
        }
        //第一个参数是Application Context
        //这里的APP_KEY即应用创建时申请的APP_KEY，同时初始化必须是在主进程中
        if (SysUtil.isMainProcess()) {
            YWAPI.init(myApplaction, Constants.APP_KEY);
        }


        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:友盟 app key
         * 参数3:友盟 channel
         * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数5:Push推送业务的secret
         */
        UMConfigure.init(this, Constants.UMENG_APPKEY, Constants.UMENG_CHANNEL, UMConfigure.DEVICE_TYPE_PHONE, null);

    }


    public static App getInstance() {
        if (null == myApplaction) {
            myApplaction = new App();
        }
        return myApplaction;
    }

    //添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void removeActivity(Activity activity) {
        //移除已经销毁的Activity
        activityList.remove(activity);
    }

    //遍历所有Activity并finish
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
            activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }
}

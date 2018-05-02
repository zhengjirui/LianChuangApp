package com.lechuang.app;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;

import com.lechuang.app.base.BaseActivity;
import com.lechuang.app.events.NetStateLisenter;
import com.lechuang.app.utils.Utils;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.splash_content)
    ConstraintLayout splashContent;

    private Handler mDelay = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private Runnable mDelayRun = new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
            // 设置Activity的切换效果
            overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        EventBus.getDefault().register(this);
    }

    @Override
    public void initCreateContent() {
        super.initCreateContent();
    }
    @Override
    protected void onResume() {
        super.onResume();
        setNetReceiver();

    }

    @Override
    protected void onStop() {
        super.onStop();
        removeNetReceiver();
    }


    /**
     * 主线程通知网络监听状态
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainThreadNetLisenter(NetStateLisenter netStateLisenter){
        mNetState = netStateLisenter.netState;
        if(mNetState){
            mDelay.postDelayed(mDelayRun,3000);
            Utils.showToast("您正在使用" + netStateLisenter.connType);
        }else {
            Utils.showToast("网络连接断开！");
            mDelay.removeCallbacks(mDelayRun);
        }
    }

}

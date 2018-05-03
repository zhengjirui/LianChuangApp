package com.lechuang.app;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;

import com.lechuang.app.base.BaseActivity;
import com.lechuang.app.events.NetStateEvent;

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
     * 用于监听网络连接状态
     */
    @Override
    public void netStateLisenter(NetStateEvent netStateEvent) {
        super.netStateLisenter(netStateEvent);
        if(mNetState){
            mDelay.postDelayed(mDelayRun,3000);
        }else {
            mDelay.removeCallbacks(mDelayRun);
        }
    }
}

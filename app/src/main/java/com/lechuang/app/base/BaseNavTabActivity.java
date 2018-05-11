package com.lechuang.app.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;


import com.lechuang.app.App;
import com.lechuang.app.R;
import com.lechuang.app.lisenters.INavTabLisenter;
import com.lechuang.app.lisenters.IPremissionLisenter;
import com.lechuang.app.utils.Logger;
import com.lechuang.app.utils.StatusBarUtil;
import com.lechuang.app.view.NavTabLayout;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public abstract class BaseNavTabActivity extends BaseActivity implements INavTabLisenter {
    private final String TAG = "TAG_BaseNavTabActivity";

    public FrameLayout mFragment;
    private NavTabLayout mNavTabLayout;
    public List<String> itemName = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_nav_tab);
    }

    @Override
    public void initCreateContent() {
        super.initCreateContent();
        mFragment = findViewById(R.id.fragment_layout);
        mNavTabLayout = findViewById(R.id.nav_tab_layout);
        mNavTabLayout.setNavTabLisenter(this);
        setItemName();
    }


    public void setItemName(){
        if(itemName != null && itemName.size() > 0){
            mNavTabLayout.setItemName(itemName);
        }else {
            throw new NumberFormatException("itmeName 为底部导航的值,值不能为空,size必须大于0！");
        }
    }


    @Override
    public void INavCurrentLisenter(int navCurrent) {
        Log.e(TAG,navCurrent + "");
        showCurrentFragment(navCurrent);
    }

    /**
     * 初始化fragment
     * @param savedInstanceState
     */
    protected abstract void initFragments(Bundle savedInstanceState);

    //只是用于显示当前tab，联动显示当前fragment
    public void setCurrentTab(int currentTab){
        Logger.e(TAG,currentTab + "");
        if(mNavTabLayout != null){
            mNavTabLayout.setTabCurrent(currentTab);
        }
    }

    protected abstract void showCurrentFragment(int currentTab);

    /**
     * 双击返回
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true;
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {

                @Override
                public void run() {

                    isExit = false;
                }
            }, 2000);
        } else {
            App.getInstance().exit();
            Process.killProcess(Process.myPid());
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
        }
        return false;
    }

    protected void openPremission(final IPremissionLisenter iPremissionLisenter, String... permissions) {
        AndPermission.with(mContext)
                .permission(permissions)
//                .rationale(mRationale)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if(iPremissionLisenter != null ){
                            iPremissionLisenter.openPreSuccess();
                        }
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        if(iPremissionLisenter != null ){
                            iPremissionLisenter.openPreFail();
                        }
                    }
                })
                .start();
    }
}

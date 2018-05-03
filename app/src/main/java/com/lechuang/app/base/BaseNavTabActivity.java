package com.lechuang.app.base;

import android.app.Fragment;
import android.os.Process;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;


import com.lechuang.app.App;
import com.lechuang.app.R;
import com.lechuang.app.lisenters.INavTabLisenter;
import com.lechuang.app.view.NavTabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public abstract class BaseNavTabActivity extends BaseActivity implements INavTabLisenter {
    private final String TAG = "BaseNavTabActivity";

    private FrameLayout mFragment;
    private NavTabLayout mNavTabLayout;
    public List<String> itemName = new ArrayList<>();


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_base_nav_tab);
        setHeadVisibility(View.GONE);
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
    }

    protected abstract void initFragments(List<Fragment> fragmentList);

    public void setCurrentTab(int currentTab){
        if(mNavTabLayout != null){
            mNavTabLayout.setTabCurrent(currentTab);
        }
    }

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
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
        }
        return false;
    }
}

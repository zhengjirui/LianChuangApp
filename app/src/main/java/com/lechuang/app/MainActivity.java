package com.lechuang.app;

import android.app.Fragment;
import android.os.Bundle;

import com.lechuang.app.base.BaseNavTabActivity;

import java.util.List;

public class MainActivity extends BaseNavTabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void initFragments(List<Fragment> initFragments){

    }

    @Override
    public void setItemName() {
        itemName.add("首页");
        itemName.add("超级搜");
        itemName.add("分享赚");
        itemName.add("爆料");
        itemName.add("我的");
        super.setItemName();
    }

    @Override
    public void setCurrentTab(int currentTab) {
        super.setCurrentTab(currentTab);
    }
}

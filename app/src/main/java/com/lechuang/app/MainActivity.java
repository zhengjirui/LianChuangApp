package com.lechuang.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.lechuang.app.base.BaseNavTabActivity;
import com.lechuang.app.view.baoliao.fragment.BaoLiaoFragment;
import com.lechuang.app.view.fenlei.fragment.FenLeiFragment;
import com.lechuang.app.view.home.fragment.HomeFragment;
import com.lechuang.app.view.mine.fragment.MineFragment;
import com.lechuang.app.view.supersearch.fragment.SuperSearchFragment;


public class MainActivity extends BaseNavTabActivity {

    /**
     * Fragment的TAG 用于解决app内存被回收之后导致的fragment重叠问题
     */
    private static final String[] FRAGMENT_TAG = {"item1", "item2", "item3", "item4", "item5"};
    private FragmentManager mFragmentManager;
    private HomeFragment mHomeFragment;
    private FenLeiFragment mFenLeiFragment;
    private SuperSearchFragment mSuperSearchFragment;
    private BaoLiaoFragment mBaoLiaoFragment;
    private MineFragment mMineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化fragment
        initFragments(savedInstanceState);
    }


    /**
     * 上一次界面 onSaveInstanceState 之前的tab被选中的状态 key 和 value
     */
    private final String PRV_SELINDEX="PREV_SELINDEX";
    private int oldTab = 0;
    private Fragment oldFragment;
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //保存tab选中的状态
        outState.putInt(PRV_SELINDEX,oldTab);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void initFragments(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            //读取上一次界面Save的时候tab选中的状态
            oldTab = savedInstanceState.getInt(PRV_SELINDEX,oldTab);
            mHomeFragment = (HomeFragment) mFragmentManager.findFragmentByTag(FRAGMENT_TAG[0]);
            mFenLeiFragment = (FenLeiFragment) mFragmentManager.findFragmentByTag(FRAGMENT_TAG[1]);
            mSuperSearchFragment = (SuperSearchFragment) mFragmentManager.findFragmentByTag(FRAGMENT_TAG[2]);
            mBaoLiaoFragment = (BaoLiaoFragment) mFragmentManager.findFragmentByTag(FRAGMENT_TAG[3]);
            mMineFragment = (MineFragment) mFragmentManager.findFragmentByTag(FRAGMENT_TAG[3]);
        }
        mFragmentManager = getFragmentManager();
        setCurrentTab(oldTab);
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
    protected void showCurrentFragment(int currentTab) {

        switch (currentTab) {
            case 0:
                showCurrentFragment(currentTab,mHomeFragment);
                break;
            case 1:
                showCurrentFragment(currentTab,mFenLeiFragment);
                break;
            case 2:
                showCurrentFragment(currentTab,mSuperSearchFragment);
                break;
            case 3:
                showCurrentFragment(currentTab,mBaoLiaoFragment);
                break;
            case 4:
                showCurrentFragment(currentTab,mMineFragment);
                break;
        }
    }

    private void showCurrentFragment(int currentTab, Fragment fragment){
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        // 如果MessageFragment不为空，则直接将它显示出来
        if(oldTab != currentTab && oldFragment != null){
            fragmentTransaction.hide(oldFragment);
        }
        this.oldTab = currentTab;
        if (fragment == null) {// 如果MessageFragment为空，则创建一个并添加到界面上
            if(currentTab == 0){
                mHomeFragment = new HomeFragment();
                oldFragment = mHomeFragment;
                fragmentTransaction.add(R.id.fragment_layout, mHomeFragment, FRAGMENT_TAG[currentTab]);
            }else if(currentTab == 1){
                mFenLeiFragment = new FenLeiFragment();
                oldFragment = mFenLeiFragment;
                fragmentTransaction.add(R.id.fragment_layout, mFenLeiFragment, FRAGMENT_TAG[currentTab]);
            }else if(currentTab == 2){
                mSuperSearchFragment = new SuperSearchFragment();
                oldFragment = mSuperSearchFragment;
                fragmentTransaction.add(R.id.fragment_layout, mSuperSearchFragment, FRAGMENT_TAG[currentTab]);
            }else if(currentTab == 3){
                mBaoLiaoFragment = new BaoLiaoFragment();
                oldFragment = mBaoLiaoFragment;
                fragmentTransaction.add(R.id.fragment_layout, mBaoLiaoFragment, FRAGMENT_TAG[currentTab]);
            }else if(currentTab == 4){
                mMineFragment = new MineFragment();
                oldFragment = mMineFragment;
                fragmentTransaction.add(R.id.fragment_layout, mMineFragment, FRAGMENT_TAG[currentTab]);
            }

        } else {

            fragmentTransaction.show(fragment);
            oldFragment = fragment;
        }

        fragmentTransaction.commit();

    }


}

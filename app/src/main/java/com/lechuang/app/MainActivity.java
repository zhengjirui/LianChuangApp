package com.lechuang.app;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.lechuang.app.base.BaseNavTabActivity;
import com.lechuang.app.base.Constants;
import com.lechuang.app.lisenters.IPremissionLisenter;
import com.lechuang.app.model.LocalSession;
import com.lechuang.app.model.bean.GetHostUrlBean;
import com.lechuang.app.model.bean.OwnCheckVersionBean;
import com.lechuang.app.net.Netword;
import com.lechuang.app.net.QUrl;
import com.lechuang.app.net.ResultBack;
import com.lechuang.app.net.netApi.CommenApi;
import com.lechuang.app.utils.Logger;
import com.lechuang.app.utils.SpreUtils;
import com.lechuang.app.utils.Utils;
import com.lechuang.app.view.baoliao.fragment.BaoLiaoFragment;
import com.lechuang.app.view.dialog.SearchDialog;
import com.lechuang.app.view.dialog.VersionUpdateDialog;
import com.lechuang.app.view.fenlei.fragment.FenLeiFragment;
import com.lechuang.app.view.home.fragment.HomeFragment;
import com.lechuang.app.view.mine.fragment.MineFragment;
import com.lechuang.app.view.supersearch.fragment.SuperSearchFragment;
import com.umeng.analytics.MobclickAgent;
import com.yanzhenjie.permission.Permission;

import java.io.File;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends BaseNavTabActivity {
    private final String TAG = "TAG_MainActivity";

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
    private LocalSession mSession;//用户信息bean

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSession = LocalSession.get(MainActivity.this);
        //初始化fragment
        initFragments(savedInstanceState);
    }


    /**
     * 上一次界面 onSaveInstanceState 之前的tab被选中的状态 key 和 value
     */
    private final String PRV_SELINDEX = "PREV_SELINDEX";
    private int oldTab = 0;
    private Fragment oldFragment;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //保存tab选中的状态
        outState.putInt(PRV_SELINDEX, oldTab);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void initCreateContent() {
        super.initCreateContent();

        openPremission(new IPremissionLisenter() {
            @Override
            public void openPreSuccess() {
                deleteImgs();
                getData();
            }

            @Override
            public void openPreFail() {
                getData();
            }
        }, Permission.Group.STORAGE);

    }

    private void getData() {
        Netword.getInstance().getApi(CommenApi.class)
                .getShareProductUrl()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultBack<GetHostUrlBean>(mContext) {
                    @Override
                    public void successed(GetHostUrlBean result) {
                        Logger.e(TAG, result.show.appHost);
                        SpreUtils.putString(MainActivity.this, Constants.getShareProductHost, result.show.appHost);
                    }
                });

        Netword.getInstance().getApi(CommenApi.class)
                .updataVersion("1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultBack<OwnCheckVersionBean>(mContext) {
                    @Override
                    public void successed(final OwnCheckVersionBean result) {
                        if (!Utils.getAppVersionName(mContext).equals(result.maxApp.versionNumber)) {//版本号
                            final VersionUpdateDialog version = new VersionUpdateDialog(mContext, result.maxApp.versionDescribe);
                            //下载地址
                            if (result.maxApp.downloadUrl != null) {
                                openPremission(new IPremissionLisenter() {
                                    @Override
                                    public void openPreSuccess() {
                                        version.setUrl(QUrl.url + result.maxApp.downloadUrl);
                                        version.show();
                                    }

                                    @Override
                                    public void openPreFail() {
                                        toast(R.string.app_update_fail);
                                    }
                                }, Permission.Group.STORAGE);

                            }


                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        setUserInfo();
        dialogClipSearch();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void initFragments(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            //读取上一次界面Save的时候tab选中的状态
            oldTab = savedInstanceState.getInt(PRV_SELINDEX, oldTab);
            mHomeFragment = (HomeFragment) mFragmentManager.findFragmentByTag(FRAGMENT_TAG[0]);
            mFenLeiFragment = (FenLeiFragment) mFragmentManager.findFragmentByTag(FRAGMENT_TAG[1]);
            mSuperSearchFragment = (SuperSearchFragment) mFragmentManager.findFragmentByTag(FRAGMENT_TAG[2]);
            mBaoLiaoFragment = (BaoLiaoFragment) mFragmentManager.findFragmentByTag(FRAGMENT_TAG[3]);
            mMineFragment = (MineFragment) mFragmentManager.findFragmentByTag(FRAGMENT_TAG[3]);
        }
        mFragmentManager = getSupportFragmentManager();
        setCurrentTab(oldTab);
    }

    @Override
    public void setItemName() {
        itemName.add("首页");
        itemName.add("分类");
        itemName.add("品牌优选");
        itemName.add("收藏");
        itemName.add("我的");
        super.setItemName();
    }

    @Override
    protected void showCurrentFragment(int currentTab) {

        switch (currentTab) {
            case 0:
                showCurrentFragment(currentTab, mHomeFragment);
                break;
            case 1:
                showCurrentFragment(currentTab, mFenLeiFragment);
                break;
            case 2:
                showCurrentFragment(currentTab, mSuperSearchFragment);
                break;
            case 3:
                showCurrentFragment(currentTab, mBaoLiaoFragment);
                break;
            case 4:
                showCurrentFragment(currentTab, mMineFragment);
                break;
        }
    }

    private void showCurrentFragment(int currentTab, Fragment fragment) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        // 如果MessageFragment不为空，则直接将它显示出来
        if (oldTab != currentTab && oldFragment != null) {
            fragmentTransaction.hide(oldFragment);
        }
        this.oldTab = currentTab;
        if (fragment == null) {// 如果MessageFragment为空，则创建一个并添加到界面上
            if (currentTab == 0) {
                mHomeFragment = new HomeFragment();
                oldFragment = mHomeFragment;
                fragmentTransaction.add(R.id.fragment_layout, mHomeFragment, FRAGMENT_TAG[currentTab]);
            } else if (currentTab == 1) {
                mFenLeiFragment = new FenLeiFragment();
                oldFragment = mFenLeiFragment;
                fragmentTransaction.add(R.id.fragment_layout, mFenLeiFragment, FRAGMENT_TAG[currentTab]);
            } else if (currentTab == 2) {
                mSuperSearchFragment = new SuperSearchFragment();
                oldFragment = mSuperSearchFragment;
                fragmentTransaction.add(R.id.fragment_layout, mSuperSearchFragment, FRAGMENT_TAG[currentTab]);
            } else if (currentTab == 3) {
                mBaoLiaoFragment = new BaoLiaoFragment();
                oldFragment = mBaoLiaoFragment;
                fragmentTransaction.add(R.id.fragment_layout, mBaoLiaoFragment, FRAGMENT_TAG[currentTab]);
            } else if (currentTab == 4) {
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

    //设置用户信息
    private void setUserInfo() {
        mSession.setId(SpreUtils.getString(this, "id", ""));
        mSession.setImge(SpreUtils.getString(this, "photo", ""));
        mSession.setName(SpreUtils.getString(this, "nickName", ""));
        mSession.setPhoneNumber(SpreUtils.getString(this, "phone", ""));
        mSession.setAccountNumber(SpreUtils.getString(this, "taobaoNumber", ""));
        mSession.setAlipayNumber(SpreUtils.getString(this, "alipayNumber", ""));
        mSession.setIsAgencyStatus(SpreUtils.getInt(this, "isAgencyStatus", 0));
        mSession.setSafeToken(SpreUtils.getString(this, "safeToken", ""));
        mSession.setLogin(SpreUtils.getBoolean(this, "isLogin", false));
    }

    /**
     * 删除手机内存中的图片(分享商品 和分享app)
     */
    private void deleteImgs() {
        File dirs = new File(getExternalCacheDir() + "");
        Utils.deleteAllFiles(dirs);
    }

    /**
     * 得到粘贴板的内容，弹出搜索框
     */
    public void dialogClipSearch() {
        ClipboardManager mClipboard = (ClipboardManager) App.getInstance().getSystemService(CLIPBOARD_SERVICE);
        //无数据时直接返回
        if (!mClipboard.hasPrimaryClip()) {
            return;
        }
        //如果是文本信息
        if (mClipboard.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
            ClipData cdText = mClipboard.getPrimaryClip();
            ClipData.Item item = cdText.getItemAt(0);
            //此处是TEXT文本信息
            final String clipStr = item.coerceToText(App.getInstance()).toString();
            if (clipStr != null && !clipStr.equalsIgnoreCase("")) {
                //说明剪切板中有内容，可以进行搜索
                final SearchDialog searchDialog = new SearchDialog(this, clipStr);
                searchDialog.setConfirmClickLisenter(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent(mContext, SearchResultActivity.class);
                        Intent intent = new Intent(mContext, null);// todo 跳转到搜索界面
                        intent.putExtra("type", 2);
                        //rootName传递过去显示在搜索框上
                        intent.putExtra("rootName", clipStr);
                        //rootId传递过去入参
                        intent.putExtra("rootId", clipStr);
                        startActivity(intent);
                        searchDialog.dismiss();
                    }
                });
                searchDialog.show();
                ClipData clipData = ClipData.newPlainText("Label", "");
                mClipboard.setPrimaryClip(clipData);
            }
        }
    }
}

package com.lechuang.app.presenter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.bumptech.glide.Glide;
import com.lechuang.app.App;
import com.lechuang.app.R;
import com.lechuang.app.base.BasePresenter;
import com.lechuang.app.base.Constants;
import com.lechuang.app.base.lisenters.IBaseView;
import com.lechuang.app.lisenters.IMineLisenter;
import com.lechuang.app.model.LocalSession;
import com.lechuang.app.model.MineModels;
import com.lechuang.app.model.bean.OwnUserInfoBean;
import com.lechuang.app.utils.SpreUtils;
import com.lechuang.app.view.XCRoundImageView;
import com.lechuang.app.view.login.LoginActivity;
import com.lechuang.app.view.mine.MineNewsActivity;
import com.lechuang.app.view.mine.ShareAppActivity;
import com.lechuang.app.view.mine.SignActivity;
import com.lechuang.app.view.mine.TaskCenterActivity;
import com.lechuang.app.view.set.SetActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: LGH
 * @since: 2018/5/3
 * @describe:
 */

public class MinePresenterF extends BasePresenter implements IMineLisenter, OnRefreshListener {


    @BindView(R.id.iv_headImg)
    XCRoundImageView mIvHeadImg;
    @BindView(R.id.tv_login_or_register)
    TextView mTvLoginOrRegister;
    @BindView(R.id.tv_my_income)
    TextView mTvMyIncome;
    @BindView(R.id.ll_get_fanli)
    LinearLayout mLlGetFanli;
    @BindView(R.id.ll_go_share)
    LinearLayout mLlGoShare;
    @BindView(R.id.tv_vip)
    TextView mTvVip;
    @BindView(R.id.tv_item_number)
    TextView mTvItemNumber;
    @BindView(R.id.smart_refresh_mine)
    SmartRefreshLayout mSmartRefreshMine;


    private LocalSession mLocalSession;

    //打开页面的方法
    private AlibcShowParams alibcShowParams = new AlibcShowParams(OpenType.Native, false);
    private Map exParams = new HashMap<>();
    private MineModels mMineModels;
    //用户密码
    private String openImPassword;
    //用户账户
    private String phone;
    //客服账号
    private String customerServiceId;
    public YWIMKit mIMKit;

    public MinePresenterF(IBaseView mIBaseView) {
        super(mIBaseView);
    }


    @Override
    public void addLayoutView(LayoutInflater inflater, FrameLayout scrollContentView, Bundle savedInstanceState) {
        super.addLayoutView(inflater, scrollContentView, savedInstanceState);
        inflater.inflate(R.layout.fragment_mine, scrollContentView);
    }

    @Override
    public void initCreateContent() {
        super.initCreateContent();
        mLocalSession = LocalSession.get(mContext);
        mMineModels = new MineModels(mContext, this);
        mSmartRefreshMine.setEnableLoadMore(false);
        mSmartRefreshMine.setOnRefreshListener(this);
        exParams.put("isv_code", "appisvcode");
        exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onResumeLoadData();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {//fragment做显示和隐藏时回调的方法，显示时需要重新加载数据，
            onResumeLoadData();
        } else {
            mSmartRefreshMine.finishRefresh();
        }
    }

    private void onResumeLoadData() {
        if (SpreUtils.getBoolean(mContext, Constants.KEY_HAS_LOGIN, false)) {
            if (!SpreUtils.getString(mContext, "photo", "").equalsIgnoreCase("")) {
                Glide.with(App.getInstance()).load(SpreUtils.getString(mContext, "photo", "")).into(mIvHeadImg);
            }
            //没有昵称时展示手机号
            String nick = SpreUtils.getString(mContext, "nickName", SpreUtils.getString(mContext, "phone", "----"));
            mTvLoginOrRegister.setText(nick.length() > 9 ? nick.substring(0, 7) + "..." : nick);
            mTvLoginOrRegister.setEnabled(false);
        } else {
            mTvLoginOrRegister.setText("登录/注册");
            mIvHeadImg.setImageResource(R.mipmap.ic_def_user_photo);
            //tvVip.findViewById(R.id.tv_vip).setVisibility(View.GONE);//vip
            mTvLoginOrRegister.setEnabled(true);
            mTvMyIncome.setText("0.00");
            mLlGetFanli.setVisibility(View.VISIBLE);
            mLlGoShare.setVisibility(View.GONE);
        }
        mMineModels.getMineData();
    }

    @OnClick({R.id.iv_set, R.id.iv_news, R.id.tv_sign, R.id.tv_tuiguang,R.id.ll_share_money,
            R.id.tv_all_order, R.id.ll_problem, R.id.ll_gouwuche, R.id.ll_version, R.id.ll_cache,
            R.id.ll_task})
    public void onViewClicked(View view) {
        if (mLocalSession.isLogin()) {
            switch (view.getId()) {

                case R.id.iv_set://设置
                    mContext.startActivity(new Intent(mContext, SetActivity.class));
                    break;
                case R.id.iv_news://消息
                    mContext.startActivity(new Intent(mContext, MineNewsActivity.class));
                    break;
                case R.id.tv_sign://签到
                    mContext.startActivity(new Intent(mContext, SignActivity.class));
                    break;
                case R.id.tv_tuiguang://推广（跟分享app是同一个方式）
                    break;
                case R.id.ll_share_money://分享app
                    mContext.startActivity(new Intent(mContext, ShareAppActivity.class));
                    break;
                case R.id.tv_all_order://我的订单
                    break;
                case R.id.ll_problem://常见问题
                    break;
                case R.id.ll_task://任务中心
                    mContext.startActivity(new Intent(mContext, TaskCenterActivity.class));
                    break;
                case R.id.ll_gouwuche://购物车
                    break;
                case R.id.ll_version://版本更新
                    break;
                case R.id.ll_cache://清除缓存
                    break;

            }
        } else {
            mContext.startActivity(new Intent(mContext, LoginActivity.class));
        }
    }

    @Override
    public void onSuccess(OwnUserInfoBean result) {
        mSmartRefreshMine.finishRefresh(true);
        if (result.vipGradeName != null && !result.vipGradeName.equals("")) {
            mTvVip.setText(result.vipGradeName);
        } else {
            mTvVip.setText("了解会员");
        }
        //我的收益
        mTvMyIncome.setText(result.withdrawIntegral);
        mTvItemNumber.setText(result.agencyNum + "人");
        SpreUtils.putInt(mContext, "isAgencyStatus", result.isAgencyStatus);

        if (result.isAgencyStatus == 1) {
            mLlGetFanli.setVisibility(View.GONE);
            mLlGoShare.setVisibility(View.VISIBLE);
        } else {
            mLlGetFanli.setVisibility(View.VISIBLE);
            mLlGoShare.setVisibility(View.GONE);
        }
        openImPassword = result.openImPassword;
        phone = result.phone;
        customerServiceId = result.customerServiceId;
        if (phone != null && openImPassword != null && customerServiceId != null) {
            //此实现不一定要放在Application onCreate中
            //此对象获取到后，保存为全局对象，供APP使用
            //此对象跟用户相关，如果切换了用户，需要重新获取
            mIMKit = YWAPI.getIMKitInstance(phone, Constants.APP_KEY);
            //开始登录
            IYWLoginService loginService = mIMKit.getLoginService();
            YWLoginParam loginParam = YWLoginParam.createLoginParam(phone, openImPassword);
            loginService.login(loginParam, new IWxCallback() {

                @Override
                public void onSuccess(Object... arg0) {

                }

                @Override
                public void onProgress(int arg0) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onError(int errCode, String description) {
                    //如果登录失败，errCode为错误码,description是错误的具体描述信息
                    // Utils.show(geta, description);
                }
            });
        }
    }

    @Override
    public void on300Error(int errorCode, String s) {
        mSmartRefreshMine.finishRefresh(false);
    }

    @Override
    public void onError(Throwable e) {
        mSmartRefreshMine.finishRefresh(false);
    }

    @Override
    public void onComplate() {
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mMineModels.getMineData();
    }
}

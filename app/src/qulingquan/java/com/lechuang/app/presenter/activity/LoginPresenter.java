package com.lechuang.app.presenter.activity;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lechuang.app.R;
import com.lechuang.app.base.BasePresenter;
import com.lechuang.app.base.Constants;
import com.lechuang.app.base.lisenters.IBaseView;
import com.lechuang.app.lisenters.ILoginModelsLisenter;
import com.lechuang.app.model.LocalSession;
import com.lechuang.app.model.LoginModels;
import com.lechuang.app.model.bean.DataBean;
import com.lechuang.app.model.bean.TaobaoLoginBean;
import com.lechuang.app.net.QUrl;
import com.lechuang.app.utils.SpreUtils;
import com.lechuang.app.utils.Utils;
import com.lechuang.app.view.ClearEditText;
import com.lechuang.app.view.loadwebview.LoadWebViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author: LGH
 * @since: 2018/5/9
 * @describe:
 */

public class LoginPresenter extends BasePresenter implements ILoginModelsLisenter {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.et_phonenum)
    ClearEditText mEtPhonenum;
    @BindView(R.id.et_pwd)
    ClearEditText mEtPwd;
    @BindView(R.id.login_login_ewai)
    RelativeLayout mLoginLoginEwai;

    Unbinder mUnbinder;
    LoginModels mLoginModels;
    private LocalSession mSession;//存放内存中的用户信息缓存

    public LoginPresenter(IBaseView mIBaseView) {
        super(mIBaseView);
    }

    @Override
    public void initCreateContent() {
        super.initCreateContent();
        View activityView = getActivityView();
        mUnbinder = ButterKnife.bind(this, activityView);
        mLoginModels = new LoginModels(mContext, this);
        mSession = LocalSession.get(mContext);
        mTvTitle.setText("登录");
        mTvRight.setText("注册");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_right, R.id.btn_login, R.id.xieyi, R.id.tv_taobao_qulingquan, R.id.tv_weixin, R.id.tv_qq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right:
                break;
            case R.id.btn_login:
                //登录
                siginIn();
                break;
            case R.id.xieyi:
                mContext.startActivity(new Intent(mContext, LoadWebViewActivity.class)
                        .putExtra("url", QUrl.userAgreement)
                        .putExtra("title", "用户协议"));
                break;
            case R.id.tv_taobao_qulingquan:
                break;
            case R.id.tv_weixin:
                break;
            case R.id.tv_qq:
                break;
        }
    }

    /**
     * 登录
     */
    private void siginIn() {
        //登录用户名
        String userName = mEtPhonenum.getText().toString().trim();
        //登录密码
        String userPsd = mEtPwd.getText().toString().trim();
        //登录后加密的密码
        String mUserPsd = Utils.getMD5(userPsd);

        //输入框为空 提示用户
        if (Utils.isEmpty(mEtPhonenum)) {
            Utils.showToast("请输入手机号!");
            return;
        }

        //输入的不是正确的手机号
        if (!Utils.isTelNumber(userName)) {
            Utils.showToast("请输入正确的手机号!");
            return;
        }
        //没有输入密码
        if (Utils.isEmpty(mEtPwd)) {
            Utils.showToast("请输入密码");
            return;
        }
        //登录
        show();
        mLoginModels.normalLogin(userName, mUserPsd);
    }


    @Override
    public void onNormalLoginSuccess(DataBean data) {
        DataBean.UserBean result = data.user;
        //用户信息
        //登录状态设为true
        mSession.setLogin(true);
        SpreUtils.putBoolean(mContext, Constants.KEY_HAS_LOGIN, true);
        //绑定的支付宝号
        if (result.alipayNumber != null) {
            mSession.setAlipayNumber(result.alipayNumber);
            SpreUtils.putString(mContext, "alipayNumber", result.alipayNumber);
        }
        //用户id
        if (result.id != null) {
            mSession.setId(result.id);
            SpreUtils.putString(mContext, "id", result.id);
        }
        //是否是代理
        if (result.isAgencyStatus != 0) {
            mSession.setIsAgencyStatus(result.isAgencyStatus);
            SpreUtils.putInt(mContext, Constants.KEY_AGENCY_STATUS, result.isAgencyStatus);
        }
        //昵称
        if (result.nickName != null) {
            mSession.setName(result.nickName);
            SpreUtils.putString(mContext, "nickName", result.nickName);
        }
        //用户手机号
        if (result.phone != null) {
            mSession.setPhoneNumber(result.phone);
            SpreUtils.putString(mContext, "phone", result.phone);
        }
        //头像
        if (result.photo != null) {
            mSession.setImge(result.photo);
            SpreUtils.putString(mContext, "photo", result.photo);
        }
        //safeToken
        if (result.safeToken != null) {
            mSession.setSafeToken(result.safeToken);
            SpreUtils.putString(mContext, "safeToken", result.safeToken);
        }
        //淘宝号
        if (result.taobaoNumber != null) {
            mSession.setAccountNumber(result.taobaoNumber);
            SpreUtils.putString(mContext, "taobaoNumber", result.taobaoNumber);
        }

        //微信名称
        if (result.weixinName != null) {
            mSession.setWeixinName(result.weixinName);
            SpreUtils.putString(mContext, "weixinName", result.weixinName);
        }

        //微信头像
        if (result.weixinPhoto != null) {
            mSession.setWeixinPhoto(result.weixinPhoto);
            SpreUtils.putString(mContext, "weixinPhoto", result.weixinPhoto);
        }


        //qq名称
        if (result.qqName != null) {
            mSession.setQqName(result.qqName);
            SpreUtils.putString(mContext, "qqName", result.qqName);
        }

        //qq头像
        if (result.qqPhoto != null) {
            mSession.setQqPhoto(result.qqPhoto);
            SpreUtils.putString(mContext, "qqPhoto", result.qqPhoto);
        }
                               /* se.putString("userId", userId);
                                se.putString("pwd", pwd)*/

        //登陆成功
        Utils.showToast("登陆成功");
        // todo 登录成功之后记得刷新首页数据
//        sendBroadcast(new Intent(LeCommon.ACTION_LOGIN_SUCCESS));
        finish();
    }

    @Override
    public void onTaoBaoLoginSuccess(TaobaoLoginBean result) {
        saveUserInfo(result);
    }

    @Override
    public void onWeiXinLoginSuccess() {

    }

    @Override
    public void onQQLoginSuccess(TaobaoLoginBean result) {
        saveUserInfo(result);
    }

    @Override
    public void on300Error(int errorCode, String s) {
        if (errorCode == 300) {    //第一次登录淘宝账号,要绑定手机号
            // TODO: 2017/10/5 绑定手机号
            Utils.showToast(s);
//            startActivity(new Intent(mContext, BoundPhoneActivity.class).putExtra("boundChannel", 1));
            finish();
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplate() {
        dismiss();
    }

    private void saveUserInfo(TaobaoLoginBean result) {
        //用户信息
        //登录状态设为true
        mSession.setLogin(true);
        SpreUtils.putBoolean(mContext, Constants.KEY_HAS_LOGIN, true);
        //绑定的支付宝号
        if (result.alipayNumber != null) {
            mSession.setAlipayNumber(result.alipayNumber);
            SpreUtils.putString(mContext, "alipayNumber", result.alipayNumber);
        }
        //用户id
        if (result.id != null) {
            mSession.setId(result.id);
            SpreUtils.putString(mContext, "id", result.id);
        }
        //是否是代理
        if (result.isAgencyStatus != 0) {
            mSession.setIsAgencyStatus(result.isAgencyStatus);
            SpreUtils.putInt(mContext, Constants.KEY_AGENCY_STATUS, result.isAgencyStatus);
        }
        //昵称
        if (result.nickName != null) {
            mSession.setName(result.nickName);
            SpreUtils.putString(mContext, "nickName", result.nickName);
        }
        //用户手机号
        if (result.phone != null) {
            mSession.setPhoneNumber(result.phone);
            SpreUtils.putString(mContext, "phone", result.phone);
        }
        //头像
        if (result.photo != null) {
            mSession.setImge(result.photo);
            SpreUtils.putString(mContext, "photo", result.photo);
        }
        //safeToken
        if (result.safeToken != null) {
            mSession.setSafeToken(result.safeToken);
            SpreUtils.putString(mContext, "safeToken", result.safeToken);
        }
        //淘宝号
        if (result.taobaoNumber != null) {
            mSession.setAccountNumber(result.taobaoNumber);
            SpreUtils.putString(mContext, "taobaoNumber", result.taobaoNumber);
        }

        //微信名称
        if (result.weixinName != null) {
            mSession.setWeixinName(result.weixinName);
            SpreUtils.putString(mContext, "weixinName", result.weixinName);
        }

        //微信头像
        if (result.weixinPhoto != null) {
            mSession.setWeixinPhoto(result.weixinPhoto);
            SpreUtils.putString(mContext, "weixinPhoto", result.weixinPhoto);
        }


        //qq名称
        if (result.qqName != null) {
            mSession.setQqName(result.qqName);
            SpreUtils.putString(mContext, "qqName", result.qqName);
        }

        //qq头像
        if (result.qqPhoto != null) {
            mSession.setQqPhoto(result.qqPhoto);
            SpreUtils.putString(mContext, "qqPhoto", result.qqPhoto);
        }
                               /* se.putString("userId", userId);
                                se.putString("pwd", pwd)*/

        //登陆成功
        Utils.showToast("登陆成功");
        // todo 登录成功之后记得刷新首页数据
//        sendBroadcast(new Intent(LeCommon.ACTION_LOGIN_SUCCESS));
        finish();
    }
}

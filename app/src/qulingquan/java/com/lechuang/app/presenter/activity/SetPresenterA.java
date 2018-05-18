package com.lechuang.app.presenter.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ali.auth.third.core.model.Session;
import com.ali.auth.third.login.callback.LogoutCallback;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.alibaba.baichuan.android.trade.callback.AlibcLoginCallback;
import com.bumptech.glide.Glide;
import com.lechuang.app.App;
import com.lechuang.app.R;
import com.lechuang.app.base.BasePresenter;
import com.lechuang.app.base.Constants;
import com.lechuang.app.base.lisenters.IBaseView;
import com.lechuang.app.lisenters.ISetModelsLisenter;
import com.lechuang.app.model.LocalSession;
import com.lechuang.app.model.SetModels;
import com.lechuang.app.model.bean.UpFileBean;
import com.lechuang.app.model.bean.UpdataInfoBean;
import com.lechuang.app.utils.PhotoUtil;
import com.lechuang.app.utils.SpreUtils;
import com.lechuang.app.utils.Utils;
import com.lechuang.app.view.XCRoundImageView;
import com.lechuang.app.view.dialog.DialogUtil;
import com.lechuang.app.view.set.FaceBackActivity;
import com.lechuang.app.view.set.SetActivity;
import com.yanzhenjie.permission.Permission;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author: LGH
 * @since: 2018/5/17
 * @describe:
 */

public class SetPresenterA extends BasePresenter implements ISetModelsLisenter {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_set_head)
    XCRoundImageView ivSetHead;
    @BindView(R.id.tv_set_name)
    TextView tvSetName;
    @BindView(R.id.tv_set_no)
    TextView tvSetNo;
    @BindView(R.id.line_user)
    LinearLayout lineUser;
    @BindView(R.id.line_modify_pwd)
    LinearLayout lineModifyPwd;
    @BindView(R.id.line_feedback)
    LinearLayout lineFeedback;
    @BindView(R.id.line_update)
    LinearLayout lineUpdate;

    @BindView(R.id.line_set_user)
    LinearLayout lineSetUser;
    @BindView(R.id.tv_userPhone)
    TextView tvUserPhone;
    @BindView(R.id.line_phone_number)
    LinearLayout linePhoneNumber;
    @BindView(R.id.tv_userTaobao)
    TextView tvUserTaobao;
    @BindView(R.id.ll_tb)
    LinearLayout llTb;
    @BindView(R.id.tv_userPay)
    TextView tvUserPay;
    @BindView(R.id.line_alipay)
    LinearLayout lineAlipay;
    @BindView(R.id.tv_exit)
    TextView tvExit;
    private LocalSession mSession;
    //保存用户登录信息的sp
    private File file;
    private List<MultipartBody.Part> parts;
    private Unbinder mUnbinder;
    private SetModels mSetModels;

    public SetPresenterA(IBaseView mIBaseView) {
        super(mIBaseView);
    }

    @Override
    public void initCreateContent() {
        super.initCreateContent();
        mUnbinder = ButterKnife.bind(this, getActivityView());
        mSession = LocalSession.get(mContext);
        mTvTitle.setText("设置");
        //昵称 没有昵称时展示手机号
        tvSetName.setText(SpreUtils.getString(mContext,"nickName", SpreUtils.getString(mContext,"phone", "----")));
        //手机号
        tvSetNo.setText("账号:" + SpreUtils.getString(mContext,"phone", "----"));
        //头像
        if (!SpreUtils.getString(mContext,"photo", "").equalsIgnoreCase("")) {
            Glide.with(App.getInstance()).load(SpreUtils.getString(mContext,"photo", "")).into(ivSetHead);
        }
        tvUserPhone.setText(SpreUtils.getString(mContext,"phone", "----"));
        tvUserTaobao.setText(SpreUtils.getString(mContext,"taobaoNumber", "绑定淘宝账号"));
        tvUserPay.setText(SpreUtils.getString(mContext,"alipayNumber", "绑定支付宝"));

        mSetModels = new SetModels(mContext, this);
    }

    @OnClick({R.id.iv_back, R.id.line_user, R.id.line_modify_pwd, R.id.line_feedback, R.id.line_set_user,
            R.id.line_phone_number, R.id.ll_tb, R.id.line_alipay,
            R.id.line_update, R.id.tv_exit, R.id.line_helpCenter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.line_set_user://设置用户名
//                mContext.startActivity(new Intent(mContext, SetUserActivity.class));
                break;
            case R.id.line_phone_number://更换手机号码
//                mContext.startActivity(new Intent(mContext, ChangePhoneNumberActivity.class));
                break;
            case R.id.line_alipay:  //更换支付宝帐号
                if (mSession.getAlipayNumber().equals("")) {//没绑定支付宝
//                    mContext.startActivity(new Intent(mContext, BoundAlipayActivity.class));
                } else {
//                    mContext.startActivity(new Intent(mContext, ChangeBoundAlipayActivity.class).putExtra("pay", SpreUtils.getString(mContext,"alipayNumber", "")));
                }
                break;
            case R.id.ll_tb:  //绑定淘宝
                // TODO: 2017/9/25 三方淘宝的接口
                if (Utils.isNetworkAvailable(mContext)) {
//                    show();
                    //授权报错
//                    AlibcLogin alibcLogin = AlibcLogin.getInstance();
//                    alibcLogin.showLogin((SetActivity) mContext, new AlibcLoginCallback() {
//                        @Override
//                        public void onSuccess() {
//                            Session taobao = alibcLogin.getSession();
//                            //mSession.setLogin(true);
//                            //mSession.setImge(taobao.avatarUrl);
//                            //mSession.setName(taobao.nick);
//                            //mSession.setAccountNumber(taobao.nick);
//                            mSetModels.updateInfoTaobao(taobao.nick);
//                            //finish();
//                        }
//
//                        @Override
//                        public void onFailure(int i, String s) {
//                            dismiss();
//                            ((SetActivity) mContext).toast(s);
//
//                        }
//                    });
                } else {
                    toast(R.string.net_error1);
                }

                break;
            case R.id.line_user://用户详情
//                startActivity(new Intent(this, UserCenterActivity.class));
                boolean premission = openPremission(Permission.Group.CAMERA);
                if (premission) {
                    File file1 = new File(Environment.getExternalStorageDirectory() + "/" + mContext.getString(R.string.app_name));//文件夹是否存在
                    if (!file1.exists()) {
                        file1.mkdir();
                    }
                    file = new File(Environment.getExternalStorageDirectory() + "/" + mContext.getString(R.string.app_name) + "/" + Constants.HEAD);

                    DialogUtil.showDialog(mContext, new DialogUtil.onClickItem() {
                        @Override
                        public void clickItem(int item) {
                            switch (item) {
                                case 1://拍照
                                    getPicture();
                                    break;
                                case 2://相册
                                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                    ((SetActivity)mContext).startActivityForResult(intent, 1);
                                    break;
                                case 3://取消

                                    break;
                                default:
                                    break;
                            }
                        }
                    });
                }
                break;
            case R.id.line_modify_pwd://密码修改
                //type 判断是找回密码还是修改密码    1  找回    2 修改
//                startActivity(new Intent(this, ChangePwdActivity.class).putExtra("type", 2));
                break;
            case R.id.line_feedback: //意见补充
                mContext.startActivity(new Intent(mContext, FaceBackActivity.class));
                break;
            case R.id.line_update:  //检查版本更新
//                startActivity(new Intent(this, VersionUpdateActivity.class));
                break;
            case R.id.tv_exit:
                mSetModels.loginOut();
                break;
            case R.id.line_helpCenter:
//                startActivity(new Intent(this, HelpCenterActivity.class));
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    private void getPicture() {
        //final String time = String.valueOf(System.currentTimeMillis())+".jpg";
        //判断内存卡是否存在
        String SDStart = Environment.getExternalStorageState();
        if (SDStart.equals(Environment.MEDIA_MOUNTED)) {
            File file1 = new File(Environment.getExternalStorageDirectory() + "/" + mContext.getString(R.string.app_name));//文件夹是否存在
            if (!file1.exists()) {
                file1.mkdir();
            }
            file = new File(Environment.getExternalStorageDirectory() + "/" + mContext.getString(R.string.app_name) + "/" + Constants.HEAD);

            //android 7.0
            //  Uri imageUri = FileProvider.getUriForFile(mContext, "com.lechuang.letaotao.fileProvider", file);
            //适配7.0和7.0一下的地址
            Uri imageUri = PhotoUtil.getUriForFile(mContext, file);
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            ((SetActivity)mContext).startActivityForResult(intent, 2);
        } else {
            Utils.showToast("内存卡不存在");
        }
    }

    @Override
    public void onUpDataInfoSuccess(UpdataInfoBean result) {
        SpreUtils.putString(mContext, "taobaoNumber", result.nickName);
        tvUserTaobao.setText(result.nickName);
    }

    @Override
    public void onUpHeadPhotoSuccess(UpFileBean result) {

    }

    @Override
    public void onLoginOutSuccess(String result) {
        //登出  清空数据
        mSession.loginOut();
        SpreUtils.clearPreferences(mContext);
        toast("退出成功！");
        //退出淘宝
//        exitTaobao();
        //退出成功发出通知到首页，更改当前界面显示，并刷新数据    todo
        finish();
    }

    @Override
    public void on300Error(int errorCode, String s) {
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onComplate() {
        dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode != -1) {
                    return;
                }
                Utils.resizeImage(data.getData(), (SetActivity)mContext, 3);
                break;
            case 2:
                if (resultCode != -1) {
                    return;
                }
                if (PhotoUtil.isSdcardExisting()) {
                    Uri uriForFile = PhotoUtil.getUriForFile(mContext, file);
                    Utils.resizeImage(uriForFile, (SetActivity)mContext, 3);
                } else {
                    Utils.showToast("未找到存储卡，无法存储照片");
                }
                break;
            case 3:
                if (data != null) {
                    showResizeImage(data);
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //退出淘宝
    private void exitTaobao() {
        AlibcLogin alibcLogin = AlibcLogin.getInstance();
        alibcLogin.logout((SetActivity)mContext, new LogoutCallback() {
            @Override
            public void onSuccess() {
                mSession.loginOut();
            }

            @Override
            public void onFailure(int i, String s) {
            }
        });

    }

    /**
     * 压缩图片
     *
     * @param data
     */
    private void showResizeImage(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap bitmap = extras.getParcelable("data");
            String path = Uri.fromFile(file).getPath();
            bitmap = PhotoUtil.toSmall(bitmap, path);
            ivSetHead.setImageBitmap(bitmap);
        }
        parts = new ArrayList<>();
        File file = new File(Environment.getExternalStorageDirectory() + "/" + mContext.getString(R.string.app_name) + "/" + Constants.HEAD);
        RequestBody requestFile = RequestBody
                .create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody
                .Part.createFormData("file", file.getName(), requestFile);
        parts.add(part);
        show();
        mSetModels.upHeadPhoto(parts);
    }
}

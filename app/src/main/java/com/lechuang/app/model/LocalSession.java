package com.lechuang.app.model;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Pair;


import com.lechuang.app.net.SessionManager;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.Observable;

/**
 * Created by zhf on 2017/8/18.
 */

public class LocalSession extends Observable implements Serializable {
    private static LocalSession mInstance;
    private String osVersion;
    private String version;
    private String token;
    private String safeToken;
    private boolean isLogin = false;
    private boolean isFirst = true;
    private String name;
    private String imge;//头像
    private String accountNumber;//淘宝账号
    private String phoneNumber;//手机号
    private String alipayNumber;//支付宝账号
    private int isAgencyStatus;//1是以代理，其它不是
    private String id;   //用户id 传给会员中心
    private WeakReference<Context> weakReference;
    private boolean isBind =false;  //是否绑定过邀请码
    private String openId;//openid
    private String weixinName;
    private String weixinPhoto;
    private String qqName;
    private String qqPhoto;

    public static LocalSession get(Context context) {
        if (mInstance == null) {
            mInstance = new LocalSession(context);
        }
        return mInstance;
    }

    private LocalSession(Context context) {
        synchronized (this) {
            weakReference = new WeakReference<>(context);
            osVersion = "Android" + android.os.Build.VERSION.RELEASE;
            version = getVERSION();
        }
    }


    private String getVERSION() {
        Context context = weakReference.get();
        if (context==null)return "";
        String version;

        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            version = info.versionName;

            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getVersion() {
        return version;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        super.setChanged();
        super.notifyObservers(new Pair<String, Object>(SessionManager.P_TOKEN,
                token));
    }

    public void setSafeToken(String safeToken) {
        this.safeToken = safeToken;
    }

    public String getSafeToken() {
        return safeToken;
    }

    public int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImge() {
        return imge;
    }

    public void setImge(String imge) {
        this.imge = imge;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAlipayNumber() {
        return alipayNumber;
    }

    public void setAlipayNumber(String alipayNumber) {
        this.alipayNumber = alipayNumber;
    }

    public boolean getIsBind() {
        return isBind;
    }

    public void setIsBind(boolean isBind) {
        this.isBind = isBind;
    }

    public int getIsAgencyStatus() {
        return isAgencyStatus;
    }

    public void setIsAgencyStatus(int isAgencyStatus) {
        this.isAgencyStatus = isAgencyStatus;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean isFirst) {
        if (this.isFirst == isFirst) {
            return;
        }

        this.isFirst = isFirst;

        super.setChanged();
        super.notifyObservers(new Pair<String, Object>(
                SessionManager.P_ISFIRST, isFirst));
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getWeixinName() {
        return weixinName;
    }

    public void setWeixinName(String weixinName) {
        this.weixinName = weixinName;
    }

    public String getWeixinPhoto() {
        return weixinPhoto;
    }

    public void setWeixinPhoto(String weixinPhoto) {
        this.weixinPhoto = weixinPhoto;
    }

    public String getQqName() {
        return qqName;
    }

    public void setQqName(String qqName) {
        this.qqName = qqName;
    }

    public String getQqPhoto() {
        return qqPhoto;
    }

    public void setQqPhoto(String qqPhoto) {
        this.qqPhoto = qqPhoto;
    }

   /* @Override
    public String toString() {
        return "LocalSession{" +
                "osVersion='" + osVersion + '\'' +
                ", version='" + version + '\'' +
                ", token='" + token + '\'' +
                ", safeToken='" + safeToken + '\'' +
                ", isLogin=" + isLogin +
                ", isFirst=" + isFirst +
                ", name='" + name + '\'' +
                ", imge='" + imge + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", alipayNumber='" + alipayNumber + '\'' +
                ", isAgencyStatus=" + isAgencyStatus +
                '}';
    }*/

    @Override
    public String toString() {
        return "LocalSession{" +
                "osVersion='" + osVersion + '\'' +
                ", version='" + version + '\'' +
                ", token='" + token + '\'' +
                ", safeToken='" + safeToken + '\'' +
                ", isLogin=" + isLogin +
                ", isFirst=" + isFirst +
                ", name='" + name + '\'' +
                ", imge='" + imge + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", alipayNumber='" + alipayNumber + '\'' +
                ", isAgencyStatus=" + isAgencyStatus +
                ", id='" + id + '\'' +
                ", isBind='" + isBind + '\'' +
                ", openId='" + openId + '\'' +
                ", weixinName='" + weixinName + '\'' +
                ", weixinPhoto='" + weixinPhoto + '\'' +
                ", qqName='" + qqName + '\'' +
                ", qqPhoto='" + qqPhoto + '\'' +
                '}';
    }

    //登出 数据清空
    public void loginOut() {
        osVersion = "";
        version = "";
        token = "";
        safeToken = "";
        isLogin = false;
        isFirst = false;
        name = "";
        imge = "";
        accountNumber = "";
        phoneNumber = "";
        alipayNumber = "";
        isAgencyStatus = 0;
        id = "";
        isBind = false;
        openId = "";
        weixinName = "";
        weixinPhoto = "";
        qqName = "";
        qqPhoto = "";
    }

}

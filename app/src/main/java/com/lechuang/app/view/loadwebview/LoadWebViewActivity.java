package com.lechuang.app.view.loadwebview;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.lechuang.app.R;
import com.lechuang.app.base.BaseActivity;
import com.lechuang.app.model.bean.HelpInfoNewBean;
import com.lechuang.app.net.Netword;
import com.lechuang.app.net.ResultBack;
import com.lechuang.app.net.netApi.CommenApi;
import com.zheng.webmanger.manger.WebViewManger;
import com.zheng.webmanger.view.CusWebView;
import com.zheng.webmanger.view.LoadView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoadWebViewActivity extends BaseActivity {

    @BindView(R.id.loading_layout)
    LoadView mLoadingLayout;
    @BindView(R.id.webview)
    CusWebView mWebview;
    @BindView(R.id.tv_title)
    TextView mTvTitle;

    private WebViewManger mWebViewManger;
    private int type = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_web_view);
        ButterKnife.bind(this);
    }

    @Override
    public void initCreateContent() {
        super.initCreateContent();
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        mTvTitle.setText(title);

        type = intent.getIntExtra("type", -1);
        mWebViewManger = new WebViewManger(mWebview);
        if (type >= 0) {
            getData();
        } else {
            mWebViewManger.setLoadUrl(intent.getStringExtra("url"));
        }

    }

    public void getData() {
        Netword.getInstance().getApi(CommenApi.class)
                .getHelpInfoNew(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultBack<HelpInfoNewBean>(this) {
                    @Override
                    public void successed(HelpInfoNewBean result) {
                        String helpInfo = result.helpInfoNew.helpInfo;
                        mWebview.loadData(helpInfo, "text/html; charset=UTF-8", null);
                    }
                });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mWebViewManger.onKeyDown(keyCode)) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}

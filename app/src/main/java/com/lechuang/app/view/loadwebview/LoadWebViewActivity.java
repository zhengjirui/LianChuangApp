package com.lechuang.app.view.loadwebview;

import android.os.Bundle;
import android.view.KeyEvent;

import com.lechuang.app.R;
import com.lechuang.app.base.BaseActivity;
import com.zheng.webmanger.manger.CusWebViewClient;
import com.zheng.webmanger.manger.WebViewManger;
import com.zheng.webmanger.view.CusWebView;
import com.zheng.webmanger.view.LoadView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadWebViewActivity extends BaseActivity implements LoadView.IReloadLisenter{

    @BindView(R.id.loading_layout)
    LoadView mLoadingLayout;
    @BindView(R.id.webview)
    CusWebView mWebview;

    private WebViewManger mWebViewManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_web_view);
        ButterKnife.bind(this);
    }

    @Override
    public void initCreateContent() {
        super.initCreateContent();

        mWebViewManger = new WebViewManger(mWebview);
        mWebViewManger.setLoadUrl("https://blog.csdn.net/maosidiaoxian/article/details/43148643");

        mWebViewManger.setWebViewClient(new CusWebViewClient(mWebview));
        mWebViewManger.setLoadingLayout(mLoadingLayout);
        mLoadingLayout.setOnReloadLisenter(this);
    }

    @Override
    public void onReloadLisenter() {
        mWebViewManger.setReloadUrl();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(mWebViewManger.onKeyDown(keyCode)){
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}

package com.lechuang.app.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lechuang.app.R;
import com.lechuang.app.base.BaseActivity;
import com.lechuang.app.base.BasePresenter;
import com.lechuang.app.presenter.activity.LoginPresenter;
import com.lechuang.app.view.ClearEditText;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        View viewById = findViewById(R.id.layout_status);
        viewById.setBackgroundColor(getResources().getColor(R.color.c_app_main_text));
        viewById.setAlpha(0.5f);
    }

    @Override
    protected BasePresenter onCreatePresenter(@Nullable Bundle savedInstanceState) {
        return mBasePresenter = new LoginPresenter(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, new IUiListener() {
            @Override
            public void onComplete(Object o) {

            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        });
    }

}

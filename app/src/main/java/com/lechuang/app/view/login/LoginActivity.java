package com.lechuang.app.view.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lechuang.app.R;
import com.lechuang.app.base.BaseActivity;
import com.lechuang.app.base.BasePresenter;
import com.lechuang.app.presenter.activity.LoginPresenterA;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected BasePresenter onCreatePresenter(@Nullable Bundle savedInstanceState) {
        return new LoginPresenterA(this);
    }
}

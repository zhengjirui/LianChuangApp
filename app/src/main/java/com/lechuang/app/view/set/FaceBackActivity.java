package com.lechuang.app.view.set;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lechuang.app.R;
import com.lechuang.app.base.BaseActivity;
import com.lechuang.app.base.BasePresenter;

public class FaceBackActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_back);
    }

    @Override
    protected BasePresenter onCreatePresenter(@Nullable Bundle savedInstanceState) {
        return mBasePresenter = new FaceBackA(this);
    }
}

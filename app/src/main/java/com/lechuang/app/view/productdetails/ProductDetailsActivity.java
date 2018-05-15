package com.lechuang.app.view.productdetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.lechuang.app.R;
import com.lechuang.app.base.BaseActivity;
import com.lechuang.app.base.BasePresenter;
import com.lechuang.app.presenter.activity.ProductDetailsA;


public class ProductDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        View viewById = findViewById(R.id.layout_status);
        viewById.setBackgroundColor(getResources().getColor(R.color.c_app_main_text));
        viewById.setAlpha(0.5f);
    }

    @Override
    protected BasePresenter onCreatePresenter(@Nullable Bundle savedInstanceState) {
        return mBasePresenter = new ProductDetailsA(this, getIntent());
    }
}

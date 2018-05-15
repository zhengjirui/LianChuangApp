package com.lechuang.app.view.productdetails;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lechuang.app.R;
import com.lechuang.app.base.BaseActivity;
import com.lechuang.app.base.BasePresenter;
import com.lechuang.app.presenter.activity.ProductDetailsShareA;

public class ProductDetailsShareActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details_share);
    }

    @Override
    protected BasePresenter onCreatePresenter(@Nullable Bundle savedInstanceState) {
        return mBasePresenter = new ProductDetailsShareA(this, getIntent());
    }
}

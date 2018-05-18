package com.lechuang.app.presenter.activity.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lechuang.app.App;
import com.lechuang.app.R;
import com.lechuang.app.model.bean.HomeKindBean;

import java.util.List;

/**
 * @author: LGH
 * @since: 2018/5/9
 * @describe:
 */

public class HomeKindAdapter extends BaseQuickAdapter<HomeKindBean.ListBean, BaseViewHolder> {


    public HomeKindAdapter(int layoutResId, @Nullable List<HomeKindBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeKindBean.ListBean item) {
        HomeKindBean.ListBean bean = item;
        if (bean != null){
            ImageView imageView = helper.getView(R.id.iv_kinds_img);
            //分类名称
            helper.setText(R.id.tv_kinds_name, bean.rootName);
            Glide.with(App.getInstance())
                    .load(bean.img)
                    .into(imageView);
        }


    }
}

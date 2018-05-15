package com.lechuang.app.presenter.activity.adapter;

import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lechuang.app.App;
import com.lechuang.app.R;
import com.lechuang.app.model.bean.GetBeanTabInfoList;
import com.lechuang.app.model.bean.HomeKindBean;
import com.lechuang.app.view.SpannelTextView;

import java.util.List;
import java.util.Locale;

/**
 * @author: LGH
 * @since: 2018/5/9
 * @describe:
 */

public class ZhuanMoneyProductAdapter extends BaseQuickAdapter<GetBeanTabInfoList.ListInfo, BaseViewHolder> {


    public ZhuanMoneyProductAdapter(int layoutResId, @Nullable List<GetBeanTabInfoList.ListInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetBeanTabInfoList.ListInfo item) {
        if (item != null){
            try {
            Glide.with(App.getInstance()).load(item.img).into((ImageView) helper.getView(R.id.img));
            Glide.get(App.getInstance()).setMemoryCategory(MemoryCategory.LOW);


            helper.setText(R.id.price, item.price);
            ((TextView) helper.getView(R.id.price)).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            helper.setText(R.id.preferentialPrice, "¥ " + item.preferentialPrice);
            helper.setText(R.id.nowNumber, "已售出 " + item.nowNumber + " 件");
            helper.setText(R.id.couponMoney, String.format(Locale.CHINESE, "%d元", item.couponMoney));
//        helper.setText(R.id.zhuanMoney, String.format("赚:%s", listInfo.zhuanMoney));
            helper.setText(R.id.zhuanMoney, item.zhuanMoney);
            SpannelTextView productName = helper.getView(R.id.spannelTextView);
            productName.setShopType(item.shopType);

            //商品名称
            if (item.productName != null && !item.productName.equalsIgnoreCase("")) {
                productName.setDrawText(item.productName);
            } else if (item.name != null && !item.name.equalsIgnoreCase("")) {
                productName.setDrawText(item.name);
            } else {
                productName.setDrawText("");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

//            final ImageView share = helper.getView(R.id.share);
//
//            final LinearLayout ll_share = helper.getView(R.id.ll_share);
//
//            final int adapterPosition = helper.getAdapterPosition();
//            ll_share.setTag(adapterPosition);
////        final CommFragment.InfoHolder tempHolder = holder;
//            ll_share.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });

//            ImageView imageView = helper.getView(R.id.iv_kinds_img);
//            //分类名称
//            helper.setText(R.id.tv_kinds_name, bean.rootName);
//            Glide.with(App.getInstance())
//                    .load(bean.img)
//                    .into(imageView);
        }


    }
}

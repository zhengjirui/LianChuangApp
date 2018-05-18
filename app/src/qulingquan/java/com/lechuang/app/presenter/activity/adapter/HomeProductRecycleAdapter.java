package com.lechuang.app.presenter.activity.adapter;

import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lechuang.app.App;
import com.lechuang.app.R;
import com.lechuang.app.model.bean.HomeKindBean;
import com.lechuang.app.model.bean.HomeLastProgramBean;
import com.lechuang.app.view.SpannelTextView;

import java.util.List;

/**
 * @author: LGH
 * @since: 2018/5/9
 * @describe:
 */

public class HomeProductRecycleAdapter extends BaseQuickAdapter<HomeLastProgramBean.ListBean, BaseViewHolder> {

    private int mIsAgencyStatus;//是否是代理

    public HomeProductRecycleAdapter(int layoutResId, @Nullable List<HomeLastProgramBean.ListBean> data, int isAgencyStatus) {
        super(layoutResId, data);
        this.mIsAgencyStatus = isAgencyStatus;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeLastProgramBean.ListBean item) {
        HomeLastProgramBean.ListBean bean = item;
        if (bean != null){

            //动态调整滑动时的内存占用
            Glide.get(mContext).setMemoryCategory(MemoryCategory.LOW);

            //商品图
            ImageView ivImg = helper.getView(R.id.iv_img);
            Glide.with(mContext).load(bean.imgs).into(ivImg);

            //原价
            helper.setText(R.id.tv_oldprice,bean.price + "");

            //中划线
            TextView tvOldprice = helper.getView(R.id.tv_oldprice);
            tvOldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            //券后价
            helper.setText(R.id.tv_nowprice,"¥" + bean.preferentialPrice);

            //商品名称
            SpannableString ss = new SpannableString(bean.name);
            ImageSpan imageSpan1;
            if ("1".equals(bean.shopType)) {
                imageSpan1 = new ImageSpan(mContext, R.mipmap.ic_taobao);
            } else {
                imageSpan1 = new ImageSpan(mContext, R.mipmap.ic_tianmao);
            }
            ss.setSpan(imageSpan1, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            SpannelTextView stvProductName = helper.getView(R.id.stv_product_name);
            stvProductName.setShopType(Integer.parseInt(bean.shopType));
            stvProductName.setDrawText(bean.name);


            //赚,判断是否为代理

            if (mIsAgencyStatus == 1) {
                helper.getView(R.id.tv_get).setVisibility(View.VISIBLE);
            } else {
                helper.getView(R.id.tv_get).setVisibility(View.GONE);
            }

            //销量
            helper.setText(R.id.tv_xiaoliang,"已抢" + bean.nowNumber + "件");

            //领券减金额
            helper.setText(R.id.tv_quanMoney,"领券减" + bean.couponMoney);
//            helper.getView(R.id.ll_layout).setTag(position);
//            helper.getView(R.id.ll_layout).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mOnItemClickLitener != null) {
//                        //注意这里使用getTag方法获取数据
//                        mOnItemClickLitener.onItemClick(v, (int) v.getTag());
//                    }
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

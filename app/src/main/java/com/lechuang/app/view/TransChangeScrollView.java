package com.lechuang.app.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import com.lechuang.app.lisenters.IOnScrollChangedListener;
import com.lechuang.app.lisenters.ITransChangeLisenter;

/**
 * Created by cmd on 2018/5/6.
 */

public class TransChangeScrollView extends ScrollView implements ITransChangeLisenter {

    private ITransChangeLisenter mITransChangeLisenter;
    private View mTransChangeView;
    private float mHeightPixels;
    private IOnScrollChangedListener onScrollChangedListener;

    public TransChangeScrollView(Context context) {
        this(context,null);
    }

    public TransChangeScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TransChangeScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mITransChangeLisenter = this;
        mHeightPixels = getContext().getResources().getDisplayMetrics().heightPixels;

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mITransChangeLisenter.onScrollChangeLisenter(l, t, oldl, oldt);
    }

    /**
     * 设置需要渐变view
     *
     * @param transChangeView
     */
    public void setTransparentChange(View transChangeView) {
        this.mTransChangeView = transChangeView;
    }

    public void setOnScrollChangedListener(IOnScrollChangedListener onScrollChangedListener) {
        this.onScrollChangedListener = onScrollChangedListener;
    }

    @Override
    public void onScrollChangeLisenter(int l, int t, int oldl, int oldt) {
        onScrollChangedListener.onScrollChanged(l, t, oldl, oldt);
        if (mTransChangeView != null) {
            // alpha = 滑出去的高度/(screenHeight/3);

//            float scrollY = getScrollY();//该值 大于0
//            float alpha = scrollY / (mHeightPixels / 3);// 0~1  透明度是1~0
////            float alpha = 1 - scrollY / (mHeightPixels / 3);// 0~1  透明度是1~0
//            //这里选择的screenHeight的1/3 是alpha改变的速率 （根据你的需要你可以自己定义）
//            mTransChangeView.setAlpha(alpha);
            float scrollY = mHeightPixels / 4;//设置在某一滑动范围内实行渐变
            if (t <= 0) {
                mTransChangeView.setBackgroundColor(Color.argb(0, 255, 0, 0));
            } else if (t > 0 && t <= scrollY) {
                float scale = t / scrollY;// 0~1  透明度是1~0
                float alpha = (255 * scale);
                mTransChangeView.setBackgroundColor(Color.argb((int) alpha, 255, 0, 0));
            } else {
                mTransChangeView.setBackgroundColor(Color.argb( 255, 255, 0, 0));
            }
        }
    }

}

package com.lechuang.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lechuang.app.R;
import com.lechuang.app.lisenters.INavTabLisenter;
import com.lechuang.app.utils.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jinding on 2018/3/10.
 */

public class NavTabLayout extends LinearLayout implements View.OnClickListener {
    private final String TAG = "CusLinearLayout";
    private Context mContent;
    private int mTabCurrent;//默认显示的下标为0
    private TextView mTabItem1,mTabItem2,mTabItem3,mTabItem4,mTabItem5;
    private List<TextView> itemView;
    private INavTabLisenter mINavTabLisenter;

    public NavTabLayout(Context context) {
        this(context,null);
    }

    public NavTabLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public NavTabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContent = context;
        itemView = new ArrayList<>();
        initView(attrs);
    }

    public void initView(@Nullable AttributeSet attrs){
        TypedArray typedArray = mContent.obtainStyledAttributes(attrs, R.styleable.NavLayout);
        mTabCurrent = typedArray.getInteger(R.styleable.NavLayout_navTabCurrent, 0);
        //添加布局
        View inflate = LayoutInflater.from(mContent).inflate(R.layout.nav_bottom_layout, this,false);
        this.addView(inflate);

        mTabItem1 = inflate.findViewById(R.id.tab_item1_txt);
        mTabItem2 = inflate.findViewById(R.id.tab_item2_txt);
        mTabItem3 = inflate.findViewById(R.id.tab_item3_txt);
        mTabItem4 = inflate.findViewById(R.id.tab_item4_txt);
        mTabItem5 = inflate.findViewById(R.id.tab_item5_txt);


        itemView.add(mTabItem1);
        itemView.add(mTabItem2);
        itemView.add(mTabItem3);
        itemView.add(mTabItem4);
        itemView.add(mTabItem5);
//
//        setDrawableTop(mTabItem1,R.drawable.nav_tab_item1_select);
//        setDrawableTop(mTabItem2,R.drawable.nav_tab_item2_select);
//        setDrawableTop(mTabItem3,R.drawable.nav_tab_item3_select);
//        setDrawableTop(mTabItem4,R.drawable.nav_tab_item4_select);
//        setDrawableTop(mTabItem5,R.drawable.nav_tab_item5_select);

        mTabItem1.setOnClickListener(this);
        mTabItem2.setOnClickListener(this);
        mTabItem3.setOnClickListener(this);
        mTabItem4.setOnClickListener(this);
        mTabItem5.setOnClickListener(this);

        setTabCurrent(mTabCurrent);
    }

    private void setDrawableTop(TextView tab,int select){
        Drawable drawable = getResources().getDrawable(select);
        drawable.setBounds(0, 0, Utils.dp2px(mContent, 20), Utils.dp2px(mContent, 20));
        tab.setCompoundDrawables(null, drawable, null, null);
        tab.setCompoundDrawablePadding(Utils.dp2px(mContent,5));//设置图片和文字之间的高度
    }

    /**
     * 设置item文字
     * @param itemName
     */
    public void setItemName(List<String> itemName){
        for(int i = 0; i < itemName.size(); i++){
            itemView.get(i).setText(itemName.get(i));
        }
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        setAllUnSelect();
        if ( id== R.id.tab_item1_txt ){
            mTabItem1.setSelected(true);
            if(mINavTabLisenter != null){
                mINavTabLisenter.INavCurrentLisenter(0);
            }
        }else if(id== R.id.tab_item2_txt){
            mTabItem2.setSelected(true);
            if(mINavTabLisenter != null){
                mINavTabLisenter.INavCurrentLisenter(1);
            }
        }else if(id== R.id.tab_item3_txt){
            mTabItem3.setSelected(true);
            if(mINavTabLisenter != null){
                mINavTabLisenter.INavCurrentLisenter(2);
            }
        }else if(id== R.id.tab_item4_txt){
            mTabItem4.setSelected(true);
            if(mINavTabLisenter != null){
                mINavTabLisenter.INavCurrentLisenter(3);
            }
        }else if(id== R.id.tab_item5_txt){
            mTabItem5.setSelected(true);
            if(mINavTabLisenter != null){
                mINavTabLisenter.INavCurrentLisenter(4);
            }
        }
    }

    private void setAllUnSelect(){
        mTabItem1.setSelected(false);
        mTabItem2.setSelected(false);
        mTabItem3.setSelected(false);
        mTabItem4.setSelected(false);
        mTabItem5.setSelected(false);
    }

    public void setTabCurrent(int tabCurrent){
        setAllUnSelect();
        if ( tabCurrent== 0 ){
            mTabItem1.setSelected(true);
        }else if(tabCurrent== 1){
            mTabItem2.setSelected(true);
        }else if(tabCurrent== 2){
            mTabItem3.setSelected(true);
        }else if(tabCurrent== 3){
            mTabItem4.setSelected(true);
        }else if(tabCurrent== 4){
            mTabItem5.setSelected(true);
        }
        if(mINavTabLisenter != null ){
            mINavTabLisenter.INavCurrentLisenter(tabCurrent);
        }

    }

    public void setNavTabLisenter(INavTabLisenter iNavTabLisenter){
        this.mINavTabLisenter = iNavTabLisenter;
    }

}

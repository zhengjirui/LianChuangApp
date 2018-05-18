package com.lechuang.app.presenter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lechuang.app.R;
import com.lechuang.app.base.BasePresenter;
import com.lechuang.app.base.Constants;
import com.lechuang.app.base.lisenters.IBaseView;
import com.lechuang.app.model.FenLeiModels;
import com.lechuang.app.model.bean.BaseEventBean;
import com.lechuang.app.model.bean.ClassInfoBean;
import com.lechuang.app.model.bean.GoodsTypeInfoBean;
import com.lechuang.app.model.bean.RefreshLoadStateBean;
import com.lechuang.app.presenter.activity.adapter.MyGroupAdapter;
import com.lechuang.app.presenter.activity.adapter.MyHeadAdapter;
import com.lechuang.app.utils.SpreUtils;
import com.lechuang.app.view.fenlei.fragment.FenLeiFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * @author: LGH
 * @since: 2018/5/3
 * @describe:
 */

public class FenLeiPresenterF extends BasePresenter implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener, MyGroupAdapter.OnItemsClickListener, StickyListHeadersListView.OnHeaderClickListener {


    @BindView(R.id.iv_get_money)
    ImageView mIvGetMoney;
    @BindView(R.id.tv_search)
    TextView mTvSearch;
    @BindView(R.id.iv_sign)
    ImageView mIvSign;
    @BindView(R.id.lv_left)
    ListView mlvLeft;
    @BindView(R.id.lv_right)
    StickyListHeadersListView mlvRight;


    public int iconB[] = {R.mipmap.icon_nvzhuang, R.mipmap.icon_nanzhuang, R.mipmap.icon_neiyi,
            R.mipmap.icon_muying, R.mipmap.icon_meizhuang, R.mipmap.icon_jiaju, R.mipmap.icon_xiebao
            , R.mipmap.icon_shuma, R.mipmap.icon_meishi, R.mipmap.icon_chepin, R.mipmap.icon_gengsuo};
    public int iconsB[] = {R.mipmap.icon_nvzhuang_s, R.mipmap.icon_nanzhuang_s, R.mipmap.icon_neiyi_s,
            R.mipmap.icon_muying_s, R.mipmap.icon_meizhuang_s, R.mipmap.icon_jiaju_s, R.mipmap.icon_xiebao_s
            , R.mipmap.icon_shuma_s, R.mipmap.icon_meishi_s, R.mipmap.icon_chepin_s, R.mipmap.icon_gengduo_s};

    String names[] = {"女装", "男装", "内衣", "母婴", "美妆", "家居", "鞋包", "数码", "美食", "车品", "更多"};
    int rootId[] = {1, 3, 2, 4, 5, 6, 7, 10, 8, 9, 11};
    public ArrayList<GoodsTypeInfoBean> datas = new ArrayList<>();
    private MyHeadAdapter myHeadAdapter;
    public MyGroupAdapter myGroupAdapter;

    public FenLeiPresenterF(IBaseView mIBaseView) {
        super(mIBaseView);
    }


    @Override
    public void addLayoutView(LayoutInflater inflater, FrameLayout scrollContentView, Bundle savedInstanceState) {
        super.addLayoutView(inflater, scrollContentView, savedInstanceState);
        inflater.inflate(R.layout.fragment_fenlei, scrollContentView);
        EventBus.getDefault().register(this);
        setEnableRefresh(true);

    }
    FenLeiModels mFenLeiModels;
    @Override
    public void initCreateContent() {
        super.initCreateContent();
        initViewData();
        mFenLeiModels = new FenLeiModels(mContext);
        mFenLeiModels.getFenLeiData();
    }

    @Override
    public void onRefreshListener() {
        super.onRefreshListener();
//        show();
        mFenLeiModels.getFenLeiData();
    }

    @OnClick({R.id.iv_get_money, R.id.tv_search, R.id.iv_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_get_money:
                break;
            case R.id.tv_search:
//                startActivity(new Intent(getActivity(), SearchActivity.class).putExtra("whereSearch", 1));
                break;
            case R.id.iv_sign:
                break;
        }
    }


    //右边点击条目的回调p
    @Override
    public void onItemsClick(View view, int position, ClassInfoBean.ClassTypeListBean bean) {
        if (SpreUtils.getBoolean(mContext, Constants.KEY_HAS_LOGIN,false)) {
//            Intent intent = new Intent(getActivity(), SearchResultActivity1.class);
//            //传递一个值,搜索结果页面用来判断是从分类还是搜索跳过去的 1:分类 2:搜索界面
//            intent.putExtra("type", 2);
//            //rootName传递过去显示在搜索框上
//            intent.putExtra("rootName", bean.tbClassTypeList.get(position).name);
//            //rootId传递过去入参
//            intent.putExtra("rootId",bean.tbClassTypeList.get(position).name);
//            startActivity(intent);
        }else {
//            startActivity(new Intent(getActivity(), LoginActivity.class));
        }

    }
    //右边点击头部的回调
    @Override
    public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition, long headerId, boolean currentlySticky) {
        if (SpreUtils.getBoolean(mContext, Constants.KEY_HAS_LOGIN,false)) {
//            Intent intent = new Intent(getActivity(), SearchResultActivity1.class);
//            //传递一个值,搜索结果页面用来判断是从分类还是搜索跳过去的 1:分类 2:搜索界面
//            intent.putExtra("type", 1);
//            //rootName传递过去显示在搜索框上
//            intent.putExtra("rootName",classTypeList.get(itemPosition).rootName);
//            //rootId传递过去入参
//            intent.putExtra("rootId", datas.get(itemPosition).rootId+"");
//            startActivity(intent);
        }else {
//            startActivity(new Intent(getActivity(), LoginActivity.class));
        }

    }

    private void initViewData() {
        for (int i = 0; i < iconB.length; i++) {
            GoodsTypeInfoBean infoBean = new GoodsTypeInfoBean();
            infoBean.name = names[i];
            infoBean.icon = iconB[i];
            infoBean.groupFirstIndex = i;
            infoBean.icons = iconsB[i];
            infoBean.rootId=rootId[i];
            datas.add(infoBean);
        }
        myHeadAdapter = new MyHeadAdapter(datas, mContext);

        mlvLeft.setAdapter(myHeadAdapter);

        mlvLeft.setOnItemClickListener(this);

    }

    boolean isScroll = false;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // 用户在滚动
        isScroll = true;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // 用户的滚动
        if (isScroll) {
            // 当前正在置顶显示的头高亮处理
            myHeadAdapter.setSelectedPositon(firstVisibleItem);
            // 判断头容器对应的条目是否处于可见状态
            // 获取到第一个可见，和最后一个可见的。比第一个小的，或者比最后一个大的均为不可见
            int firstVisiblePosition = mlvLeft.getFirstVisiblePosition();
            int lastVisiblePosition = mlvLeft.getLastVisiblePosition();
            if (firstVisibleItem< firstVisiblePosition ||firstVisibleItem > lastVisiblePosition) {
                mlvLeft.setSelection(firstVisibleItem);// 可见处理
            }
        }


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        myHeadAdapter.setSelectedPositon(position);
        mlvRight.setSelection(position);
        isScroll = false;
    }

    private void updata(ArrayList<ClassInfoBean.ClassTypeListBean> mClassTypeList){
        myGroupAdapter = new MyGroupAdapter(datas, mClassTypeList, mContext);
        mlvRight.setAdapter(myGroupAdapter);
        mlvRight.setOnScrollListener(FenLeiPresenterF.this);
        myGroupAdapter.setOnItemsClickListener(FenLeiPresenterF.this);
        mlvRight.setOnHeaderClickListener(FenLeiPresenterF.this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void fenLeiData(BaseEventBean eventBean) {
//        dismiss();

        if(eventBean instanceof ClassInfoBean && eventBean != null){
            ClassInfoBean classInfoBean = (ClassInfoBean) eventBean;
            updata(classInfoBean.classTypeList);
            mSmartRefresh.finishRefresh(true);
        }else if(eventBean instanceof RefreshLoadStateBean){
            RefreshLoadStateBean refreshLoadStateBean = (RefreshLoadStateBean) eventBean;
            toast(refreshLoadStateBean.info);
            mSmartRefresh.finishRefresh(false);
        }
    }
}

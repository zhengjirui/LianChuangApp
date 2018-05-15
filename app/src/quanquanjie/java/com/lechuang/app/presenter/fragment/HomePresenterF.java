package com.lechuang.app.presenter.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lechuang.app.R;
import com.lechuang.app.base.BasePresenter;
import com.lechuang.app.base.Constants;
import com.lechuang.app.base.lisenters.IBaseView;
import com.lechuang.app.lisenters.IOnScrollChangedListener;
import com.lechuang.app.model.HomeModels;
import com.lechuang.app.model.bean.BaseEventBean;
import com.lechuang.app.model.bean.GetBeanTablayout;
import com.lechuang.app.model.bean.HomeBannerBean;
import com.lechuang.app.model.bean.HomeDefaultKindBean;
import com.lechuang.app.model.bean.HomeGunDongTextBean;
import com.lechuang.app.model.bean.HomeKindBean;
import com.lechuang.app.model.bean.HomeLastProgramBean;
import com.lechuang.app.model.bean.HomeProgramBean;
import com.lechuang.app.model.bean.HomeTipoffListBean;
import com.lechuang.app.model.bean.HomeTodayProductBean;
import com.lechuang.app.presenter.activity.adapter.HomeKindAdapter;
import com.lechuang.app.presenter.activity.adapter.HomeProductRecycleAdapter;
import com.lechuang.app.view.AutoTextView;
import com.lechuang.app.view.TransChangeScrollView;
import com.lechuang.app.view.productdetails.ProductDetailsActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: LGH
 * @since: 2018/5/3
 * @describe:
 */

public class HomePresenterF extends BasePresenter implements IOnScrollChangedListener {

    @BindView(R.id.header)
    View mHeader;
    @BindView(R.id.refreshview)
    TransChangeScrollView mRefreshView;
    @BindView(R.id.banner_home_top)
    Banner mBannerHomeTop;
    @BindView(R.id.recycle_home_Kind)
    RecyclerView mRecycleHomeKind;
    @BindView(R.id.tv_auto_text)
    AutoTextView mAutoTextView;
    @BindView(R.id.iv_program1)
    ImageView mIvProgram1;
    @BindView(R.id.iv_program3)
    ImageView mIvProgram3;
    @BindView(R.id.iv_program2)
    ImageView mIvProgram2;
    @BindView(R.id.iv_program4)
    ImageView mIvProgram4;
    @BindView(R.id.tablayout_home_bellow)
    TabLayout mTablayoutHomeBellow;
    @BindView(R.id.recycle_home_product)
    RecyclerView mRecycleHomeProduct;
    @BindView(R.id.tablayout_home_top)
    TabLayout mTablayoutHomeTop;


    HomeModels mHomeModels;
    private View mFragmentHome;
    private DisplayMetrics mDisplayMetrics;


    public HomePresenterF(IBaseView mIBaseView) {
        super(mIBaseView);
    }


    @Override
    public void addLayoutView(LayoutInflater inflater, FrameLayout scrollContentView, Bundle savedInstanceState) {
        super.addLayoutView(inflater, scrollContentView, savedInstanceState);
        mFragmentHome = inflater.inflate(R.layout.fragment_home, scrollContentView);
        EventBus.getDefault().register(this);
        mLayoutStatus.setVisibility(View.GONE);
        mDisplayMetrics = mContext.getResources().getDisplayMetrics();
        setEnableRefresh(true);
        setEnableLoadMore(true);
    }

    @Override
    public void initCreateContent() {
        super.initCreateContent();
        mHeader.setBackgroundColor(Color.argb(0, 255, 0, 0));
        mRefreshView.setTransparentChange(mHeader);
        mRefreshView.setOnScrollChangedListener(this);

        //初始化banner
        initBannerData();

        //初始化kindRecycle数据
        initKingRecycleData();

        //初始化快播数据
        initKuaiboData();

        //初始化底部和顶部tab的数据
        initTablayoutData();

        //初始化底部商品的数据
        initProductRecycleData();


        loadAllData();
    }

    private void loadAllData() {
        mHomeModels = new HomeModels(mContext);
        mHomeModels.getHomeBannerData();
        mHomeModels.getHomeKindData_1();
        mHomeModels.getHomeKindData_2();
        mHomeModels.getHomeProgram();
        mHomeModels.getHomeScrollTextView_1();
        mHomeModels.getHomeScrollTextView_2();
        mHomeModels.getProductList("1", "0");
        mHomeModels.getTabData();
        mHomeModels.getTodayProduct(1);
    }


    /**
     * 初始化banner
     */
    private void initBannerData() {

        //设置指示器位置（当banner模式中有指示器时）
        mBannerHomeTop.setIndicatorGravity(BannerConfig.CENTER);
        mBannerHomeTop.isAutoPlay(true);//禁止轮播
        mBannerHomeTop.setDelayTime(2000);
        mBannerHomeTop.setViewPagerIsScroll(true);//开启手动滑动

        final List<String> images = new ArrayList<>();
        //设置图片集合
        mBannerHomeTop.setImages(images);
        mBannerHomeTop.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, final ImageView imageView) {

                //Glide 加载图片简单用法
                Glide.with(context)
                        .load(path)
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                                Bitmap scaleBitmap = getScaleBitmap(resource, mDisplayMetrics.widthPixels);
                                ViewGroup.LayoutParams params = mBannerHomeTop.getLayoutParams();
                                params.height = scaleBitmap.getHeight();
                                params.width = scaleBitmap.getWidth();
                                mBannerHomeTop.setLayoutParams(params);
                                scaleBitmap.recycle();
                                imageView.setImageBitmap(resource);
                            }
                        });

            }
        });

        //banner设置方法全部调用完毕时最后调用
        mBannerHomeTop.start();

        mBannerHomeTop.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (position == images.size() - 1) {
                    //点击跳转界面
                }
            }
        });
    }

    /**
     * 更新banner
     *
     * @param homeBannerBean
     */
    private void updateBannerData(HomeBannerBean homeBannerBean) {
        //banner数据更新
        List<HomeBannerBean.IndexBannerList> indexBannerList0 = homeBannerBean.indexBannerList0;
        final List<String> images = new ArrayList<>();
        if (indexBannerList0 != null) {
            for (HomeBannerBean.IndexBannerList s : indexBannerList0) {
                images.add(s.img);
            }
        }
        mBannerHomeTop.update(images);
    }


    /**
     * 初始化kindRecycle数据
     */
    HomeKindAdapter homeKindAdapter;

    private void initKingRecycleData() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 5);
        mRecycleHomeKind.setNestedScrollingEnabled(false);
        mRecycleHomeKind.setLayoutManager(gridLayoutManager);
        HomeKindBean homeKindBean = new HomeKindBean();
        homeKindAdapter = new HomeKindAdapter(R.layout.home_kinds_item, homeKindBean.tbclassTypeList);
        mRecycleHomeKind.setAdapter(homeKindAdapter);
        homeKindAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                toast(position + "");
            }
        });
    }

    private void updataHomeKind(HomeKindBean homeKindBean) {
        List<HomeKindBean.ListBean> tbclassTypeList = homeKindBean.tbclassTypeList;
        List<HomeKindBean.ListBean> newData = new ArrayList<>();

        for (int i = 0; i <= 9; i++) {
            newData.add(tbclassTypeList.get(i));
        }
        homeKindAdapter.setNewData(newData);
    }

    /**
     * 初始化快播的数据
     */
    private void initKuaiboData() {
        ArrayList<String> text = new ArrayList<>();
        mAutoTextView.setTextAuto(text);
        //设置时间
        mAutoTextView.setGap(3000);
        mAutoTextView.setOnItemClickListener(new AutoTextView.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                startActivity(new Intent(getActivity(), ProductDetailsActivity.class)
//                        .putExtra(Constants.listInfo, JSON.toJSONString(list.get(position))));
            }
        });
    }

    private void updataKuaiboData(HomeGunDongTextBean homeGunDongTextBean) {
        List<HomeGunDongTextBean.IndexMsgListBean> list = homeGunDongTextBean.indexMsgList;
        ArrayList<String> text = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            text.add(list.get(i).productName);
        }
        mAutoTextView.setTextAuto(text);
    }

    /**
     * 更新商品栏目
     */
    private void updataProgramData(HomeProgramBean homeProgramBean) {
        List<HomeProgramBean.ListBean> programaImgList = homeProgramBean.programaImgList;
        //栏目1
        if (programaImgList.get(0) != null)
            Glide.with(mContext).load(programaImgList.get(0).img).placeholder(mContext.getResources().getDrawable(R.mipmap.home_program_jiazaitu)).into(mIvProgram1);
        //栏目2
        if (programaImgList.get(1) != null)
            Glide.with(mContext).load(programaImgList.get(1).img).placeholder(mContext.getResources().getDrawable(R.mipmap.home_program_jiazaitu)).into(mIvProgram2);
        //栏目3
        if (programaImgList.get(2) != null)
            Glide.with(mContext).load(programaImgList.get(2).img).placeholder(mContext.getResources().getDrawable(R.mipmap.home_program_jiazaitu)).into(mIvProgram3);

        //栏目4
        if (programaImgList.get(3) != null)
            Glide.with(mContext).load(programaImgList.get(3).img).placeholder(mContext.getResources().getDrawable(R.mipmap.home_program_jiazaitu)).into(mIvProgram4);
    }

    private int tabCurrentPositon = 0;

    private void initTablayoutData() {

        //上面的tab
        //tab可滚动
        mTablayoutHomeTop.setTabMode(TabLayout.MODE_SCROLLABLE);
        //tab居中显示
        mTablayoutHomeTop.setTabGravity(TabLayout.GRAVITY_CENTER);

        //下面的tab
        //tab可滚动
        mTablayoutHomeBellow.setTabMode(TabLayout.MODE_SCROLLABLE);
        //tab居中显示
        mTablayoutHomeBellow.setTabGravity(TabLayout.GRAVITY_CENTER);


//        float scaleTopX = mTablayoutHomeTop.getScaleX();
//        float scaleBellowX = mTablayoutHomeBellow.getScaleX();
//        if(scaleTopX != scaleBellowX){
//            if(mTablayoutHomeTop.getVisibility() == View.VISIBLE){
//                mTablayoutHomeTop.setScaleX(scaleTopX);
//                mTablayoutHomeBellow.setScaleX(scaleTopX);
//            }else {
//                mTablayoutHomeTop.setScaleX(scaleBellowX);
//                mTablayoutHomeBellow.setScaleX(scaleBellowX);
//            }
//        }

        TabSelected tabSelected = new TabSelected();
        mTablayoutHomeTop.addOnTabSelectedListener(tabSelected);
        mTablayoutHomeBellow.addOnTabSelectedListener(tabSelected);
    }

    @OnClick({R.id.iv_program1, R.id.iv_program3, R.id.iv_program2, R.id.iv_program4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_program1:
                break;
            case R.id.iv_program3:
                break;
            case R.id.iv_program2:
                break;
            case R.id.iv_program4:
                break;
        }
    }

    class TabSelected implements TabLayout.OnTabSelectedListener {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            tabCurrentPositon = tab.getPosition();
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }

    }

    /**
     * 更新TablayoutData
     */

    private void updataTablayoutData(GetBeanTablayout getBeanTablayout) {
        for (GetBeanTablayout.TopTab tab : getBeanTablayout.tbclassTypeList) {
            mTablayoutHomeTop.addTab(mTablayoutHomeTop.newTab().setText(tab.rootName));
        }

        for (GetBeanTablayout.TopTab tab : getBeanTablayout.tbclassTypeList) {
            mTablayoutHomeBellow.addTab(mTablayoutHomeBellow.newTab().setText(tab.rootName));
        }
    }

    /**
     * 初始化Product商品数据
     */
    HomeProductRecycleAdapter mHomeProductRecycleAdapter;

    private void initProductRecycleData() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        mRecycleHomeProduct.setNestedScrollingEnabled(false);
        mRecycleHomeProduct.setLayoutManager(gridLayoutManager);
        HomeLastProgramBean homeLastProgramBean = new HomeLastProgramBean();
        mHomeProductRecycleAdapter = new HomeProductRecycleAdapter(R.layout.home_product_item, homeLastProgramBean.productList, 1);
        mRecycleHomeProduct.setAdapter(mHomeProductRecycleAdapter);
        mHomeProductRecycleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                toast(position + "");
                Intent intent = new Intent(mContext, ProductDetailsActivity.class);
                intent.putExtra(Constants.listInfo, JSON.toJSONString(adapter.getData().get(position)));
                mContext.startActivity(intent);
            }
        });
    }

    /**
     * 更新Product
     */
    private void updataProductData(HomeLastProgramBean homeLastProgramBean) {
        //实现方式一
        List<HomeLastProgramBean.ListBean> productList = homeLastProgramBean.productList;
        mHomeProductRecycleAdapter.setNewData(productList);

    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        mBannerHomeTop.startAutoPlay();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        mBannerHomeTop.stopAutoPlay();
    }

    @Override
    public void onRefreshListener() {
        super.onRefreshListener();
        loadAllData();
        mSmartRefresh.finishRefresh(2000);
    }

    @Override
    public void onLoadMoreListener() {
        super.onLoadMoreListener();
        mSmartRefresh.finishLoadMore(2000);
    }

    @Override
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (t >= (mTablayoutHomeBellow.getTop() - mHeader.getHeight())) {
            mTablayoutHomeTop.setVisibility(View.VISIBLE);
        } else {
            mTablayoutHomeTop.setVisibility(View.INVISIBLE);
        }
        mTablayoutHomeTop.setScrollPosition(tabCurrentPositon, 0, true);
        mTablayoutHomeBellow.setScrollPosition(tabCurrentPositon, 0, true);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void homeData(BaseEventBean eventBean) {
        if (eventBean instanceof HomeBannerBean) {
            //设置banner
            HomeBannerBean homeBannerBean = (HomeBannerBean) eventBean;
            //更新banner
            updateBannerData(homeBannerBean);
            toast("设置banner");
        } else if (eventBean instanceof HomeDefaultKindBean) {
            //初始化kindRecycle数据
//            initKingRecyckeData();
            toast("2");
        } else if (eventBean instanceof HomeKindBean) {
            HomeKindBean homeKindBean = (HomeKindBean) eventBean;
            //更新kindData
            updataHomeKind(homeKindBean);
            toast("3");
        } else if (eventBean instanceof HomeTipoffListBean) {
            toast("4");
        } else if (eventBean instanceof HomeGunDongTextBean) {
            HomeGunDongTextBean homeGunDongTextBean = (HomeGunDongTextBean) eventBean;
            updataKuaiboData(homeGunDongTextBean);
            toast("5");
        } else if (eventBean instanceof HomeProgramBean) {
            HomeProgramBean homeProgramBean = (HomeProgramBean) eventBean;
            updataProgramData(homeProgramBean);
            toast("6");
        } else if (eventBean instanceof HomeTodayProductBean.ProudList) {
            toast("7");
        } else if (eventBean instanceof GetBeanTablayout) {
            GetBeanTablayout getBeanTablayout = (GetBeanTablayout) eventBean;
            updataTablayoutData(getBeanTablayout);
            toast("8");
        } else if (eventBean instanceof HomeLastProgramBean) {
            HomeLastProgramBean homeLastProgramBean = (HomeLastProgramBean) eventBean;
            updataProductData(homeLastProgramBean);
            toast("9");
        }
    }

    /**
     * 获取等比缩放的Bitmap
     *
     * @param bitmap
     * @return
     */
    public static Bitmap getScaleBitmap(Bitmap bitmap, int newWidth) {
        // 获得图片的宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleWidth);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return newbm;
    }

}

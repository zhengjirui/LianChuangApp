package com.lechuang.app.presenter.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lechuang.app.base.adapter.BaseRecyclerViewHolder;
import com.lechuang.app.model.data.HomeData;

/**
 * @author: LGH
 * @since: 2018/5/9
 * @describe:
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private Context mContent;
    private LayoutInflater inflater;
    private HomeData mHomeData;

    public HomeAdapter(Context context, HomeData homeData) {
        this.inflater = LayoutInflater.from(context);
        this.mContent = context;
        this.mHomeData = homeData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
//                return new MsgTypeHeaderAdapter(mContent,inflater.inflate(R.layout.ny_msgtype_header_item,parent,false));

            case 1:
//                return new MsgTypeGvAdapter(mContent,inflater.inflate(R.layout.ny_msgtype_gv_item,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //判断是第几个item，根据item的不同，往bindHolder里面穿不同的值（通过position判断）
        BaseRecyclerViewHolder vh = (BaseRecyclerViewHolder)holder;
        if(position == 0){
//            vh.bindHolder(msgTypeBean.getMsgTypeHeaderBean());
        }else {
//            vh.bindHolder(msgTypeBean.getMsgTypeGvBeenList().get(position - 1));
        }

    }

    @Override
    public int getItemViewType(int position) {
        //判断是第几个item，然后返回对应item的数据（通过position判断）
//        return super.getItemViewType(position);
        if(position == 0){
            return 0;
        }else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
//        int size = msgTypeBean.getMsgTypeGvBeenList().size() + 1;
        return 0;
    }

//    public void setNotifyDataSetChanged(MsgTypeBean msgTypeBean){
////        this.msgTypeBean = msgTypeBean;
//        this.notifyDataSetChanged();
//    }
}

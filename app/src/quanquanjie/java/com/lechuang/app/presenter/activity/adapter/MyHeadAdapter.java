package com.lechuang.app.presenter.activity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lechuang.app.R;
import com.lechuang.app.model.bean.GoodsTypeInfoBean;

import java.util.ArrayList;


/**
 * 商品信息类别列表数据适配
 */

public class MyHeadAdapter extends BaseAdapter {
    private ArrayList<GoodsTypeInfoBean> headDataSet;
    private int selectedHeadIndex;
private  Context mContent;
    public MyHeadAdapter(ArrayList<GoodsTypeInfoBean> headDataSet, Context context) {
        this.headDataSet = headDataSet;
        mContent=context;
    }


    @Override
    public int getCount() {
        return headDataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return headDataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        GoodsTypeInfoBean data = headDataSet.get(position);
        HeadViewHolder holder;
        if (convertView == null) {
          convertView= View.inflate(mContent,R.layout.class_left_head,null);
            holder = new HeadViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (HeadViewHolder) convertView.getTag();
        }
        if (position != selectedHeadIndex) {
            holder.setData(data);
            holder.itemView.setBackgroundColor(mContent.getResources().getColor(R.color.rgb_eee));
        }else {
            holder.itemView.setBackgroundColor(mContent.getResources().getColor(R.color.white));
            holder.changeView(data);
        }
        return convertView;
    }

    public void setSelectedPositon(int index) {
        if(selectedHeadIndex!=index){
            selectedHeadIndex = index;
            notifyDataSetChanged();
        }

    }

    private class HeadViewHolder {
        private View itemView;
        private GoodsTypeInfoBean data;
        private TextView tvLeftName;
        private ImageView tvLeftIcon;

        public HeadViewHolder(View itemView) {
            this.itemView = itemView;
            tvLeftName = (TextView) itemView.findViewById(R.id.lv_left_name);
            tvLeftIcon = (ImageView) itemView.findViewById(R.id.lv_left_icon);
        }

        public void setData(GoodsTypeInfoBean data) {
            this.data = data;
            tvLeftIcon.setImageDrawable(mContent.getResources().getDrawable(data.icon));
            tvLeftName.setText(data.name);
            tvLeftName.setTextColor(mContent.getResources().getColor(R.color.c_6D6D6D));

        }

        public void changeView(GoodsTypeInfoBean data) {
            tvLeftName.setTextColor(mContent.getResources().getColor(R.color.main));
            tvLeftIcon.setImageDrawable(mContent.getResources().getDrawable(data.icons));
        }
    }
}
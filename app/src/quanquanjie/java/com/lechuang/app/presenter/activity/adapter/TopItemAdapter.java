package com.lechuang.app.presenter.activity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lechuang.app.R;
import com.lechuang.app.model.bean.ClassInfoBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/6.
 */

public class TopItemAdapter extends BaseAdapter {
    public Context mContext;
   public List<ClassInfoBean.ClassTypeListBean.TbClassTypeListBean> bean;


    public TopItemAdapter(ClassInfoBean.ClassTypeListBean classBean , Context context){
        mContext=context;
        this.bean= classBean.tbClassTypeList;
    }
    @Override
    public int getCount() {
        return bean==null?0:bean.size();
    }

    @Override
    public Object getItem(int position) {
        return bean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyItemHolder itemHolder;
        if(convertView==null){
            convertView=View.inflate(mContext, R.layout.top_item,null);
            itemHolder = new MyItemHolder(convertView);
            convertView.setTag(itemHolder);
        }else {
            itemHolder= (MyItemHolder) convertView.getTag();
        }
        itemHolder.setData(bean.get(position));
        return convertView;
    }


    private class MyItemHolder {

        private  TextView itemName;
        private ImageView itemIcon;

        public MyItemHolder(View convertView) {
            itemName = (TextView) convertView.findViewById(R.id.tv_icon);
            itemIcon = (ImageView) convertView.findViewById(R.id.iv_icon);

        }

        public void setData(ClassInfoBean.ClassTypeListBean.TbClassTypeListBean data) {
            Glide.with(mContext).load(data.img).into(itemIcon);
            itemName.setText(data.name);
        }
    }
}

package com.lechuang.app.presenter.activity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.lechuang.app.R;
import com.lechuang.app.model.bean.ClassInfoBean;
import com.lechuang.app.model.bean.GoodsTypeInfoBean;
import com.lechuang.app.view.MGridView;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;


/**
 * 分组容器适配
 */

public class MyGroupAdapter extends BaseAdapter implements StickyListHeadersAdapter {


    private final ArrayList<GoodsTypeInfoBean> headDataSet;
    private final ArrayList<ClassInfoBean.ClassTypeListBean> itemDataSet;
    public Context mContent;

    public MyGroupAdapter(ArrayList<GoodsTypeInfoBean> headDataSet, ArrayList<ClassInfoBean.ClassTypeListBean> itemDataSet, Context context) {
        this.headDataSet = headDataSet;
        this.itemDataSet = itemDataSet;
        mContent = context;
    }

    //////////////////////////////////头管理/////////////////////////////////////////////
    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        // 向下滚动：头数据加载的是每组的第一条
        // 向上滚动：头数据加载的是每组的最后一
        ItemHead itemHead;
        if (convertView == null) {
            convertView = View.inflate(mContent, R.layout.item_head, null);
            itemHead = new ItemHead(convertView);
            convertView.setTag(itemHead);
        } else {
            itemHead = (ItemHead) convertView.getTag();
        }
        itemHead.setHeadData(headDataSet.get(position));
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return itemDataSet.get(position).rootId - 1;
    }

    //////////////////////////////////普通条目管理/////////////////////////////////////////////
    int size=0;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewHolder item;
        if (convertView == null) {
            convertView = View.inflate(mContent, R.layout.item_normal, null);
            item = new ItemViewHolder(convertView);
            convertView.setTag(item);
        } else {
            item = (ItemViewHolder) convertView.getTag();
        }
        int rootId = headDataSet.get(position).rootId;

        if(size==0){
           size = itemDataSet.size();
        }
        for (int i = 0; i <size; i++) {
            if(itemDataSet.get(i).rootId==rootId) {
                item.setItemData(itemDataSet.get(i));
                break;
            }
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return itemDataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return itemDataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    class ItemViewHolder {
        View itemView;
        private MGridView gridView;
        ClassInfoBean.ClassTypeListBean bean;

        public ItemViewHolder(View itemView) {
            this.itemView = itemView;
            gridView = (MGridView) itemView.findViewById(R.id.gv_kind);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    onItemsClickListener.onItemsClick(view, position, bean);
                }
            });
        }

        public void setItemData(ClassInfoBean.ClassTypeListBean bean) {
            this.bean = bean;
            gridView.setAdapter(new TopItemAdapter(bean, mContent));
        }

    }

    public OnItemsClickListener onItemsClickListener;

    public void setOnItemsClickListener(OnItemsClickListener onItemsClickListener) {
        this.onItemsClickListener = onItemsClickListener;

    }


    public interface OnItemsClickListener {
        /**
         * @param view     选中的条目
         * @param position 条目在glideview中的位置
         * @param bean     GlideView的数据
         */
        void onItemsClick(View view, int position, ClassInfoBean.ClassTypeListBean bean);
    }

    private class ItemHead {
        View itemHead;
        private TextView tv;

        public ItemHead(View convertView) {
            itemHead = convertView;
            tv = (TextView) itemHead.findViewById(R.id.tv_name);
        }

        public void setHeadData(GoodsTypeInfoBean bean) {
            tv.setText(bean.name);
        }
    }
}
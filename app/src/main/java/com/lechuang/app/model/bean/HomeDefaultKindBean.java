package com.lechuang.app.model.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yrj
 * @date 2017/10/2
 * @E-mail 1422947831@qq.com
 * @desc 类目写死的时候实体类
 */
public class HomeDefaultKindBean extends BaseEventBean{


    public List<ListBean> tbclassTypeList = new ArrayList<>();

    public static class ListBean {
        public String id;
        //图片
        public int img;
        //淘宝类目名
        public String name;
        //淘宝父类目ID
        public int parentId;
        //网站根目录
        public int rootId;
        //网站根目录
        public String rootName;
    }


}

package com.lechuang.app.view.set;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.lechuang.app.R;
import com.lechuang.app.base.BasePresenter;
import com.lechuang.app.base.lisenters.IBaseView;
import com.lechuang.app.net.Netword;
import com.lechuang.app.net.ResultBack;
import com.lechuang.app.net.netApi.CommenApi;
import com.lechuang.app.utils.Utils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author: LGH
 * @since: 2018/5/17
 * @describe:
 */

public class FaceBackA extends BasePresenter{


    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.et_opinion)
    EditText mEtPpinion;

    private Unbinder mUnbinder;

    public FaceBackA(IBaseView mIBaseView) {
        super(mIBaseView);
    }

    @Override
    public void initCreateContent() {
        super.initCreateContent();
        mUnbinder = ButterKnife.bind(this, getActivityView());
    }

    @OnClick({R.id.iv_back, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_ok:
                if (Utils.isEmpty(mEtPpinion)) {
                    toast("请输入意见反馈");
                } else {
                    //提交意见
                    commitOpinion(mEtPpinion.getText().toString().trim());
                }
                break;
        }
    }

    private void commitOpinion(String opinion) {
        show();
        final HashMap<String,String> map=new HashMap();
        map.put("opinion",opinion);
        Netword.getInstance().getApi(CommenApi.class)
                .opinion(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultBack<String>(mContext) {
                    @Override
                    public void successed(String result) {
                        toast(result);
                        finish();
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        dismiss();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mUnbinder != null){
            mUnbinder.unbind();
        }
    }
}

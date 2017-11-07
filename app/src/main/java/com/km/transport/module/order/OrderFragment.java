package com.km.transport.module.order;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextPaint;
import android.util.SparseArray;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.km.transport.R;
import com.km.transport.basic.BaseFragment;
import com.km.transport.module.home.goods.GoodsSearchFragment;
import com.km.transport.module.home.path.FastPathFragment;
import com.km.transport.module.order.finished.GoodsOrderFinishFragment;
import com.km.transport.module.order.unfinished.GoodsOrderUnFinishFragment;

import butterknife.BindView;

public class OrderFragment extends BaseFragment {

    @BindView(R.id.radio_group)
    RadioGroup mRadioGroup;
    @BindView(R.id.rab_unfinished)
    RadioButton rabUnfinished;
    TextPaint rabTpUnfinished;
    @BindView(R.id.rab_finished)
    RadioButton rabFinished;
    TextPaint rabTpFinished;

    private SparseArray<Fragment> mFragments;

    @Override
    protected int getContentView() {
        return R.layout.fragment_order;
    }

    @Override
    protected void createView() {
        initTitleBarRadioGroup();
    }


    /**
     * 初始化 标题栏 中间选择radioGroup
     */
    private void initTitleBarRadioGroup(){
        mFragments = new SparseArray<>();
        rabTpUnfinished = rabUnfinished.getPaint();
        rabTpFinished = rabFinished.getPaint();
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rab_unfinished:
                        rabTpUnfinished.setFakeBoldText(true);
                        rabTpFinished.setFakeBoldText(false);
                        break;
                    case R.id.rab_finished:
                        rabTpUnfinished.setFakeBoldText(false);
                        rabTpFinished.setFakeBoldText(true);
                        break;
                }
                setPageByRadioButton(checkedId);
            }
        });
        mRadioGroup.check(R.id.rab_unfinished);
    }

    /**
     * 设置 货源搜索 、 快捷路线对应的页面
     * @param id
     */
    private void setPageByRadioButton(int id){
        FragmentManager fm = getFragmentManager();
        if (id == R.id.rab_unfinished){
            if (mFragments.get(id) == null){
                mFragments.put(id, GoodsOrderUnFinishFragment.newInstance(null));
            }
            fm.beginTransaction().replace(R.id.fl_order_content, mFragments.get(id)).commit();
        } else {
            if (mFragments.get(id) == null){
                mFragments.put(id, GoodsOrderFinishFragment.newInstance(null));
            }
            fm.beginTransaction().replace(R.id.fl_order_content, mFragments.get(id)).commit();
        }
    }



}

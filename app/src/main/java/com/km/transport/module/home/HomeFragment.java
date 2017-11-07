package com.km.transport.module.home;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextPaint;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.km.transport.R;
import com.km.transport.basic.BaseFragment;
import com.km.transport.event.NoLoginEvent;
import com.km.transport.module.home.goods.GoodsSearchFragment;
import com.km.transport.module.home.path.FastPathFragment;
import com.ps.androidlib.utils.StatusBarUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.radio_group)
    RadioGroup mRadioGroup;
    @BindView(R.id.rab_goods)
    RadioButton rabGoods;
    TextPaint rabTpGoods;
    @BindView(R.id.rab_path)
    RadioButton rabPath;
    TextPaint rabTpPath;

    private SparseArray<Fragment> mFragments;


    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
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
        rabTpGoods = rabGoods.getPaint();
        rabTpPath = rabPath.getPaint();
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rab_goods:
                        rabTpGoods.setFakeBoldText(true);
                        rabTpPath.setFakeBoldText(false);
                        break;
                    case R.id.rab_path:
                        rabTpGoods.setFakeBoldText(false);
                        rabTpPath.setFakeBoldText(true);
                        break;
                }
                setPageByRadioButton(checkedId);
            }
        });
        mRadioGroup.check(R.id.rab_goods);
    }

    /**
     * 设置 货源搜索 、 快捷路线对应的页面
     * @param id
     */
    private void setPageByRadioButton(int id){
        FragmentManager fm = getFragmentManager();
        if (id == R.id.rab_goods){
            if (mFragments.get(id) == null){
                mFragments.put(id,GoodsSearchFragment.newInstance(null));
            }
            fm.beginTransaction().replace(R.id.fl_home_content, mFragments.get(id)).commit();
        } else {
            if (mFragments.get(id) == null){
                mFragments.put(id, FastPathFragment.newInstance(null));
            }
            fm.beginTransaction().replace(R.id.fl_home_content, mFragments.get(id)).commit();
        }
    }

    /**
     * 没有登录 ， 返回首页
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNoLogin(NoLoginEvent event){
        setPageByRadioButton(R.id.rab_goods);
    }

    /**
     * 浏览记录
     * @param view
     */
    @OnClick(R.id.tv_tool_bar_right)
    public void clickToolBarRight(View view){
        toNextActivity(BrowsingHistoryActivity.class);
    }



}

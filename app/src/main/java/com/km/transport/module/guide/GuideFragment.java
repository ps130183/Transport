package com.km.transport.module.guide;

import android.view.View;

import com.km.transport.R;
import com.km.transport.basic.BaseFragment;
import com.km.transport.module.MainActivity;
import com.ps.androidlib.utils.SPUtils;

import butterknife.OnClick;

/**
 * Created by kamangkeji on 17/4/15.
 */

public class GuideFragment extends BaseFragment {


    @Override
    protected int getContentView() {
        return R.layout.fragment_guide;
    }

    @Override
    protected void createView() {

    }

    @OnClick(R.id.to_home)
    public void toHome(View view){
        toNextActivity(MainActivity.class);
//        toNextActivity(HomeActivity.class);
        SPUtils.getInstance().put("isFirst",false);
        getActivity().finish();
    }
}

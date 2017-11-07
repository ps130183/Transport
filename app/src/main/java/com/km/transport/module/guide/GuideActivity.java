package com.km.transport.module.guide;

import android.graphics.Bitmap;

import com.chechezhi.ui.guide.AbsGuideActivity;
import com.chechezhi.ui.guide.SinglePage;
import com.km.transport.R;
import com.ps.androidlib.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;


public class GuideActivity extends AbsGuideActivity {

    @Override
    public List<SinglePage> buildGuideContent() {
        StatusBarUtil.setFullScreen(this);
        List<SinglePage> guideContent = new ArrayList<SinglePage>();

        SinglePage page01 = new SinglePage();
        page01.mBackground = getResources().getDrawable(R.mipmap.guide1);
        guideContent.add(page01);

        SinglePage page02 = new SinglePage();
        page02.mBackground = getResources().getDrawable(R.mipmap.guide2);
        guideContent.add(page02);

        SinglePage page03 = new SinglePage();
        page03.mBackground = getResources().getDrawable(R.mipmap.guide3);
        guideContent.add(page03);

        SinglePage page05 = new SinglePage();
        page05.mCustomFragment = new GuideFragment();
        guideContent.add(page05);

        return guideContent;
    }

    @Override
    public boolean drawDot() {
        return false;
    }

    @Override
    public Bitmap dotDefault() {
        return null;
    }

    @Override
    public Bitmap dotSelected() {
        return null;
    }

    @Override
    public int getPagerId() {
        return R.id.guide_container;
    }
}

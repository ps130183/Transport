package com.km.transport.module.personal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.transport.R;
import com.km.transport.basic.BaseFragment;
import com.km.transport.dto.UserInfoDto;
import com.km.transport.event.MessageUnreadStatusEvent;
import com.km.transport.event.RefreshPersonalEvent;
import com.km.transport.module.login.LoginActivity;
import com.km.transport.module.personal.account.MyAccountActivity;
import com.km.transport.module.personal.approve.ApproveDriverInfoActivity;
import com.km.transport.utils.Constant;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.utils.EventBusUtils;
import com.ps.androidlib.utils.StatusBarUtil;
import com.ps.androidlib.utils.glide.GlideUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends BaseFragment<PersonalPresenter> implements PersonalContract.View {

    @BindView(R.id.iv_user_portrait)
    ImageView ivUserPortrait;
    @BindView(R.id.tv_nike_name)
    TextView tvNikeName;
    @BindView(R.id.tv_approve)
    TextView tvApprove;
    @BindView(R.id.tv_join_hint)
    TextView tvJoinHint;
    @BindView(R.id.iv_arrow_right)
    ImageView ivArrowRight;
    @BindView(R.id.tv_my_account)
    TextView tvMyAccount;
    @BindView(R.id.tv_my_message)
    TextView tvMyMessage;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.tv_service)
    TextView tvService;
    @BindView(R.id.tv_about_me)
    TextView tvAboutMe;

    @BindView(R.id.tv_unread)
    TextView tvUnread;


    @Override
    protected int getContentView() {
        return R.layout.fragment_personal;
    }

    @Override
    public PersonalPresenter getmPresenter() {
        return new PersonalPresenter(this);
    }

    @Override
    protected void createView() {
        StatusBarUtil.initState(getActivity());
        if (Constant.userInfo != null){
            showUserInfo(Constant.userInfo);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getUserInfo();
    }

    /**
     * 个人资料
     *
     * @param view
     */
    @OnClick({R.id.iv_user_portrait, R.id.iv_arrow_right,R.id.tv_approve,R.id.tv_nike_name,R.id.tv_join_hint})
    public void editDriverInfo(View view) {
        if (Constant.userInfo == null){
            Constant.user.clear();
            toNextActivity(LoginActivity.class);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable("userInfo",Constant.userInfo);
        toNextActivity(ApproveDriverInfoActivity.class,bundle);
    }

    /**
     * 更多设置
     * @param view
     */
    @OnClick(R.id.tv_setting)
    public void setting(View view){
        toNextActivity(SettingActivity.class);
    }

    /**
     * 关于我们
     * @param view
     */
    @OnClick(R.id.tv_about_me)
    public void aboutMe(View view){
        toNextActivity(AboutMeActivity.class);
    }
    /**
     * 我的账户
     *
     * @param view
     */
    @OnClick(R.id.tv_my_account)
    public void myAccount(View view) {
        toNextActivity(MyAccountActivity.class);
    }

    /**
     * 我的消息
     * @param view
     */
    @OnClick(R.id.tv_my_message)
    public void myMessage(View view){
        toNextActivity(MessageActivity.class);
    }

    @Override
    public void showUserInfo(UserInfoDto userInfoDto) {
        Constant.userInfo = userInfoDto;
        GlideUtils.loadCircleImage(ivUserPortrait,userInfoDto.getHeadImg());
        tvNikeName.setText(userInfoDto.getName());
        tvJoinHint.setText(userInfoDto.getJoinTime());
        String status = "未认证";
        if (userInfoDto.getValidStatus() == 1){
            status = "审核中";
        } else if (userInfoDto.getValidStatus() == 2){
            status = "已认证";
        }
        tvApprove.setText(status);

        tvUnread.setVisibility(userInfoDto.getReadStatus() > 0 ? View.VISIBLE : View.GONE);
        EventBusUtils.post(new MessageUnreadStatusEvent(userInfoDto.getReadStatus()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshPersonal(RefreshPersonalEvent event){
        mPresenter.getUserInfo();
    }

}

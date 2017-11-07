package com.km.transport.module.personal.approve;

import com.km.transport.dto.UserInfoDto;
import com.km.transport.utils.retrofit.PresenterWrapper;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 17/9/22.
 */

public class ApproveDriverInfoPresenter extends PresenterWrapper<ApproveDriverInfoContract.View> implements ApproveDriverInfoContract.Presenter {

    public ApproveDriverInfoPresenter(ApproveDriverInfoContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void uploadPicture(String picturePath, String optionType) {
        mApiwrapper.imageUpload(optionType,picturePath)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.uploadSuccess(s);
                    }
                }));
    }

    @Override
    public void submitUserInfo(UserInfoDto userInfoDto) {
        mView.showLoading();
        mApiwrapper.editUserInfo(userInfoDto.getHeadImg(),
                userInfoDto.getDriveCard(),
                userInfoDto.getName(),
                userInfoDto.getPersonalCard(),
                userInfoDto.getPhone(),
                userInfoDto.getTravelBook(),
                userInfoDto.getCarType(),
                userInfoDto.getCarLocation(),
                userInfoDto.getLicensePlate(),
                userInfoDto.getMaxLoad())
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.submitSuccess();
                    }
                }));
    }
}

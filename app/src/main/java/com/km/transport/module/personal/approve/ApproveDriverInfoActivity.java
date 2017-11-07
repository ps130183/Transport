package com.km.transport.module.personal.approve;

import android.Manifest;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.km.transport.R;
import com.km.transport.basic.BaseActivity;
import com.km.transport.dto.UserInfoDto;
import com.km.transport.event.UploadImageEvent;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.DialogUtils;
import com.ps.androidlib.utils.EventBusUtils;
import com.ps.androidlib.utils.glide.GlideUtils;
import com.ps.androidlib.utils.imageselector.ImageUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class ApproveDriverInfoActivity extends BaseActivity<ApproveDriverInfoPresenter> implements ApproveDriverInfoContract.View {

    @BindView(R.id.tv_step1)
    TextView tvStep1;
    @BindView(R.id.tv_step2)
    TextView tvStep2;
    @BindView(R.id.tv_step1_name)
    TextView tvStep1Name;
    @BindView(R.id.tv_step2_name)
    TextView tvStep2Name;
    @BindView(R.id.view_center)
    View viewCenter;
    @BindView(R.id.iv_arrow_portrait)
    ImageView ivArrowPortrait;
    @BindView(R.id.iv_user_portrait)
    ImageView ivUserPortrait;
    @BindView(R.id.iv_arrow_driving)
    ImageView ivArrowDriving;
    @BindView(R.id.iv_user_driving)
    ImageView ivUserDriving;
    @BindView(R.id.tv_name_title)
    TextView tvNameTitle;
    @BindView(R.id.iv_arrow_name)
    ImageView ivArrowName;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.tv_card_title)
    TextView tvCardTitle;
    @BindView(R.id.iv_arrow_card)
    ImageView ivArrowCard;
    @BindView(R.id.et_user_card)
    EditText etUserCard;
    @BindView(R.id.tv_phone_title)
    TextView tvPhoneTitle;
    @BindView(R.id.iv_arrow_phone)
    ImageView ivArrowPhone;
    @BindView(R.id.et_user_phone)
    EditText etUserPhone;
    @BindView(R.id.btn_next)
    Button btnNext;

    private int uploadImageType;

    private String userPortraitUrl;
    private String userDrivingUrl;

    private UserInfoDto mUserInfo;

    @Override
    protected int getContentView() {
        return R.layout.activity_approve_driver_info;
    }

    @Override
    protected String getTitleName() {
        return "驾驶员信息";
    }

    @Override
    public ApproveDriverInfoPresenter getmPresenter() {
        return new ApproveDriverInfoPresenter(this);
    }

    @Override
    protected void onCreate() {
        mUserInfo = getIntent().getParcelableExtra("userInfo");
        if (mUserInfo != null){
            showUserInfo();
        }
    }

    private void showUserInfo(){
        userPortraitUrl = mUserInfo.getHeadImg();
        userDrivingUrl = mUserInfo.getDriveCard();
        GlideUtils.loadCircleImage(ivUserPortrait,userPortraitUrl);
        GlideUtils.loadImage(ivUserDriving,userDrivingUrl);
        etUserName.setText(TextUtils.isEmpty(mUserInfo.getName()) ? "" : mUserInfo.getName());
        etUserCard.setText(TextUtils.isEmpty(mUserInfo.getPersonalCard()) ? "" : mUserInfo.getPersonalCard());
        etUserPhone.setText(TextUtils.isEmpty(mUserInfo.getPhone()) ? "" : mUserInfo.getPhone());
    }

    @OnClick(R.id.btn_next)
    public void next(View view) {
        String name = etUserName.getText().toString();
        String userCard = etUserCard.getText().toString();
        String userPhone = etUserPhone.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(userCard) || TextUtils.isEmpty(userPhone)
                || TextUtils.isEmpty(userPortraitUrl) || TextUtils.isEmpty(userDrivingUrl)){
            showToast("请将信息补充完整");
            return;
        }

        if (mUserInfo == null){
            mUserInfo = new UserInfoDto();
        }
        mUserInfo.setName(name);
        mUserInfo.setPersonalCard(userCard);
        mUserInfo.setPhone(userPhone);
        mUserInfo.setHeadImg(userPortraitUrl);
        mUserInfo.setDriveCard(userDrivingUrl);

        Bundle bundle = new Bundle();
        bundle.putParcelable("userInfo",mUserInfo);

        toNextActivity(ApproveTruckInfoActivity.class,bundle);
    }

    @OnClick({R.id.iv_user_portrait,R.id.iv_arrow_portrait})
    public void uploadUserPortrait(View view){
        uploadImg(1);
    }

    @OnClick({R.id.iv_user_driving,R.id.iv_arrow_driving})
    public void uploadUserDriving(View view){
        uploadImg(2);
    }

    /**
     * 上传图片  1，头像 2，证件
     * @param imageType
     */
    private void uploadImg(int imageType){
        uploadImageType = imageType;
        String[] locationPermission = {Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        PermissionGen.with(ApproveDriverInfoActivity.this)
                .addRequestCode(1)
                .permissions(locationPermission)
                .request();
    }

    @PermissionSuccess(requestCode = 1)
    public void requestCameraSuccess(){
        DialogUtils.showBottomDialogForChoosePhoto(new MyItemDialogListener() {
            @Override
            public void onItemClick(CharSequence charSequence, int i) {
                switch (i){
                    case 0:
                        ImageUtils.getImageFromCamera(ApproveDriverInfoActivity.this,false,selectImageListener);
                        break;
                    case 1:
                        ImageUtils.getImageFromPhotoAlbum(ApproveDriverInfoActivity.this,
                                ImageUtils.ImageType.PRODUCT,
                                ImageUtils.ImageNumber.SINGLE,
                                null,
                                selectImageListener);
                        break;
                }
            }
        });
    }
    @PermissionFail(requestCode = 1)
    public void requestCameraFail(){
        showToast("没有相机的使用权限");
    }

    private ImageUtils.SelectImageListener selectImageListener =  new ImageUtils.SelectImageListener() {
        @Override
        public void onSuccess(List<String> photoList) {
            String imagePath = photoList.get(0);

            if (uploadImageType == 1){ //上传头像
                GlideUtils.loadCircleImage(ivUserPortrait,imagePath);
            } else if (uploadImageType == 2){//上传 驾驶证
                GlideUtils.loadImage(ivUserDriving,imagePath);
            }
            EventBusUtils.post(new UploadImageEvent(imagePath));
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSelecedPhoto(UploadImageEvent event){
        com.ps.androidlib.utils.ImageUtils.compressImage(event.getImagePath(),
                AppUtils.getCurWindowWidth(ApproveDriverInfoActivity.this)/2,
                AppUtils.getCurWindowHeight(ApproveDriverInfoActivity.this)/2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mPresenter.uploadPicture(s,uploadImageType+"");
                    }
                });

    }

    @Override
    public void uploadSuccess(String resultUrl) {
        if (uploadImageType == 1){
            userPortraitUrl = resultUrl;
        } else if (uploadImageType == 2){
            userDrivingUrl = resultUrl;
        }
    }

    @Override
    public void submitSuccess() {

    }
}


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
import com.km.transport.event.ChooseCarAddressEvent;
import com.km.transport.event.ChooseCarTypeEvent;
import com.km.transport.event.UploadImageEvent;
import com.km.transport.greendao.City;
import com.km.transport.module.MainActivity;
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

public class ApproveTruckInfoActivity extends BaseActivity<ApproveDriverInfoPresenter> implements ApproveDriverInfoContract.View {

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
    @BindView(R.id.iv_arrow_driving)
    ImageView ivArrowDriving;
    @BindView(R.id.iv_vehicle)
    ImageView ivVehicle;
    @BindView(R.id.tv_name_title)
    TextView tvNameTitle;
    @BindView(R.id.iv_arrow_name)
    ImageView ivArrowName;
    @BindView(R.id.tv_car_type)
    TextView tvCarType;
    @BindView(R.id.tv_card_title)
    TextView tvCardTitle;
    @BindView(R.id.iv_arrow_card)
    ImageView ivArrowCard;
    @BindView(R.id.tv_car_address)
    TextView tvCarAddress;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;

    @BindView(R.id.tv_license_plate_title)
    TextView tvLicensePlateTitle;
    @BindView(R.id.iv_license_plate)
    ImageView ivLicensePlate;
    @BindView(R.id.et_license_plate)
    EditText etLicensePlate;
    @BindView(R.id.tv_max_weight_title)
    TextView tvMaxWeightTitle;
    @BindView(R.id.iv_max_weight)
    ImageView ivMaxWeight;
    @BindView(R.id.et_max_weight)
    EditText etMaxWeight;

    private String vehicleUrl;
    private String carAddress;
    private String carType;

    private UserInfoDto mUserInfo;

    @Override
    protected int getContentView() {
        return R.layout.activity_approve_truck_info;
    }

    @Override
    protected String getTitleName() {
        return "车辆信息";
    }

    @Override
    public ApproveDriverInfoPresenter getmPresenter() {
        return new ApproveDriverInfoPresenter(this);
    }

    @Override
    protected void onCreate() {
        mUserInfo = getIntent().getParcelableExtra("userInfo");
        if (!TextUtils.isEmpty(mUserInfo.getTravelBook())) {
            vehicleUrl = mUserInfo.getTravelBook();
            GlideUtils.loadImage(ivVehicle, vehicleUrl);
        }
        if (!TextUtils.isEmpty(mUserInfo.getCarType())) {
            carType = mUserInfo.getCarType();
            tvCarType.setText(mUserInfo.getCarType());
        }

        if (!TextUtils.isEmpty(mUserInfo.getLicensePlate())){
            etLicensePlate.setText(mUserInfo.getLicensePlate());
        }
        if (!TextUtils.isEmpty(mUserInfo.getMaxLoad())){
            etMaxWeight.setText(mUserInfo.getMaxLoad());
        }

        if (!TextUtils.isEmpty(mUserInfo.getCarLocation())) {
            String carLocation = mUserInfo.getCarLocation().replaceAll("#", "");
            carAddress = mUserInfo.getCarLocation();
            tvCarAddress.setText(carLocation);
        }
    }

    /**
     * 行车本
     *
     * @param view
     */
    @OnClick(R.id.iv_vehicle)
    public void vehicle(View view) {
        uploadImg(2);
    }

    /**
     * 选择车辆类型
     *
     * @param view
     */
    @OnClick(R.id.tv_car_type)
    public void chooseCarType(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("carType", 0);
        toNextActivity(ChooseCarTypeActivity.class, bundle);
    }

    /**
     * 选择车辆所在地
     *
     * @param view
     */
    @OnClick(R.id.tv_car_address)
    public void chooseCarAddress(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("carType", 1);
        toNextActivity(ChooseCarAddressActivity.class, bundle);
    }

    @OnClick(R.id.btn_confirm)
    public void confirm(View view) {
        String licensePlate = etLicensePlate.getText().toString();
        String maxWeight = etMaxWeight.getText().toString();
        if (TextUtils.isEmpty(vehicleUrl)){
            showToast("请重新上传行车本");
            return;
        } else if (TextUtils.isEmpty(carType)){
            showToast("请选择车辆类型");
            return;
        } else if (TextUtils.isEmpty(carAddress)){
            showToast("请选择车辆所在地");
            return;
        } else if (TextUtils.isEmpty(licensePlate)){
            showToast("请编辑正确的车牌号码");
            return;
        } else if (TextUtils.isEmpty(maxWeight)) {
            showToast("请编辑正确的最大载重");
            return;
        }
        if (mUserInfo != null) {
            mUserInfo.setCarType(carType);
            mUserInfo.setCarLocation(carAddress);
            mUserInfo.setTravelBook(vehicleUrl);
            mUserInfo.setLicensePlate(licensePlate);
            mUserInfo.setMaxLoad(maxWeight);
            mPresenter.submitUserInfo(mUserInfo);
        } else {
            showToast("获取不到上一页的数据");
        }

    }

    /**
     * 上传图片  1，头像 2，证件
     *
     * @param imageType
     */
    private void uploadImg(int imageType) {
        String[] locationPermission = {Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        PermissionGen.with(ApproveTruckInfoActivity.this)
                .addRequestCode(1)
                .permissions(locationPermission)
                .request();
    }

    @PermissionSuccess(requestCode = 1)
    public void requestCameraSuccess() {
        DialogUtils.showBottomDialogForChoosePhoto(new MyItemDialogListener() {
            @Override
            public void onItemClick(CharSequence charSequence, int i) {
                switch (i) {
                    case 0:
                        ImageUtils.getImageFromCamera(ApproveTruckInfoActivity.this, false, selectImageListener);
                        break;
                    case 1:
                        ImageUtils.getImageFromPhotoAlbum(ApproveTruckInfoActivity.this,
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
    public void requestCameraFail() {
        showToast("没有相机的使用权限");
    }

    private ImageUtils.SelectImageListener selectImageListener = new ImageUtils.SelectImageListener() {
        @Override
        public void onSuccess(List<String> photoList) {
            String imagePath = photoList.get(0);
            GlideUtils.loadImage(ivVehicle, imagePath);
            EventBusUtils.post(new UploadImageEvent(imagePath));
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSelecedPhoto(UploadImageEvent event) {
        com.ps.androidlib.utils.ImageUtils.compressImage(event.getImagePath(),
                AppUtils.getCurWindowWidth(ApproveTruckInfoActivity.this) / 2,
                AppUtils.getCurWindowHeight(ApproveTruckInfoActivity.this) / 2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mPresenter.uploadPicture(s, "2");
                    }
                });

    }

    @Override
    public void uploadSuccess(String resultUrl) {
        vehicleUrl = resultUrl;
    }

    @Override
    public void submitSuccess() {
        toNextActivity(MainActivity.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCarType(ChooseCarTypeEvent event) {
        tvCarType.setText(event.getTypeName());
        carType = event.getTypeName();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCarAddress(ChooseCarAddressEvent event) {
        City[] cities = event.getChooseCitys();
        tvCarAddress.setText(cities[0].getName() + cities[1].getName() + cities[2].getName());
        StringBuffer carAddressBuf = new StringBuffer();
        carAddressBuf.append(cities[0].getName()).append("#")
                .append(cities[1].getName()).append("#")
                .append(cities[2].getName());
        carAddress = carAddressBuf.toString();
    }
}

package com.km.transport.module.order.unfinished;

import android.Manifest;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.km.transport.R;
import com.km.transport.basic.BaseActivity;
import com.km.transport.event.ShipmentEvent;
import com.km.transport.event.UploadImageEvent;
import com.km.transport.module.MainActivity;
import com.km.transport.module.home.goods.GoodsOrderFinishInfoActivity;
import com.km.transport.utils.PickerUtils;
import com.lvfq.pickerview.TimePickerView;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.DateUtils;
import com.ps.androidlib.utils.DialogUtils;
import com.ps.androidlib.utils.EventBusUtils;
import com.ps.androidlib.utils.glide.GlideUtils;
import com.ps.androidlib.utils.imageselector.ImageUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class PutStorageActivity extends BaseActivity<PutStoragePresenter> implements PutStorageContract.View {

    @BindView(R.id.et_real_weight)
    EditText etRealWeight;
    @BindView(R.id.et_receive_time)
    EditText etReceiveTime;

    @BindView(R.id.iv_putstoreage)
    ImageView ivPutStorage;


    private String orderId;
    private String shipmentUrl;

    @Override
    protected int getContentView() {
        return R.layout.activity_put_storage;
    }

    @Override
    protected String getTitleName() {
        return "收货详情";
    }

    @Override
    public PutStoragePresenter getmPresenter() {
        return new PutStoragePresenter(this);
    }

    @Override
    protected void onCreate() {
        orderId = getIntent().getStringExtra("orderId");
        etReceiveTime.setText(DateUtils.getInstance().dateToString(new Date(System.currentTimeMillis()),DateUtils.YMDHM));
    }

    @OnClick(R.id.btn_finish)
    public void finish(View view){
        String realWeight = etRealWeight.getText().toString();
        String receiveTime = etReceiveTime.getText().toString();

        if (TextUtils.isEmpty(realWeight)){
            showToast("请填写实际的收货吨数");
            return;
        }
        if (TextUtils.isEmpty(receiveTime)){
            showToast("请选择shou货时间");
            return;
        }

        if (TextUtils.isEmpty(shipmentUrl)){
            showToast("请上传收货单据");
            return;
        }
        mPresenter.putStorageGoods(orderId,realWeight,shipmentUrl,receiveTime);


    }

    @OnClick(R.id.et_receive_time)
    public void choosLeaveTime(View view){
        PickerUtils.alertTimerPicker(PutStorageActivity.this, TimePickerView.Type.ALL,
                DateUtils.getInstance().dateToString(new Date(System.currentTimeMillis()), DateUtils.YMDHM),
                DateUtils.YMDHM, new PickerUtils.TimerPickerCallBack() {
                    @Override
                    public void onTimeSelect(String date) {
                        etReceiveTime.setText(date);
                    }
                });
    }

    @OnClick(R.id.iv_putstoreage)
    public void uploadShipmentImg(View view){
        String[] locationPermission = {Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        PermissionGen.with(PutStorageActivity.this)
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
                        ImageUtils.getImageFromCamera(PutStorageActivity.this,false,selectImageListener);
                        break;
                    case 1:
                        ImageUtils.getImageFromPhotoAlbum(PutStorageActivity.this,
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
            GlideUtils.loadImage(ivPutStorage,imagePath);
            EventBusUtils.post(new UploadImageEvent(imagePath));
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSelecedPhoto(UploadImageEvent event){
        com.ps.androidlib.utils.ImageUtils.compressImage(event.getImagePath(),
                AppUtils.getCurWindowWidth(PutStorageActivity.this)/2,
                AppUtils.getCurWindowHeight(PutStorageActivity.this)/2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mPresenter.uploadPicture(s,"1");
                    }
                });

    }

    @Override
    public void putStorageSuccess() {
        EventBusUtils.post(new ShipmentEvent());
        toNextActivity(MainActivity.class);
    }

    @Override
    public void uploadSuccess(String resultUrl) {
        shipmentUrl = resultUrl;
    }
}

package com.km.transport.api;


import com.google.gson.Gson;
import com.km.transport.dto.AppVersionDto;
import com.km.transport.dto.BrowsingHistoryDto;
import com.km.transport.dto.GoodsOrderDetailDto;
import com.km.transport.dto.HomeFastPathDto;
import com.km.transport.dto.HomeGoodsOrderDto;
import com.km.transport.dto.MessageDto;
import com.km.transport.dto.UserAccountDetailDto;
import com.km.transport.dto.UserDto;
import com.km.transport.dto.UserInfoDto;
import com.km.transport.dto.WithDrawAccountDto;
import com.km.transport.greendao.City;
import com.km.transport.utils.Constant;
import com.km.transport.utils.fileupload.FileUploadingListener;
import com.km.transport.utils.fileupload.UploadFileRequestBody;
import com.km.transport.utils.retrofit.RetrofitUtil;

import java.io.File;
import java.util.List;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Created by kamangkeji on 17/2/11.
 */

public class ApiWrapper extends RetrofitUtil {

    private static Gson gson = null;
    public static final int maxData = 10;//每页返回的数据条数
    /**
     * 单例
     */
    private static ApiWrapper instance = null;
    private ApiWrapper(){}
    public static ApiWrapper getInstance(){
        if (instance == null){
            synchronized (ApiWrapper.class){
                if (instance == null){
                    instance = new ApiWrapper();
                    gson = new Gson();
                }
            }
        }
        return  instance;
    }

    /**
     * 登录
     * @param mobilePhone
     * @param smsCode
     * @return
     */
    public Flowable<UserDto> login(String mobilePhone, String smsCode){
        return getService().login(mobilePhone,smsCode).compose(this.<UserDto>applySchedulers());
    }

    /**
     * 获取手机验证码
     * @param mobilePhone
     * @return
     */
    public Flowable<String> getPhoneCode(String mobilePhone){
        return getService().getPhoneCode(mobilePhone)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 上传图片
     * @param optionType
     * @param imagePath
     * @return
     */
    public Flowable<String> imageUpload(String optionType, String imagePath){
        return imageUpload(optionType,imagePath,null);
    }

    /**
     * 上传图片
     * @param optionType
     * @param imagePath
     * @return
     */
    public Flowable<String> imageUpload(String optionType, String imagePath, FileUploadingListener fileUploadObserver){
        RetrofitUtil util = new RetrofitUtil();
        RequestBody requestOptionType = util.createRequestBody(optionType);
        File image = new File(imagePath);

        UploadFileRequestBody uploadFileRequestBody = new UploadFileRequestBody(image, fileUploadObserver);
        MultipartBody.Part part = MultipartBody.Part.createFormData("pictureFile", image.getName(), uploadFileRequestBody);

        return getService().imageUpload(requestOptionType,part).compose(this.<String>applySchedulers());
    }

    /**
     * 检测是否有新版本
     * @param appVersion
     * @return
     */
    public Flowable<AppVersionDto> checkAppVersion(int appVersion){
        return getService().checkAppVersion(appVersion)
                .compose(this.<AppVersionDto>applySchedulers());
    }

    /**
     * 获取中国所有城市信息
     * @return
     */
    public Flowable<List<City>> getChinaCitysInfo(){
        return getService().getChinaCitysInfo("")
                .compose(this.<List<City>>applySchedulers());
    }

    /**
     * 获取首页 后台发布的货源订单列表
     * @param pageNo
     * @param sourceProvince
     * @param sourceCity
     * @param sourceZoning
     * @param bournProvince
     * @param bournCity
     * @param bournZoning
     * @param carType
     * @param carWidth
     * @return
     */
    public Flowable<List<HomeGoodsOrderDto>> getHomeGoodsOrders(int pageNo,
                                                                String sourceProvince,String sourceCity,String sourceZoning,
                                                                String bournProvince,String bournCity,String bournZoning,
                                                                String carType,String carWidth){
        return getService().getHomeGoodsOrders(pageNo,sourceProvince,sourceCity,sourceZoning,bournProvince,bournCity,bournZoning,carType,carWidth)
                .compose(this.<List<HomeGoodsOrderDto>>applySchedulers());
    }

    /**
     * 添加快捷路线
     * @param sourceProvince
     * @param sourceCity
     * @param sourceZoning
     * @param bournProvince
     * @param bournCity
     * @param bournZoning
     * @param carType
     * @param carWidth
     * @return
     */
    public Flowable<String> addFastPath(String sourceProvince,String sourceCity,String sourceZoning,
                                        String bournProvince,String bournCity,String bournZoning,
                                        String carType,String carWidth){
        return getService().addFastPath(Constant.user.getToken(),sourceProvince,sourceCity,sourceZoning,bournProvince,bournCity,bournZoning,carType,carWidth)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 删除快捷路线
     * @param id
     * @return
     */
    public Flowable<String> deleteFastPath(String id){
        return getService().deleteFastPath(Constant.user.getToken(),id)
                .compose(this.<String>applySchedulers());
    }
    /**
     * 首页 获取 快捷路线 列表
     * @return
     */
    public Flowable<List<HomeFastPathDto>> getFastPathList(int pageNo){
        return getService().getFastPathList(Constant.user.getToken(),pageNo)
                .compose(this.<List<HomeFastPathDto>>applySchedulers());
    }

    /**
     * 我要承揽
     * @param id
     * @return
     */
    public Flowable<GoodsOrderDetailDto> hireGoods(String id){
        return getService().hireGoods(Constant.user.getToken(),id)
                .compose(this.<GoodsOrderDetailDto>applySchedulers());
    }

    /**
     * 获取浏览记录 列表
     * @param pageNo
     * @return
     */
    public Flowable<List<BrowsingHistoryDto>> getBrowsingHistoryList(int pageNo){
        return getService().getBrowsingHistoryList(Constant.user.getToken(),pageNo)
                .compose(this.<List<BrowsingHistoryDto>>applySchedulers());
    }

    /**
     * 获取用户信息
     * @return
     */
    public Flowable<UserInfoDto> getUserInfo(){
        return getService().getUserInfo(Constant.user.getToken())
                .compose(this.<UserInfoDto>applySchedulers());
    }

    /**
     * 编辑个人信息
     * @param headImg
     * @param driveCard
     * @param name
     * @param personalCard
     * @param phone
     * @param travelBook
     * @param carType
     * @param carLocation
     * @param licensePlate
     * @param maxLoad
     * @return
     */
    public Flowable<String> editUserInfo(String headImg,String driveCard,String name,String personalCard,
                                         String phone,String travelBook,String carType,String carLocation,
                                         String licensePlate,String maxLoad){
        return getService().editUserInfo(Constant.user.getToken(),headImg,driveCard,name,personalCard,
                phone,travelBook,carType,carLocation,licensePlate,maxLoad)
                .compose(this.<String>applySchedulers());
    }
    /**
     * 确认竞价
     * @param demandId
     * @param dealQuote
     * @return
     */
    public Flowable<String> confirmOrder(String demandId,String dealQuote){
        return getService().confirmOrder(Constant.user.getToken(),demandId,dealQuote)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 竞价完成，获取订单信息
     * @param orderId
     * @return
     */
    public Flowable<GoodsOrderDetailDto> getOrderInfo(String orderId){
        return getService().getOrderInfo(Constant.user.getToken(),orderId)
                .compose(this.<GoodsOrderDetailDto>applySchedulers());
    }


    /**
     * 获取订单列表
     * @param type
     * @param pageNo
     * @return
     */
    public Flowable<List<GoodsOrderDetailDto>> getGoodsOrders(int type,int pageNo){
        return getService().getGoodsOrders(Constant.user.getToken(),type,pageNo)
                .compose(this.<List<GoodsOrderDetailDto>>applySchedulers());
    }

    /**
     * 出货完成
     * @param id
     * @param outTunnage
     * @param sourceImage
     * @param sourceTime
     * @return
     */
    public Flowable<String> shipMentGoods(String id,String outTunnage,String sourceImage,String sourceTime){
        return getService().shipMentGoods(Constant.user.getToken(),id,outTunnage,sourceImage,sourceTime)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 送货完成
     * @param id
     * @param bournTunnage
     * @param bournImage
     * @param bournTime
     * @return
     */
    public Flowable<String> sendGoods(String id,String bournTunnage,String bournImage,String bournTime){
        return getService().sendGoods(Constant.user.getToken(),id,bournTunnage,bournImage,bournTime)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 取消订单
     * @param id
     * @return
     */
    public Flowable<String> cancelOrder(String id){
        return getService().cancelOrder(Constant.user.getToken(),id,"4")
                .compose(this.<String>applySchedulers());
    }
    /**
     * 获取消息列表数据
     * @param pageNo
     * @return
     */
    public Flowable<List<MessageDto>> getMessageList(int pageNo){
        return getService().getMessageList(Constant.user.getToken(),pageNo)
                .compose(this.<List<MessageDto>>applySchedulers());
    }

    /**
     * 获取我的账户 详细信息 列表
     * @param pageNo
     * @return
     */
    public Flowable<List<UserAccountDetailDto>> getAccountDetails(int pageNo){
        return getService().getAccountDetails(Constant.user.getToken(),pageNo)
                .compose(this.<List<UserAccountDetailDto>>applySchedulers());
    }

    /**
     * 提现账户的操作
     * 添加、修改、设置默认
     * @param withDrawAccountDto
     * @param isDefault
     * @return
     */
    public  Flowable<String> updateWithDrawAccount(WithDrawAccountDto withDrawAccountDto,String isDefault){
        return getService().createWithDrawAccount(Constant.user.getToken(),withDrawAccountDto.getId(),
                withDrawAccountDto.getUserName(),withDrawAccountDto.getUserPhone(),
                withDrawAccountDto.getCardNumber(),withDrawAccountDto.getAccountType(),withDrawAccountDto.getBankName(),isDefault)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 删除提现账户
     * @param id
     * @return
     */
    public Flowable<String> deleteWithDrawAccount(String id){
        return getService().deleteWithDrawAccount(Constant.user.getToken(),id)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 获取提现列表
     * @return
     */
    public Flowable<List<WithDrawAccountDto>> getWithDrawList(){
        return getService().getWithDrawList(Constant.user.getToken())
                .compose(this.<List<WithDrawAccountDto>>applySchedulers());
    }

    /**
     * 提交 提现申请
     * @param accountId
     * @param amount
     * @return
     */
    public Flowable<String> submiWithDraw(String accountId,String amount){
        return getService().submitWithDraw(Constant.user.getToken(),accountId,amount)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 清空浏览记录
     * @return
     */
    public Flowable<String> clearHistoryBrowing(){
        return getService().clearHistoryBrowing(Constant.user.getToken())
                .compose(this.<String>applySchedulers());
    }

    /**
     * 获取首页跑马灯的 初始化信息
     * @return
     */
    public Flowable<List<String>> getHomeMarqueeDatas(){
        return getService().getHomeMarqueeDatas("")
                .compose(this.<List<String>>applySchedulers());
    }

    /**
     * 上传友盟推送唯一识别码
     * @param deviceToken
     * @return
     */
    public Flowable<String> uploadDeviceToken(String deviceToken){
        return getService().uploadDeviceToken(Constant.user.getToken(),deviceToken)
                .compose(this.<String>applySchedulers());
    }

}

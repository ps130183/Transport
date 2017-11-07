package com.km.transport.api;

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
import com.km.transport.utils.retrofit.Response;
import com.km.transport.utils.retrofit.SecretConstant;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
//import rx.Observable;

/**
 * Created by kamangkeji on 17/2/10.
 */

public interface ApiService {



    /**
     * 登录
     *
     * @param mobilePhone
     * @param smsCode
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/ysUser/registerAndLogin")
    Flowable<Response<UserDto>> login(@Field("phone") String mobilePhone,
                                      @Field("smsCode") String smsCode);

    /**
     * 获取手机验证码
     * @param mobilePhone
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/sms/send/code")
    Flowable<Response<String>> getPhoneCode(@Field("mobilePhone") String mobilePhone);


    /**
     * 上传图片
     *
     * @param file
     * @return
     */
    @Multipart
    @POST(SecretConstant.API_HOST_PATH + "/file/up")
    Flowable<Response<String>> imageUpload(@Part("optionType") RequestBody optionType,
                                           @Part MultipartBody.Part file);

    /**
     * 检测新版本
     * @param version
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/get/app/version")
    Flowable<Response<AppVersionDto>> checkAppVersion(@Field("version") int version);

    /**
     * 获取中国所有城市信息
     * @param city
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/area/findList")
    Flowable<Response<List<City>>> getChinaCitysInfo(@Field("city") String city);

    /**
     * 首页获取 后台提交的货源订单 列表
     * @param pageNo
     * @param sourceProvince
     * @param sourceCity
     * @param sourceZoning
     * @param bournProvince
     * @param bournCity
     * @param bournZoning
     * @param carType
     * @param cardWidth
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/ysdemand/list")
    Flowable<Response<List<HomeGoodsOrderDto>>> getHomeGoodsOrders(@Field("pageNo") int pageNo,
                                                                   @Field("sourceProvince") String sourceProvince,
                                                                   @Field("sourceCity") String sourceCity,
                                                                   @Field("sourceZoning") String sourceZoning,
                                                                   @Field("bournProvince") String bournProvince,
                                                                   @Field("bournCity") String bournCity,
                                                                   @Field("bournZoning") String bournZoning,
                                                                   @Field("carType") String carType,
                                                                   @Field("carWidth") String cardWidth);

    /**
     * 添加快捷路线
     * @param token
     * @param sourceProvince
     * @param sourceCity
     * @param sourceZoning
     * @param bournProvince
     * @param bournCity
     * @param bournZoning
     * @param carType
     * @param cardWidth
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/ysfast/addYsFastLine")
    Flowable<Response<String>> addFastPath(@Field("token") String token,
                                           @Field("sourceProvince") String sourceProvince,
                                           @Field("sourceCity") String sourceCity,
                                           @Field("sourceZoning") String sourceZoning,
                                           @Field("bournProvince") String bournProvince,
                                           @Field("bournCity") String bournCity,
                                           @Field("bournZoning") String bournZoning,
                                           @Field("carType") String carType,
                                           @Field("carWidth") String cardWidth);

    /**
     * 删除快捷路线
     * @param token
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/ysfast/delete")
    Flowable<Response<String>> deleteFastPath(@Field("token") String token,
                                              @Field("id") String id);

    /**
     * 首页 获取 快捷路线列表
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/ysfast/list")
    Flowable<Response<List<HomeFastPathDto>>> getFastPathList(@Field("token") String token,
                                                              @Field("pageNo") int pageNo);

    /**
     * 我 要承揽
     * @param token
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/ysdemand/getYsDemand")
    Flowable<Response<GoodsOrderDetailDto>> hireGoods(@Field("token") String token,
                                                      @Field("id") String id);

    /**
     * 获取浏览记录列表
     * @param token
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/ysBrowse/list")
    Flowable<Response<List<BrowsingHistoryDto>>> getBrowsingHistoryList(@Field("token") String token,
                                                                        @Field("pageNo") int pageNo);

    /**
     * 获取用户信息
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/ysUser/getUser")
    Flowable<Response<UserInfoDto>> getUserInfo(@Field("token") String token);

    /**
     * 修改个人信息
     * @param token
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
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/ysUser/editUser")
    Flowable<Response<String>> editUserInfo(@Field("token") String token,
                                            @Field("headImg") String headImg,
                                            @Field("driveCard") String driveCard,
                                            @Field("name") String name,
                                            @Field("personalCard") String personalCard,
                                            @Field("phone") String phone,
                                            @Field("travelBook") String travelBook,
                                            @Field("carType") String carType,
                                            @Field("carLocation") String carLocation,
                                            @Field("licensePlate") String licensePlate,
                                            @Field("maxLoad") String maxLoad);

    /**
     * 确认竞价
     * @param token
     * @param demandId
     * @param dealQuote
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/ysorder/saveOrder")
    Flowable<Response<String>> confirmOrder(@Field("token") String token,
                                            @Field("demandId") String demandId,
                                            @Field("dealQuote") String dealQuote);

    /**
     * 竞价完成，获取订单信息
     * @param token
     * @param orderId
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/ysorder/getOrderDetail")
    Flowable<Response<GoodsOrderDetailDto>> getOrderInfo(@Field("token") String token,
                                                         @Field("id") String orderId);

    /**
     * 获取订单列表
     * @param token
     * @param type
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/ysorder/list")
    Flowable<Response<List<GoodsOrderDetailDto>>> getGoodsOrders(@Field("token") String token,
                                                                 @Field("type") int type,
                                                                 @Field("pageNo") int pageNo);

    /**
     * 出货
     * @param token
     * @param id
     * @param outTunnage
     * @param sourceImage
     * @param sourceTime
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/ysOrder/edit")
    Flowable<Response<String>> shipMentGoods(@Field("token") String token,
                                        @Field("id") String id,
                                        @Field("outTunnage") String outTunnage,
                                        @Field("sourceImage") String sourceImage,
                                        @Field("source") String sourceTime);

    /**
     * 取消订单
     * @param token
     * @param id
     * @param status
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/ysOrder/edit")
    Flowable<Response<String>> cancelOrder(@Field("token") String token,
                                             @Field("id") String id,
                                             @Field("status") String status);

    /**
     * 送货完成
     * @param token
     * @param id
     * @param bournTunnage
     * @param bournImage
     * @param bournTime
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/ysOrder/edit")
    Flowable<Response<String>> sendGoods(@Field("token") String token,
                                        @Field("id") String id,
                                        @Field("bournTunnage") String bournTunnage,
                                        @Field("bournImage") String bournImage,
                                        @Field("bourn") String bournTime);


    /**
     * 获取消息列表数据
     * @param token
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/ysUserMessage/messageList")
    Flowable<Response<List<MessageDto>>> getMessageList(@Field("token") String token,
                                                        @Field("pageNo") int pageNo);

    /**
     * 获取账户详细信息 列表
     * @param token
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/ysUserAccountStream/findStream")
    Flowable<Response<List<UserAccountDetailDto>>> getAccountDetails(@Field("token") String token,
                                                                     @Field("pageNo") int pageNo);

    /**
     * 体现账户的操作
     * 添加 、 修改 、设置默认 、
     * @param token
     * @param id
     * @param userName
     * @param userPhone
     * @param cardNumber
     * @param accountType
     * @param isDefault
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/ysUserAccount/edit")
    Flowable<Response<String>> createWithDrawAccount(@Field("token") String token,
                                                     @Field("id") String id,
                                                     @Field("userName") String userName,
                                                     @Field("userPhone") String userPhone,
                                                     @Field("cardNumber") String cardNumber,
                                                     @Field("accountType") String accountType,
                                                     @Field("bankName") String bankName,
                                                     @Field("isdDefault") String isDefault);

    /**
     * 删除体现账户
     * @param token
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/ysUserAccount/delete")
    Flowable<Response<String>> deleteWithDrawAccount(@Field("token") String token,
                                                     @Field("id") String id);

    /**
     * 获取提现账户列表
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/ysUserAccount/withdraw")
    Flowable<Response<List<WithDrawAccountDto>>> getWithDrawList(@Field("token") String token);

    /**
     * 提交提现申请
     * @param token
     * @param accountId
     * @param amount
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/ysUserAccount/addWithdraw")
    Flowable<Response<String>> submitWithDraw(@Field("token") String token,
                                              @Field("accountId") String accountId,
                                              @Field("amount") String amount);

    /**
     * 请空历史记录  浏览记录
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/ysBrowse/deleteBrowse")
    Flowable<Response<String>> clearHistoryBrowing(@Field("token") String token);

    /**
     * 获取首页跑马灯的  初始化信息
     * @param fault
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/upush/recentlyList")
    Flowable<Response<List<String>>> getHomeMarqueeDatas(@Field("fault") String fault);

    /**
     * 上传友盟 推送唯一识别码
     * @param token
     * @param deviceTotken
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/ysUser/editDeviceToken")
    Flowable<Response<String>> uploadDeviceToken(@Field("token") String token,
                                                 @Field("deviceToken") String deviceTotken);
}

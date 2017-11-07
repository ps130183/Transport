package com.km.transport.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PengSong on 17/9/21.
 */

public class GoodsOrderDetailDto implements Parcelable {

    /**
     * access : 已注册13天已发货14条
     * adress : fdsfds
     * bournCity : 保定
     * bournProvince : 河北
     * bournZoning : 易县
     * carType : 六轴仓栏
     * carWidth : 4.2
     * comment : 12
     * createDate : 1505720082000
     * dayTunnage : 12
     * demandNumber : 14
     * headImg : f
     * id : 1
     * material : 12
     * name : dsf
     * phone : 15733235525
     * price : 12
     * sourceCity : 邯郸
     * sourceProvince : 河北
     * sourceZoning : 磁县
     * tunnage : 12
     * updateDate : 1505720084000
     * userDate : 1504769652000
     * userId : 1
     */

    private String access;
    private String adress;
    private String bournCity;
    private String bournProvince;
    private String bournZoning;
    private String carType;
    private String carWidth;
    private String comment;
    private long createDate;
    private String dayTunnage;
    private String demandNumber;
    private String headImg;
    private String id;
    private String material;
    private String name;
    private String phone;
    private String price;
    private String sourceCity;
    private String sourceProvince;
    private String sourceZoning;
    private String tunnage;
    private long updateDate;
    private long userDate;
    private String userId;
    /**
     * bournAdressDetail : fsdfsd
     * paccess : 2天前
     * sourceAdressDetail : fgdsfsdf
     */

    private String bournAdressDetail;
    private String paccess;
    private String sourceAdressDetail;
    /**
     * acceptTime : 1506062052000
     */

    private long acceptTime;
    /**
     * dealQuote : 12
     * deliverCompany : dsf
     * demandId : 1
     * orderNo : PO20170925164033109
     * quote : 12
     * status : 1
     */

    private String dealQuote;
    private String deliverCompany;
    private String demandId;
    private String orderNo;
    private String quote;
    private int status;
    /**
     * bournImage : http://192.168.10.131:8083/img/user/201709/0b9900cbb4344df8bb2e565b4ddcb6de.jpg
     * bournTime : 1506421020000
     * bournTunnage : 18
     * outTunnage : 20
     * sourceImage : http://192.168.10.131:8083/img/user/201709/92fa4bf8c3c14926b0cf120e6efd05bf.jpg
     * sourceTime : 1506420900000
     * status : 7
     */

    private String bournImage;
    private long bournTime;
    private String bournTunnage;
    private String outTunnage;
    private String sourceImage;
    private long sourceTime;
    /**
     * clientName : 规范大哥
     * clientPhone : 132131
     * codeUrl : http://192.168.10.131:8083/auth/ysorder/getOrderDetail?id=9
     * deliverUserId : admin
     * manufacturer : 北京汽车
     * status : 2
     * stockBanks : 1234564767
     * stockUnits : 天堂鸟
     */

    private String clientName;
    private String clientPhone;
    private String codeUrl;
    private String deliverUserId;
    private String manufacturer;
    private String stockBanks;
    private String stockUnits;
    /**
     * licensePlate : 2134131
     * status : 2
     */

    private String licensePlate;
    /**
     * status : 7
     * sumPrice : 1400
     */

    private String sumPrice;


    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getBournCity() {
        return bournCity;
    }

    public void setBournCity(String bournCity) {
        this.bournCity = bournCity;
    }

    public String getBournProvince() {
        return bournProvince;
    }

    public void setBournProvince(String bournProvince) {
        this.bournProvince = bournProvince;
    }

    public String getBournZoning() {
        return bournZoning;
    }

    public void setBournZoning(String bournZoning) {
        this.bournZoning = bournZoning;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarWidth() {
        return carWidth;
    }

    public void setCarWidth(String carWidth) {
        this.carWidth = carWidth;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getDayTunnage() {
        return dayTunnage;
    }

    public void setDayTunnage(String dayTunnage) {
        this.dayTunnage = dayTunnage;
    }

    public String getDemandNumber() {
        return demandNumber;
    }

    public void setDemandNumber(String demandNumber) {
        this.demandNumber = demandNumber;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSourceCity() {
        return sourceCity;
    }

    public void setSourceCity(String sourceCity) {
        this.sourceCity = sourceCity;
    }

    public String getSourceProvince() {
        return sourceProvince;
    }

    public void setSourceProvince(String sourceProvince) {
        this.sourceProvince = sourceProvince;
    }

    public String getSourceZoning() {
        return sourceZoning;
    }

    public void setSourceZoning(String sourceZoning) {
        this.sourceZoning = sourceZoning;
    }

    public String getTunnage() {
        return tunnage;
    }

    public void setTunnage(String tunnage) {
        this.tunnage = tunnage;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public long getUserDate() {
        return userDate;
    }

    public void setUserDate(long userDate) {
        this.userDate = userDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public GoodsOrderDetailDto() {
    }

    public String getBournAdressDetail() {
        return bournAdressDetail;
    }

    public void setBournAdressDetail(String bournAdressDetail) {
        this.bournAdressDetail = bournAdressDetail;
    }

    public String getPaccess() {
        return paccess;
    }

    public void setPaccess(String paccess) {
        this.paccess = paccess;
    }

    public String getSourceAdressDetail() {
        return sourceAdressDetail;
    }

    public void setSourceAdressDetail(String sourceAdressDetail) {
        this.sourceAdressDetail = sourceAdressDetail;
    }

    public long getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(long acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getDealQuote() {
        return dealQuote;
    }

    public void setDealQuote(String dealQuote) {
        this.dealQuote = dealQuote;
    }

    public String getDeliverCompany() {
        return deliverCompany;
    }

    public void setDeliverCompany(String deliverCompany) {
        this.deliverCompany = deliverCompany;
    }

    public String getDemandId() {
        return demandId;
    }

    public void setDemandId(String demandId) {
        this.demandId = demandId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBournImage() {
        return bournImage;
    }

    public void setBournImage(String bournImage) {
        this.bournImage = bournImage;
    }

    public long getBournTime() {
        return bournTime;
    }

    public void setBournTime(long bournTime) {
        this.bournTime = bournTime;
    }

    public String getBournTunnage() {
        return bournTunnage;
    }

    public void setBournTunnage(String bournTunnage) {
        this.bournTunnage = bournTunnage;
    }

    public String getOutTunnage() {
        return outTunnage;
    }

    public void setOutTunnage(String outTunnage) {
        this.outTunnage = outTunnage;
    }

    public String getSourceImage() {
        return sourceImage;
    }

    public void setSourceImage(String sourceImage) {
        this.sourceImage = sourceImage;
    }

    public long getSourceTime() {
        return sourceTime;
    }

    public void setSourceTime(long sourceTime) {
        this.sourceTime = sourceTime;
    }

    @Override
    public String toString() {
        return "GoodsOrderDetailDto{" +
                "access='" + access + '\'' +
                ", adress='" + adress + '\'' +
                ", bournCity='" + bournCity + '\'' +
                ", bournProvince='" + bournProvince + '\'' +
                ", bournZoning='" + bournZoning + '\'' +
                ", carType='" + carType + '\'' +
                ", carWidth='" + carWidth + '\'' +
                ", comment='" + comment + '\'' +
                ", createDate=" + createDate +
                ", dayTunnage='" + dayTunnage + '\'' +
                ", demandNumber='" + demandNumber + '\'' +
                ", headImg='" + headImg + '\'' +
                ", id='" + id + '\'' +
                ", material='" + material + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", price='" + price + '\'' +
                ", sourceCity='" + sourceCity + '\'' +
                ", sourceProvince='" + sourceProvince + '\'' +
                ", sourceZoning='" + sourceZoning + '\'' +
                ", tunnage='" + tunnage + '\'' +
                ", updateDate=" + updateDate +
                ", userDate=" + userDate +
                ", userId='" + userId + '\'' +
                ", bournAdressDetail='" + bournAdressDetail + '\'' +
                ", paccess='" + paccess + '\'' +
                ", sourceAdressDetail='" + sourceAdressDetail + '\'' +
                ", acceptTime=" + acceptTime +
                ", dealQuote='" + dealQuote + '\'' +
                ", deliverCompany='" + deliverCompany + '\'' +
                ", demandId='" + demandId + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", quote='" + quote + '\'' +
                ", status=" + status +
                ", bournImage='" + bournImage + '\'' +
                ", bournTime=" + bournTime +
                ", bournTunnage='" + bournTunnage + '\'' +
                ", outTunnage='" + outTunnage + '\'' +
                ", sourceImage='" + sourceImage + '\'' +
                ", sourceTime=" + sourceTime +
                ", clientName='" + clientName + '\'' +
                ", clientPhone='" + clientPhone + '\'' +
                ", codeUrl='" + codeUrl + '\'' +
                ", deliverUserId='" + deliverUserId + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", stockBanks='" + stockBanks + '\'' +
                ", stockUnits='" + stockUnits + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", sumPrice=" + sumPrice +
                '}';
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public String getDeliverUserId() {
        return deliverUserId;
    }

    public void setDeliverUserId(String deliverUserId) {
        this.deliverUserId = deliverUserId;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }


    public String getStockBanks() {
        return stockBanks;
    }

    public void setStockBanks(String stockBanks) {
        this.stockBanks = stockBanks;
    }

    public String getStockUnits() {
        return stockUnits;
    }

    public void setStockUnits(String stockUnits) {
        this.stockUnits = stockUnits;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(String sumPrice) {
        this.sumPrice = sumPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.access);
        dest.writeString(this.adress);
        dest.writeString(this.bournCity);
        dest.writeString(this.bournProvince);
        dest.writeString(this.bournZoning);
        dest.writeString(this.carType);
        dest.writeString(this.carWidth);
        dest.writeString(this.comment);
        dest.writeLong(this.createDate);
        dest.writeString(this.dayTunnage);
        dest.writeString(this.demandNumber);
        dest.writeString(this.headImg);
        dest.writeString(this.id);
        dest.writeString(this.material);
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.price);
        dest.writeString(this.sourceCity);
        dest.writeString(this.sourceProvince);
        dest.writeString(this.sourceZoning);
        dest.writeString(this.tunnage);
        dest.writeLong(this.updateDate);
        dest.writeLong(this.userDate);
        dest.writeString(this.userId);
        dest.writeString(this.bournAdressDetail);
        dest.writeString(this.paccess);
        dest.writeString(this.sourceAdressDetail);
        dest.writeLong(this.acceptTime);
        dest.writeString(this.dealQuote);
        dest.writeString(this.deliverCompany);
        dest.writeString(this.demandId);
        dest.writeString(this.orderNo);
        dest.writeString(this.quote);
        dest.writeInt(this.status);
        dest.writeString(this.bournImage);
        dest.writeLong(this.bournTime);
        dest.writeString(this.bournTunnage);
        dest.writeString(this.outTunnage);
        dest.writeString(this.sourceImage);
        dest.writeLong(this.sourceTime);
        dest.writeString(this.clientName);
        dest.writeString(this.clientPhone);
        dest.writeString(this.codeUrl);
        dest.writeString(this.deliverUserId);
        dest.writeString(this.manufacturer);
        dest.writeString(this.stockBanks);
        dest.writeString(this.stockUnits);
        dest.writeString(this.licensePlate);
        dest.writeString(this.sumPrice);
    }

    protected GoodsOrderDetailDto(Parcel in) {
        this.access = in.readString();
        this.adress = in.readString();
        this.bournCity = in.readString();
        this.bournProvince = in.readString();
        this.bournZoning = in.readString();
        this.carType = in.readString();
        this.carWidth = in.readString();
        this.comment = in.readString();
        this.createDate = in.readLong();
        this.dayTunnage = in.readString();
        this.demandNumber = in.readString();
        this.headImg = in.readString();
        this.id = in.readString();
        this.material = in.readString();
        this.name = in.readString();
        this.phone = in.readString();
        this.price = in.readString();
        this.sourceCity = in.readString();
        this.sourceProvince = in.readString();
        this.sourceZoning = in.readString();
        this.tunnage = in.readString();
        this.updateDate = in.readLong();
        this.userDate = in.readLong();
        this.userId = in.readString();
        this.bournAdressDetail = in.readString();
        this.paccess = in.readString();
        this.sourceAdressDetail = in.readString();
        this.acceptTime = in.readLong();
        this.dealQuote = in.readString();
        this.deliverCompany = in.readString();
        this.demandId = in.readString();
        this.orderNo = in.readString();
        this.quote = in.readString();
        this.status = in.readInt();
        this.bournImage = in.readString();
        this.bournTime = in.readLong();
        this.bournTunnage = in.readString();
        this.outTunnage = in.readString();
        this.sourceImage = in.readString();
        this.sourceTime = in.readLong();
        this.clientName = in.readString();
        this.clientPhone = in.readString();
        this.codeUrl = in.readString();
        this.deliverUserId = in.readString();
        this.manufacturer = in.readString();
        this.stockBanks = in.readString();
        this.stockUnits = in.readString();
        this.licensePlate = in.readString();
        this.sumPrice = in.readString();
    }

    public static final Creator<GoodsOrderDetailDto> CREATOR = new Creator<GoodsOrderDetailDto>() {
        @Override
        public GoodsOrderDetailDto createFromParcel(Parcel source) {
            return new GoodsOrderDetailDto(source);
        }

        @Override
        public GoodsOrderDetailDto[] newArray(int size) {
            return new GoodsOrderDetailDto[size];
        }
    };
}

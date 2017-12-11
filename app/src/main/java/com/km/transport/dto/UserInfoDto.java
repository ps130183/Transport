package com.km.transport.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PengSong on 17/9/21.
 */

public class UserInfoDto implements Parcelable {

    /**
     * adress : fdsfds
     * balance : 100
     * carLocation : dsf
     * carType : dsf
     * company : dsf
     * createDate : 1504769652000
     * driveCard : dsf
     * headImg : f
     * id : 1
     * joinTime : 嗨,这是你加入土石Pro的第12天
     * name : dsf
     * personalCard : fdsf
     * phone : 15733235525
     * travelBook : dsf
     * updateDate : 1505720050000
     */

    private String adress;
    private String balance;
    private String carLocation;
    private String carType;
    private String company;
    private long createDate;
    private String driveCard;
    private String headImg;
    private String id;
    private String joinTime;
    private String name;
    private String personalCard;
    private String phone;
    private String travelBook;
    private int validStatus; //0：未验证 1：已验证
    private long updateDate;
    private int readStatus;

    private String licensePlate;
    private String maxLoad;
    private String shareUrl;

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getMaxLoad() {
        return maxLoad;
    }

    public void setMaxLoad(String maxLoad) {
        this.maxLoad = maxLoad;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCarLocation() {
        return carLocation;
    }

    public void setCarLocation(String carLocation) {
        this.carLocation = carLocation;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getDriveCard() {
        return driveCard;
    }

    public void setDriveCard(String driveCard) {
        this.driveCard = driveCard;
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

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonalCard() {
        return personalCard;
    }

    public void setPersonalCard(String personalCard) {
        this.personalCard = personalCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTravelBook() {
        return travelBook;
    }

    public void setTravelBook(String travelBook) {
        this.travelBook = travelBook;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public int getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(int validStatus) {
        this.validStatus = validStatus;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    @Override
    public String toString() {
        return "UserInfoDto{" +
                "adress='" + adress + '\'' +
                ", balance='" + balance + '\'' +
                ", carLocation='" + carLocation + '\'' +
                ", carType='" + carType + '\'' +
                ", company='" + company + '\'' +
                ", createDate=" + createDate +
                ", driveCard='" + driveCard + '\'' +
                ", headImg='" + headImg + '\'' +
                ", id='" + id + '\'' +
                ", joinTime='" + joinTime + '\'' +
                ", name='" + name + '\'' +
                ", personalCard='" + personalCard + '\'' +
                ", phone='" + phone + '\'' +
                ", travelBook='" + travelBook + '\'' +
                ", validStatus=" + validStatus +
                ", updateDate=" + updateDate +
                ", readStatus=" + readStatus +
                ", licensePlate='" + licensePlate + '\'' +
                ", maxLoad='" + maxLoad + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                '}';
    }

    public int getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }

    public UserInfoDto() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.adress);
        dest.writeString(this.balance);
        dest.writeString(this.carLocation);
        dest.writeString(this.carType);
        dest.writeString(this.company);
        dest.writeLong(this.createDate);
        dest.writeString(this.driveCard);
        dest.writeString(this.headImg);
        dest.writeString(this.id);
        dest.writeString(this.joinTime);
        dest.writeString(this.name);
        dest.writeString(this.personalCard);
        dest.writeString(this.phone);
        dest.writeString(this.travelBook);
        dest.writeInt(this.validStatus);
        dest.writeLong(this.updateDate);
        dest.writeInt(this.readStatus);
        dest.writeString(this.licensePlate);
        dest.writeString(this.maxLoad);
        dest.writeString(this.shareUrl);
    }

    protected UserInfoDto(Parcel in) {
        this.adress = in.readString();
        this.balance = in.readString();
        this.carLocation = in.readString();
        this.carType = in.readString();
        this.company = in.readString();
        this.createDate = in.readLong();
        this.driveCard = in.readString();
        this.headImg = in.readString();
        this.id = in.readString();
        this.joinTime = in.readString();
        this.name = in.readString();
        this.personalCard = in.readString();
        this.phone = in.readString();
        this.travelBook = in.readString();
        this.validStatus = in.readInt();
        this.updateDate = in.readLong();
        this.readStatus = in.readInt();
        this.licensePlate = in.readString();
        this.maxLoad = in.readString();
        this.shareUrl = in.readString();
    }

    public static final Creator<UserInfoDto> CREATOR = new Creator<UserInfoDto>() {
        @Override
        public UserInfoDto createFromParcel(Parcel source) {
            return new UserInfoDto(source);
        }

        @Override
        public UserInfoDto[] newArray(int size) {
            return new UserInfoDto[size];
        }
    };
}

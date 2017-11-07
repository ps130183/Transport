package com.km.transport.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PengSong on 17/9/20.
 */

public class HomeFastPathDto implements Parcelable {

    /**
     * bournCity : 天津市
     * bournProvince : 天津
     * bournZoning : 红桥区
     * demandCount : 0
     * id : 2
     * sourceCity : 秦皇岛市
     * sourceProvince : 河北省
     * sourceZoning : 山海关区
     */

    private String bournCity;
    private String bournProvince;
    private String bournZoning;
    private String demandCount;
    private String id;
    private String sourceCity;
    private String sourceProvince;
    private String sourceZoning;

    private String carType;
    private String carWidth;

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

    public String getDemandCount() {
        return demandCount;
    }

    public void setDemandCount(String demandCount) {
        this.demandCount = demandCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "HomeFastPathDto{" +
                "bournCity='" + bournCity + '\'' +
                ", bournProvince='" + bournProvince + '\'' +
                ", bournZoning='" + bournZoning + '\'' +
                ", demandCount='" + demandCount + '\'' +
                ", id='" + id + '\'' +
                ", sourceCity='" + sourceCity + '\'' +
                ", sourceProvince='" + sourceProvince + '\'' +
                ", sourceZoning='" + sourceZoning + '\'' +
                ", carType='" + carType + '\'' +
                ", carWidth='" + carWidth + '\'' +
                '}';
    }

    public HomeFastPathDto() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bournCity);
        dest.writeString(this.bournProvince);
        dest.writeString(this.bournZoning);
        dest.writeString(this.demandCount);
        dest.writeString(this.id);
        dest.writeString(this.sourceCity);
        dest.writeString(this.sourceProvince);
        dest.writeString(this.sourceZoning);
        dest.writeString(this.carType);
        dest.writeString(this.carWidth);
    }

    protected HomeFastPathDto(Parcel in) {
        this.bournCity = in.readString();
        this.bournProvince = in.readString();
        this.bournZoning = in.readString();
        this.demandCount = in.readString();
        this.id = in.readString();
        this.sourceCity = in.readString();
        this.sourceProvince = in.readString();
        this.sourceZoning = in.readString();
        this.carType = in.readString();
        this.carWidth = in.readString();
    }

    public static final Creator<HomeFastPathDto> CREATOR = new Creator<HomeFastPathDto>() {
        @Override
        public HomeFastPathDto createFromParcel(Parcel source) {
            return new HomeFastPathDto(source);
        }

        @Override
        public HomeFastPathDto[] newArray(int size) {
            return new HomeFastPathDto[size];
        }
    };
}

package com.km.transport.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.km.transport.basic.BaseEntity;

/**
 * Created by kamangkeji on 17/3/19.
 */

public class WithDrawAccountDto extends BaseEntity implements Parcelable {

    /**
     * accountType : 支付宝
     * cardNumber : 15631707132
     * createDate : 1506655701000
     * id : 6
     * isDefault : 1
     * updateDate : 1506655701000
     * userId : 8
     * userName : 彭松
     * userPhone : 15631707132
     */

    private String accountType;
    private String cardNumber;
    private long createDate;
    private String id;
    private String isDefault;
    private long updateDate;
    private String userId;
    private String userName;
    private String userPhone;
    private String bankName;

    @Override
    public boolean isEmpty() {
        return false;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public WithDrawAccountDto() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.accountType);
        dest.writeString(this.cardNumber);
        dest.writeLong(this.createDate);
        dest.writeString(this.id);
        dest.writeString(this.isDefault);
        dest.writeLong(this.updateDate);
        dest.writeString(this.userId);
        dest.writeString(this.userName);
        dest.writeString(this.userPhone);
        dest.writeString(this.bankName);
    }

    protected WithDrawAccountDto(Parcel in) {
        this.accountType = in.readString();
        this.cardNumber = in.readString();
        this.createDate = in.readLong();
        this.id = in.readString();
        this.isDefault = in.readString();
        this.updateDate = in.readLong();
        this.userId = in.readString();
        this.userName = in.readString();
        this.userPhone = in.readString();
        this.bankName = in.readString();
    }

    public static final Creator<WithDrawAccountDto> CREATOR = new Creator<WithDrawAccountDto>() {
        @Override
        public WithDrawAccountDto createFromParcel(Parcel source) {
            return new WithDrawAccountDto(source);
        }

        @Override
        public WithDrawAccountDto[] newArray(int size) {
            return new WithDrawAccountDto[size];
        }
    };
}

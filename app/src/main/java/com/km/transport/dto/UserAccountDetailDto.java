package com.km.transport.dto;

/**
 * Created by kamangkeji on 17/4/1.
 */

public class UserAccountDetailDto {

    /**
     * accountId : 1
     * amount : 14
     * content : 帐户提取现金
     * createDate : 1505964325000
     * id : 1
     * orderNo : 14141
     * tradeType : 1
     * updateDate : 1505964327000
     * userId : 8
     */

    private String accountId;
    private int amount;
    private String content;
    private long createDate;
    private String id;
    private String orderNo;
    private String tradeType;
    private long updateDate;
    private String userId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
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
}

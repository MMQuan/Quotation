package com.example.database.bean;

import com.orm.SugarRecord;

import java.util.Arrays;

/**
 * @author zhc
 */
public class MoneyBean extends SugarRecord {


    private String moneyId;
    private String moneyType;
    private String moneyDate;
    private String moneyName;
    private double moneyNum;
    private byte[] picture;
    private String remark;

    @Override
    public String toString() {
        return "MoneyBean{" +
                "moneyId='" + moneyId + '\'' +
                ", moneyType='" + moneyType + '\'' +
                ", moneyDate='" + moneyDate + '\'' +
                ", moneyName='" + moneyName + '\'' +
                ", moneyNum=" + moneyNum +
                ", picture=" + Arrays.toString(picture) +
                ", remark='" + remark + '\'' +
                '}';
    }

    public String getMoneyId() {
        return moneyId;
    }

    public void setMoneyId(String moneyId) {
        this.moneyId = moneyId;
    }

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }

    public String getMoneyDate() {
        return moneyDate;
    }

    public void setMoneyDate(String moneyDate) {
        this.moneyDate = moneyDate;
    }

    public String getMoneyName() {
        return moneyName;
    }

    public void setMoneyName(String moneyName) {
        this.moneyName = moneyName;
    }

    public double getMoneyNum() {
        return moneyNum;
    }

    public void setMoneyNum(double moneyNum) {
        this.moneyNum = moneyNum;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

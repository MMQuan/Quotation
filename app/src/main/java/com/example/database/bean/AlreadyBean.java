package com.example.database.bean;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * @author zhc
 */
public class AlreadyBean extends SugarRecord {

    /**
     * 事项 id
     */
    private String alreadyId;

    /**
     * 事项名
     */
    private String alreadyName;

    /**
     * 负责人
     */
    private String headName;

    /**
     * 完成日期
     */
    private String endDate;

    /**
     * 单价
     */
    private double price;

    /**
     * 数量
     */
    private double number;

    /**
     * 单位
     */
    private String unit;

    /**
     * 合计
     */
    private double total;

    /**
     * 施工人
     */
    private String workName;

    @Override
    public String toString() {
        return "AlreadyBean{" +
                "alreadyId=" + alreadyId +
                ", alreadyName='" + alreadyName + '\'' +
                ", headName='" + headName + '\'' +
                ", endDate=" + endDate +
                ", price=" + price +
                ", number=" + number +
                ", unit='" + unit + '\'' +
                ", total=" + total +
                ", workName='" + workName + '\'' +
                '}';
    }

    public String getAlreadyId() {
        return alreadyId;
    }

    public void setAlreadyId(String alreadyId) {
        this.alreadyId = alreadyId;
    }

    public String getAlreadyName() {
        return alreadyName;
    }

    public void setAlreadyName(String alreadyName) {
        this.alreadyName = alreadyName;
    }

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }
}

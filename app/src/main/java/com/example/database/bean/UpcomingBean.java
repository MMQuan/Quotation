package com.example.database.bean;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhc
 */
public class UpcomingBean extends SugarRecord {

    /**
     * 待办 id
     */
    private String upcomingId;

    /**
     * 待办名
     */
    private String upcomingName;

    /**
     * 最迟日期
     */
    private Date latestDate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否完成
     */
    private boolean logo;

    @Override
    public String toString() {
        return "UpcomingBean{" +
                "upcomingId=" + upcomingId +
                ", upcomingName='" + upcomingName + '\'' +
                ", latestDate=" + latestDate +
                ", remark='" + remark + '\'' +
                ", logo=" + logo +
                '}';
    }

    public String getUpcomingId() {
        return upcomingId;
    }

    public void setUpcomingId(String upcomingId) {
        this.upcomingId = upcomingId;
    }

    public String getUpcomingName() {
        return upcomingName;
    }

    public void setUpcomingName(String upcomingName) {
        this.upcomingName = upcomingName;
    }

    public Date getLatestDate() {
        return latestDate;
    }

    public void setLatestDate(Date latestDate) {
        this.latestDate = latestDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isLogo() {
        return logo;
    }

    public void setLogo(boolean logo) {
        this.logo = logo;
    }
}

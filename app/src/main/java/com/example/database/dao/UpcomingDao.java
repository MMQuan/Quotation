package com.example.database.dao;

import com.example.database.bean.UpcomingBean;
import com.orm.SugarRecord;

import java.util.List;

/**
 * @author zhc
 */
public class UpcomingDao {

    public void saveOne(UpcomingBean upcomingBean){
        upcomingBean.save();
    }

    public List<UpcomingBean> findAll(){
        return SugarRecord.find(UpcomingBean.class,"logo = false order by latest_date");
    }

    public List<UpcomingBean> findAllY(){
        return SugarRecord.find(UpcomingBean.class,"logo = true order by latest_date desc");
    }

    public void deleteOne(String id){
        List<UpcomingBean> upcomingBean = UpcomingBean.find(UpcomingBean.class,"upcoming_id = '" + id + "'");
        for (UpcomingBean bean : upcomingBean) {
            bean.delete();
        }
    }

    public void updateOne(String id){
        List<UpcomingBean> upcomingBean = UpcomingBean.find(UpcomingBean.class,"upcoming_id = '" + id + "'");
        for (UpcomingBean bean : upcomingBean) {
            bean.setLogo(true);
            bean.save();
        }
    }

    public void deleteAll() {
        List<UpcomingBean> upcomingBean = UpcomingBean.find(UpcomingBean.class,"logo = true");
        for (UpcomingBean bean : upcomingBean) {
            bean.delete();
        }
    }
}

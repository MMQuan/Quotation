package com.example.database.dao;

import com.example.database.bean.AlreadyBean;
import com.example.database.bean.UpcomingBean;
import com.orm.SugarRecord;

import java.util.List;

/**
 * @author zhc
 */
public class AlreadyDao {

    public void saveOne(AlreadyBean alreadyBean){
        alreadyBean.save();
    }

    public List<AlreadyBean> findAll(String year, String month){
        return AlreadyBean.find(AlreadyBean.class,
                "end_date like '"+ year + "%" + month + "-%' order by end_date desc");
    }

    public String findSum(String year, String month){
        List<AlreadyBean> alreadyBean = AlreadyBean.find(AlreadyBean.class,
                "end_date like '"+ year + "%" + month + "-%'");
        double sum = 0.00;
        for (AlreadyBean bean : alreadyBean) {
            sum += bean.getTotal();
        }
        return String.valueOf(sum);
    }

    public void deleteOne(String id){
        List<AlreadyBean> alreadyBean = AlreadyBean.find(AlreadyBean.class,"already_id = '" + id + "'");
        for (AlreadyBean bean : alreadyBean) {
            bean.delete();
        }
    }

    public AlreadyBean updateOne(String id){
        List<AlreadyBean> alreadyBean = AlreadyBean.find(AlreadyBean.class,"already_id = '" + id + "'");
        return alreadyBean.get(0);
    }


    public List<AlreadyBean> findAll(String year, String month, String name){
        return AlreadyBean.find(AlreadyBean.class,
                "end_date like '"+ year + "%" + month + "-%' and head_name = '"+ name + "' order by end_date");
    }

    public String findAllSum(String year, String month, String name){
        List<AlreadyBean> alreadyBeans = AlreadyBean.find(AlreadyBean.class,
                "end_date like '" + year + "%" + month + "-%' and head_name = '" + name + "' order by end_date");
        double sum = 0.00;
        for (AlreadyBean alreadyBean : alreadyBeans) {
            sum += alreadyBean.getTotal();
        }
        return  String.valueOf(sum);
    }

}

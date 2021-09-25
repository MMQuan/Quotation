package com.example.database.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.database.DBCreat.SugarDB;
import com.example.database.bean.MoneyBean;
import com.example.tool.BitmapFormToByte;
import com.orm.SugarDb;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;


import static com.orm.SugarContext.getSugarContext;

/**
 * @author zhc
 */
public class MoneyDao {


    public long saveOne(MoneyBean moneyBean){
        return moneyBean.save();
    }

    public List<MoneyBean> findAll(Context context,String year, String month){
        SugarDb sugarDb = new SugarDb(context);
        SQLiteDatabase writableDatabase = sugarDb.getWritableDatabase();
        String[] aaa = {year + "%" + month +"-%"};
        Cursor money_bean = writableDatabase.query("money_bean",
                new String[]{"money_id","money_type","money_name","money_num","money_date","remark"},
                "money_date like ?", aaa, null, null, "money_date desc");
        List<MoneyBean> list = new ArrayList<>();
        while (money_bean.moveToNext()){
            MoneyBean moneyBean = new MoneyBean();
            moneyBean.setMoneyId(money_bean.getString(money_bean.getColumnIndex("money_id")+1));
            moneyBean.setMoneyType(money_bean.getString(money_bean.getColumnIndex("money_type")+2));
            moneyBean.setMoneyName(money_bean.getString(money_bean.getColumnIndex("money_name")+3));
            moneyBean.setMoneyNum(money_bean.getDouble(money_bean.getColumnIndex("money_num")+4));
            moneyBean.setMoneyDate(money_bean.getString(money_bean.getColumnIndex("money_date")+5));
            moneyBean.setRemark(money_bean.getString(money_bean.getColumnIndex("remark")+6));
            list.add(moneyBean);
        }
        return list;
    }

    public List<MoneyBean> findAll(Context context,String sea){
        SugarDb sugarDb = new SugarDb(context);
        SQLiteDatabase writableDatabase = sugarDb.getWritableDatabase();
        String[] aaa = {"%" + sea + "%"};
        Cursor money_bean;
        if (new BitmapFormToByte().isDouble(sea)){
            money_bean = writableDatabase.query("money_bean",
                    new String[]{"money_id","money_type","money_name","money_num","money_date","remark"},
                    "money_num like ?", aaa, null, null, null);
        }else {
            money_bean = writableDatabase.query("money_bean",
                    new String[]{"money_id","money_type","money_name","money_num","money_date","remark"},
                    "money_name like ?", aaa, null, null, null);
        }
        List<MoneyBean> list = new ArrayList<>();
        while (money_bean.moveToNext()){
            MoneyBean moneyBean = new MoneyBean();
            moneyBean.setMoneyId(money_bean.getString(money_bean.getColumnIndex("money_id")+1));
            moneyBean.setMoneyType(money_bean.getString(money_bean.getColumnIndex("money_type")+2));
            moneyBean.setMoneyName(money_bean.getString(money_bean.getColumnIndex("money_name")+3));
            moneyBean.setMoneyNum(money_bean.getDouble(money_bean.getColumnIndex("money_num")+4));
            moneyBean.setMoneyDate(money_bean.getString(money_bean.getColumnIndex("money_date")+5));
            moneyBean.setRemark(money_bean.getString(money_bean.getColumnIndex("remark")+6));
            list.add(moneyBean);
        }
        return list;
    }

    public String findOut (Context context,String year,String month){
        SugarDb sugarDb = new SugarDb(context);
        SQLiteDatabase writableDatabase = sugarDb.getWritableDatabase();
        String[] aaa = {year + "%" + month +"-%","支出"};
        Cursor money_bean = writableDatabase.query("money_bean",
                new String[]{"money_num"},
                "money_date like ? and money_type = ?", aaa, null, null, null);
        double sum = 0;
        while (money_bean.moveToNext()){
            sum += money_bean.getDouble(money_bean.getColumnIndex("money_num")+1);
        }
        return String.valueOf(sum);
    }
    public String findIn(Context context,String year,String month){
        SugarDb sugarDb = new SugarDb(context);
        SQLiteDatabase writableDatabase = sugarDb.getWritableDatabase();
        String[] aaa = {year + "%" + month +"-%","收入"};
        Cursor money_bean = writableDatabase.query("money_bean",
                new String[]{"money_num"},
                "money_date like ? and money_type = ?", aaa, null, null, null);
        double sum = 0;
        while (money_bean.moveToNext()){
            sum += money_bean.getDouble(money_bean.getColumnIndex("money_num")+1);
        }
        return String.valueOf(sum);
    }

    public byte[] findImage(Context context,String id){
        SugarDb sugarDb = new SugarDb(context);
        SQLiteDatabase writableDatabase = sugarDb.getWritableDatabase();
        String[] aaa = {id};
        Cursor money_bean = writableDatabase.query("money_bean",
                new String[]{"picture"},
                "money_id = ?", aaa, null, null, null);
        byte []  picture = null;
        while (money_bean.moveToNext()){
            picture = money_bean.getBlob(money_bean.getColumnIndex("picture") + 1);
        }
        return picture;
    }

    public int deleteOne(Context context,String id){
        SugarDb sugarDb = new SugarDb(context);
        SQLiteDatabase writableDatabase = sugarDb.getWritableDatabase();
        String[] aaa = {id};
        int money_bean = writableDatabase.delete("money_bean",
                "money_id = ?", aaa);
        return money_bean;
    }

}

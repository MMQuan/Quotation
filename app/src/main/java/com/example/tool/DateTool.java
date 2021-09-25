package com.example.tool;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTool {
    Calendar calendar = Calendar.getInstance();
    @SuppressLint("SimpleDateFormat")
    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @SuppressLint("SimpleDateFormat")
    DateFormat sdf1 = new SimpleDateFormat("yyyy-MM");

    public String getYear(Date date){
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    public String getMonth(Date date){
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.MONTH) + 1);
    }

    public String getDay(Date date){
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }

    public int getYearNum(Date date){
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public int getMonthNum(Date date){
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public int getDayNum(Date date){
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public String getMonthNow(){
        calendar.setTime(calendar.getTime());
        return String.valueOf(calendar.get(Calendar.MONTH) + 1);
    }

    public String getYearNow(){
        calendar.setTime(calendar.getTime());
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    public Date getDateGood(String date) throws ParseException {
        return sdf.parse(date);
    }

    public Date getDateGood1(String date) throws ParseException {
        return sdf1.parse(date);
    }

    public String getDateOO(Date date) throws ParseException {
        return sdf.format(date);
    }

    public String getDateOO1(Date date) throws ParseException {
        return sdf1.format(date);
    }



}

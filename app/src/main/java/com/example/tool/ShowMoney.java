package com.example.tool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.example.database.bean.AlreadyBean;
import com.example.database.bean.MoneyBean;
import com.example.database.bean.MoneyBeanTest;
import com.example.database.bean.UpcomingBean;
import com.example.database.dao.AlreadyDao;
import com.example.quotation.R;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author zhc
 */
public class ShowMoney extends BaseAdapter implements View.OnClickListener {

    private List<MoneyBean> mData;
    private Context mContext;
    private InnerItemOnclickListener mListener;

    public ShowMoney(Context mContext,List<MoneyBean> data){
        this.mData = data;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.showmoney, null);
        MoneyBean moneyBean = mData.get(position);
        TextView moneyType = view.findViewById(R.id.moneyType);
        TextView moneyPer = view.findViewById(R.id.moneyPer);
        TextView moneyNumber = view.findViewById(R.id.moneyNumber);
        TextView bZhu = view.findViewById(R.id.bZhu);
        TextView monthMoney = view.findViewById(R.id.monthMoney);
        TextView dayMoney = view.findViewById(R.id.dayMoney);
        moneyType.setText(moneyBean.getMoneyType());
        if ("支出".equals(moneyBean.getMoneyType())){
            moneyPer.setTextColor(mContext.getResources().getColorStateList(R.color.del1));
        }else {
            moneyPer.setTextColor(mContext.getResources().getColorStateList(R.color.ok1));
        }
        moneyPer.setText(moneyBean.getMoneyName());
        moneyNumber.setText(String.valueOf(moneyBean.getMoneyNum()));
        bZhu.setText(moneyBean.getRemark());
        DateTool dateTool = new DateTool();
        try {
            Date d = dateTool.getDateGood(moneyBean.getMoneyDate());
            monthMoney.setText(dateTool.getMonth(d));
            dayMoney.setText(dateTool.getDay(d));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Button lookImage = view.findViewById(R.id.lookImage);
        Button mDel = view.findViewById(R.id.mDel);
        lookImage.setOnClickListener(this);
        mDel.setOnClickListener(this);
        lookImage.setTag(moneyBean.getMoneyId());
        mDel.setTag(moneyBean.getMoneyId());
        return view;
    }

    public interface InnerItemOnclickListener {
        void itemClick(View v);
    }

    public void setOnInnerItemOnClickListener(InnerItemOnclickListener listener){
        this.mListener=listener;
    }

    @Override
    public void onClick(View v) {
        mListener.itemClick(v);
    }
}

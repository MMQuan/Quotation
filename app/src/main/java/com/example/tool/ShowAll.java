package com.example.tool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.database.bean.AlreadyBean;
import com.example.database.bean.UpcomingBean;
import com.example.quotation.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author zhc
 */
public class ShowAll extends BaseAdapter {

    private List<UpcomingBean> mData;
    private Context mContext;

    public ShowAll(Context mContext,List<UpcomingBean> data){
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.showall, null);
        UpcomingBean upcomingBean = mData.get(position);
        TextView serial = view.findViewById(R.id.serial1);
        TextView title = view.findViewById(R.id.titleNo1);
        TextView remarkNo = view.findViewById(R.id.remarkNo1);
        TextView month = view.findViewById(R.id.month1);
        TextView day = view.findViewById(R.id.day1);
        serial.setText(new StringBuilder().append(position+1));
        title.setText(upcomingBean.getUpcomingName());
        remarkNo.setText(upcomingBean.getRemark());
        Date latestDate = upcomingBean.getLatestDate();

        month.setText(new DateTool().getMonth(latestDate));
        day.setText(new DateTool().getDay(latestDate));
        return view;
    }
}

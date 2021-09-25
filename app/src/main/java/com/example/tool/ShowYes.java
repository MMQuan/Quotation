package com.example.tool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.example.database.bean.AlreadyBean;
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
public class ShowYes extends BaseAdapter implements View.OnClickListener {

    private List<AlreadyBean> mData;
    private Context mContext;
    private InnerItemOnclickListener mListener;

    public ShowYes(Context mContext,List<AlreadyBean> data){
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.showyes, null);
        AlreadyBean alreadyBean = mData.get(position);
        TextView serial2 = view.findViewById(R.id.serial2);
        TextView title = view.findViewById(R.id.titleYes);
        TextView Fu = view.findViewById(R.id.Fu);
        TextView Shi = view.findViewById(R.id.Shi);
        TextView Dan = view.findViewById(R.id.Dan);
        TextView Shu = view.findViewById(R.id.Shu);
        TextView Wei = view.findViewById(R.id.Wei);
        TextView He = view.findViewById(R.id.He);
        TextView month = view.findViewById(R.id.month1);
        TextView day = view.findViewById(R.id.day1);
        serial2.setText(new StringBuilder().append(position+1));
        title.setText(alreadyBean.getAlreadyName());
        Fu.setText(alreadyBean.getHeadName());
        Shi.setText(alreadyBean.getWorkName());
        Dan.setText(new StringBuilder().append(alreadyBean.getPrice()));
        Shu.setText(new StringBuilder().append(alreadyBean.getNumber()));
        Wei.setText(new StringBuilder().append(alreadyBean.getUnit()));
        He.setText(new StringBuilder().append(alreadyBean.getTotal()));
        String endDate = alreadyBean.getEndDate();
        DateTool dateTool = new DateTool();
        try {
            Date d = dateTool.getDateGood(endDate);
            month.setText(dateTool.getMonth(d));
            day.setText(dateTool.getDay(d));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Button update = view.findViewById(R.id.update);
        Button del = view.findViewById(R.id.del);
        update.setOnClickListener(this);
        del.setOnClickListener(this);
        update.setTag(alreadyBean.getAlreadyId());
        del.setTag(alreadyBean.getAlreadyId());
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

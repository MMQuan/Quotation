package com.example.quotation;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bigkoo.pickerview.TimePickerView;
import com.example.database.bean.UpcomingBean;
import com.example.database.dao.UpcomingDao;
import com.example.tool.DateTool;
import com.example.tool.ToastWei;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class AddNoActivity extends AppCompatActivity {

    EditText matters,oldDate,remark;
    Button upData1,gotoShowNo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nothing);

        matters = findViewById(R.id.matters1);
        oldDate = findViewById(R.id.oldDate);
        remark = findViewById(R.id.remark);
        upData1 = findViewById(R.id.upData1);
        gotoShowNo = findViewById(R.id.gotoShowNo);

        @SuppressLint("SimpleDateFormat") final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        upData1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                if (matters.getText().toString().isEmpty() || oldDate.getText().toString().isEmpty()){
                    new ToastWei().showToast(AddNoActivity.this,"请至少对前两项做出填写！");
                }else {
                    UpcomingBean upcomingBean = new UpcomingBean();
                    upcomingBean.setUpcomingId(UUID.randomUUID().toString());
                    upcomingBean.setUpcomingName(matters.getText().toString());
                    try {
                        upcomingBean.setLatestDate(sdf.parse(oldDate.getText().toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    upcomingBean.setRemark(remark.getText().toString());
                    upcomingBean.setLogo(false);

                    UpcomingDao upcomingDao = new UpcomingDao();
                    upcomingDao.saveOne(upcomingBean);

                    clear();
                    new ToastWei().showToast(AddNoActivity.this,"保存成功！");
                }
            }
        });

        gotoShowNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddNoActivity.this,NoThingActivity.class));
                finish();
            }
        });


        // 日期
        oldDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                showDate();
            }
        });
        oldDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                showDate();
            }
        });

        //键盘
        findViewById(R.id.key).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
        });
    }


    /**
     * 清理
     */
    private  void clear(){
        matters.setText("");
        remark.setText("");
        oldDate.setText("");
    }


    /**
     * 日期处理
     */
    private void showDate(){
        TimePickerView timePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String start = null;
                try {
                    start = new DateTool().getDateOO(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                oldDate.setText(start);
            }
        }).setType(new boolean[]{true, true, true, false, false, false}).build();
        //注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        timePickerView.setDate(Calendar.getInstance());
        timePickerView.show();
    }
}

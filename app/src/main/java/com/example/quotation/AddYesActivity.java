package com.example.quotation;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.example.database.bean.AlreadyBean;
import com.example.database.dao.AlreadyDao;
import com.example.tool.DateTool;
import com.example.tool.ToastWei;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zhc
 */
public class AddYesActivity extends AppCompatActivity {

    private EditText endDate,headName,matters,price,number,unit,workName;
    private EditText totalOne;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yesthing);

        matters = findViewById(R.id.matters);
        headName = findViewById(R.id.headName);
        endDate = findViewById(R.id.endDate);
        price = findViewById(R.id.price);
        number = findViewById(R.id.number);
        unit = findViewById(R.id.unit);
        totalOne = findViewById(R.id.total);
        workName = findViewById(R.id.workName);
        Button upData = findViewById(R.id.upData);
        Button gotoShowYes = findViewById(R.id.gotoShowYes);


        gotoShowYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddYesActivity.this,YesThingActivity.class));
                finish();
            }
        });

        // 保存数据
        upData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                if (matters.getText().toString().isEmpty()
                        || endDate.getText().toString().isEmpty()
                        || headName.getText().toString().isEmpty()){
                    new ToastWei().showToast(AddYesActivity.this,"请至少对前三项做出填写！");
                }else {
                    AlreadyBean alreadyBean = new AlreadyBean();

                    alreadyBean.setAlreadyId(UUID.randomUUID().toString());
                    alreadyBean.setAlreadyName(matters.getText().toString());
                    alreadyBean.setHeadName(headName.getText().toString());
                    alreadyBean.setEndDate(endDate.getText().toString());
                    if (price.getText().toString().isEmpty()){
                        alreadyBean.setPrice(0.00);
                    }else {
                        alreadyBean.setPrice(Double.parseDouble(price.getText().toString()));
                    }
                    if (number.getText().toString().isEmpty()){
                        alreadyBean.setNumber(0.00);
                    } else {
                        alreadyBean.setNumber(Double.parseDouble(number.getText().toString()));
                    }
                    alreadyBean.setUnit(unit.getText().toString());
                    if (totalOne.getText().toString().isEmpty()){
                        alreadyBean.setTotal(0.00);
                    } else {
                        alreadyBean.setTotal(Double.parseDouble(totalOne.getText().toString()));
                    }
                    alreadyBean.setWorkName(workName.getText().toString());

                    AlreadyDao alreadyDao = new AlreadyDao();
                    alreadyDao.saveOne(alreadyBean);

                    clear();
                    new ToastWei().showToast(AddYesActivity.this,"保存成功！");
                }
            }
        });

        // 负责人选择
        headName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                select();
            }
        });

        // 自动求和
        textChange(number, price);
        textChange(price, number);


        // 日期弹窗
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                showDate();
            }
        });
        endDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                showDate();
            }
        });

        //键盘
        findViewById(R.id.key1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
    }

    /**
     * 清空
     */
    private void clear(){
        matters.setText("");
        headName.setText("");
        endDate.setText("");
        price.setText("0.00");
        number.setText("0.00");
        unit.setText("");
        totalOne.setText("");
        workName.setText("");
    }


    /**
     * 自动求和处理
     * @param price1
     */
    private void textChange(final EditText price1, EditText numberP) {
        numberP.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double number1,number2;
                if (!price1.getText().toString().isEmpty()) {
                    number1 = Double.parseDouble(price1.getText().toString());
                }else {
                    number1 = 0.00;
                }
                DecimalFormat df = new DecimalFormat("0.00");
                if (!s.toString().isEmpty()){
                    number2 = Double.parseDouble(s.toString());
                }else {
                    number2 = 0.00;
                }
                double number3 = number1*number2;
                totalOne.setText(new StringBuilder().append(df.format(number3)));
            }
        });
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
                endDate.setText(start);
            }
        }).setType(new boolean[]{true, true, true, false, false, false}).build();
        //注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        timePickerView.setDate(Calendar.getInstance());
        timePickerView.show();
    }

    /**
     * test tiao
     */
    public void select(){
        final ArrayList names = new ArrayList();
        names.add("姜");
        names.add("河源");
        OptionsPickerView pvOptions = new  OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String tx = names.get(options1).toString();
                headName.setText(tx);
            }
        }).build();
        pvOptions.setPicker(names,null,null);
        pvOptions.show();
    }
}

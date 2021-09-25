package com.example.tool;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import com.example.database.bean.AlreadyBean;
import com.example.database.dao.AlreadyDao;
import com.example.quotation.R;
import com.example.quotation.YesThingActivity;

import java.text.DecimalFormat;

/**
 * @author zhc
 */
public class CustomizationDialog extends Dialog {
    private EditText endDate,headName,matters,price,number,unit,workName,totalOne;
    private Button saveNew,cancel;
    private onOkOnclickListener onOkOnclickListener;

    public CustomizationDialog(@NonNull Context context) {
        super(context, R.style.Dialog_Msg);
    }

    public void setOnOkOnclickListener(CustomizationDialog.onOkOnclickListener onOkOnclickListener){
        this.onOkOnclickListener = onOkOnclickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateyes);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
        //初始化界面控件的事件
        initEvent();
    }

    private void initEvent() {

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomizationDialog.super.dismiss();
            }
        });

        saveNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = onOkOnclickListener.onOkClick();
                AlreadyBean alreadyBean = new AlreadyDao().updateOne(s);
                alreadyBean.setAlreadyName(matters.getText().toString());
                alreadyBean.setHeadName(headName.getText().toString());
                alreadyBean.setEndDate(endDate.getText().toString());
                alreadyBean.setPrice(Double.parseDouble(price.getText().toString()));
                alreadyBean.setNumber(Double.parseDouble(number.getText().toString()));
                alreadyBean.setUnit(unit.getText().toString());
                alreadyBean.setTotal(Double.parseDouble(totalOne.getText().toString()));
                alreadyBean.setWorkName(workName.getText().toString());
                new AlreadyDao().saveOne(alreadyBean);
                new ToastWei().showToast(YesThingActivity.getContext(),"修改成功！");
                CustomizationDialog.super.dismiss();
            }
        });

        textChange(number, price);
        textChange(price, number);

    }


//    public void setOnOkOnclickListener(final Context context, final String id) {
//        final AlreadyBean alreadyBean = new AlreadyDao().updateOne(id);
//        saveNew.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alreadyBean.setAlreadyName(matters.getText().toString());
//                alreadyBean.setHeadName(headName.getText().toString());
//                alreadyBean.setEndDate(endDate.getText().toString());
//                alreadyBean.setPrice(Double.parseDouble(price.getText().toString()));
//                alreadyBean.setNumber(Double.parseDouble(number.getText().toString()));
//                alreadyBean.setUnit(unit.getText().toString());
//                alreadyBean.setTotal(Double.parseDouble(totalOne.getText().toString()));
//                alreadyBean.setWorkName(workName.getText().toString());
//                new AlreadyDao().saveOne(alreadyBean);
//                new ToastWei().showToast(context,"修改完成！");
//                CustomizationDialog.super.dismiss();
//            }
//        });
//    }


    public void initData(AlreadyBean alreadyBean) {
        matters.setText(alreadyBean.getAlreadyName());
        headName.setText(alreadyBean.getHeadName());
        endDate.setText(alreadyBean.getEndDate());
        price.setText(String.valueOf(alreadyBean.getPrice()));
        number.setText(String.valueOf(alreadyBean.getNumber()));
        unit.setText(alreadyBean.getUnit());
        totalOne.setText(String.valueOf(alreadyBean.getTotal()));
        workName.setText(alreadyBean.getWorkName());
    }

    private void initView() {
        matters = findViewById(R.id.matters1);
        headName = findViewById(R.id.headName1);
        endDate = findViewById(R.id.endDate1);
        price = findViewById(R.id.price1);
        number = findViewById(R.id.number1);
        unit = findViewById(R.id.unit1);
        totalOne = findViewById(R.id.total1);
        workName = findViewById(R.id.workName1);
        saveNew = findViewById(R.id.saveNew);
        cancel = findViewById(R.id.cancel);
    }


    @Override
    public void show() {
        super.show();
        Window window = super.getWindow();
        window.setGravity(Gravity.CENTER);
        super.setCanceledOnTouchOutside(true);
        super.setCancelable(true);
    }


    public interface onOkOnclickListener{
        String onOkClick();
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
                double number1,number2,number3;
                if (!price1.getText().toString().isEmpty()) {
                    number1 = Double.parseDouble(price1.getText().toString());
                }else {
                    number1 = 0.00;
                }
                DecimalFormat df = new DecimalFormat("0.00");
                if (!s.toString().isEmpty()) {
                    number2 = Double.parseDouble(s.toString());
                }else {
                    number2 = 0.00;
                }
                number3 = number1*number2;
                totalOne.setText(new StringBuilder().append(df.format(number3)));
            }
        });
    }

}



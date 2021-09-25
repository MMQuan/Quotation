package com.example.quotation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bigkoo.pickerview.TimePickerView;
import com.example.database.bean.AlreadyBean;
import com.example.database.bean.MoneyBean;
import com.example.database.dao.AlreadyDao;
import com.example.database.dao.MoneyDao;
import com.example.tool.*;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author zhc
 */
public class MoneyThingActivity extends AppCompatActivity implements ShowMoney.InnerItemOnclickListener,
        AdapterView.OnItemClickListener {

    private LinearLayout viewOne, viewOL, viewLO;

    private EditText mName;
    private Button search, resetM;

    private EditText mDate, moneyOut, moneyIn;
    private Button searchView, newM;
    private ListView MoneyShow;
    private static Context context;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.moneything);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        viewOne = findViewById(R.id.viewOne);
        viewOL = findViewById(R.id.viewOL);
        viewLO = findViewById(R.id.viewLO);

        mName = findViewById(R.id.mName);
        search = findViewById(R.id.search);
        resetM = findViewById(R.id.resetM);

        mDate = findViewById(R.id.mDate);
        moneyOut = findViewById(R.id.moneyOut);
        moneyIn = findViewById(R.id.moneyIn);
        searchView = findViewById(R.id.searchView);
        newM = findViewById(R.id.newM);

        MoneyShow = findViewById(R.id.MoneyShow);

        final DateTool d = new DateTool();
        show(MoneyShow, d.getYearNow(), d.getMonthNow());
        moneyOut.setText(new MoneyDao().findOut(this, d.getYearNow(), d.getMonthNow()));
        moneyIn.setText(new MoneyDao().findIn(this, d.getYearNow(), d.getMonthNow()));

        mDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    try {
                        show(MoneyShow, d.getYear(d.getDateGood1(s.toString())),
                                d.getMonth(d.getDateGood1(s.toString())));
                        moneyOut.setText(new MoneyDao().findOut(MoneyThingActivity.this, d.getYear(d.getDateGood1(s.toString()))
                                , d.getMonth(d.getDateGood1(s.toString()))));
                        moneyIn.setText(new MoneyDao().findIn(MoneyThingActivity.this, d.getYear(d.getDateGood1(s.toString()))
                                , d.getMonth(d.getDateGood1(s.toString()))));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                show(MoneyShow, mName.getText().toString());
                mName.clearFocus();
            }
        });

        mName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    mName.setText("");
                }
            }
        });

        newM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MoneyThingActivity.this, AddMoneyActivity.class));
                finish();
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewOne.setVisibility(View.VISIBLE);
                viewLO.setVisibility(View.GONE);
                viewOL.setVisibility(View.GONE);
                MoneyShow.setAdapter(null);
                mName.requestFocus();
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mName,InputMethodManager.SHOW_IMPLICIT);
            }
        });

        resetM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewOne.setVisibility(View.GONE);
                viewLO.setVisibility(View.VISIBLE);
                viewOL.setVisibility(View.VISIBLE);
                showReset();
            }
        });

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate();
            }
        });

        findViewById(R.id.keyII).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
    }

    public void show(ListView tt, String year, String month) {
        MoneyDao moneyDao = new MoneyDao();
        List<MoneyBean> all = moneyDao.findAll(this, year, month);
        ShowMoney showMoney = new ShowMoney(this, all);
        showMoney.setOnInnerItemOnClickListener(this);
        tt.setAdapter(showMoney);
        tt.setOnItemClickListener(this);
    }

    public void show(ListView tt, String sea) {
        MoneyDao moneyDao = new MoneyDao();
        List<MoneyBean> all = moneyDao.findAll(this, sea);
        ShowMoney showMoney = new ShowMoney(this, all);
        showMoney.setOnInnerItemOnClickListener(this);
        tt.setAdapter(showMoney);
        tt.setOnItemClickListener(this);
    }

    public void showReset() {
        final DateTool d = new DateTool();
        String s = mDate.getText().toString();
        if (s.isEmpty()) {
            show(MoneyShow, d.getYearNow(), d.getMonthNow());
            moneyOut.setText(new MoneyDao().findOut(MoneyThingActivity.this, d.getYearNow(), d.getMonthNow()));
            moneyIn.setText(new MoneyDao().findIn(MoneyThingActivity.this, d.getYearNow(), d.getMonthNow()));
        } else {
            try {
                show(MoneyShow, d.getYear(d.getDateGood1(s)),
                        d.getMonth(d.getDateGood1(s)));
                moneyOut.setText(new MoneyDao().findOut(MoneyThingActivity.this, d.getYear(d.getDateGood1(s))
                        , d.getMonth(d.getDateGood1(s))));
                moneyIn.setText(new MoneyDao().findIn(MoneyThingActivity.this, d.getYear(d.getDateGood1(s))
                        , d.getMonth(d.getDateGood1(s))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void itemClick(View v) {
        String position;
        position = (String) v.getTag();
        switch (v.getId()) {
            case R.id.lookImage:
                imageDialogSet(position);
                break;
            case R.id.mDel:
                deleteLog(position);
                break;
            default:
                break;
        }
    }


    /**
     * 修改弹窗
     */
    public void imageDialogSet(final String id) {
        final ImageDialog imageDialog = new ImageDialog(MoneyThingActivity.this, 1);
        imageDialog.show();
        imageDialog.initData(this, new MoneyDao().findImage(MoneyThingActivity.this, id));
    }

    public void deleteLog(final String position) {
        final DeleteDialog dialog = new DeleteDialog(this);
        dialog.setOnDeleteOkOnclickListener(new DeleteDialog.onDeleteOkOnclickListener() {
            @Override
            public void onDeleteOkClick() {
                new MoneyDao().deleteOne(MoneyThingActivity.this, position);
                new ToastWei().showToast(MoneyThingActivity.this, "已删除！");
                if (mName.getText().toString().isEmpty()) {
                    showReset();
                } else {
                    show(MoneyShow, mName.getText().toString());
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    /**
     * 日期处理
     */
    private void showDate() {

        TimePickerView timePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String start = null;
                try {
                    start = new DateTool().getDateOO(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mDate.setText(start);
            }
        }).setType(new boolean[]{true, true, false, false, false, false}).build();
        //注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        timePickerView.setDate(Calendar.getInstance());
        timePickerView.show();
    }

    public static Context getContext() {
        return context;
    }
}

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

import java.text.DecimalFormat;

/**
 * @author zhc
 */
public class DeleteDialog extends Dialog {

    private Button deleteOk,cancel;
    private onDeleteOkOnclickListener onDeleteOkOnclickListener;

    public DeleteDialog(@NonNull Context context) {
        super(context, R.style.Dialog_Msg);
    }

    public void setOnDeleteOkOnclickListener(DeleteDialog.onDeleteOkOnclickListener onDeleteOkOnclickListener){
        this.onDeleteOkOnclickListener = onDeleteOkOnclickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deletedialog);
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
                DeleteDialog.super.dismiss();
            }
        });

        deleteOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteOkOnclickListener.onDeleteOkClick();
            }
        });

    }


    private void initView() {
        deleteOk = findViewById(R.id.deleteOk);
        cancel = findViewById(R.id.cancelDel);
    }

    public interface onDeleteOkOnclickListener{
        void onDeleteOkClick();
    }


    @Override
    public void show() {
        super.show();
        Window window = super.getWindow();
        window.setGravity(Gravity.CENTER);
        super.setCanceledOnTouchOutside(true);
        super.setCancelable(true);
    }

}




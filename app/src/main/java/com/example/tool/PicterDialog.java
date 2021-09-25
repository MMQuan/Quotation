package com.example.tool;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.example.database.bean.AlreadyBean;
import com.example.database.dao.AlreadyDao;
import com.example.quotation.R;

import java.text.DecimalFormat;

/**
 * @author zhc
 */
public class PicterDialog extends Dialog {

    private Button cancelPPP,camera,photo;
    private onCameraOnclickListener onCameraOnclickListener;
    private onPhotoOnclickListener onPhotoOnclickListener;

    public PicterDialog(@NonNull Context context) {
        super(context, R.style.Dialog_Msg);
    }

    public void setOnCameraOnclickListener(onCameraOnclickListener onCameraOnclickListener) {
        this.onCameraOnclickListener = onCameraOnclickListener;
    }

    public void setOnPhotoOnclickListener(onPhotoOnclickListener onPhotoOnclickListener) {
        this.onPhotoOnclickListener = onPhotoOnclickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picturedialog);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
        //初始化界面控件的事件
        initEvent();
    }

    private void initEvent() {

        cancelPPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PicterDialog.super.dismiss();
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCameraOnclickListener.onCameraClick();
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPhotoOnclickListener.onPhotoClick();
            }
        });

    }


    private void initView() {
        camera = findViewById(R.id.camera);
        cancelPPP = findViewById(R.id.cancelPPP);
        photo = findViewById(R.id.photo);
    }

    public interface onCameraOnclickListener{
        void onCameraClick();
    }

    public interface onPhotoOnclickListener{
        void onPhotoClick();
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




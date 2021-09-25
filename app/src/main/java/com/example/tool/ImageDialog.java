package com.example.tool;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import com.example.database.bean.AlreadyBean;
import com.example.database.dao.AlreadyDao;
import com.example.quotation.MoneyThingActivity;
import com.example.quotation.R;

import java.text.DecimalFormat;

/**
 * @author zhc
 */
public class ImageDialog extends Dialog {

    private Button deleteOk,cancel;
    private ImageView imageU;
    private Bitmap bitmap;
    private int BS;
    private RelativeLayout daX;
    private onDeleteOkOnclickListener onDeleteOkOnclickListener;

    public ImageDialog(@NonNull Context context,int BS) {
        super(context, R.style.Dialog_Msg);
        this.BS = BS;
    }

    public ImageDialog(@NonNull Context context,Bitmap bitmap) {
        super(context, R.style.Dialog_Msg);
        this.bitmap = bitmap;
    }

    public void setOnDeleteOkOnclickListener(ImageDialog.onDeleteOkOnclickListener onDeleteOkOnclickListener){
        this.onDeleteOkOnclickListener = onDeleteOkOnclickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagedialog);
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
                ImageDialog.super.dismiss();
            }
        });

        deleteOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteOkOnclickListener.onDeleteOkClick();
            }
        });


        imageU.setImageBitmap(bitmap);

    }

    public void initData(Context context,byte[] bb) {
        Bitmap bit = new BitmapFormToByte().ByteToBitmap(bb);
        if (bit != null){
            imageU.setImageBitmap(bit);
        }else {
            imageU.setImageDrawable(context.getDrawable(R.drawable.wule));
        }
    }


    private void initView() {
        deleteOk = findViewById(R.id.imageOk);
        cancel = findViewById(R.id.imageCancel);
        imageU = findViewById(R.id.imageU);
        daX = findViewById(R.id.daX);
    }

    public interface onDeleteOkOnclickListener{
        void onDeleteOkClick();
    }

    int count = 0,sum = 0;
    long firClick,secClick;
    float posX,posY,curPosX,curPosY;


    @Override
    public void show() {
        super.show();
        if (BS == 1){
            deleteOk.setVisibility(View.GONE);
            cancel.setVisibility(View.GONE);
            imageU.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (MotionEvent.ACTION_DOWN == event.getAction()){
                        count++;
                        if (count ==1){
                            firClick = System.currentTimeMillis();
                        }else if (count==2){
                            secClick = System.currentTimeMillis();
                            if (secClick - firClick<1000){
                                sum ++;
                                if (sum%2!=0){
                                    ViewGroup.LayoutParams layoutParams = daX.getLayoutParams();
                                    layoutParams.width = 1080;
                                    daX.setLayoutParams(layoutParams);
                                }else {
                                    ViewGroup.LayoutParams layoutParams = daX.getLayoutParams();
                                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                                    daX.setLayoutParams(layoutParams);
                                }
                            }
                            count = 0;
                            firClick = 0;
                            secClick = 0;
                        }
                    }
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            posX = event.getX();
                            posY = event.getY();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            curPosX = event.getX();
                            curPosY = event.getY();
                            break;
                        case MotionEvent.ACTION_UP:
                            if ((curPosY - posY > 0) && (Math.abs(curPosY - posY) > 25
                                    || (curPosY - posY < 0) && (Math.abs(curPosY-posY) > 25))){
                                ImageDialog.super.dismiss();
                            }
                            break;
                    }
                    return true;
                }
            });
            BS = 0;
        }
        Window window = super.getWindow();
        window.setGravity(Gravity.CENTER);
        super.setCanceledOnTouchOutside(true);
        super.setCancelable(true);
    }

}




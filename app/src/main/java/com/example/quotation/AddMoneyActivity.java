package com.example.quotation;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.example.database.bean.MoneyBean;
import com.example.database.dao.MoneyDao;
import com.example.tool.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * @author MECHREVO
 */
public class AddMoneyActivity extends AppCompatActivity {

    private EditText typeMoney, moneyDate, moneyName, moneyNum, moneyFile, moneyRemark;

    private Button addMoney, gotoShowMoney;

    private static int PHOTO_CODE = 1;
    private static int CAMERA_CODE = 2;
    private String filePath;
    private FileInputStream is;

    private byte[] imageS = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.money);

        if (ContextCompat.checkSelfPermission(AddMoneyActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddMoneyActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
        }



        typeMoney = findViewById(R.id.typeMoney);
        moneyDate = findViewById(R.id.moneyDate);
        moneyName = findViewById(R.id.moneyName);
        moneyNum = findViewById(R.id.moneyNum);
        moneyFile = findViewById(R.id.moneyFile);
        moneyRemark = findViewById(R.id.moneyRemark);

        addMoney = findViewById(R.id.addMoney);
        gotoShowMoney = findViewById(R.id.gotoShowMoney);

        addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                if (typeMoney.getText().toString().isEmpty()
                        && moneyDate.getText().toString().isEmpty()
                        && moneyName.getText().toString().isEmpty()
                        && moneyNum.getText().toString().isEmpty()){
                    new ToastWei().showToast(AddMoneyActivity.this,"请至少对前四项做出填写！");
                }else {
                    MoneyBean moneyBean = new MoneyBean();
                    moneyBean.setMoneyId(UUID.randomUUID().toString());
                    moneyBean.setMoneyType(typeMoney.getText().toString());
                    moneyBean.setMoneyDate(moneyDate.getText().toString());
                    moneyBean.setMoneyName(moneyName.getText().toString());
                    moneyBean.setMoneyNum(Double.parseDouble(moneyNum.getText().toString()));
                    moneyBean.setPicture(imageS);
                    moneyBean.setRemark(moneyRemark.getText().toString());
                    MoneyDao moneyDao = new MoneyDao();
                    long l = moneyDao.saveOne(moneyBean);
                    CustomDialog customDialog = new CustomDialog(AddMoneyActivity.this);
                    customDialog.show();
                    if (l!=0){
                        customDialog.dismiss();
                        new ToastWei().showToast(AddMoneyActivity.this,"保存成功！");
                        clear();
                    }
                }
            }
        });

        moneyFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictureDlg();
            }
        });


        gotoShowMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddMoneyActivity.this, MoneyThingActivity.class));
                finish();
            }
        });

        // 类型选择监听
        typeMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                select();
            }
        });

        moneyDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                showDate();
            }
        });


        //键盘
        findViewById(R.id.keyIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });


    }

    public void imageDlg(final Bitmap uri, final int i) {
        final ImageDialog dialog = new ImageDialog(this, uri);
        dialog.setOnDeleteOkOnclickListener(new ImageDialog.onDeleteOkOnclickListener() {
            @Override
            public void onDeleteOkClick() {
                if (i == 1){
                    moneyFile.setText("已上传(相册)");
                }else {
                    moneyFile.setText("已上传(相机)");
                }
                Bitmap bitmap = new BitmapFormToByte().compressImage(uri);
                imageS = new BitmapFormToByte().BitmapToByte(bitmap);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void  pictureDlg(){
        final PicterDialog picterDialog = new PicterDialog(this);
        picterDialog.setOnCameraOnclickListener(new PicterDialog.onCameraOnclickListener() {
            @Override
            public void onCameraClick() {
                if (ContextCompat.checkSelfPermission(AddMoneyActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddMoneyActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                }
                String state = Environment.getExternalStorageState();
                File file = null;
                if (state.equals(Environment.MEDIA_MOUNTED)) {
                    String[] SdCardPermission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    if (ContextCompat.checkSelfPermission(AddMoneyActivity.this, SdCardPermission[0]) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(AddMoneyActivity.this, SdCardPermission, 100);
                    }
                    filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/福桑报价单";
                    file = new File(filePath, "test.jpg");
                    if(!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                }
                Uri mUri = FileProvider.getUriForFile(AddMoneyActivity.this, "com.example.quotation.provider", file);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
                startActivityForResult(intent, CAMERA_CODE);
                picterDialog.dismiss();
            }
        });

        picterDialog.setOnPhotoOnclickListener(new PicterDialog.onPhotoOnclickListener() {
            @Override
            public void onPhotoClick() {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");//图片
                startActivityForResult(galleryIntent, PHOTO_CODE);
                picterDialog.dismiss();
            }
        });
        picterDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1  && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            String path = getUriPath(uri);
            try {
                is = new FileInputStream(path);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                imageDlg(bitmap,1);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (requestCode == 2 && resultCode == RESULT_OK) {
            try {
                is = new FileInputStream(filePath+"/test.jpg");
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                imageDlg(bitmap,2);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            new ToastWei().showToast(this, "未选择照片/拍照！");
        }
    }

    public String getUriPath(Uri uri) {
        String res = null;
        String[] strings = {MediaStore.Images.Media.DATA};
        Cursor query = getContentResolver().query(uri, strings, null, null, null);
        if (query.moveToFirst()) {
            int columnIndexOrThrow = query.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = query.getString(columnIndexOrThrow);
        }
        query.close();
        return res;
    }

    /**
     * 清空
     */
    private void clear() {
        typeMoney.setText("");
        moneyDate.setText("");
        moneyName.setText("");
        moneyNum.setText("");
        moneyFile.setText("");
        moneyRemark.setText("");
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
                moneyDate.setText(start);
            }
        }).setType(new boolean[]{true, true, true, false, false, false}).build();
        //注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        timePickerView.setDate(Calendar.getInstance());
        timePickerView.show();
    }


    /**
     * test tiao
     */
    public void select() {
        final ArrayList names = new ArrayList();
        names.add("支出");
        names.add("收入");
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = names.get(options1).toString();
                typeMoney.setText(tx);
            }
        }).build();
        pvOptions.setPicker(names, null, null);
        pvOptions.show();
    }
}

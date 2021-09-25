package com.example.quotation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            String[] SdCardPermission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (ContextCompat.checkSelfPermission(MainActivity.this, SdCardPermission[0]) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, SdCardPermission, 100);

            }
        }

        Button insertDataYes = findViewById(R.id.insertDataYes);
        Button insertDataNo = findViewById(R.id.insertDataNo);
        Button disposeNo = findViewById(R.id.disposeNo);
        Button dataSum = findViewById(R.id.dataSum);
        Button out = findViewById(R.id.out);
        Button insertMoney = findViewById(R.id.insertMoney);
        Button lookMoney = findViewById(R.id.lookMoney);

        insertDataYes.setOnClickListener(this);
        insertDataNo.setOnClickListener(this);
        disposeNo.setOnClickListener(this);
        dataSum.setOnClickListener(this);
        out.setOnClickListener(this);
        insertMoney.setOnClickListener(this);
        lookMoney.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.insertDataYes:
                startActivity(new Intent(MainActivity.this, AddYesActivity.class));
                break;
            case R.id.insertDataNo:
                startActivity(new Intent(MainActivity.this, AddNoActivity.class));
                break;
            case R.id.disposeNo:
                startActivity(new Intent(MainActivity.this, NoThingActivity.class));
                break;
            case R.id.dataSum:
                startActivity(new Intent(MainActivity.this, YesThingActivity.class));
                break;
            case R.id.insertMoney:
                startActivity(new Intent(MainActivity.this,AddMoneyActivity.class));
                break;
            case R.id.lookMoney:
                startActivity(new Intent(MainActivity.this,MoneyThingActivity.class));
                break;
            default:
                startActivity(new Intent(MainActivity.this,OutExcelActivity.class));
                break;
        }
    }
}

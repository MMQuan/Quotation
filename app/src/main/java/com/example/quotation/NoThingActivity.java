package com.example.quotation;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.database.bean.UpcomingBean;
import com.example.database.dao.UpcomingDao;
import com.example.tool.DeleteDialog;
import com.example.tool.ShowAll;
import com.example.tool.ShowNo;
import com.example.tool.ToastWei;

import java.util.List;

public class NoThingActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, ShowNo.InnerItemOnclickListener {

    private ListView nothingShow;
    private Button noAll,yesAll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nothingdispose);
        nothingShow = findViewById(R.id.noThingShow);
        noAll = findViewById(R.id.noAll);
        yesAll = findViewById(R.id.yesAll);
        show();
        noAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = noAll.getText().toString();
                if ("查看历史待办".equals(title)){
                    noAll.setText("返回");
                    yesAll.setText("删除所有历史待办");
                    yesAll.setBackgroundResource(R.drawable.del_button_draw);
                    show2();
                }else {
                    noAll.setText("查看历史待办");
                    yesAll.setText("新加待办");
                    yesAll.setBackgroundResource(R.drawable.ok_button_blue_draw);
                    show();
                }
            }
        });

        yesAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = yesAll.getText().toString();
                if ("新加待办".equals(title)){
                    startActivity(new Intent(NoThingActivity.this,AddNoActivity.class));
                    finish();
                }else {
                    deleteLog1();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void itemClick(View v) {
        String position;
        position = (String) v.getTag();
        switch (v.getId()) {
            case R.id.update1:
                new UpcomingDao().updateOne(position);
                new ToastWei().showToast(NoThingActivity.this,"已完成！");
                show();
                break;
            case R.id.del1:
                deleteLog(position);
                break;
            default:
                break;
        }
    }


    public void show() {
        UpcomingDao upcomingDao = new UpcomingDao();
        List<UpcomingBean> all = upcomingDao.findAll();
        ShowNo showNo = new ShowNo(this, all);
        showNo.setOnInnerItemOnClickListener(this);
        nothingShow.setAdapter(showNo);
        nothingShow.setOnItemClickListener(this);
    }

    public void show2() {
        UpcomingDao upcomingDao = new UpcomingDao();
        List<UpcomingBean> all = upcomingDao.findAllY();
        ShowAll showAll = new ShowAll(this, all);
        nothingShow.setAdapter(showAll);
    }


    public void deleteLog(final String position){
        final DeleteDialog dialog = new DeleteDialog(this);
        dialog.setOnDeleteOkOnclickListener(new DeleteDialog.onDeleteOkOnclickListener() {
            @Override
            public void onDeleteOkClick() {
                new UpcomingDao().deleteOne(position);
                new ToastWei().showToast(NoThingActivity.this,"已删除！");
                show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void deleteLog1(){
        final DeleteDialog dialog = new DeleteDialog(this);
        dialog.setOnDeleteOkOnclickListener(new DeleteDialog.onDeleteOkOnclickListener() {
            @Override
            public void onDeleteOkClick() {
                new UpcomingDao().deleteAll();
                new ToastWei().showToast(NoThingActivity.this,"已删除所有！");
                show2();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}

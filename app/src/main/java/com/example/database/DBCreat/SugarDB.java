package com.example.database.DBCreat;

import android.app.Application;
import com.orm.SugarContext;

/**
 * @author zhc
 */
public class SugarDB extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }

}


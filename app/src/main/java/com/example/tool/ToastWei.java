package com.example.tool;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * @author zhc
 */
public class ToastWei {

    public void showToast(Context context, String text) {
        Toast toast = Toast.makeText(context,text,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

}

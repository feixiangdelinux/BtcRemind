package com.ccg.btcremind;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-4-17 下午4:00
 */
public class CustomerDialog {
    static void show(final Context context, String msg){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage(msg);
        builder.setPositiveButton("去查看", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(context,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        Dialog dialog=builder.create();

        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            if (attributes != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    attributes.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                } else {
                    attributes.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
                }
            }
            window.setAttributes(attributes);
        }
        dialog.show();
    }
}

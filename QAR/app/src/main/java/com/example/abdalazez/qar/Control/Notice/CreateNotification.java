package com.example.abdalazez.qar.Control.Notice;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.abdalazez.qar.Fragment.Controller.BroadcastController;
import com.example.abdalazez.qar.R;

/**
 * Created by ABD ALAZEZ on 04/06/2018.
 */

public class CreateNotification extends AppCompatActivity {

    String msg;
    String title;
    int id;
    Context context;
    String type;

    public CreateNotification(String msg, String title, int id, Context context ,String type) {
        this.msg = msg;
        this.title = title;
        this.id = id;
        this.context = context;
        this.type = type;

        if(type.equalsIgnoreCase("0")) {
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    new Intent(context, LogNotification.class), 0);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_notifications)
                    .setContentTitle(title)
                    .setTicker("Hello")
                    .setContentText(msg);

            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            mBuilder.setSound(uri);
            long[] vibrate = {500, 1000};
            mBuilder.setVibrate(vibrate);
            mBuilder.addAction(R.drawable.ic_info, "Open Message Request", pendingIntent);
            //mBuilder.addAction(R.drawable.ic_apology,"Take Medication", pendingIntent);
            mBuilder.setContentIntent(pendingIntent);
            mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
            mBuilder.setAutoCancel(true);

            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            mNotificationManager.notify("QAR", id, mBuilder.build());
        }else if(type.equalsIgnoreCase("1")){
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    new Intent(context, BroadcastController.class) , 0);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_notifications)
                    .setContentTitle(title)
                    .setTicker("Hello")
                    .setContentText(msg);

            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            mBuilder.setSound(uri);
            long[] vibrate = {500,1000};
            mBuilder.setVibrate(vibrate);
            mBuilder.addAction(R.drawable.ic_info,"Open Message Broadcast", pendingIntent);
            //mBuilder.addAction(R.drawable.ic_apology,"Take Medication", pendingIntent);
            mBuilder.setContentIntent(pendingIntent);
            mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
            mBuilder.setAutoCancel(true);

            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            mNotificationManager.notify("QAR",id, mBuilder.build());
        }
    }

    public void showMessageNotification(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle(title);
        builder1.setMessage(msg);
        builder1.setIcon(R.drawable.logo);
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}

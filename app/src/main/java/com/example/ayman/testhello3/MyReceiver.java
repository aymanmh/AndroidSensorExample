package com.example.ayman.testhello3;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    public static final String ACTION_MY_DATA = "com.example.ayman.testhello3.action.MY_DATA";
    SensorActivity mySensorActivity = null;
    public MyReceiver() {
    }

    public void setMainActivityHandler(SensorActivity main){
        mySensorActivity = main;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();

        Log.d("[BroadcastReceiver]", "MyReceiver");

        if(action.equals(Intent.ACTION_SCREEN_ON))
        {
            Log.d("[BroadcastReceiver]", "Screen ON");
            mySensorActivity.registerSensorListner();
        }
        else if(action.equals(Intent.ACTION_SCREEN_OFF)){
            Log.d("[BroadcastReceiver]", "Screen OFF");
           mySensorActivity.unRegisterSensorListner();
        }
/*
        else if(action.equals(ACTION_MY_DATA))
        {
            Log.d("MyReceiver", "received my daya");

            final Intent intent1 = new Intent(context,NotificationActivity.class);
            final PendingIntent pIntent = PendingIntent.getActivity(context,0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
            final NotificationCompat.Builder builder= new NotificationCompat.Builder(context);
            final NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
            style.bigText("very big text");
            style.setBigContentTitle("the big title");
            style.setSummaryText("symmury text");

            final NotificationCompat.Action cAction = new NotificationCompat.Action(R.drawable.ic_stat_ayman,"A-I con",pIntent);

            builder.setSmallIcon(R.drawable.ic_stat_ayman);
            builder.setContentTitle("ayman's noty title");
            builder.setContentText("more info\n info");
            builder.setAutoCancel(true);
            builder.setContentIntent(pIntent);
            builder.setStyle(style);
            builder.addAction(cAction);
            builder.setColor(context.getResources().getColor(R.color.ripple_material_dark));
            final Notification notification = builder.build();

            final NotificationManagerCompat nmc = NotificationManagerCompat.from(context);
            nmc.notify(0,notification);
        }
        */
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
    }
}

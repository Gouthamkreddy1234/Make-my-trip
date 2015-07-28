package com.androidexample.makemytrip.Notification;

/**
 * Created by Gautam on 2/11/2015.
 */
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.androidexample.makemytrip.MainActivity;
import com.androidexample.makemytrip.R;

/**
 * Created by shalini on 31-01-2015.
 */
public class ShowNotification {
    Intent intentlogin,intentlogout,intent;
    PendingIntent pIntentlogin,pIntentlogout,pIntent;
    NotificationManager manager;
    Notification notification;
    Notification.Builder nBuilder;

    public ShowNotification(){
    }
    public ShowNotification(Context context){

        Log.d("notif","notified?");


        intent = new Intent(context,MainActivity.class);
        pIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        intentlogin = new Intent(context,NotificationReceiver.class);
        intentlogin.putExtra("action","login");
        pIntentlogin = PendingIntent.getBroadcast(context,1,intentlogin,PendingIntent.FLAG_UPDATE_CURRENT);
        intentlogout = new Intent(context,NotificationReceiver.class);
        intentlogout.putExtra("action","logout");
        pIntentlogout = PendingIntent.getBroadcast(context,2,intentlogout,PendingIntent.FLAG_UPDATE_CURRENT);
        manager =(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nBuilder=new Notification.Builder(context)
                .setContentTitle("Volsbb Onetouch")
                .setContentText("Login/Logout")
                .setSmallIcon(R.drawable.ic_refresh_black_48dp)//changed
                .setPriority(Notification.PRIORITY_MAX)
                .setContentIntent(pIntent)
                .setOnlyAlertOnce(true)
                .setAutoCancel(false)/*this matters al lot*/
                .setTicker("Login/Logout")
                .addAction(R.drawable.ic_arrow_forward_black_48dp, "Login", pIntentlogin)//changed
                .addAction(R.drawable.ic_arrow_back_black_48dp, "Logout", pIntentlogout);//changed
        notification=nBuilder.build();
        notification.flags=Notification.FLAG_ONGOING_EVENT;

    }
    public void notifyInstant()
    {
        manager.notify(667,notification);
    }
    public void remove()
    {
        manager.cancel(667);
    }
    public ShowNotification setText(String s)
    {
        nBuilder.setContentText(s).setTicker(s);
        notification=nBuilder.build();
        notification.flags= Notification.FLAG_ONGOING_EVENT;
        return this;
    }
}

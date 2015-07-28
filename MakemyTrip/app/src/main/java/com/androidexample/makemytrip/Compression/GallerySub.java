package com.androidexample.makemytrip.Compression;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.androidexample.makemytrip.MyService;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Gautam on 7/24/2015.
 */
public class GallerySub extends BroadcastReceiver{

    private Intent intent1;
    @Override
    public void onReceive(Context context, Intent intent) {

        String action=intent.getStringExtra("action");
        intent1 = new Intent(context.getApplicationContext(), MyService.class);

        Log.d("action",action);
        if(action.equals("login"))
        {
            Calendar cal = Calendar.getInstance();
            //SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            //Date now=cal.getTime();//contains both date and time

            //String t = sdf.format(cal.getTime());
            String d = df.format(cal.getTime());
            // Log.e("Curr time", t);
            Log.e("Curr date", d);
            //recorded the time of button click and sending the details to the service


            // intent.putExtra("time",t);
            intent1.putExtra("date", d);
           // Toast.makeText(context,"Syncing....",Toast.LENGTH_LONG).show();
            context.startService(intent1);
            // stopService(intent);//can do this on second button click
        }
        else if(action.equals("logout")) {
           context.stopService(intent1);
            NotificationManager notificationmanager = (NotificationManager) context.getApplicationContext().getSystemService(context.NOTIFICATION_SERVICE);
            // Build Notification with Notification Manager
            notificationmanager.cancel(667);
            //what if he clicks here first?
        }

    }
}

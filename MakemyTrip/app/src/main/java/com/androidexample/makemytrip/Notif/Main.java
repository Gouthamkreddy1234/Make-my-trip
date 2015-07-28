package com.androidexample.makemytrip.Notif;

/**
 * Created by Gautam on 7/23/2015.
 */
import android.app.Notification;
import android.os.Bundle;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.androidexample.makemytrip.Compression.GallerySub;
import com.androidexample.makemytrip.HomeRecycler.MyActivity;
import com.androidexample.makemytrip.MyService;
import com.androidexample.makemytrip.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main extends Activity {

    Notification notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        Notification();

        finish();
        Intent intent = new Intent(this, MyActivity.class);
        startActivity(intent);//so this is dummy
      //  bnotify.setOnClickListener(new OnClickListener() {

           // public void onClick(View arg0) {
               // Notification();
                // TODO Auto-generated method stub





    }

    public void Notification() {
        // Set Notification Title
        String strtitle = getString(R.string.notificationtitle);
        // Set Notification Text
        String strtext = getString(R.string.notificationtext);


       Intent login = new Intent(this, GallerySub.class);
        // intent.putExtra("time",t);
        login.putExtra("action", "login");


        Intent logout = new Intent(this, GallerySub.class);
        // intent.putExtra("time",t);
        logout.putExtra("action", "logout");



        PendingIntent in = PendingIntent.getBroadcast(this, 1, login,
                PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent out = PendingIntent.getBroadcast(this, 2, logout,
                PendingIntent.FLAG_UPDATE_CURRENT);

        //Create Notification using NotificationCompat.Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                // Set Icon
                .setSmallIcon(R.drawable.ic_refresh_black_48dp)
                        // Set Ticker Message
                .setTicker(getString(R.string.notificationticker))
                        // Set Title
                .setContentTitle(getString(R.string.notificationtitle))
                        // Set Text
                .setContentText(getString(R.string.notificationtext))
                        // Add an Action Button below Notification
                .addAction(R.drawable.ic_sync, "SYNC", in)
                        // Set PendingIntent into Notification
                .addAction(R.drawable.ic_close, "STOP", out)
                .setContentIntent(in)
                        // Dismiss Notification
                .setAutoCancel(false);

        // Create Notification Manager

        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager


        notification= builder.build();
        notification.flags=Notification.FLAG_ONGOING_EVENT;

        notificationmanager.notify(667,notification);
        //Setting AutoCancel(true) for this notification makes it disappear on swipe/click.

    }

    public void CustomNotification() {
        // Using RemoteViews to bind custom layouts into Notification
        RemoteViews remoteViews = new RemoteViews(getPackageName(),
                R.layout.customnotification);

        // Set Notification Title
        String strtitle = getString(R.string.customnotificationtitle);
        // Set Notification Text
        String strtext = getString(R.string.customnotificationtext);

        // Open NotificationView Class on Notification Click
        Intent intent = new Intent(this, NotificationView.class);
        // Send data to NotificationView Class
        intent.putExtra("title", strtitle);
        intent.putExtra("text", strtext);
        // Open NotificationView.java Activity
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                // Set Icon
                .setSmallIcon(R.drawable.ic_arrow_back_black_48dp)
                        // Set Ticker Message
                .setTicker(getString(R.string.customnotificationticker))
                        // Dismiss Notification
                .setAutoCancel(true)
                        // Set PendingIntent into Notification
                .setContentIntent(pIntent)
                        // Set RemoteViews into Notification
                .setContent(remoteViews);

        // Locate and set the Image into customnotificationtext.xml ImageViews
        remoteViews.setImageViewResource(R.id.imagenotileft,R.drawable.ic_arrow_forward_black_48dp);
        remoteViews.setImageViewResource(R.id.imagenotiright,R.drawable.ic_arrow_back_black_48dp);

        Intent buttonsIntent = new Intent(this, NotifyActivityHandler.class);
        buttonsIntent.putExtra("do_action", "play");
        remoteViews.setOnClickPendingIntent(R.id.imagenotileft, PendingIntent.getActivity(this, 0, buttonsIntent, 0));

        // Locate and set the Text into customnotificationtext.xml TextViews
        remoteViews.setTextViewText(R.id.title,getString(R.string.customnotificationtitle));
        remoteViews.setTextViewText(R.id.text,getString(R.string.customnotificationtext));

        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify(667, builder.build());

    }

   /* public void dis(View view)
    {
        Log.d("dude", "dude");
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
package com.androidexample.makemytrip.Notif;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Gautam on 7/23/2015.
 */
public class NotifyActivityHandler extends Activity {
    public static final String PERFORM_NOTIFICATION_BUTTON = "perform_notification_button";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String action = (String) getIntent().getExtras().get("do_action");
        if (action != null) {
            if (action.equals("play")) {
                // for example play a music
                //start uploadng the images to server in your app
                Log.d("dude", "dude");
                //and instead of an activity use a service
            } else if (action.equals("close")) {
                //close current notification
            }
        }

        finish();
    }
}
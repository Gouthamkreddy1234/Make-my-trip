package com.androidexample.makemytrip;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.androidexample.makemytrip.Compression.Recieve;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyService extends Service {

    Date tim,dat;
    int i;
    Thread t;
    private Boolean flag = true;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("created","created");
        //control passed here only once
        //creation happens only the first time
        //on start is called every time you start the service


    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
     Log.e("started","started");
        Toast.makeText(this, "Syncing....", Toast.LENGTH_LONG).show();
        //control always here
        ++i;//problem 1 solved


       final String date= intent.getStringExtra("date");


        try {

              dat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
                .parse(date);
            //now these tow are comparable
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final File yourDir = new File("/storage/sdcard1/DCIM/Camera");//works fine
        //File yourDir = new File("/storage/emulated/0/DCIM/Camera");
        if(i==1) {
           t =  new Thread(new Runnable() {
                @Override
                public void run() {
                    //------------------------

                    while (flag == true) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                           e.printStackTrace();
                        }

                        Log.e("called", "called");

                        for (File f : yourDir.listFiles()) {
                            if (f.isFile()) {
                                //f has to be locally locally located somewhere,temp var ,replaced everytime
                                String name = f.getName();
                                Date d = new Date(f.lastModified());//already in that format
                                //  String t = new SimpleDateFormat("HH:mm:ss").format(date);
                                String da = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);

                                Date parsed = null;
                                try {
                                    parsed = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(da);

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                                if (dat.compareTo(parsed) < 0) {
                                    // Log.i("File time", t);
                                    dat = parsed;
                                    Log.i("File date", da);
                                    Log.d("name", name);
                                    //the file is here pass it to compress class to compress it

                                    Intent intent1 = new Intent(getBaseContext(),Recieve.class);
                                    intent1.putExtra("name",name);
                                    sendBroadcast(intent1);
                                }
                            }
                        }
                    }


                    //-------------------------

                }
            });
            t.start();
        }


        //return super.onStartCommand(intent, flags, startId);
        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("stopped", "stopped");
        Toast.makeText(this, "Stopped!", Toast.LENGTH_LONG).show();
        flag = false;
        t.interrupt();



    }
}


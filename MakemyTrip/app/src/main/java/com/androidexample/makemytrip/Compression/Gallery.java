package com.androidexample.makemytrip.Compression;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidexample.makemytrip.MyService;
import com.androidexample.makemytrip.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Gallery extends Activity implements View.OnClickListener {

    private Button getimgs,stop;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        getimgs = (Button) findViewById(R.id.getimgs);
        stop = (Button) findViewById(R.id.button2);
        getimgs.setOnClickListener(this);
        stop.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gallery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.getimgs:
            Calendar cal = Calendar.getInstance();
            //SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            //Date now=cal.getTime();//contains both date and time

            //String t = sdf.format(cal.getTime());
            String d = df.format(cal.getTime());
            // Log.e("Curr time", t);
            Log.e("Curr date", d);
            //recorded the time of button click and sending the details to the service

             intent = new Intent(this, MyService.class);
            // intent.putExtra("time",t);
            intent.putExtra("date", d);
                Toast.makeText(this,"Syncing....",Toast.LENGTH_LONG).show();
            startService(intent);
            // stopService(intent);//can do this on second button click


      /*  File sdCardRoot = Environment.getExternalStorageDirectory();
       // File internalroot = Environment.getDataDirectory();//app local dir
        //getFilesDir() vs getdir()

       // File yourDir = new File(sdCardRoot,"DCIM/Camera");//GETTING INTERNAL STORAGE
        //therefore chck broth locations
       File yourDir = new File("/storage/sdcard1/DCIM/Camera");//works fine
        //File yourDir = new File("/storage/emulated/0/DCIM/Camera");
        for (File f : yourDir.listFiles()) {
            if (f.isFile()) {
                //f has to be locally locally located somewhere,temp var ,replaced everytime
               String name =  f.getName();
                Date date = new Date(f.lastModified());
                String time = new SimpleDateFormat("HH:mm:ss").format(date);
                String dat = new SimpleDateFormat("yyyy-MM-dd").format(date);


              //if(now<)
                Log.i("File time", time);
                Log.i("File date", dat);
                Log.d("name", name);
                // make something with the name

            }*/
                break;

            case R.id.button2:

                stopService(intent);
                //what if he clicks here first?
                //MainFragment.callFacebookLogout(this);

                break;

        }
    }
    }


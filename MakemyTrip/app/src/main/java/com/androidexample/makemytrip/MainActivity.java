package com.androidexample.makemytrip;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidexample.makemytrip.HomeRecycler.MyActivity;
import com.androidexample.makemytrip.Notif.Main;
import com.androidexample.makemytrip.TextToSpeech.MainSpeech;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends Activity implements View.OnClickListener {

    Button btnShowLocation,start,stop;
    TextView textView;
    static double latitude;
    static double longitude;

    // GPSTracker class
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, MyActivity.class);
        startActivity(intent);


        //-----------------------------------
        btnShowLocation = (Button) findViewById(R.id.btnShowLocation);
        textView = (TextView) findViewById(R.id.textView);
        start = (Button) findViewById(R.id.startservice);
        stop = (Button) findViewById(R.id.stopservice);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);

        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // create class object
                gps = new GPSTracker(MainActivity.this);

                // check if GPS enabled
                if(gps.canGetLocation()){

                     latitude = gps.getLatitude();
                     longitude = gps.getLongitude();

                    //get the address using the Geocode class
                    Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(latitude,
                                longitude, 1);

                        if (addresses != null) {
                            Address returnedAddress = addresses.get(0);
                            StringBuilder strReturnedAddress = new StringBuilder(
                                    "Address:\n");
                            for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                                strReturnedAddress
                                        .append(returnedAddress.getAddressLine(i)).append(
                                        "\n");
                            }
                            textView.setText(strReturnedAddress.toString());
                        } else {
                            textView.setText("No Address returned!");
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        textView.setText("Canont get Address!");
                    }

                    // \n is for new line
                    textView.append(latitude + "\\n" + longitude);
                    //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }

            }
        });



        //------------------------------------


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        switch(v.getId())
        {
            case R.id.startservice:

                gps = new GPSTracker(MainActivity.this);

                break;


            case R.id.stopservice:

                gps.stopUsingGPS();

                break;





        }
    }


}

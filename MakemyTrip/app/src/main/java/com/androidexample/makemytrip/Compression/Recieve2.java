package com.androidexample.makemytrip.Compression;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Environment;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.androidexample.makemytrip.Controller.AppController;
import com.androidexample.makemytrip.GPSTracker;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Gautam on 7/24/2015.
 */
public class Recieve2 extends BroadcastReceiver {
    int serverResponseCode = 0;
    File sourceFile;
    String max = "";
    Context c;
    android.os.Handler mhandler;
    static double latitude;
    static double longitude;
    String lat,lon,fileName;
    String name2;
    private String url ="http://broado-server.herokuapp.com/api?img=URL&latitude=num1&longitude=num2";//perfect
    String imgprefix = "http://vitown.esy.es/gcm/upload/uploads/XYZ";
    private String TAG ="Eventparsing";

    // GPSTracker class
    GPSTracker gps;

    @Override
    public void onReceive(final Context context, Intent intent) {


        mhandler = new android.os.Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                // This is where you do your work in the UI thread.
                // Your worker tells you in the message what to do.
                Toast.makeText(context,(String)message.obj,Toast.LENGTH_LONG).show();
                //here make a get request to the database,so basically even this will be running in the background
                //automatic upload from outside the app while using the app
                Log.d("finalname",sourceFile.getName());
                imgprefix = imgprefix.replace("XYZ",sourceFile.getName());
                url= url.replace("URL",imgprefix);
                url = url.replace("num1",lat);
                url = url.replace("num2",lon);
                Log.d("URL",url);

                uploadagain(url);
            }


        };



        //--------------------------------------------------
        String address =  getloc(context);
        Log.d("Address",address);



        c=context;
        Toast.makeText(context,"new broadcast on !",Toast.LENGTH_LONG).show();

        File sdCardRoot = Environment.getExternalStorageDirectory();
        File yourDir = new File(sdCardRoot, "MyFolder/Images");


        //----------------------------------------

        for (File f : yourDir.listFiles()) {
            if (f.isFile()) {
                //f has to be locally locally located somewhere,temp var ,replaced everytime
                String name = f.getName();
                Date date = new Date(f.lastModified());
                String dat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

                if (dat.compareTo(max) > 0) {
                    max = dat;
                    try {
                        Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
                                .parse(max);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //start


                }

            }
        }
        File file = null;
        for (File f1 : yourDir.listFiles()) {
            if (f1.isFile()) {
                //f has to be locally locally located somewhere,temp var ,replaced everytime
                 name2 = f1.getName();
                Date date = new Date(f1.lastModified());
                String dat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

                if (dat.equals(max)) {
                     Log.d("max",f1.getName());
                    file = f1;
                    break;


                }


            }
        }


        //--------------------------------------------


        sourceFile = file; /*The actual file bro progress man */

        new Thread(new Runnable() {
            public void run() {

                //--------------gps----------------------------------

                //Burden relieved on the Main thread.This is a new thread with one function only

                //---------------------------------------------------
                int response = uploadFile(sourceFile.getName());
                System.out.println("RES : " + response);

            }
        }).start();


    }

    private void uploadagain(String uri) {

        Log.d("called man","called");


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                uri, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                //JSONArray events = response.getJSONArray("event");
                //jsonArray=events;


                if(response!=null) {
                    //progressBar.setVisibility(View.INVISIBLE);
                    //jsonArray= events;
                  JSONObject  jsonObject =response;
                    Log.d("res",response.toString());
                     /*new SponsorMain(jsonArray);//anonymous object*/
                   // displayData(jsonObject);
                    //getDescription(events);
                    // getjsonData();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                //progressBar.setVisibility(View.VISIBLE);
               // Toast.makeText(, "Please Check your internet connection", Toast.LENGTH_LONG).show();
                //progressBar.setVisibility(View.INVISIBLE);

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq, "events");





    }

    private String getloc(Context context) {

        StringBuilder strReturnedAddress = new StringBuilder();

        gps = new GPSTracker(context);

        // check if GPS enabled
        if(gps.canGetLocation()){

            //while(latitude!=null)
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Log.d("latitude",Double.toString(latitude));
            Log.d("longitude",Double.toString(longitude));

            lat =  Double.toString(latitude);
            lon = Double.toString(longitude);

            strReturnedAddress.append("latitude:"+lat+"\n"+"longitude:"+lon);

            //get the address using the Geocode class
          // strReturnedAddress.append("latitude:"+Double.toString(latitude)+"\n"+"longitude:"+Double.toString(longitude));
            // \n is for new line
            //textView.append(latitude + "\\n" + longitude);
            //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
            //Ask user to enable the GPS
        }


        return strReturnedAddress.toString();


    }

    void workerThread() {
        // And this is how you call it from the worker thread:
        // ....
        String message = "Successfully Uploaded!!";
        Message msg = Message.obtain(); // Creates an new Message instance
        msg.obj = message; // Put the string into Message, into "obj" field.
        msg.setTarget(mhandler); // Set the Handler
        msg.sendToTarget(); //Send the message
//....
    }



    public int uploadFile(String sourceFileUri) {
        String upLoadServerUri = "http://vitown.esy.es/gcm/upload/upload_media_test.php";
         fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        //------------------------------------------------


        //------------------------------------
        if (!sourceFile.isFile()) {
            Log.e("uploadFile", "Source File Does not exist");
            return 0;


        }
        try { //open a URL connection to the Servlet
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            URL url = new URL(upLoadServerUri);
            conn = (HttpURLConnection) url.openConnection(); // Open a HTTP  connection to  the URL
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("uploaded_file", fileName);
            dos = new DataOutputStream(conn.getOutputStream());
            //add a post param location
            //add a post param - his name

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + fileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            bytesAvailable = fileInputStream.available(); // create a buffer of  maximum size

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();

            Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
            if (serverResponseCode == 200) {
                       workerThread();
                //called on successful upload
                //       Toast.makeText(c.getApplicationContext(), "File Upload Complete.", Toast.LENGTH_SHORT).show();
                //just check this out and finish this

            }

            //close the streams //
            fileInputStream.close();
            dos.flush();
            dos.close();

        } catch (MalformedURLException ex) {

            ex.printStackTrace();
            //  Toast.makeText(c.getApplicationContext(), "MalformedURLException", Toast.LENGTH_SHORT).show();
            Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
        } catch (Exception e) {

            e.printStackTrace();
            // Toast.makeText(c.getApplicationContext(), "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e(" Exception", "Exception : ");
        }

        return serverResponseCode;
    }

}

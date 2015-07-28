package com.androidexample.makemytrip.Compression;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidexample.makemytrip.Notification.ShowNotification;
import com.androidexample.makemytrip.R;

public class Upload extends Activity {

    TextView tv;
    Button b;
    int serverResponseCode = 0;
    ProgressDialog dialog = null;
    File sourceFile;
    String max = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

      /*  final ShowNotification s=new ShowNotification(this);
        s.notifyInstant();*/

        //s.remove();
        //------------------------------------

     /*  Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                     if (CHECK==false) {
                        Log.d("status", "NOT NEEDED!!");
                        s.remove();
                        //tcheck=true;
                    }
                }
            }
        });

        t1.start();*/

        //------------------------------------

        b = (Button) findViewById(R.id.but);
        tv = (TextView) findViewById(R.id.tv);

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
                    String name = f1.getName();
                    Date date = new Date(f1.lastModified());
                    String dat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

                    if (dat.equals(max)) {

                        file = f1;
                        break;


                    }


                }
            }


            //--------------------------------------------


            sourceFile = file; /* */
            tv.setText(sourceFile.getName());

            b.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog = ProgressDialog.show(Upload.this, "", "Uploading file...", true);
                    new Thread(new Runnable() {
                        public void run() {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    tv.setText("uploading started.....");
                                }
                            });
                            int response = uploadFile(sourceFile.getName());
                            System.out.println("RES : " + response);
                        }
                    }).start();
                }
            });


        }

    public int uploadFile(String sourceFileUri) {
        String upLoadServerUri = "http://vitown.esy.es/gcm/upload/upload_media_test.php";
        String fileName = sourceFileUri;

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
                runOnUiThread(new Runnable() {
                    public void run() {
                        tv.setText("File Upload Completed.");
                        Toast.makeText(Upload.this, "File Upload Complete.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            //close the streams //
            fileInputStream.close();
            dos.flush();
            dos.close();

        } catch (MalformedURLException ex) {
            dialog.dismiss();
            ex.printStackTrace();
            Toast.makeText(Upload.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
            Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
        } catch (Exception e) {
            dialog.dismiss();
            e.printStackTrace();
            Toast.makeText(Upload.this, "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e(" Exception", "Exception : ");
        }
        dialog.dismiss();
        return serverResponseCode;
    }
}

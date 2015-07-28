package com.androidexample.makemytrip.Remote;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import com.androidexample.makemytrip.R;

/**
 * Created by Gautam on 7/23/2015.
 */
public class Hi extends Activity {
    Switch aSwitch;
    ImageView one,two,three,four,five;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hi);

        ImageView one = (ImageView)findViewById(R.id.one);
        ImageView two = (ImageView)findViewById(R.id.two);
        ImageView three = (ImageView)findViewById(R.id.three);
        ImageView four = (ImageView)findViewById(R.id.four);
        ImageView five = (ImageView)findViewById(R.id.five);
        //imageView.sele
     /*   aSwitch=(Switch)findViewById(R.id.switch1);
        aSwitch.isChecked();*/

    }
    public void print(View view) {

        Log.d("hi", "hi");


    }

    public void display(View view) {

        Log.d("display", "display");
        Switch s=(Switch)view;
        if(s.isChecked())
        {
            Log.d("yes","yes");
        }
        else
        {
            Log.d("no","no");
        }


    }

}

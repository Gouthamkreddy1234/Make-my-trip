package com.androidexample.makemytrip.TwoClasses;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidexample.makemytrip.R;

public class DummyActivity extends ActionBarActivity implements View.OnClickListener {

    LinearLayout tv1,tv2;
    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);
        name =  getIntent().getStringExtra("cityname");

        tv1=(LinearLayout)findViewById(R.id.oneday);
        tv2=(LinearLayout)findViewById(R.id.fullweek);

        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);


        AnimatorSet animatorSet=new AnimatorSet();
        AnimatorSet animatorSet2=new AnimatorSet();

        ObjectAnimator translateY=ObjectAnimator.ofFloat(tv1,"translationY",-200,0,100,0,-50,0);
        ObjectAnimator translateY2=ObjectAnimator.ofFloat(tv2,"translationY",200,0,-100,0,50,0);



        translateY.setInterpolator(new AnticipateInterpolator());
        translateY2.setInterpolator(new AnticipateInterpolator());

        translateY.setDuration(3000);
        translateY2.setDuration(3000);


        animatorSet.playTogether(translateY);
        animatorSet.playTogether(translateY2);
        animatorSet.start();
        animatorSet2.start();




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dummy, menu);
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

        Intent intent = new Intent();

        switch(v.getId())
        {

            case R.id.oneday:
                intent.setClass(this,Trip.class);
                intent.putExtra("cityname",name);
                startActivity(intent);


                break;

            case R.id.fullweek:

                intent.setClass(this,Trip2.class);
                intent.putExtra("cityname",name);
                startActivity(intent);


                break;


        }
    }
}

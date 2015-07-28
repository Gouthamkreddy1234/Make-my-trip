package com.androidexample.makemytrip.HomeRecycler;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.androidexample.makemytrip.Compression.GallerySub;
import com.androidexample.makemytrip.Notif.NotificationView;
import com.androidexample.makemytrip.Notif.NotifyActivityHandler;
import com.androidexample.makemytrip.R;
import com.androidexample.makemytrip.TextToSpeech.MainSpeech;
import com.androidexample.makemytrip.TwoClasses.Budget;
import com.androidexample.makemytrip.TwoClasses.DummyActivity;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

/**
 * Created by Gautam on 7/9/2015.
 */
public class MyActivity extends ActionBarActivity  implements MyAdapter.ClickListener {
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Toolbar toolbar;
    Button trip,budget;
    Notification notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid);
       /* trip=(Button)findViewById(R.id.trip);
        budget=(Button)findViewById(R.id.budget);*/
       /* trip.setOnClickListener(this);
        budget.setOnClickListener(this);*/
        Notification();
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToRecyclerView(mRecyclerView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainSpeech.class);
                startActivity(intent);
            }
        });

       /* toolbar=(Toolbar)findViewById(R.id.app_bars);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.violet)));*/

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a grid staggered layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final ArrayList<String[]> myDataset = new ArrayList<>();
        String name[],url[];
        name = new String[]{ "BANGALORE", "CHENNAI", "MUMBAI"};
        url = new String[]{"http://cdn2.justbooksclc.com/cities/Bangalore.jpg","http://www.thehindu.com/multimedia/dynamic/01528/LAT_AERIAL_VIEW_OF_1528512g.jpg","http://s1.it.atcdn.net/wp-content/uploads/2013/10/Sunset-over-Chinese-Fishing-nets-and-boat-in-Cochin-Kochi-Kerala-India-shutterstock_104171129.jpg"};
        // specify an adapter (see also next example)
        myDataset.add(name);
        myDataset.add(url);
        mAdapter = new MyAdapter(myDataset,this);
        mAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

      /*  fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mAdapter.add(2,"new");
                mAdapter.notifyItemInserted(2);
                mAdapter.notifyDataSetChanged();


            }
        });*/
       /* mRecyclerView.setItemAnimator(new RecyclerView.ItemAnimator() {
            @Override
            public void runPendingAnimations() {




            }

            @Override
            public boolean animateRemove(RecyclerView.ViewHolder holder) {
                return false;
            }

            @Override
            public boolean animateAdd(RecyclerView.ViewHolder viewToAnimate) {

                Log.d("Add","ADD");
                /*if (position > lastPosition)//reuse this code!!
                {*/
                    // Animation animation = AnimationUtils.loadAnimation(context, R.anim.abc_slide_in_bottom);
                /*    AnimatorSet animatorSet = new AnimatorSet();
                    ObjectAnimator animationY = ObjectAnimator.ofFloat(viewToAnimate, "translationY", 300, 0);
                    animationY.setInterpolator(new DecelerateInterpolator());
                    ObjectAnimator animationX = ObjectAnimator.ofFloat(viewToAnimate, "translationX", -600, 0);
                    ObjectAnimator scaleX = ObjectAnimator.ofFloat(viewToAnimate, "scaleX", 0F, 1F);
                    ObjectAnimator scaleY = ObjectAnimator.ofFloat(viewToAnimate, "scaleY", 0F, 1F);
                    animationY.setDuration(600);
                    animationX.setDuration(400);
                    scaleX.setDuration(500);
                    scaleY.setDuration(500);
                    animatorSet.playTogether(animationX, animationY, scaleX, scaleY);
                    animatorSet.start();
                    //viewToAnimate.startAnimation(animation);

                //}
               // lastPosition = position;
                return true;
            }

            @Override
            public boolean animateMove(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
                return false;
            }

            @Override
            public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, int fromLeft, int fromTop, int toLeft, int toTop) {
                return false;
            }

            @Override
            public void endAnimation(RecyclerView.ViewHolder item) {

            }

            @Override
            public void endAnimations() {

            }

            @Override
            public boolean isRunning() {
                return false;
            }
        });
        */

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
        notification.flags= Notification.FLAG_ONGOING_EVENT;

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



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View v, int position) {


        Intent intent = new Intent(this,DummyActivity.class);
       TextView tv = (TextView)v.findViewById(R.id.name);
       String t= tv.getText().toString();
        intent.putExtra("cityname",t);
        startActivity(intent);
    }


}
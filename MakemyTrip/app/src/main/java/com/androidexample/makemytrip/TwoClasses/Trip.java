package com.androidexample.makemytrip.TwoClasses;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.androidexample.makemytrip.Controller.AppController;
import com.androidexample.makemytrip.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Trip extends ActionBarActivity implements TripAdapter.ClickListener {

    private String url ="http://broado-server.herokuapp.com/travelApi?location=";//perfect
    private String TAG ="Eventparsing";
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private TripAdapter adapter;//static-->all objects
    private String[] name;
    private String[] end;
    private String[] start;
    private String[] distance;
    private String[] duration;
    private String[] imageurl;
    private SwipeRefreshLayout swipeRefreshLayout;


    RecyclerView recList;
    String name1;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        name1 = getIntent().getStringExtra("cityname");
        url=url+name1;

        recList = (RecyclerView) findViewById(R.id.cardList);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        getData();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
    }

    public void getData()
    {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                   // JSONArray events = response.getJSONArray("event");
                    //jsonArray=events;


                    if(response!=null) {
                        progressBar.setVisibility(View.INVISIBLE);
                        //jsonArray= events;
                        jsonObject =response;
                         /*new SponsorMain(jsonArray);//anonymous object*/
                        /*displayData(events);
                        getDescription(events);*/
                        getjsonData(jsonObject);
                    }


                } catch (JSONException e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                progressBar.setVisibility(View.VISIBLE);
                //Toast.makeText(, "Please Check your internet connection", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq, "nearby");



    }

    private void getjsonData(JSONObject jsonobj) throws JSONException {

        JSONArray jsonArray1 = null;
        try {
            jsonArray1 = jsonobj.getJSONArray("nearby");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        distance=new String[jsonArray1.length()];
        duration=new String[jsonArray1.length()];
        end=new String[jsonArray1.length()];
        imageurl=new String[jsonArray1.length()];
        start=new String[jsonArray1.length()];


        for(int i =0;i<jsonArray1.length();i++)
        {
            JSONObject temp =null;
            try {
                 temp = jsonArray1.getJSONObject(i);

            distance[i]=temp.getString("distance");
            duration[i]=temp.getString("duration");
            end[i]=temp.getString("end");
            start[i]=temp.getString("start");
            imageurl[i]=temp.getString("img");


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        List<TripInfo> data = new ArrayList<>();

        for (int j = 0; j < distance.length; j++) {//two as of now
            TripInfo current = new TripInfo();
            current.distance = distance[j];
            current.duration = duration[j];
            current.end = end[j];
            current.start = start[j];
            current.imageurl = imageurl[j];
            current.name = name1;

            Log.d("date12345",duration[j]);
            Log.d("description12345",distance[j]);
            Log.d("name12345",end[j]);
            Log.d("imageurl12345",imageurl[j]);


            data.add(current);
        }




        adapter = new TripAdapter(this,data);
        adapter.setClickListener(this);
        recList.setAdapter(adapter);
        recList.setHasFixedSize(true);

        adapter.notifyDataSetChanged();//notify data set changed changed done X1000
        // Stop refresh animation
        //called from inside of the onresponse() method



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trip, menu);
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
    public void onItemClick(View v, int position) {

    }
}

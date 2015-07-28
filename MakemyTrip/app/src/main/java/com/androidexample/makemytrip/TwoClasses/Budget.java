package com.androidexample.makemytrip.TwoClasses;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;

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

public class Budget extends ActionBarActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, BudgetAdapter.ClickListener {

    LinearLayoutManager mLayoutManager;
    private RadioButton r1, r2, r3;
    Button b1;
    EditText et;
    private String url ="http://broado-server.herokuapp.com/budgetApi?city=X&living=Y";//perfect
    private String TAG ="Eventparsing";
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private BudgetAdapter adapter;//static-->all objects
    private String[] name;
    private String[] rating;
    private String[] facilities;
    private String[] review;
    private SwipeRefreshLayout swipeRefreshLayout;

String cityname;
    RecyclerView recList;
    String name1;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
      //RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler);

     cityname =   getIntent().getStringExtra("cityname");
        recList = (RecyclerView) findViewById(R.id.my_recycler);


        recList.setHasFixedSize(true);

        // use a grid staggered layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recList.setLayoutManager(mLayoutManager);
        //c

        r1 = (RadioButton) findViewById(R.id.radioButton);
        r2 = (RadioButton) findViewById(R.id.radioButton2);
        r3 = (RadioButton) findViewById(R.id.radioButton3);

        r2.setSelected(true);
        
        //b1=(Button)findViewById(R.id.butt);
        b1=(Button)findViewById(R.id.butt);
        et=(EditText)findViewById(R.id.edit);
        et.setText(cityname);
        
        b1.setOnClickListener(this);
        
        

        r1.setOnCheckedChangeListener(this);
        r2.setOnCheckedChangeListener(this);
        r3.setOnCheckedChangeListener(this);

        recList = (RecyclerView) findViewById(R.id.my_recycler);
        progressBar=(ProgressBar)findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.INVISIBLE);


        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_budget, menu);
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
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

       
            switch(buttonView.getId()) {
                case R.id.radioButton:
                    if (isChecked) {
                        r2.setChecked(false);
                        r3.setChecked(false);
                        // t4.setChecked(false);
                    }
                    break;

                case R.id.radioButton2:
                    if (isChecked) {
                        r1.setChecked(false);
                        r3.setChecked(false);
                        //t1.setChecked(false);
                    }
                    break;

                case R.id.radioButton3:
                    if (isChecked) {
                        r2.setChecked(false);
                        r1.setChecked(false);
                        //t4.setChecked(false);
                    }
                    break;

            }
        
        
      
        

    }

    @Override
    public void onClick(View v) {

        progressBar.setVisibility(View.VISIBLE);

        String city= et.getText().toString();
        String temp = null;

        if(r1.isChecked() && !r2.isChecked() && !r3.isChecked())
        {
            Log.d("h","h");
            url = "http://broado-server.herokuapp.com/budgetApi?city=X&living=high";
        }

       else if(r2.isChecked() && !r1.isChecked() && !r3.isChecked())
        {
            Log.d("n","n");
           url = "http://broado-server.herokuapp.com/budgetApi?city=X&living=normal";
        }
        else if(r3.isChecked() && !r2.isChecked() && !r1.isChecked())
        {
            Log.d("l","l");
            url = "http://broado-server.herokuapp.com/budgetApi?city=X&living=low";
        }
        url = url.replace("X",city);


        Log.d("url",url);

        


            getData(url);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recList.setLayoutManager(llm);

        
    }

    public void getData(String url)
    {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
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

    private void getjsonData(JSONObject jsonObject) {

        JSONArray jsonArray1 = null;
        try {
            jsonArray1 = jsonObject.getJSONArray("see");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        name=new String[jsonArray1.length()];
        rating=new String[jsonArray1.length()];
        facilities=new String[jsonArray1.length()];
        review=new String[jsonArray1.length()];



        for(int i =0;i<jsonArray1.length();i++)
        {
            JSONObject temp =null;
            try {
                temp = jsonArray1.getJSONObject(i);

                name[i]=temp.getString("name");
                rating[i]=temp.getString("rating");
                facilities[i]=temp.getString("facilities");
                review[i]=temp.getString("review");


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        List<BudgetInfo> data = new ArrayList<>();

        for (int j = 0; j < name.length; j++) {//two as of now
            BudgetInfo current = new BudgetInfo();
            current.name = name[j];
            current.rating = rating[j];
            current.facilities = facilities[j];
            current.review = review[j];


            Log.d("date12345",name[j]);
            Log.d("description12345",rating[j]);


            data.add(current);
        }




        adapter = new BudgetAdapter(this,data);
        adapter.setClickListener(this);
        recList.setAdapter(adapter);
        recList.setHasFixedSize(true);

        adapter.notifyDataSetChanged();//notify data set changed changed done X1000
        // Stop refresh animation
        //called from inside of the onresponse() method



    }

    @Override
    public void onItemClick(View v, int position) {

    }
}

package com.androidexample.makemytrip.TwoClasses;

/**
 * Created by Gautam on 7/26/2015.
 */
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.androidexample.makemytrip.Controller.AppController;
import com.androidexample.makemytrip.R;


import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;

/**
 * Created by Gautam on 3/13/2015.
 */
//contains an I and C->imp
public class TripAdapter extends  RecyclerView.Adapter<TripAdapter.TripViewHolder>
{
    private ClickListener clickListener;
    private List<TripInfo> info= Collections.emptyList();
    private LayoutInflater inflater;
    private ImageLoader imageloader;
    private Context context;
    private int lastPosition=-1;


    public TripAdapter(Context c, List<TripInfo> info)
    {
        context=c;
        inflater = LayoutInflater.from(context);
        this.info=info;
        imageloader= AppController.getInstance().getImageLoader();
    }



    @Override
    public int getItemCount() {

        return info.size();
    }

    @Override
    public void onBindViewHolder(TripViewHolder sponsorViewHolder, int i) {

        TripInfo ci = info.get(i);

        sponsorViewHolder.name.setText(ci.name);
        sponsorViewHolder.duration.setText(ci.duration);
        sponsorViewHolder.end.setText(ci.end);
        sponsorViewHolder.start.setText(ci.start);
        sponsorViewHolder.distance.setText(ci.distance);
        try {
            imageloader.get(ci.imageurl, ImageLoader.getImageListener(sponsorViewHolder.img, R.drawable.ic_loader, R.drawable.round_2));
            //sponsorViewHolder.description.setText(ci.description);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        setAnimation(sponsorViewHolder.cw,i);

    }


    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {

            AnimatorSet animatorSet=new AnimatorSet();
            //ObjectAnimator animationY=ObjectAnimator.ofFloat(viewToAnimate,"translationY",300,0);
            //animationY.setInterpolator(new DecelerateInterpolator());
            //ObjectAnimator animationX=ObjectAnimator.ofFloat(viewToAnimate,"translationX",-600,0);
            ObjectAnimator translateX=ObjectAnimator.ofFloat(viewToAnimate,"translationX",-200,0);
            ObjectAnimator translateY=ObjectAnimator.ofFloat(viewToAnimate,"translationY",600,0);
            ObjectAnimator rotate=ObjectAnimator.ofFloat(viewToAnimate,"rotation",50,0);
            translateX.setInterpolator(new AccelerateInterpolator());
            translateY.setInterpolator(new DecelerateInterpolator());
            rotate.setInterpolator(new DecelerateInterpolator());
            translateX.setDuration(300);
            translateY.setDuration(400);

            rotate.setDuration(400);
            animatorSet.playTogether(translateX,translateY,rotate);
            animatorSet.start();

        }
        lastPosition = position;
    }
    //creation happens for a row
    @Override
    public TripViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.trip_card, viewGroup, false);
        TripViewHolder myholder= new TripViewHolder(view);
        return myholder;
    }
    //Using inner C from a function of the base class



    public void setClickListener(ClickListener c){
        clickListener=c;
    }



    public  class TripViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected TextView name;
        protected TextView duration;
        protected TextView distance;
        protected TextView end;
        protected TextView start;
        protected ImageView img;
        private CardView cw;



        public TripViewHolder(View v) {
            super(v);
            name= (TextView)v.findViewById(R.id.name);
            duration= (TextView)v.findViewById(R.id.duration);
            distance =(TextView)v.findViewById(R.id.distance);
            end= (TextView)v.findViewById(R.id.end);
            start=(TextView)v.findViewById(R.id.start);
            cw=(CardView)v.findViewById(R.id.card_view);

            img=(ImageView)v.findViewById(R.id.img);

        }


        @Override
        public void onClick(View v) {
            if(clickListener!=null)
            {
                clickListener.onItemClick(v,getPosition());

            }
        }
    }



    public interface ClickListener{
        public void onItemClick(View v, int position);
    }



}
//global variables
//control passed to bindview
//either ill do it or ask another function to do it


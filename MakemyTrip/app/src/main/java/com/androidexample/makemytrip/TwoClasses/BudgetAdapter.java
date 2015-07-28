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
public class BudgetAdapter extends  RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder>
{
    private ClickListener clickListener;
    private List<BudgetInfo> info= Collections.emptyList();
    private LayoutInflater inflater;
    private ImageLoader imageloader;
    private Context context;
    private int lastPosition=-1;


    public BudgetAdapter(Context c, List<BudgetInfo> info)
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
    public void onBindViewHolder(BudgetViewHolder sponsorViewHolder, int i) {

        BudgetInfo ci = info.get(i);

        sponsorViewHolder.name.append(ci.name);
        sponsorViewHolder.rating.append(ci.rating);
        sponsorViewHolder.facilities.append(ci.facilities);
        sponsorViewHolder.review.append(ci.review);

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
    public BudgetViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.budget_card, viewGroup, false);
        BudgetViewHolder myholder= new BudgetViewHolder(view);
        return myholder;
    }
    //Using inner C from a function of the base class



    public void setClickListener(ClickListener c){
        clickListener=c;
    }



    public  class BudgetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected TextView name;
        protected TextView rating;
        protected TextView facilities;
        protected TextView review;
        protected CardView cw;



        public BudgetViewHolder(View v) {
            super(v);
            name= (TextView)v.findViewById(R.id.name);
            rating= (TextView)v.findViewById(R.id.rating);
            facilities= (TextView)v.findViewById(R.id.facilities);
            review= (TextView)v.findViewById(R.id.review);
            cw= (CardView)v.findViewById(R.id.card_view1);


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

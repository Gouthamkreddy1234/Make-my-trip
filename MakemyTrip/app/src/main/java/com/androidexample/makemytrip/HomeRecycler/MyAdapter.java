package com.androidexample.makemytrip.HomeRecycler;

/**
 * Created by Gautam on 7/9/2015.
 */

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.androidexample.makemytrip.Controller.AppController;
import com.androidexample.makemytrip.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<String[]> mDataset;
    private ImageLoader imageLoader;
    Context context;
    private LayoutInflater inflater;
    private ClickListener clickListener;


    private int lastPosition=-1;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        public TextView name;
        public ImageView placeimage;
        private CardView cv;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            placeimage = (ImageView)v.findViewById(R.id.placeimage);
            cv = (CardView)v.findViewById(R.id.card_view);
            cv.setOnClickListener(this);
            name.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if(clickListener!=null)
            {
                clickListener.onItemClick(v,getPosition());

            }

        }
    }
    public void setClickListener(ClickListener c){
        clickListener=c;
    }

    public interface ClickListener{
        public void onItemClick(View v, int position);
    }

    public void add(int position, String item[]) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(String item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<String[]> myDataset,Context c) {
        mDataset = myDataset;
        context=c;
        inflater = LayoutInflater.from(context);
        lastPosition = (myDataset.size())-1;
        imageLoader = AppController.getInstance().getImageLoader();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = inflater.inflate(R.layout.eachcard, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String name = (mDataset.get(0))[position];
        final String url = (mDataset.get(1))[position];



        imageLoader.get(url, ImageLoader.getImageListener(holder.placeimage,R.drawable.ic_loader,R.drawable.round_2));
        holder.name.setText(name);
        setAnimation(holder.cv,position);

    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)//reuse this code!!
        {
            // Animation animation = AnimationUtils.loadAnimation(context, R.anim.abc_slide_in_bottom);
            AnimatorSet animatorSet=new AnimatorSet();
            ObjectAnimator animationY=ObjectAnimator.ofFloat(viewToAnimate,"translationY",300,0);
            animationY.setInterpolator(new DecelerateInterpolator());
            ObjectAnimator animationX=ObjectAnimator.ofFloat(viewToAnimate,"translationX",-600,0);
            ObjectAnimator scaleX=ObjectAnimator.ofFloat(viewToAnimate,"scaleX",0F,1F);
            ObjectAnimator scaleY=ObjectAnimator.ofFloat(viewToAnimate,"scaleY",0F,1F);
            animationY.setDuration(600);
            animationX.setDuration(400);
            scaleX.setDuration(500);
            scaleY.setDuration(500);
            animatorSet.playTogether(animationX,animationY,scaleX,scaleY);
            animatorSet.start();
            //viewToAnimate.startAnimation(animation);

        }
        lastPosition = position;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.get(0).length;
    }

}
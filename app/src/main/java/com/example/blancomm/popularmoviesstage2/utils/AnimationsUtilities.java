package com.example.blancomm.popularmoviesstage2.utils;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.blancomm.popularmoviesstage2.R;


/**
 * Created by manuel on 12/10/15.
 */
public class AnimationsUtilities {

    public static void fadeInAlphaAdults(Context context,ImageView imageView){

        Animation myFadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.tween);
        imageView.startAnimation(myFadeInAnimation);
    }

    public static void fadeInAlphaIcons(Context context,ImageView imageView, int animation){

        Animation myFadeInAnimation = AnimationUtils.loadAnimation(context, animation);
        imageView.startAnimation(myFadeInAnimation);
    }

    public static void changeScaleIcons(Context context,ImageView imageView){

        Animation myFadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.tween);
        imageView.startAnimation(myFadeInAnimation);
    }

    public static void movePopular(Context context, ImageView imageView, int anim){
        Animation animation = AnimationUtils.loadAnimation(context, anim);
        imageView.startAnimation(animation);
    }

    public static void moveIcons(Context context, ImageView imageView, int animation){
        Animation animation1 = AnimationUtils.loadAnimation(context, animation);
        imageView.startAnimation(animation1);
    }
}

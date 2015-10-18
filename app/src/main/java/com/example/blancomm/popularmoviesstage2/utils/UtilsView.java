package com.example.blancomm.popularmoviesstage2.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.text.TextPaint;

import com.example.blancomm.popularmoviesstage2.R;

import java.lang.reflect.Field;

public class UtilsView {

    public static void makeCollapsingToolbarLayoutTypeface(CollapsingToolbarLayout collapsingToolbarLayout, Context context) {
        try {
            final Field field = collapsingToolbarLayout.getClass().getDeclaredField("mCollapsingTextHelper");
            field.setAccessible(true);

            final Object object = field.get(collapsingToolbarLayout);
            final Field tpf = object.getClass().getDeclaredField("mTextPaint");
            tpf.setAccessible(true);

            ((TextPaint) tpf.get(object)).setTypeface(Typeface.createFromAsset(context.getAssets(), "Roboto-Bold.ttf"));

        } catch (Exception ignored) {
        }
    }

    public static int setFlagLanguageDetail(String idLanguage){

        int icon = 0;

        switch (idLanguage){
            case "en":
                icon = R.drawable.flag_uk;
                break;
            case "es":
                icon = R.drawable.flag_spain;
                break;
            case "fr":
                icon = R.drawable.flag_france;
                break;
            case "de":
                icon = R.drawable.flag_germany;
                break;
            case "ja":
                icon = R.drawable.flag_japan;
                break;
            case "zh":
                icon = R.drawable.flag_china;
                break;
            case "it":
                icon = R.drawable.flag_italy;
                break;
            case "pt":
                icon = R.drawable.flag_portugal;
                break;
            case "ru":
                icon = R.drawable.flag_russia;
                break;
            default:
                icon = R.drawable.flag_uk;
                break;
        }

        return icon;
    }

    public static int setIconGenre(String genre){

        int icon = 0;

        switch (genre){
            case "Science Fiction":
                icon = R.drawable.ic_sci_fi;
                break;
            case "Action":
                icon = R.drawable.ic_actions;
                break;
            case "Adventure":
                icon = R.drawable.ic_adventures;
                break;
            case "Animation":
                icon = R.drawable.ic_fantasy;
                break;
            case "Comedy":
                icon = R.drawable.ic_comedy;
                break;
            case "Crime":
                icon = R.drawable.ic_crime;
                break;
            case "Documentary":
                icon = R.drawable.ic_documentary;
                break;
            case "Drama":
                icon = R.drawable.ic_drama;
                break;
            case "Family":
                icon = R.drawable.ic_comedy;
                break;
            case "Fantasy":
                icon = R.drawable.ic_fantasy;
                break;
            case "Foreign":
                icon = R.drawable.ic_adventures;
                break;
            case "History":
                icon = R.drawable.ic_documentary;
                break;
            case "Horror":
                icon = R.drawable.ic_horror;
                break;
            case "Music":
                icon = R.drawable.ic_comedy;
                break;
            case "Mystery":
                icon = R.drawable.ic_triller;
                break;
            case "Romance":
                icon = R.drawable.ic_novel;
                break;
            case "TV Movie":
                icon = R.drawable.ic_documentary;
                break;
            case "Thriller":
                icon = R.drawable.ic_triller;
                break;
            case "War":
                icon = R.drawable.ic_actions;
                break;
            case "Western":
                icon = R.drawable.ic_western;
                break;
            default:
                icon = R.drawable.ic_comedy;
                break;
        }

        return icon;
    }
}

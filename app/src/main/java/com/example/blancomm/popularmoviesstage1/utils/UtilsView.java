package com.example.blancomm.popularmoviesstage1.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.text.TextPaint;

import com.example.blancomm.popularmoviesstage1.R;

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
}

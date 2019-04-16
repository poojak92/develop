package com.scarlett.helper;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by deVere on 1/5/2017.
 */
public class FontHelper {
    public static Typeface getGothamBlack(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Gotham-Black.otf");
        return font;
    }

    public static Typeface getGothamBold(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Gotham-Bold.otf");
        return font;
    }


    public static Typeface getGothamBook(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Gotham-Book.otf");
        return font;
    }


    public static Typeface getGothamLight(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Gotham-Light.otf");
        return font;
    }

    public static Typeface getGothamMedium(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Gotham-Medium.otf");
        return font;
    }


    public static Typeface getGothamThin(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Gotham-Thin.otf");
        return font;
    }
}

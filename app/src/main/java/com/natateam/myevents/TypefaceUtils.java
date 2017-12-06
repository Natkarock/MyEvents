package com.natateam.myevents;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

/**
 * Created by macbook on 02/09/ 15.
 */
public class TypefaceUtils {
    public static Typeface getBikham(Context context){
        AssetManager assetManager = context.getAssets();
        return Typeface.createFromAsset(assetManager, "fonts/bikham_font.ttf");
    }
}

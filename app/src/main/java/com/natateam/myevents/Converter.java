package com.natateam.myevents;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.StaticLayout;
import android.text.TextPaint;

/**
 * Created by macbook on 02/09/ 15.
 */
public class Converter {


    public  static Bitmap drawTextToBitmap(Context gContext,
                                   int gResId,
                                   String gText) {
        Resources resources = gContext.getResources();
        float scale = resources.getDisplayMetrics().density;
        Bitmap bitmap =
                BitmapFactory.decodeResource(resources, gResId);

        android.graphics.Bitmap.Config bitmapConfig =
                bitmap.getConfig();
        // set default bitmap config if none
        if(bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);
        // new antialised Paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint.setColor(Color.rgb(61, 61, 61));
        // text size in pixels
        paint.setTextSize((int) (14 * scale));
        // text shadow
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

        // draw  to the Canvas center
        Rect bounds = new Rect();
        paint.getTextBounds(gText, 0, gText.length(), bounds);
        int x = (bitmap.getWidth() - bounds.width())/2;
        int y = (bitmap.getHeight() + bounds.height())/2;

        canvas.drawText(gText, x, y, paint);


        return bitmap;
    }

    // Convert Text to image Method
    // pass the paramter : text , size , stroke ,color,typeface
    public  static Bitmap textAsBitmap(String text, float textSize, float stroke,
                               int color, Typeface typeface) {

        TextPaint paint = new TextPaint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(textSize);
        paint.setStrokeWidth(stroke);
        //paint.setTypeface(typeface);

        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);

        float baseline = (int) (-paint.ascent() + 3f); // ascent() is negative

        StaticLayout staticLayout = new StaticLayout(text, 0, text.length(),
                paint, 435, android.text.Layout.Alignment.ALIGN_NORMAL, 1.0f,
                1.0f, false);

        int linecount = staticLayout.getLineCount();

        int height = (int) (baseline + paint.descent() + 3) * linecount + 10;

        Bitmap image = Bitmap
                .createBitmap(435, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        canvas.drawARGB(0xFF, 0x00, 0x00, 0xFF);

        staticLayout.draw(canvas);

        return image;

    }

    // Adding Border to bitmap
    public Bitmap addBorder(Bitmap bmp, int borderSize, int borderColor) {
        Bitmap bmpWithBorder = Bitmap.createBitmap(bmp.getWidth() + borderSize
                * 2, bmp.getHeight() + borderSize * 2, bmp.getConfig());
        Canvas canvas = new Canvas(bmpWithBorder);
        canvas.drawColor(borderColor);
        canvas.drawBitmap(bmp, borderSize, borderSize, null);
        return bmpWithBorder;
    }

}

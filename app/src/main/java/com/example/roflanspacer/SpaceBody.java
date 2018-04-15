package com.example.roflanspacer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import java.io.FileNotFoundException;

import static com.example.roflanspacer.MainActivity.unitH;
import static com.example.roflanspacer.MainActivity.unitW;

/**
 * Created by Никита on 10.04.2018.
 */

public class SpaceBody  {
    protected float x;
    protected float y;
    protected float size;
    protected float speed;
    protected int bitmapId;
    protected Bitmap bitmap;
    void init(Context context) throws FileNotFoundException {
        Bitmap cBitmap = BitmapFactory.decodeResource(context.getResources(), bitmapId);
        bitmap =  Bitmap.createScaledBitmap(
                cBitmap, (int)(Math.abs(size *unitW)), Math.abs((int)(size *unitH)), false);
        cBitmap.recycle();
    }

    void update(){
    }

    void drow(Paint paint, Canvas canvas){
        canvas.drawBitmap(bitmap, x*unitW, y*unitH, paint);
    }
}

package com.example.roflanspacer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import java.io.FileNotFoundException;

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
                cBitmap, (int)(Math.abs(size * GameView.unitW))+1, Math.abs((int)(size * GameView.unitH))+1, false);
        cBitmap.recycle();
    }

    void update(){
    }

    void drow(Paint paint, Canvas canvas){
        canvas.drawBitmap(bitmap, x*GameView.unitW, y*GameView.unitH, paint);
    }
}

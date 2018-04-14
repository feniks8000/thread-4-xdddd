package com.example.roflanspacer;

import android.content.Context;

import java.io.FileNotFoundException;

/**
 * Created by Никита on 10.04.2018.
 */

public class Ship extends SpaceBody {

    private double t;

    public Ship(Context context) throws FileNotFoundException {

        bitmapId = R.drawable.ship;

    size = 3;
    x=7;
    y=GameView.maxY - size - 1;
    speed = (float) 0.35;

    init(context);
}
    @Override
    public void update() {

    t+=0.00001;
        if(MainActivity.isLeftPressed && x >= 0){
            x -= speed*(1+t);
        }
        if(MainActivity.isRightPressed && x <= GameView.maxX - 5){
            x += speed*(1+t);
        }
    }

}

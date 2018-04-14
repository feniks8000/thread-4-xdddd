package com.example.roflanspacer;

import android.content.Context;

import java.io.FileNotFoundException;
import java.util.Random;

/**
 * Created by Никита on 13.04.2018.
 */

class Bboom extends SpaceBody {
    private int radius = 1;
    private double t;
    private float minSpeed = (float) 0.1;
    private float maxSpeed = (float) 0.5;
    public Bboom(Context context) throws FileNotFoundException {
        Random random = new Random();

        bitmapId = R.drawable.bonus;
        y=0;
        x = random.nextInt(GameView.maxX) - radius;
        size = radius*2;
        speed = minSpeed + (maxSpeed - minSpeed) * random.nextFloat()*(float)(1+t);

        init(context);
    }
    @Override
    public void update() {
        y += speed+t;
        t+=0.00001;
    }
    public boolean isCollisionBboom(float shipX, float shipY, float shipSize) {
        return !(((x+size) < shipX)||(x > (shipX+shipSize))||((y+size) < shipY)||(y > (shipY+shipSize)));
    }
}
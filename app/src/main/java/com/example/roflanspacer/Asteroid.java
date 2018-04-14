package com.example.roflanspacer;

import android.content.Context;

import java.io.FileNotFoundException;
import java.util.Random;

/**
 * Created by Никита on 10.04.2018.
 */

public class Asteroid extends SpaceBody  {
    private float radius = (float) 1.5;
    private double t;
    private float minSpeed = (float) 0.1;
    private float maxSpeed = (float) 0.5;
    public Asteroid(Context context) throws FileNotFoundException {
        Random random = new Random();

        bitmapId =Math.abs(R.drawable.asteroid);
        y=0;
        x = random.nextInt(GameView.maxX) - radius;
        size = radius*2;
        speed = minSpeed + (maxSpeed - minSpeed) * random.nextFloat()*(float)(1+t);

        init(context);
    }
    @Override
    public void update() {
        y += speed;
        t+=0.0001;
    }
    public boolean isCollision(float shipX, float shipY, float shipSize) {
        return !(((x+size) < shipX)||(x > (shipX+shipSize))||((y+size) < shipY)||(y > (shipY+shipSize)));
    }
}

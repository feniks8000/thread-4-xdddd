package com.example.roflanspacer;

import android.content.Context;

import java.io.FileNotFoundException;
import java.util.Random;

/**
 * Created by Никита on 10.04.2018.
 */

public class Money extends SpaceBody{
    private int radius = 1;
    private float minSpeed = (float) 0.1;
    private float maxSpeed = (float) 0.5;
    public Money (Context context) throws FileNotFoundException {
        Random random = new Random();

        bitmapId = R.drawable.money;
        y=0;
        x = random.nextInt(GameView.maxX) - radius;
        size = radius*2;
        speed = minSpeed + (maxSpeed - minSpeed) * random.nextFloat();

        init(context);
    }
    @Override
    public void update() {
        y += speed;
    }
    public boolean isCollisionMoney(float shipX, float shipY, float shipSize) {
        return !(((x+size) < shipX)||(x > (shipX+shipSize))||((y+size) < shipY)||(y > (shipY+shipSize)));
    }
}


package com.example.spacewallpaper;

import java.util.Random;

public class Stars {
    private int x;
    private int y;

    private int maxX;
    private int maxY;


    Stars(int screenX, int screenY) {
        maxX = screenX;
        maxY = screenY;
        Random generator = new Random();

        //generating a random coordinate
        //but keeping the coordinate inside the screen size
        x = generator.nextInt(maxX);
        y = generator.nextInt(maxY);
    }


    float getStarWidth() {
        //Making the star width random so that
        //it will give a real look
        float minX = 1.0f;
        float maxX = 4.0f;
        Random rand = new Random();
        return rand.nextFloat() * (maxX - minX) + minX;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }
}

package com.example.spacewallpaper;

import java.util.ArrayList;

/**
 * Created by Aleksandra on 05.04.2018.
 */

public class Nebula {
    private int resId;
    private int [] coloursSet;
    private int countStars;

    public int getResId() {
        return resId;
    }

    public int[] getColoursSet() {
        return coloursSet;
    }

    public int getCountStars() {
        return countStars;
    }

    public ArrayList<Stars> getCosmos() {
        return cosmos;
    }
    private ArrayList<Stars> cosmos = new ArrayList<>();

    public Nebula(int resId, int[] coloursSet, int countStars, int screenX, int screenY) {
        this.resId = resId;
        this.coloursSet = coloursSet;
        this.countStars = countStars;
        for (int i = 0; i < countStars; i++) {
            Stars c = new Stars(screenX, screenY);
            cosmos.add(c);
        }
    }
}

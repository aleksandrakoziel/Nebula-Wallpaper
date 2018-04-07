package com.example.spacewallpaper;

import android.graphics.Color;

import java.util.ArrayList;


public class Utils {

    public static ArrayList<Nebula> availableNebulas(int screenX, int screenY) {
        ArrayList<Nebula> nebulas = new ArrayList<>();
        int[] colors = {Integer.parseInt("fdc36f", 16),
                Integer.parseInt("581f42", 16),
                Integer.parseInt("f18db0", 16),
                Integer.parseInt("3c65b3", 16),
                Color.WHITE};
        nebulas.add(new Nebula(R.drawable.space, colors, 300, screenX, screenY));
        nebulas.add(new Nebula(R.drawable.space1, colors, 300, screenX, screenY));
        nebulas.add(new Nebula(R.drawable.space2, colors, 300, screenX, screenY));
        nebulas.add(new Nebula(R.drawable.space3, colors, 300, screenX, screenY));
        nebulas.add(new Nebula(R.drawable.space4, colors, 300, screenX, screenY));
        nebulas.add(new Nebula(R.drawable.space5, colors, 300, screenX, screenY));
        nebulas.add(new Nebula(R.drawable.space6, colors, 300, screenX, screenY));
        nebulas.add(new Nebula(R.drawable.space7, colors, 300, screenX, screenY));
        nebulas.add(new Nebula(R.drawable.space8, colors, 300, screenX, screenY));
        nebulas.add(new Nebula(R.drawable.space9, colors, 300, screenX, screenY));
        nebulas.add(new Nebula(R.drawable.space10, colors, 300, screenX, screenY));
        nebulas.add(new Nebula(R.drawable.space11, colors, 300, screenX, screenY));
        nebulas.add(new Nebula(R.drawable.space12, colors, 300, screenX, screenY));
        nebulas.add(new Nebula(R.drawable.space13, colors, 300, screenX, screenY));
        nebulas.add(new Nebula(R.drawable.space14, colors, 300, screenX, screenY));
        nebulas.add(new Nebula(R.drawable.space15, colors, 300, screenX, screenY));
        nebulas.add(new Nebula(R.drawable.space16, colors, 300, screenX, screenY));
        nebulas.add(new Nebula(R.drawable.space17, colors, 300, screenX, screenY));
        nebulas.add(new Nebula(R.drawable.space18, colors, 300, screenX, screenY));
        nebulas.add(new Nebula(R.drawable.space19, colors, 300, screenX, screenY));


        return nebulas;
    }
}

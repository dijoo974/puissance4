package com.example.android.puissance4;

import java.util.Random;

/**
 * Created by Francois on 21/02/2016.
 */
public class CPU extends Player {

    Random r;
    boolean isHuman = true;
    int difficulty = 1;


    public CPU(Game game, boolean iamBlack)
    {
        super(game, iamBlack);
        r = new Random();


    }

    public int move()
    {






return -1;
    }


    public boolean isHuman()
    {
        return isHuman;

    }

    public void setDifficulty(int difficulty)
    {
        this.difficulty = difficulty;

    }

    public int getDifficulty()
    {
        return this.difficulty;

    }
}

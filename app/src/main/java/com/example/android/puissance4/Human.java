package com.example.android.puissance4;

import java.util.Random;

/**
 * Created by Francois on 08/02/2016.
 */
public class Human extends Player{

    Random r;
    boolean isHuman = true;

    public Human(Game game, boolean iamBlack)
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





}

package com.example.android.puissance4;

/**
 * Created by Francois on 07/02/2016.
 */
public abstract class Player {

    protected Game myGame;
    protected boolean iAmBlack;

    public Player(Game game, boolean iamBlack)
    {
        this.myGame = game;
        this.iAmBlack = iamBlack;


    }


    public abstract int move();


    public abstract boolean isHuman();

}

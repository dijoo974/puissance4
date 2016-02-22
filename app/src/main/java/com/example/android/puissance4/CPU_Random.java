package com.example.android.puissance4;

import java.util.Random;

/**
 * Created by Francois on 08/02/2016.
 */
    public class CPU_Random extends Player{

        Random r;
        boolean isHuman = false;
        Game myGame;


        public CPU_Random(Game game, boolean iamBlack)
        {
            super(game, iamBlack);
            myGame = game;
            r = new Random();

        }

        public int move()
        {

            int randomMove = r.nextInt(7);

            while (myGame.board.firstEmptySlot(randomMove) == -1)
            {
                randomMove = r.nextInt(6);
            }


            return randomMove;
        }


    public boolean isHuman()
    {
        return false;

    }







}

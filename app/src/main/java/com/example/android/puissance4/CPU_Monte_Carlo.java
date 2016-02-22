package com.example.android.puissance4;

import android.os.AsyncTask;

import java.util.Random;

/**
 * Created by Francois on 09/02/2016.
 */
public class CPU_Monte_Carlo extends Player{

    Random r;
    boolean isHuman = false;
    Game gameCopy = new Game();
    Game myGame = new Game();
    int myColor = 0;

    int[] statPerColumn;

    int n;
    int m;

    public CPU_Monte_Carlo(Game game, boolean iAmBlack)
    {
        super(game, iAmBlack);

        r = new Random();

            myGame = game;


        if (iAmBlack)
        {
            myColor = 1;
        }
        else
        {
            myColor = 2;
        }

    }


    public boolean isHuman()
    {
        return false;

    }


    public int move()
    {


        int monteCarlo = -1;


        if (myGame.canWin(iAmBlack) != -1)
        {
            System.out.println("I can win ! : " + myGame.canWin(iAmBlack));
            return myGame.canWin(iAmBlack);
                    }
        else if (myGame.canWin(!iAmBlack) != -1)
        {
            System.out.println("They can win ! : " + myGame.canWin(!iAmBlack));
            return myGame.canWin(!iAmBlack);
        }


        else
        {
            int depth = 5000;
            monteCarlo = monteCarloMove(depth);

            System.out.println("Monte Carlo : ");
            for (int i = 0; i < 7; i++) {
                System.out.print(" // ");
                System.out.printf("%-10.1f%n", ((float) statPerColumn[i] * 100) / depth);
            }
            System.out.println("");
            return monteCarlo;
        }



    }

        public int monteCarloMove(int depth)
        {
            Game tempGame = new Game();

            statPerColumn = new int[7];

            int maxColumn = 0;

            for ( int column = 0; column < 7; column++)
            {
                tempGame.copyGame(myGame);

                statPerColumn[column] = 0;

                if (tempGame.board.isColumnFull(column))
                {
                    statPerColumn[column] = -1;

                }
                else
                {

                    for ( int tries = 0; tries < depth; tries++)
                    {
                        tempGame.copyGame(myGame);

                        tempGame.board.playColumn(tempGame.board.firstEmptySlot(column), column, tempGame.playerColorTurn());
                        tempGame.blacksTurn = !tempGame.blacksTurn;

                        int randomWinner = playFullRandomGame(tempGame);

                        if (randomWinner == myColor)
                        {
                            statPerColumn[column]++;
                        }
                    }
                }

                if (statPerColumn[maxColumn] < statPerColumn[column])
                {
                    maxColumn = column;
                }
            }
            return maxColumn;
        }



        public int playFullRandomGame(Game tryGame)
        {
            while (!tryGame.board.isFull())
            {
                int rMove = tryGame.randomMove();
                tryGame.board.playColumn(tryGame.board.firstEmptySlot(rMove), rMove, tryGame.playerColorTurn());

               if (tryGame.isWonMonteCarlo() == 1)
                {
                    return 1;
                }

                if (tryGame.isWonMonteCarlo() == 2)
                {
                    return 2;
                }
                tryGame.blacksTurn = !tryGame.blacksTurn;
            }
             return -1;
        }







}

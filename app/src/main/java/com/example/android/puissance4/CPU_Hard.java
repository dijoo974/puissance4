package com.example.android.puissance4;

import java.util.Random;

/**
 * Created by Francois on 09/02/2016.
 */
public class CPU_Hard extends Player{

    Random r;
    boolean isHuman = false;




    public CPU_Hard(Game game, boolean iAmBlack)
    {
        super(game, iAmBlack);

        r = new Random();

    }

    public int move()
    {

        int a = bestRandomMove();
        int b = goodMove(iAmBlack);
        int c = goodMove(!iAmBlack);


        if (canWin(iAmBlack) != -1)
        {
            System.out.println("I can win ! : " + canWin(iAmBlack));
            return canWin(iAmBlack);



        }
        else if (canWin(!iAmBlack) != -1)
        {
            System.out.println("They can win ! : " + canWin(!iAmBlack));
            return canWin(!iAmBlack);



        }
        else if (b != -1 && !dangerousMove(iAmBlack, b))
        {
            System.out.println("My good move : " + b);
            return b;
        }

        else if (c != -1 && !dangerousMove(iAmBlack, c) && !dangerousMove(!iAmBlack, c))
        {
            System.out.println("Avoid good move for them ! :" + c);
            return c;
        }



        else if (a != -1 && !dangerousMove(iAmBlack, a))
        {


            System.out.println("Play best random move : " + a);
            return a;
        }

        else
        {
            int d = randomMove();
            int n = 0;

            while (dangerousMove(iAmBlack, d) && n < 10)
            {
                d = randomMove();
                n++;
            }

            System.out.println("Just a random move : " + a);
            return d;
        }
               
           
         
               
         
            


/*
        // int i = 0; i < 7; i++
        // int j = 5; j >= 0 ; j--
        for (int j = 0 ; j < 6 ; j++)
        {
            System.out.print("| ");
            for (int i = 0; i < 7; i++)
            {

                if (!myGame.board.isSlotFilled(j,i) )
                {
                    System.out.print("- ");
                }
                else
                {
                    if (myGame.board.isSlotBlack(j,i))
                    {
                        System.out.print("X ");
                    }
                    else
                    {
                        System.out.print("O ");
                    }
                }

            }
            System.out.println("");
        }

        System.out.println("_ _ _ _ _ _ _ _");



       */




    }







    public boolean isHuman()
    {
        return false;

    }

    /**
     * Returns a random valid move amongst a tab of best start moves.
     * According to the value of the different slots included in a table.
     *
     * @return a random valid move.
     */
    public int bestRandomMove()
    {

        int[][] table = { {3, 4, 5, 7, 5, 4, 3},
                {4, 6, 8, 10, 8, 6, 4},
                {5, 8, 11, 13, 11, 8, 5},
                {5, 8, 11, 13, 11, 8, 5},
                {4, 6, 8, 10, 8, 6, 4},
                {3, 4, 5, 7, 5, 4, 3} };


        int value = 0;
        int[] randomValue = {0, 0, 0, 0, 0, 0, 0};


        for (int i = 0; i < 7; i++)
        {
            if (!myGame.board.isColumnFull(i))
            {

                if (value == table[myGame.board.firstEmptySlot(i)][i])
                {
                    randomValue[i] = value;

                }

                if (value < table[myGame.board.firstEmptySlot(i)][i])
                {

                    for (int k = 0; k < 7; k++)
                    {
                        randomValue[k] = 0;
                    }

                    value = table[myGame.board.firstEmptySlot(i)][i];

                    randomValue[i] = value;

                }

            }

        }

        int j = 0;

        while (randomValue[j] == 0)
        {
            j = r.nextInt(7);

        }

        return j;
    }

    /**
     * Returns a random valid move. If your agent doesn't know what to do, making a random move
     * can allow the game to go on anyway.
     *
     * @return a random valid move.
     */
    public int randomMove()
    {
        int i = r.nextInt(7);
        while (myGame.board.firstEmptySlot(i) == -1)
        {
            i = r.nextInt(7);
        }
        return i;
    }


    /**
     * Returns the column that would allow one agent to win.
     *
     * You might want your agent to check to see if it has a winning move available to it so that
     * it can go ahead and make that move. Implement this method to return what column would
     * allow the agent to win.
     * @param player says the color of the player
     * @return the column that would allow the agent to win or -1 if that column doesn't exist
     */
    public int canWin(boolean player)
    {

        for (int i = 0; i <7; i++)
        {
            if (winGame(player, i))
            {
                return i;
            }

        }

        return -1;


    }


    /**
     * Check if the game has been won for player when adding a token in column tryColumn.
     *
     * @param player says the color of the player
     * @param tryColumn tries to add a token in tryColumn
     * @return true if player wins when adding a token in column tryColumn
     */
    public boolean winGame(boolean player, int tryColumn)
    {


        if (myGame.board.isColumnFull(tryColumn))
        {
            return false;
        }

        int lowestEmptySlotIndex = myGame.board.firstEmptySlot(tryColumn);
        int [][] board = myGame.board.getBoard();
        int playerColor;


        if (player)
        {
            board[lowestEmptySlotIndex][tryColumn] = 1;
            playerColor = 1;
        }
        else
        {
            board[lowestEmptySlotIndex][tryColumn] = 2;
            playerColor = 2;
        }





        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 6; j++)
            {
                if(board[j][i] != 'B')
                {
                    if (j + 3 < 6)
                    {
                        if(board[j][i] == board[j + 1][i] && board[j][i] == board[j + 2][i] && board[j][i] == board[j + 3][i] && board[j][i] == playerColor)
                        {

                            return true;
                        }
                    }
                    if (i + 3 < 7)
                    {
                        if (board[j][i] == board[j][i + 1] && board[j][i] == board[j][i + 2] && board[j][i] == board[j][i + 3] && board[j][i] == playerColor)
                        {
                            return true;
                        }
                    }
                    if (i + 3 < 7 && j + 3 < 6)
                    {
                        if(board[j][i] == board[j + 1][i + 1] && board[j][i] == board[j + 2][i + 2] && board[j][i] == board[j + 3][i + 3] && board[j][i] == playerColor)
                        {

                            return true;
                        }
                    }
                    if (i > 2 && j + 3 < 6)
                    {
                        if (board[j][i] == board[j + 1][i - 1] && board[j][i] == board[j + 2][i - 2] && board[j][i] == board[j + 3][i - 3] && board[j][i] == playerColor)
                        {

                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }


    /**
     * Check if the game has been won for player when adding a token in column tryColumn.
     *
     *
     * @return int column 
     */
    public int goodMove(boolean player)
    {
        int[] table = new int[7];

        for (int k = 0; k < table.length; k++)
        {
            table[k] = 0;
        }

        int playerColor;




        if (player)
        {

            playerColor = 1;
        }
        else
        {

            playerColor = 2;
        }






        for (int m = 0; m < 7; m++)
        {

            if (!myGame.board.isColumnFull(m))
            {








                for (int n = m; n < 7; n++)
                {
                    int lowestEmptySlotIndex = myGame.board.firstEmptySlot(m);
                    int[][] board = myGame.board.getBoard();
                    if ( m==n && lowestEmptySlotIndex > 0 )
                    {


                        if (player)
                        {
                            board[lowestEmptySlotIndex][m] = 1;
                            board[lowestEmptySlotIndex - 1][m] = 1;

                        }
                        else
                        {
                            board[lowestEmptySlotIndex][m] = 2;
                            board[lowestEmptySlotIndex - 1][m] = 2;

                        }

                    }

                    if ( m !=n && myGame.board.firstEmptySlot(n) >= 0 )
                    {


                        if (player)
                        {
                            board[lowestEmptySlotIndex][m] = 1;
                            board[myGame.board.firstEmptySlot(n)][n] = 1;

                        }
                        else
                        {
                            board[lowestEmptySlotIndex][m] = 2;
                            board[myGame.board.firstEmptySlot(n)][n] = 2;

                        }

                    }


                    for (int i = 0; i < 7; i++)
                    {
                        for (int j = 0; j < 6; j++)
                        {
                            if(board[j][i] != 0)
                            {
                                if (j + 3 < 6)
                                {
                                    if(board[j][i] == board[j + 1][i] && board[j][i] == board[j + 2][i] && board[j][i] == board[j + 3][i] && board[j][i] == playerColor)
                                    {

                                        table[m] = 1;
                                    }
                                }
                                if (i + 3 < 7)
                                {
                                    if (board[j][i] == board[j][i + 1] && board[j][i] == board[j][i + 2] && board[j][i] == board[j][i + 3] && board[j][i] == playerColor)
                                    {
                                        table[m] = 1;
                                    }
                                }
                                if (i + 3 < 7 && j + 3 < 6)
                                {
                                    if(board[j][i] == board[j + 1][i + 1] && board[j][i] == board[j + 2][i + 2] && board[j][i] == board[j + 3][i + 3] && board[j][i] == playerColor)
                                    {

                                        table[m] = 1;
                                    }
                                }
                                if (i > 2 && j + 3 < 6)
                                {
                                    if (board[j][i] == board[j + 1][i - 1] && board[j][i] == board[j + 2][i - 2] && board[j][i] == board[j + 3][i - 3] && board[j][i] == playerColor)
                                    {

                                        table[m] = 1;
                                    }
                                }
                            }
                        }
                    }







                }

            }

        }

        int z = 0;

        int temp2 = 0;
        Random rand = new Random();
        z = rand.nextInt(table.length);

        while(temp2 < 20)
        {
            if (table[z]>0)
            {
                return z;
            }
            temp2++;
            z = rand.nextInt(table.length);
        }
    
    /*for (int r = 0; r < table.length; r++)
    {
        if (table[r] != 0)
        {
            return r;
        }
    }*/

        return -1;
    }



    /**
     * Check if the game has been won for player when adding a token in column tryColumn.
     *
     *
     * @return int column 
     */
    public boolean dangerousMove(boolean player, int tryColumn)
    {




        int notPlayerColor;




        if (player)
        {

            notPlayerColor = 2;
        }
        else
        {

            notPlayerColor = 1;
        }
        


        if (!myGame.board.isColumnFull(tryColumn))
        {
            
            int lowestEmptySlotIndex = myGame.board.firstEmptySlot(tryColumn);
            int[][] board = myGame.board.getBoard();
            if (lowestEmptySlotIndex > 0 )
            {


                if (player)
                {
                    board[lowestEmptySlotIndex][tryColumn] = 1;
                    board[lowestEmptySlotIndex - 1][tryColumn] = 2;

                }
                else
                {
                    board[lowestEmptySlotIndex][tryColumn] = 2;
                    board[lowestEmptySlotIndex - 1][tryColumn] = 1;

                }

            }




            for (int i = 0; i < 7; i++)
            {
                for (int j = 0; j < 6; j++)
                {
                    if(board[j][i] != 'B')
                    {
                        if (j + 3 < 6)
                        {
                            if(board[j][i] == board[j + 1][i] && board[j][i] == board[j + 2][i] && board[j][i] == board[j + 3][i] && board[j][i] == notPlayerColor)
                            {

                                return true;
                            }
                        }
                        if (i + 3 < 7)
                        {
                            if (board[j][i] == board[j][i + 1] && board[j][i] == board[j][i + 2] && board[j][i] == board[j][i + 3] && board[j][i] == notPlayerColor)
                            {
                                return true;
                            }
                        }
                        if (i + 3 < 7 && j + 3 < 6)
                        {
                            if(board[j][i] == board[j + 1][i + 1] && board[j][i] == board[j + 2][i + 2] && board[j][i] == board[j + 3][i + 3] && board[j][i] == notPlayerColor)
                            {

                                return true;
                            }
                        }
                        if (i > 2 && j + 3 < 6)
                        {
                            if (board[j][i] == board[j + 1][i - 1] && board[j][i] == board[j + 2][i - 2] && board[j][i] == board[j + 3][i - 3] && board[j][i] == notPlayerColor)
                            {

                                return true;
                            }
                        }
                    }
                }
            }











        }



        return false;
    }



}

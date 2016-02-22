package com.example.android.puissance4;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

/**
 * Created by Francois on 07/02/2016.
 */
public class Game {

    Board board ;

    Board highlightBoard;

    boolean gameActive;
    boolean blacksTurn;


    Random r = new Random();

    public Game()
    {

     this.board = new Board();
     this.highlightBoard = new Board();
        this.gameActive = true;

        blacksTurn = true;

    }

/* retourne 1 si les noirs gagnent
2 si les blancs gagnent 
0 si personne n'a gagné
 */

    public int isWon() {

        int[][] board = this.board.getBoard();

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                if (board[j][i] != 0) {
                    if (j + 3 < 6) {
                        if (board[j][i] == board[j + 1][i] && board[j][i] == board[j + 2][i] && board[j][i] == board[j + 3][i]) {
                            this.highlightBoard.playColumn(j, i, 1);
                            this.highlightBoard.playColumn(j + 1, i, 1);
                            this.highlightBoard.playColumn(j + 2, i, 1);
                            this.highlightBoard.playColumn(j + 3, i, 1);

                            gameActive = false;
                            return board[j][i];
                        }
                    }
                    if (i + 3 < 7) {
                        if (board[j][i] == board[j][i + 1] && board[j][i] == board[j][i + 2] && board[j][i] == board[j][i + 3]) {
                            this.highlightBoard.playColumn(j, i, 1);
                            this.highlightBoard.playColumn(j, i + 1, 1);
                            this.highlightBoard.playColumn(j, i + 2, 1);
                            this.highlightBoard.playColumn(j, i + 3, 1);

                            gameActive = false;
                            return board[j][i];
                        }
                    }
                    if (i + 3 < 7 && j + 3 < 6) {
                        if (board[j][i] == board[j + 1][i + 1] && board[j][i] == board[j + 2][i + 2] && board[j][i] == board[j + 3][i + 3]) {
                            this.highlightBoard.playColumn(j, i, 1);
                            this.highlightBoard.playColumn(j + 1, i + 1, 1);
                            this.highlightBoard.playColumn(j + 2, i + 2, 1);
                            this.highlightBoard.playColumn(j + 3, i + 3, 1);

                            gameActive = false;
                            return board[j][i];
                        }
                    }
                    if (i > 2 && j + 3 < 6) {
                        if (board[j][i] == board[j + 1][i - 1] && board[j][i] == board[j + 2][i - 2] && board[j][i] == board[j + 3][i - 3]) {
                            this.highlightBoard.playColumn(j, i, 1);
                            this.highlightBoard.playColumn(j + 1, i - 1, 1);
                            this.highlightBoard.playColumn(j + 2, i - 2, 1);
                            this.highlightBoard.playColumn(j + 3, i - 3, 1);

                            gameActive = false;
                            return board[j][i];
                        }
                    }
                }
            }
        }
        return 0;
    }


    public boolean isActive()
    {
        return gameActive;

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
            if (this.winGame(player, i))
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

        Board boardCopy = new Board();
        boardCopy.copyBoard(this.board);

        if (this.board.isColumnFull(tryColumn))
        {
            return false;
        }

        int lowestEmptySlotIndex = boardCopy.firstEmptySlot(tryColumn);
        int [][] board = boardCopy.getBoard();


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
                if(board[j][i] != 0)
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
     * Returns if the player can win at next move
     *
     *
     * @param player says the color of the player
     * @return true if the player can win at next move, false otherwise
     */
    public boolean canWinSomewhere(boolean player)
    {

        for (int i = 0; i <7; i++)
        {
            if (this.winGame(player, i))
            {
                return true;
            }

        }

        return false;


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
        while (this.board.firstEmptySlot(i) == -1)
        {
            i = r.nextInt(7);
        }
        return i;
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
            if (!this.board.isColumnFull(i))
            {

                if (value == table[this.board.firstEmptySlot(i)][i])
                {
                    randomValue[i] = value;

                }

                if (value < table[this.board.firstEmptySlot(i)][i])
                {

                    for (int k = 0; k < 7; k++)
                    {
                        randomValue[k] = 0;
                    }

                    value = table[this.board.firstEmptySlot(i)][i];

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


    public int playerColorTurn()
    {
        if (this.blacksTurn)
        {
            return 1;
        }
        else
        {
            return 2;
        }

    }

    public void copyGame(Game otherGame)
    {
        this.board.copyBoard(otherGame.board);

        this.highlightBoard.copyBoard(otherGame.highlightBoard);
        this.gameActive = otherGame.gameActive;

        this.blacksTurn = otherGame.blacksTurn;


    }



    public void printGameBoard()
    {
        // int i = 0; i < 7; i++
        // int j = 5; j >= 0 ; j--
        for (int j = 0 ; j < 6 ; j++)
        {
            System.out.print("| ");
            for (int i = 0; i < 7; i++)
            {

                if (!this.board.isSlotFilled(j,i) )
                {
                    System.out.print("- ");
                }
                else
                {
                    if (this.board.isSlotBlack(j,i))
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

    }


    /* retourne 1 si les noirs gagnent
2 si les blancs gagnent
0 si personne n'a gagné
 */

    public int isWonMonteCarlo() {

        int[][] board = this.board.getBoard();

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                if (board[j][i] != 0) {
                    if (j + 3 < 6) {
                        if (board[j][i] == board[j + 1][i] && board[j][i] == board[j + 2][i] && board[j][i] == board[j + 3][i]) {
                            return board[j][i];
                        }
                    }
                    if (i + 3 < 7) {
                        if (board[j][i] == board[j][i + 1] && board[j][i] == board[j][i + 2] && board[j][i] == board[j][i + 3]) {

                            return board[j][i];
                        }
                    }
                    if (i + 3 < 7 && j + 3 < 6) {
                        if (board[j][i] == board[j + 1][i + 1] && board[j][i] == board[j + 2][i + 2] && board[j][i] == board[j + 3][i + 3]) {

                            return board[j][i];
                        }
                    }
                    if (i > 2 && j + 3 < 6) {
                        if (board[j][i] == board[j + 1][i - 1] && board[j][i] == board[j + 2][i - 2] && board[j][i] == board[j + 3][i - 3]) {

                            return board[j][i];
                        }
                    }
                }
            }
        }
        return 0;
    }





}

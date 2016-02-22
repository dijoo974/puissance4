package com.example.android.puissance4;

import android.view.View;
import android.widget.ImageView;

/**
 * Created by Francois on 07/02/2016.
 */
public class Board {

    int [][] board = new int[6][7];

    /* contruit le tableau de jeu
    *
    *  Valeurs dans le tableau
    *  0 vide
    *  1 noir
    *  2 blanc
    * */

    public Board()
    {
        for (int i = 0; i < 6; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                board[i][j] = 0;
            }
        }
    }

   /* récupère le tableau de jeu */

    public int[][] getBoard()
    {
        int [][] returnedBoard = new int[6][7];

        for (int i = 0; i < 6; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                returnedBoard[i][j] = board[i][j];
            }
        }

        return returnedBoard;
    }

    /* vide le tableau */


    public void resetBoard()
    {
        for (int i = 0; i < 6; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                board[i][j] = 0;
            }
        }
    }


  /* vérifie si le tableau est plein */

    public boolean isFull()
    {
        for (int i = 0; i < 6; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                if (board[i][j] == 0)
                {
                    return false;
                }
            }
        }

        return true;
    }


    /* joue dans la colonne i */

    public void playColumn(int rowNumber, int columnNumber, int playerColor)
    {
            board[rowNumber][columnNumber] = playerColor;
    }


    /* vérifie si la colonne a une place de libre */

    public boolean isColumnFull(int columnNumber)
    {
        for (int i = 0; i < 6; i++)
        {
            if (board[i][columnNumber] == 0)
            {
                return false;
            }
        }

        return true;
    }


 /* donne le premier emplacement libre de la colonne */

    public int firstEmptySlot(int columnNumber)
    {
        for (int i = 5; i >= 0; i--)
        {
            if (board[i][columnNumber] == 0)
            {
                return i;
            }
        }

        return -1;

    }

  public boolean isSlotFilled(int rowNumber, int columnNumber)
  {
      if (board[rowNumber][columnNumber] != 0)
      {
          return true;

      }

      return false;
  }



    public boolean isSlotBlack(int rowNumber, int columnNumber)
    {
        if (board[rowNumber][columnNumber] == 1)
        {
            return true;

        }

        return false;
    }


    public boolean isSlotWhite(int rowNumber, int columnNumber)
    {
        if (board[rowNumber][columnNumber] == 2)
        {
            return true;

        }

        return false;
    }


    public void copyBoard(Board copyBoard)
    {
        for (int i = 0; i < 6; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                this.board[i][j] = copyBoard.board[i][j];
            }
        }

    }


}

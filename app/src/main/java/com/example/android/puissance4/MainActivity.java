package com.example.android.puissance4;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    
    Game game;

    Player blackPlayer;
    Player whitePlayer;

    View blackTurnView;
    View whiteTurnView;

    int CPU_move = -1;

    long startTime;
    long endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       
        game = new Game();
        blackPlayer = new Human(game,true);
        whitePlayer = new CPU_Monte_Carlo(game, false);

        blackTurnView = findViewById(R.id.black_turn);
        whiteTurnView = findViewById(R.id.white_turn);

        highlightPlayer(game.blacksTurn);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void clickOnColumn(View view)
    {
        /* Cherche la column sur laquelle on a cliqué et retourne son tag qui est le numéro de la colonne*/

        ImageView columnView = (ImageView) findViewById(view.getId());
        String columnTagString = (String) columnView.getTag();
        int columnNumber = Integer.parseInt(columnTagString.substring(columnTagString.length() - 1, columnTagString.length()));


        startTime = System.currentTimeMillis();

        /* Si le joueur est humain, on joue dans la colonne */

        if (game.blacksTurn && game.isActive() && blackPlayer.isHuman())
        {
            playColumn(columnNumber);
            game.blacksTurn = !game.blacksTurn;

        }
        else if (!game.blacksTurn && game.isActive() && whitePlayer.isHuman()) {
            playColumn(columnNumber);
            game.blacksTurn = !game.blacksTurn;
        }


        highlightWinner();

        /* Ca marchait !
        if (game.blacksTurn && game.isActive() && !blackPlayer.isHuman())
        {

            playColumn(blackPlayer.move());
            game.blacksTurn = !game.blacksTurn;
        }
        else if (!game.blacksTurn && game.isActive() && !whitePlayer.isHuman())
        {
            playColumn(whitePlayer.move());
            game.blacksTurn = !game.blacksTurn;
        }
*/

        CalculMonteCarlo CPU_coup = new CalculMonteCarlo();
        CPU_coup.execute();




/* marchait aussi

        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Temps de calcul : " + endTime);

        highlightPlayer(game.blacksTurn);
        highlightWinner();

*/



    }






    public void playColumn(int columnNumber)
    {


        int emptyRow = game.board.firstEmptySlot(columnNumber);

        /* si la ligne est libre ET le jeu en cours ET met un jeton de la couleur game.blacksTurn

         */

        if (emptyRow != -1 && game.isActive())
        {
            String imageID = "disc" + emptyRow + columnNumber;

            int resID = getResources().getIdentifier(imageID, "id", getPackageName());
            ImageView discView = (ImageView) findViewById(resID);

            if (game.blacksTurn)
            {
                discView.setImageResource(R.drawable.red_disc);
                discView.setVisibility(View.VISIBLE);
                game.board.playColumn(emptyRow, columnNumber, 1);

            }

            if (!game.blacksTurn)
            {
                discView.setImageResource(R.drawable.yellow_disc);
                discView.setVisibility(View.VISIBLE);
                game.board.playColumn(emptyRow, columnNumber, 2);


            }

        }





    }




    public void highlightWinner()
    {
        if (game.isWon() != 0)
        {
            int [][] highlightedBoard = game.highlightBoard.getBoard();
            for (int i = 0; i < 6; i++)
            {
                for (int j = 0; j < 7; j++)
                {

                    if (highlightedBoard[i][j] == 1)
                    {

                        String imageID = "ring" + i + j;

                        int resID = getResources().getIdentifier(imageID, "id", getPackageName());
                        ImageView ringView = (ImageView) findViewById(resID);

                        ringView.setImageResource(R.drawable.green_ring);

                    }

                }
            }

        }

    }

    public void highlightPlayer(boolean turn)
        {
        if (turn)
        {
            whiteTurnView.setAlpha(0f);

            blackTurnView.setBackgroundColor(getResources().getColor(R.color.colorRed));
            blackTurnView.animate().alpha(1f).setDuration(700);
        }
            if (!turn)
            {
               blackTurnView.setAlpha(0f);

                whiteTurnView.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                whiteTurnView.animate().alpha(1f).setDuration(700);
            }



        }

    /* remet le jeu à l'état initial */

    public void reset(View view)
    {
        game.blacksTurn = true;
        game = new Game();
        blackPlayer = new Human(game,true);
        whitePlayer = new CPU_Monte_Carlo(game, false);

        highlightPlayer(game.blacksTurn);

        /* rend invisible tous les disques et tous les rings en blancs*/

        for (int row = 0; row < 6; row++)
        {
            for (int column = 0; column < 7; column++)
            {
                String discImageID = "disc" + row + column;
                String ringImageID = "ring" + row + column;

                int resDiscID = getResources().getIdentifier(discImageID, "id", getPackageName());
                ImageView discView = (ImageView) findViewById(resDiscID);

                int resRingID = getResources().getIdentifier(ringImageID, "id", getPackageName());
                ImageView ringView = (ImageView) findViewById(resRingID);

                discView.setVisibility(View.INVISIBLE);
                ringView.setImageResource(R.drawable.white_ring);

            }

        }

        /* fait touner le reset */
        final ImageView myImage = (ImageView) findViewById(R.id.reset);
        final Animation myRotation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotator);
        myImage.startAnimation(myRotation);


    }

    /* Test Asynctask */

    private class CalculMonteCarlo extends AsyncTask<Void, Void, Integer>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // Toast.makeText(getApplicationContext(), "Début du calcul", Toast.LENGTH_LONG).show();
        }


        @Override
        protected Integer doInBackground(Void... arg0) {

            int move = -1;

            if (game.blacksTurn && game.isActive() && !blackPlayer.isHuman())
            {

                move = blackPlayer.move();

            }
            else if (!game.blacksTurn && game.isActive() && !whitePlayer.isHuman())
            {
                move = whitePlayer.move();

            }

            return move;
        }

@Override
        protected void onPostExecute(Integer result) {

            Toast.makeText(getApplicationContext(), "Le traitement asynchrone est terminé : " + result, Toast.LENGTH_LONG).show();

            CPU_move = result;

    highlightPlayer(game.blacksTurn);
    if (game.blacksTurn && game.isActive() && !blackPlayer.isHuman())
    {

        playColumn(CPU_move);
        game.blacksTurn = !game.blacksTurn;
    }
    else if (!game.blacksTurn && game.isActive() && !whitePlayer.isHuman())
    {
        playColumn(CPU_move);
        game.blacksTurn = !game.blacksTurn;
    }

    endTime = System.currentTimeMillis() - startTime;
    System.out.println("Temps de calcul : " + endTime);

    highlightPlayer(game.blacksTurn);
    highlightWinner();


}
    }




}

package ca.cmpt276.project.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import ca.cmpt276.project.tictactoe.model.Cell;
import ca.cmpt276.project.tictactoe.model.TicTacToeGame;

public class TicTacToeScreen extends AppCompatActivity {
    TableLayout grid;
    Button gridButton[][];

    int num_size = 3;
    TicTacToeGame ticTacGame;

    boolean playerTurn = true;

    //Make intent for this activity
    public static Intent makeIntent(Context context) {
        return new Intent(context, TicTacToeScreen.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe_screen);

        ticTacGame = new TicTacToeGame(num_size);
        gridButton = new Button[num_size][num_size];
        populateGrid();
    }

    private void populateGrid(){
        grid = findViewById(R.id.grid);

        //Add grid layout with buttons
        for (int row = 0; row < num_size; row++){
            TableRow gridRow = new TableRow(this);
            gridRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            ));
            grid.addView(gridRow);

            for (int col = 0; col < num_size; col++){
                final int final_col = col;
                final int final_row = row;

                //Set up button
                Button button = new Button(this);
                button.setPadding(0,0,0,0);
                button.setOnClickListener(view -> gridOnClick(final_row,final_col));
                button.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);

                gridButton[row][col] = button;

                /*
                button.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        button.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        Cell cell = ticTacGame.getCell(final_row,final_col);
                        //lockButtonSizes();

                        //Set button images and settings after button is fully created
                        if(!cell.isHidden()){
                            if(cell.isInfected()){
                                setImageToButton(final_row,final_col,R.drawable.infected);
                                button.setClickable(true);

                                if(cell.isScanShown()){
                                    button.setClickable(false);
                                }
                            }

                            else{
                                setImageToButton(final_row,final_col,R.drawable.healthy);
                                button.setClickable(false);
                            }
                        }
                        else{
                            setImageToButton(final_row,final_col,R.drawable.unknown);
                        }
                        updateScan();
                    }
                });
                 */
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f
                ));

                gridRow.addView(button);
            }
        }

        AiMove();

    }

    private void gridOnClick(int row, int col) {
        Button button = gridButton[row][col];
        lockButtonSizes();

        if (ticTacGame.hasSymbol(row, col) || ticTacGame.isOver()) return;

        //Set the symbol to the corresponding player
        if(playerTurn){
            ticTacGame.setSymbol(row, col, Cell.Symbol.X);
            button.setText("X");
        }

        else{
            ticTacGame.setSymbol(row, col, Cell.Symbol.O);
            button.setText("O");
        }

        //Change player's turn
        //playerTurn = !playerTurn;

        if (!checkGameOver()){
            AiMove();
            checkGameOver();
        }

    }

    public boolean checkGameOver(){
        //Get the winner's symbol if available
        Cell.Symbol playerSym;
        playerSym = ticTacGame.checkWinner();

        if (playerSym!= null){
            ticTacGame.setGameOver();
            Toast.makeText(this, playerSym.toString() + " has won", Toast.LENGTH_SHORT).show();
            return true;
        }

        if(ticTacGame.isFull()){
            ticTacGame.setGameOver();
            Toast.makeText(this, "It's a tie!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    private void lockButtonSizes() {
        for (int row = 0; row < num_size; row++) {
            for (int col = 0; col < num_size; col++) {
                Button button = gridButton[row][col];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }

    //Set and scale images to the buttons
    private void setImageToButton(int row, int col,  int rid){
        Button button = gridButton[row][col];
        int newWidth = button.getWidth();
        int newHeight = button.getHeight();

        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), rid);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);

        Resources resource = getResources();
        button.setBackground(new BitmapDrawable(resource, scaledBitmap));

    }

    private void AiMove(){
        int bestScore = -99999;
        int bestMove_X = 0;
        int bestMove_Y = 0;

        for (int i = 0; i < num_size; i++){
            for (int j = 0; j < num_size; j++){
                //Check if the spot is available
                if (!ticTacGame.hasSymbol(i,j)){
                    ticTacGame.setSymbol(i,j, Cell.Symbol.O);

                    int score = minimax(ticTacGame, 0, false);
                    ticTacGame.setSymbol(i,j, Cell.Symbol.Empty);

                    if (score > bestScore){
                        bestScore = score;
                        bestMove_X = i;
                        bestMove_Y = j;
                    }
                }
            }
        }

        Log.i("cc", String.valueOf(bestMove_X));
        Log.i("cc", String.valueOf(bestMove_Y));
        ticTacGame.setSymbol(bestMove_X,bestMove_Y, Cell.Symbol.O);
        gridButton[bestMove_X][bestMove_Y].setText("O");
        playerTurn = true;

    }

    private int minimax(TicTacToeGame board, int depth, boolean isMaximizing){

        //Check terminal node
        Cell.Symbol symbol = board.checkWinner();
        if (symbol != null){
            if (symbol == Cell.Symbol.X){
                return -1;
            }
            if (symbol == Cell.Symbol.O){
                return 1;
            }
        }

        if(board.isFull()){
            return 0;
        }

        //Check if it is A.I turn (maximizing)
        if (isMaximizing){
            int bestScore = -100000;
            for (int i = 0 ; i < num_size; i++){
                for (int j = 0; j < num_size; j++){
                    //Check if spot is available
                    if (!ticTacGame.hasSymbol(i,j)){
                        ticTacGame.setSymbol(i,j, Cell.Symbol.O);
                        int score = minimax(ticTacGame, depth + 1, false);
                        ticTacGame.setSymbol(i,j, Cell.Symbol.Empty);

                        bestScore = max(score, bestScore);
                    }
                }
            }
            return bestScore;
        }

        //Check if it is player's turn (minimizing)
        else{
            int bestScore = 100000;
            for (int i = 0 ; i < num_size; i++){
                for (int j = 0; j < num_size; j++){
                    //Check if spot is available
                    if (!ticTacGame.hasSymbol(i,j)){
                        ticTacGame.setSymbol(i,j, Cell.Symbol.X);

                        int score = minimax(ticTacGame, depth + 1, true);
                        ticTacGame.setSymbol(i,j, Cell.Symbol.Empty);

                        bestScore = min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }

    }

    private int max(int x, int y){
        if (x > y){
            return x;
        }
        return y;
    }

    private int min(int x, int y){
        if (x < y){
            return x;
        }
        return  y;
    }
}
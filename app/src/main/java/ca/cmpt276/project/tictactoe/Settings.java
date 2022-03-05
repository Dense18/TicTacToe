package ca.cmpt276.project.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

//Activity that handles custom options for the game
public class Settings extends AppCompatActivity {

    private static final String BOARD_SETTING = "Board Settings";
    private static final String NUM_SIZE = "Size of board";

    private static final String MODE_SETTING = "Mode Settings";
    private static final String MODE_TYPE = "Mode Type";

    //Make intent for this activity
    public static Intent makeIntent(Context context) {
        return new Intent(context, Settings.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setUpRadioButton();
    }

    private void setUpRadioButton(){
        int[] size_list = getResources().getIntArray(R.array.board_size);
        int[] mode_list = getResources().getIntArray(R.array.game_mode);

        RadioButton board_buttons[] = new RadioButton[size_list.length];
        RadioButton mode_buttons[] = new RadioButton[mode_list.length];

        RadioGroup board_size= findViewById(R.id.board_size_group);
        RadioGroup game_mode= findViewById(R.id.game_mode_group);

        //Create radio buttons for game mode
        for (int i= 0; i < mode_list.length; i++) {
            mode_buttons[i] = new RadioButton(this);
            RadioButton button = mode_buttons[i];
            int mode = mode_list[i];

            //Set up radio buttons
            if (mode == TicTacToeScreen.GameMode.TWO_PLAYER.getValue()) {
                button.setText(TicTacToeScreen.GameMode.TWO_PLAYER.toString());
            }

            if (mode == TicTacToeScreen.GameMode.COMPUTER.getValue()) {
                button.setText(TicTacToeScreen.GameMode.COMPUTER.toString());
            }

            button.setOnClickListener(view -> saveModeSetting(mode));
            game_mode.addView(button);

            if (mode == getMode(this)) {
                button.setChecked(true);
            }
        }

        //Create radio buttons for board size
        for (int i = 0; i < size_list.length; i++) {
            board_buttons[i] = new RadioButton(this);
            RadioButton button = board_buttons[i];
            int size = size_list[i];

            //Set up radio buttons
            button.setText(getString(R.string.board_size, size));
            button.setOnClickListener(view -> {
                if (size != 3){
                    mode_buttons[1].setVisibility(View.GONE);
                    mode_buttons[0].setChecked(true);
                    saveModeSetting(TicTacToeScreen.GameMode.TWO_PLAYER.getValue());
                }
                else{
                    mode_buttons[1].setVisibility(View.VISIBLE);
                }
                saveBoardSetting(size);
            });

            board_size.addView(button);

            if (size == getSize(this)) {
                if (size != 3){
                    mode_buttons[1].setVisibility(View.GONE);
                }
                button.setChecked(true);
            }
        }

    }

    ///--------------------------Functions to handle game settings-------------------------///

    private void saveBoardSetting(int size){
        SharedPreferences pref =this.getSharedPreferences(BOARD_SETTING, MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(NUM_SIZE, size);
        editor.apply();
    }

    private void saveModeSetting(int mode){
        SharedPreferences pref =this.getSharedPreferences(MODE_SETTING, MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(MODE_TYPE, mode);
        editor.apply();
    }

    public static int getSize(Context context){
        SharedPreferences pref = context.getSharedPreferences(BOARD_SETTING, MODE_PRIVATE);
        return pref.getInt(NUM_SIZE, context.getResources().getInteger(R.integer.default_size));
    }

    public static int getMode(Context context){
        SharedPreferences pref = context.getSharedPreferences(MODE_SETTING, MODE_PRIVATE);
        return pref.getInt(MODE_TYPE, context.getResources().getInteger(R.integer.default_mode));
    }
}
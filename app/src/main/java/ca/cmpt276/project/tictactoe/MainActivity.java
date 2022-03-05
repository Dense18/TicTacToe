package ca.cmpt276.project.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

//Screen that appears when app is initially launched
public class MainActivity extends AppCompatActivity {

    Button start_button;
    Button settings_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpButtons();
    }

    private void setUpButtons(){
        start_button = findViewById(R.id.start_button);
        start_button.setOnClickListener(view -> {
            Intent i = TicTacToeScreen.makeIntent(MainActivity.this);
            startActivity(i);
        });

        settings_button = findViewById(R.id.settings_button);
        settings_button.setOnClickListener(view -> {
            Intent i = Settings.makeIntent(MainActivity.this);
            startActivity(i);
        });
    }
}
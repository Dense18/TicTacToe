package ca.cmpt276.project.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button start_button;

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
    }
}
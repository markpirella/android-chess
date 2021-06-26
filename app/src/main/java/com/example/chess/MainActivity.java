package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // link all buttons
        final Button play_button = (Button) findViewById(R.id.button_play_game);
        final Button watch_button = (Button) findViewById(R.id.button_watch_game);

        // set button listeners
        play_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Go to PlayGame
                Intent intent = new Intent(v.getContext(), PlayGame.class);
                startActivity(intent);
            }
        });

        watch_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Go to WatchGame
                Intent intent = new Intent(v.getContext(), WatchGame.class);
                startActivity(intent);

            }
        });
    }



}
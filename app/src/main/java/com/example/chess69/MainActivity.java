package com.example.chess69;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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

            }
        });
    }



}
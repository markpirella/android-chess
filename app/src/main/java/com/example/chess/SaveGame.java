package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SaveGame extends AppCompatActivity {

    File tempFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_game);

        tempFile = new File(getApplicationInfo().dataDir + File.separator + "SavedGames.dat");
        if(!tempFile.exists()) {
            System.out.println("nah doesnt exist");
            try {
                tempFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        final EditText title = findViewById(R.id.input_game_title);

        // link buttons here
        final Button save_button = (Button) findViewById(R.id.save_game_button);
        final Button cancel_button = (Button) findViewById(R.id.cancel_save_game_button);

        // set up button listeners here
        save_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String inputTitle = title.getText().toString();
                if(inputTitle.equals("")){
                    Toast.makeText(SaveGame.this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    System.out.println(title.getText().toString().toUpperCase());
                    if(tempFile.exists()){
                        System.out.println("yeah it exists");
                        SavedGames savedGamesObj = new SavedGames();
                        try {
                            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(getApplicationInfo().dataDir + File.separator + "SavedGames.dat"));
                            savedGamesObj = (SavedGames) ois.readObject();
                            ois.close();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        if(savedGamesObj == null){
                            savedGamesObj = new SavedGames();
                        }
                        // add new game!

                        ArrayList<String> moves = (ArrayList<String>) getIntent().getSerializableExtra("moves");
                        System.out.println(savedGamesObj);
                        System.out.println(moves);
                        System.out.println(inputTitle);
                        savedGamesObj.addNewGame(moves, inputTitle);
                        try {
                            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getApplicationInfo().dataDir + File.separator + "SavedGames.dat"));
                            oos.writeObject(savedGamesObj);
                            oos.close();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        for(int i = 0; i < savedGamesObj.games.size(); i++){
                            System.out.println(savedGamesObj.games.get(i).title + " -> " + savedGamesObj.games.get(i).moves + " / " + savedGamesObj.games.get(i).creationDate);
                        }
                        finish();
                    }
                }
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}
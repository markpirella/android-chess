package com.example.chess69;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_game);

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
                    // see if SavedGames.dat exists
                    File tempFile = new File(getApplicationInfo().dataDir + File.separator + "SavedGames.dat");
                    if(!tempFile.exists()){
                        System.out.println("nah doesnt exist");
                        try {
                            tempFile.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getApplicationInfo().dataDir + File.separator + "SavedGames.dat"));
                            SavedGames savedGamesObj = new SavedGames();
                            oos.writeObject(savedGamesObj);
                            oos.close();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        System.out.println("yeah it exists");
                        SavedGames savedGamesObj = null;
                        try {
                            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(getApplicationInfo().dataDir + File.separator + "SavedGames.dat"));
                            savedGamesObj = (SavedGames) ois.readObject();
                            ois.close();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        //savedGamesObj.addNewGame(inputTitle, moves);
                        try {
                            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getApplicationInfo().dataDir + File.separator + "SavedGames.dat"));
                            oos.writeObject(savedGamesObj);
                            oos.close();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
    }
}
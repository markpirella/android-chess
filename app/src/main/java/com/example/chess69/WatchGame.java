package com.example.chess69;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class WatchGame extends AppCompatActivity {

    private ListView gameListView;
    //private String[] gameNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_game);

        SavedGames savedGamesObj;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(getApplicationInfo().dataDir + File.separator + "SavedGames.dat"));
            savedGamesObj = (SavedGames) ois.readObject();
            ois.close();

            System.out.println(savedGamesObj.games.size());

            GameListAdapter adapter = new GameListAdapter(this, savedGamesObj.games);
            gameListView = (ListView) findViewById(R.id.game_list);
            gameListView.setAdapter(adapter);

            gameListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(view.getContext(), WatchSavedGame.class);
                }
            });
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
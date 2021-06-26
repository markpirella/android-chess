package com.example.chess;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class WatchGame extends AppCompatActivity {

    private ListView gameListView;
    private Switch date_title_switch;
    private GameListAdapter adapter;
    private SavedGames savedGamesObj;
    //private String[] gameNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_game);

        date_title_switch = (Switch) findViewById(R.id.switch1);

        date_title_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                System.out.println("SWITCH TOGGLED");
                if(isChecked){
                    System.out.println("SORTING BY DATE");
                    sortByDate();
                }else{
                    System.out.println("SORTING BY TITLE");
                    sortByTitle();
                }
                for(SavedGame s : savedGamesObj.games){
                    System.out.println(s.title);
                }
                adapter.notifyDataSetChanged();
            }
        });

        // set up back arrow button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(getApplicationInfo().dataDir + File.separator + "SavedGames.dat"));
            savedGamesObj = (SavedGames) ois.readObject();
            ois.close();

            sortByTitle();

            System.out.println(savedGamesObj.games.size());

            adapter = new GameListAdapter(this, savedGamesObj.games);
            gameListView = (ListView) findViewById(R.id.game_list);
            gameListView.setAdapter(adapter);

            gameListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(view.getContext(), WatchSavedGame.class);
                    intent.putExtra("moves", savedGamesObj.games.get(position));
                    startActivity(intent);
                }
            });
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    private void sortByDate(){
        ArrayList<SavedGame> games = savedGamesObj.games;
        for (int j = 1; j < games.size(); j++) {
            SavedGame current = games.get(j);
            int i = j-1;
            while ((i > -1) && ((games.get(i).creationDate.compareTo(current.creationDate)) > 0)) {
                games.set(i+1, games.get(i));
                i--;
            }
            games.set(i+1, current);
        }
    }

    private void sortByTitle(){
        ArrayList<SavedGame> games = savedGamesObj.games;
        for (int j = 1; j < games.size(); j++) {
            SavedGame current = games.get(j);
            int i = j-1;
            while ((i > -1) && ((games.get(i).title.compareTo(current.title)) > 0)) {
                games.set(i+1, games.get(i));
                i--;
            }
            games.set(i+1, current);
        }
    }


}
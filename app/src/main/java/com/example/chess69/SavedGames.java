package com.example.chess69;

import java.io.Serializable;
import java.util.ArrayList;

public class SavedGames implements Serializable {

    ArrayList<ArrayList<String>> games;
    ArrayList<String> titles;

    public SavedGames(){
        games = new ArrayList<ArrayList<String>>();
        titles = new ArrayList<String>();
    }

    public void addNewGame(String title, ArrayList<String> game){
        games.add(game);
        titles.add(title);
    }

}

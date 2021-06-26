package com.example.chess;

import java.io.Serializable;
import java.util.ArrayList;

public class SavedGames implements Serializable {

    ArrayList<SavedGame> games;

    public SavedGames(){
        games = new ArrayList<SavedGame>();
    }

    public void addNewGame(ArrayList<String> moves, String title){
        games.add(new SavedGame(moves, title));
    }

}

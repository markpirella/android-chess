package com.example.chess;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class SavedGame implements Serializable {

    ArrayList<String> moves;
    String title;
    Date creationDate;

    public SavedGame(ArrayList<String> moves, String title){
        this.moves = moves;
        this.title = title;
        creationDate = new Date(System.currentTimeMillis());
    }

    public ArrayList<String> getMoves(){
        return moves;
    }

}

package com.example.chess;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GameListAdapter extends ArrayAdapter<SavedGame> {
    private final Activity context;
    //private final ArrayList<String> titles;
    private final ArrayList<SavedGame> savedGames;

    public GameListAdapter(Activity context, ArrayList<SavedGame> games) {
        super(context, R.layout.game_list_entry, games);
        this.context = context;

        //this.titles = new ArrayList<String>();
        /*
        this.savedGames = new ArrayList<SavedGame>();

        for (SavedGame savedGame : games) {
            this.savedGames.add(savedGame);
        }

         */
        savedGames = games;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.game_list_entry, null, true);

        TextView gameTitle = (TextView) rowView.findViewById(R.id.game_entry);
        gameTitle.setText(savedGames.get(position).title + " - " + savedGames.get(position).creationDate);

        return rowView;
    }

}
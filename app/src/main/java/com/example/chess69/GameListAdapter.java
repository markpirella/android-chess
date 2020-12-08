package com.example.chess69;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.chess69.MainActivity;

import java.util.ArrayList;

public class GameListAdapter extends ArrayAdapter<SavedGame> {
    private final Activity context;
    private final ArrayList<String> titles;
    private final ArrayList<SavedGame> savedGames;

    public GameListAdapter(Activity context, ArrayList<SavedGame> games) {
        super(context, R.layout.game_list_entry, games);
        this.context = context;
        this.titles = new ArrayList<String>();
        this.savedGames = new ArrayList<SavedGame>();
        for (SavedGame savedGame : games) {
            this.savedGames.add(savedGame);
        }
        for (SavedGame s : games) {
            titles.add(s.title + " - " + s.creationDate);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.game_list_entry, null, true);

        TextView gameTitle = (TextView) rowView.findViewById(R.id.game_entry);
        gameTitle.setText(titles.get(position));

        return rowView;
    }

}
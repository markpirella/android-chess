package com.example.chess69;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    GridView gridView;

    int count = 0;

    public static final String[] square_colors = {"#000000", "#FFFFFF", "#000000", "#FFFFFF", "#000000", "#FFFFFF", "#000000", "#FFFFFF",
            "#000000", "#FFFFFF", "#000000", "#FFFFFF", "#000000", "#FFFFFF", "#000000", "#FFFFFF",
            "#000000", "#FFFFFF", "#000000", "#FFFFFF", "#000000", "#FFFFFF", "#000000", "#FFFFFF",
            "#000000", "#FFFFFF", "#000000", "#FFFFFF", "#000000", "#FFFFFF", "#000000", "#FFFFFF",
            "#000000", "#FFFFFF", "#000000", "#FFFFFF", "#000000", "#FFFFFF", "#000000", "#FFFFFF",
            "#000000", "#FFFFFF", "#000000", "#FFFFFF", "#000000", "#FFFFFF", "#000000", "#FFFFFF",
            "#000000", "#FFFFFF", "#000000", "#FFFFFF", "#000000", "#FFFFFF", "#000000", "#FFFFFF",
            "#000000", "#FFFFFF", "#000000", "#FFFFFF", "#000000", "#FFFFFF", "#000000", "#FFFFFF"};

    public static final ImageView[] squares = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.chessboard);
        //GridView gridView;

        //int count = 0;
        /*
        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, square_colors) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                int color = 0x000000; // black
                if (count%2==0) {
                    color = 0xffffff; // white
                }

                view.setBackgroundColor(color);
                count++;
                return view;
            }
        };

         */


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, square_colors);
        gridView.setAdapter(adapter);

        for(int i = 0; i < gridView.getCount(); i++) {
            View tv = (View) gridView.getChildAt(i);
            if(i%2==0 && tv != null) {
                tv.setBackgroundColor(Color.BLACK);
            }
        }


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                Toast.makeText(MainActivity.this, "You Clicked On " +square_colors[+ position], Toast.LENGTH_SHORT).show();

            }
        });

    }
}
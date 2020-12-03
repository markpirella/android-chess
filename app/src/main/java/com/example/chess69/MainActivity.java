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

    public static final String[] square_nums =
            {"a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
            "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
            "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
            "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
            "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
            "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
            "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
            "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"};

    public static final ImageView[] squares = {};

    // create board
    static Piece[][] board = new Piece[8][8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startGame();
        Piece[] pieces = new Piece[64];
        for(int  i = 0; i < 64; i++){
            pieces[i] = null;
        }
        int count = 0;
        for(int i = 7; i >= 0; i--){
            for(int j = 0; j < 8; j++){
                pieces[count] = board[j][i];
                count++;
            }
        }

        GridView chessboard_gridview = (GridView)findViewById(R.id.chessboard);
        SquaresAdapter squaresAdapter = new SquaresAdapter(this, pieces);
        chessboard_gridview.setAdapter(squaresAdapter);

        //System.out.println("******INDEX 2: " + chessboard_gridview.getChildAt(2).toString());

        /*
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

    /*
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, square_colors);
        gridView.setAdapter(adapter);

        for(int i = 0; i < gridView.getCount(); i++) {
            View tv = (View) gridView.getChildAt(i);
            if(i%2==0 && tv != null) {
                tv.setBackgroundColor(Color.BLACK);
            }
        }
        */


        chessboard_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                Toast.makeText(MainActivity.this, "You Clicked On " +square_nums[+ position], Toast.LENGTH_SHORT).show();
                /*
                System.out.println("***VIEW: " + view.toString());
                System.out.println("pieces[20] before: "+pieces[20]);
                pieces[20] = new Pawn("white");
                squaresAdapter.notifyDataSetChanged();
                System.out.println("pieces[20] after: "+pieces[20]);
                 */
            }
        });

        // everything set up, now implement gameplay

    }

    /**
     * Method used to populate the "board" double matrix with
     * appropriate chess pieces, representing a real-life chess board
     */
    public static void startGame() {
        board[0][0] = new Rook("white");
        board[1][0] = new Knight("white");
        board[2][0] = new Bishop("white");
        board[3][0] = new Queen("white");
        board[4][0] = new King("white");
        board[5][0] = new Bishop("white");
        board[6][0] = new Knight("white");
        board[7][0] = new Rook("white");
        for(int i = 0; i < 8; i++) {
            board[i][1] = new Pawn("white");
        }
        for(int i = 0; i < 8; i++) {
            board[i][6] = new Pawn("black");
        }
        board[0][7] = new Rook("black");
        board[1][7] = new Knight("black");
        board[2][7] = new Bishop("black");
        board[3][7] = new Queen("black");
        board[4][7] = new King("black");
        board[5][7] = new Bishop("black");
        board[6][7] = new Knight("black");
        board[7][7] = new Rook("black");

        // set rest of board to null
        for(int i = 0; i < 8; i++){
            for(int j = 2; j < 6; j++){
                board[i][j] = null;
            }
        }
    }
}
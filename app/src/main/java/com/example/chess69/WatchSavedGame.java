package com.example.chess69;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WatchSavedGame extends AppCompatActivity {

    static GridView chessboard_gridview;
    public static SquaresAdapter squaresAdapter;
    static Piece[][] board = new Piece[8][8];
    static Piece[] pieces = new Piece[64];
    ArrayList<String> moves;
    int move_num = 0;
    static int firstSquareSelection = -1;
    static int secondSquareSelection = -1;
    static String turn;
    static boolean[][] enpassantMatrix = new boolean[8][8];
    String currentColor;
    String move;
    public static final String[] GUI_square_FileRanks =
            {"a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
                    "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
                    "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
                    "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
                    "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
                    "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
                    "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
                    "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_saved_game);

        // set up back arrow button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SavedGame savedGame = (SavedGame) getIntent().getSerializableExtra("moves");
        moves = savedGame.moves;

        final Button next_button = (Button) findViewById(R.id.next_move_button);

        next_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                if(move_num < moves.size() - 1) {
                    System.out.println("MOVE: " + moves.get(move_num));
                    if(moves.get(move_num).equals("END")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(WatchSavedGame.this);
                        builder.setTitle(moves.get(move_num + 1) + " What would you like to do now:");
                        final String[] options = {"Go back to saved games list", "Watch this game again"};
                        builder.setCancelable(false);
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // the user clicked on options[which]
                                if(which == 0){
                                    finish();
                                }else{
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                }
                            }
                        });
                        builder.show();
                    }else{
                        move = moves.get(move_num);
                        executeMoveAndAllChecks();
                    }
                    move_num++;
                }else{
                    Toast.makeText(WatchSavedGame.this, "No more moves ", Toast.LENGTH_SHORT).show();
                }

            }
        });

        startGame();
        chessboard_gridview = (GridView)findViewById(R.id.watch_chessboard);
        squaresAdapter = new SquaresAdapter(this, pieces);
        chessboard_gridview.setAdapter(squaresAdapter);
    }

    public static void startGame() {
        turn = "white";
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

        pieces = new Piece[64];

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
    }

    private void executeMoveAndAllChecks(){

        int startFile = (int)(move.charAt(0)) - 97;
        int startRank = (int)(move.charAt(1)) - 49;
        int endFile = (int)(move.charAt(3)) - 97;
        int endRank = (int)(move.charAt(4)) - 49;
        firstSquareSelection = ((7 - startRank) * 8) + startFile;
        secondSquareSelection = ((7 - endRank) * 8) + endFile;
        System.out.println("first pos: "+firstSquareSelection+" second pos: "+secondSquareSelection);

        move(move, secondSquareSelection);

        // revert all enpassant values back to false for the player who DIDN'T just make a move
        for(int a = 0; a < 8; a++) {
            for(int b = 0; b < 8; b++) {
                if( board[a][b] != null && !(board[a][b].getColor().equals(turn)) ) {
                    enpassantMatrix[a][b] = false;
                }
            }
        }

        boolean isCheck = false;
        boolean isCheckmate = false;

        if(firstSquareSelection < 0 || secondSquareSelection < 0){
            return;
        }
        pieces[secondSquareSelection] = pieces[firstSquareSelection];
        pieces[firstSquareSelection] = null;
        squaresAdapter.notifyDataSetChanged();

        // change turn
        if(turn.equals("white")){
            turn = "black";
        }else{
            turn = "white";
        }

        // set hasMoved field of moved piece
        if(pieces[secondSquareSelection] != null){
            pieces[secondSquareSelection].incNumMoves();
        }

    }

    public void move(String move, int endPosition) { // takes move as parameter in form e2 e3
        //System.out.println("USER CHOSE: " + promotionSelection);
        int startFile = (int)(move.charAt(0)) - 97;
        int startRank = (int)(move.charAt(1)) - 49;
        int endFile = (int)(move.charAt(3)) - 97;
        int endRank = (int)(move.charAt(4)) - 49;

        //int endPosition = (endRank * 8) + endFile;

        //System.out.println("moving "+startFile +","+startRank+" to "+endFile+","+endRank);

        System.out.println("accessing piece at FileRank: " + startFile+","+startRank);

        if(board[startFile][startRank] == null){
            return;
        }

        currentColor = board[startFile][startRank].getColor();

        // set the hasMoved field of the current piece to true
        //board[startFile][startRank].incNumMoves();

        // takes care of castling
        if (board[startFile][startRank].getType().equals("King") && Math.abs(endFile-startFile) == 2) {
            if (endFile-startFile == 2) {
                board[7][startRank].incNumMoves();
                board[5][startRank] = board[7][startRank];
                board[7][startRank] = null;
            } else {
                board[0][startRank].incNumMoves();
                board[3][startRank] = board[0][startRank];
                board[0][startRank] = null;
            }
            writeBoardToPieces();
            return;
        }

        // place piece in new location and remove it from old location
        board[endFile][endRank] = board[startFile][startRank];
        board[startFile][startRank] = null;

    }

    private void writeBoardToPieces(){
        int count = 0;
        for(int i = 7; i >= 0; i--){
            for(int j = 0; j < 8; j++){
                pieces[count] = board[j][i];
                count++;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
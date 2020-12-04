package com.example.chess69;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView gridView;

    int count = 0;

    String move;

    int numOfMoves = 0;
    static String turn = "white";
    boolean changeTurn = false;

    static String whiteKingLocation = "e1";
    static String blackKingLocation = "e8";

    String promotionSelection;

    /**
     * boolean value used to determine if a player's king was in check prior to the current move
     */
    static boolean inCheckBeforeMove = false;

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

    static Piece[] pieces;

    /**
     * a double matrix that holds boolean values for if a pawn is eligible to have the en passant move used to capture them
     */
    static boolean[][] enpassantMatrix = new boolean[8][8];

    // variables to first and second square selections, useful for moving chess pieces
    // -1 means no selection made
    int firstSquareSelection = -1;
    int secondSquareSelection = -1;

    TextView textview_turndisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startGame();
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

        GridView chessboard_gridview = (GridView)findViewById(R.id.chessboard);
        textview_turndisplay = (TextView)findViewById(R.id.turnDisplay);
        textview_turndisplay.setText("white's turn!");
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
                //Toast.makeText(MainActivity.this, "You Clicked On " +square_nums[+ position], Toast.LENGTH_SHORT).show();
                if(firstSquareSelection == -1){ // no first selection made, so set it
                    firstSquareSelection = position;
                }else{ // first selection already made, so fill in second selection and perform move if its legal
                    secondSquareSelection = position;

                    move = square_nums[firstSquareSelection] + " " + square_nums[secondSquareSelection];
                    System.out.println("given move: "+move);
                    if(!isMoveLegal(move, turn, firstSquareSelection)) {
                        firstSquareSelection = -1;
                        secondSquareSelection = -1;
                        Toast.makeText(MainActivity.this, "Illegal move, try again", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    move(move, secondSquareSelection);
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
                    if(pieces[position] != null){
                        pieces[position].setHasMoved(true);
                    }

                    printBoard();
                    textview_turndisplay.setText(turn + "'s turn!");

                    firstSquareSelection = -1;
                    secondSquareSelection = -1;



                }
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

    /*******************************************************************************************************************************************************
     * move()
     */

    /**
     * method used to execute a "move" of a piece on the board[][]
     * @param move given move to execute
     */
    public void move(String move, int endPosition) { // takes move as parameter in form e2 e3
        int startFile = (int)(move.charAt(0)) - 97;
        int startRank = (int)(move.charAt(1)) - 49;
        int endFile = (int)(move.charAt(3)) - 97;
        int endRank = (int)(move.charAt(4)) - 49;

        //int endPosition = (endRank * 8) + endFile;

        //System.out.println("moving "+startFile +","+startRank+" to "+endFile+","+endRank);

        String currentColor = board[startFile][startRank].getColor();

        // set the hasMoved field of the current piece to true
        //board[startFile][startRank].setHasMoved(true);

        // takes care of castling
        if (board[startFile][startRank].getType().equals("King") && Math.abs(endFile-startFile) == 2) {
            if (endFile-startFile == 2) {
                board[7][startRank].setHasMoved(true);
                board[5][startRank] = board[7][startRank];
                board[7][startRank] = null;
            } else {
                board[0][startRank].setHasMoved(true);
                board[3][startRank] = board[0][startRank];
                board[0][startRank] = null;
            }
        }

        // place piece in new location and remove it from old location
        board[endFile][endRank] = board[startFile][startRank];
        board[startFile][startRank] = null;

        String[] new_piece_options = {"Queen", "Rook", "Bishop", "Knight"};

        // take care of promotion
        if( (board[endFile][endRank].getType().equals("Pawn") && endRank == 0 && currentColor.equals("black") )
                || (board[endFile][endRank].getType().equals("Pawn") && endRank == 7 && currentColor.equals("white")) ) {
            promotionSelection = "Queen";

            // pop up dialog asking user which piece they'd like to upgrade to
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Pick a piece to promote to");
            builder.setItems(new_piece_options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // the user clicked on new_piece_options[which]
                    promotionSelection = new_piece_options[which];
                }
            });
            builder.show();

            System.out.println("USER CHOSE: " + promotionSelection);


            if(promotionSelection.equals("Queen")) { // either no promotion piece specified, or queen chosen - so promote to queen
                board[endFile][endRank] = new Queen(currentColor);
                board[endFile][endRank].setHasMoved(true);
            }else if(promotionSelection.equals("Rook")) { // rook chosen
                board[endFile][endRank] = new Rook(currentColor);
                board[endFile][endRank].setHasMoved(true);
            }else if(promotionSelection.equals("Bishop")) { // bishop chosen
                board[endFile][endRank] = new Bishop(currentColor);
                board[endFile][endRank].setHasMoved(true);
            }else if(promotionSelection.equals("Knight")) { // knight chosen
                board[endFile][endRank] = new Knight(currentColor);
                board[endFile][endRank].setHasMoved(true);
            }

            // make sure pieces array is up to date as well
            pieces[endPosition] = board[endFile][endRank];
        }



        // if king moves, update whiteKingLocation or blackKingLocation var
        if(board[endFile][endRank].getType().equals("King")) { // king gets moved
            if(currentColor.equals("black")) {
                blackKingLocation = move.substring(3);
            }else {
                whiteKingLocation = move.substring(3);
            }
        }

        // revert all enpassant values back to false for the player who DIDN'T just make a move
        for(int a = 0; a < 8; a++) {
            for(int b = 0; b < 8; b++) {
                if( board[a][b] != null && !(board[a][b].getColor().equals(turn)) ) {
                    enpassantMatrix[a][b] = false;
                }
            }
        }

    }

    /***********************************************************************************************************************************************************8
     * isMoveLegal()
     */

    /**
     * method to determine if a given move is legal or illegal
     * @param move A String representing the proposed move, i.e. e2 e3
     * @param color A String representing the color that is making the move
     * @return A boolean representing if move is legal (true) or illegal (false)
     */
    public static boolean isMoveLegal(String move, String color, int position) {
        ArrayList<String> preSearch = new ArrayList<String>();
        String preKingLocation;
        if(turn.equals("white")) {
            preSearch = calculateAllPossibleMoves("black");
            preKingLocation = getKingLocation("white");
        }else {
            preSearch = calculateAllPossibleMoves("white");
            preKingLocation = getKingLocation("black");
        }

        inCheckBeforeMove = false;
        for(int k = 0; k < preSearch.size(); k++) {
            if(preSearch.get(k).substring(3,5).equals(preKingLocation)) {
                inCheckBeforeMove = true;
                break;
            }
        }

        int startFile = (int)(move.charAt(0)) - 97;
        int startRank = (int)(move.charAt(1)) - 49;
        int endFile = (int)(move.charAt(3)) - 97;
        int endRank = (int)(move.charAt(4)) - 49;

        if(endFile > 7 || endRank > 7) {
            return false;
        }
        if(board[startFile][startRank] == null) {
            return false;
        }

        if( !(board[startFile][startRank].getColor().equals(color)) ) { // user is trying to move opponent's piece
            return false;
        }
        if(startFile == endFile && startRank == endRank) {
            return false;
        }

        String pieceType = board[startFile][startRank].getType();
        Piece piece = board[startFile][startRank];

        switch(pieceType) {
            case "Pawn":
                //take care of enpassant first, and on its own
                if( Math.abs(endFile - startFile) == 1 && Math.abs(endRank - startRank) == 1 && board[endFile][endRank] == null) { // trying to move diagonal to empty spot - check if enpassant
                    if(color.equals("black") && endRank - startRank < 0) {
                        if(enpassantMatrix[endFile][endRank+1]) {
                            board[endFile][endRank+1] = null;
                            pieces[position+1] = null;
                            enpassantMatrix[endFile][endRank+1] = false;
                            return true;
                        }
                    }else if(color.equals("white") && endRank - startRank > 0){
                        if(enpassantMatrix[endFile][endRank-1]) {
                            board[endFile][endRank-1] = null;
                            pieces[position-1] = null;
                            enpassantMatrix[endFile][endRank-1] = false;
                            return true;
                        }
                    }
                }

                if( (endRank < startRank && color.equals("white") || (endRank > startRank && color.equals("black"))) ) { // attempting to move pawn backwards - illegal
                    //System.out.println("messing up at 1");
                    return false;
                }else if( Math.abs(endRank - startRank) > 2 || Math.abs(endFile - startFile) > 1) { // attempting to move pawn more than 2 spaces forward or more than 1 space left or right - illegal
                    //System.out.println("messing up at 2");
                    return false;
                }else if( color.equals("white") && Math.abs(endRank - startRank) == 2 && (piece.hasMoved() || board[endFile][endRank] != null || board[endFile][endRank-1] != null || endFile != startFile) ) { // attempting to move 2 spaces forward NOT on its first move, or to capture, or jumping over a piece, or moving diagonally - illegal
                    //System.out.println("messing up at 3");
                    return false;
                }else if( color.equals("black") && Math.abs(endRank - startRank) == 2 && (piece.hasMoved() || board[endFile][endRank] != null || board[endFile][endRank+1] != null || endFile != startFile) ) { // attempting to move 2 spaces forward NOT on its first move, or to capture, or jumping over a piece, or moving diagonally - illegal
                    //System.out.println("messing up at 3a");
                    return false;
                }else if(endFile != startFile && endRank == startRank) { // attempting to move pawn left or right - illegal
                    //System.out.println("messing up at 4");
                    return false;
                }else if(Math.abs(endFile - startFile) == 1 && Math.abs(endRank - startRank) == 1 && (board[endFile][endRank] == null || board[endFile][endRank].getColor() == color) ) { // attempting to move diagonal when NOT capturing opponent's piece, illegal
                    //System.out.println("messing up at 5");
                    return false;
                }else if(Math.abs(endRank - startRank) == 1 && endFile == startFile && board[endFile][endRank] != null) { // attempting to capture piece directly in front of it - illegal
                    //System.out.println("messing up at 6");
                    return false;
                }

                // if pawn moved two spaces on first move, set its spot in enpassant matrix to true
                enpassantMatrix[endFile][endRank] = true;

                break;
            case "Rook": {
                int i=0;
                if (startFile == endFile) {
                    if (startRank < endRank) {
                        for (i=startRank+1; i<(endRank); i++) {
                            if (board[startFile][i] != null) {
                                return false;
                            }
                        }
                    } else {
                        for (i=startRank-1; i>(endRank); i--) {
                            if (board[startFile][i] != null) {
                                return false;
                            }
                        }
                    }
                    if (board[startFile][i] != null) {
                        if (board[startFile][i].getColor().equals(color)) {
                            return false;
                        }
                    }
                } else if (startRank == endRank) {
                    if (startFile < endFile) {
                        for (i=startFile+1; i<(endFile); i++) {
                            if (board[i][startRank] != null) {
                                return false;
                            }
                        }
                    } else {
                        for (i=startFile-1; i>(endFile); i--) {
                            if (board[i][startRank] != null) {
                                return false;
                            }
                        }
                    }
                    if (board[i][startRank] != null) {
                        if (board[i][startRank].getColor().equals(color)) {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
                break;
            }
            case "Knight":
                if(!( (Math.abs(endFile - startFile) == 2 && Math.abs(endRank - startRank) == 1) ||
                        (Math.abs(endRank - startRank) == 2 && Math.abs(endFile - startFile) == 1)) ) { // make sure move is L shaped
                    return false;
                }else if(board[endFile][endRank] != null && board[endFile][endRank].getColor() == color) { // trying to capture own piece - illegal
                    return false;
                }
                break;
            case "Bishop": {
                if (Math.abs(endRank-startRank) != Math.abs(endFile-startFile)) {
                    return false;
                }
                int i=0;
                int j=0;
                if (startFile < endFile) {
                    if (startRank < endRank) {
                        j=startRank+1;
                        for (i=startFile+1; i<endFile; i++) {
                            if (board[i][j] != null) {
                                return false;
                            }
                            j++;
                        }
                    } else {
                        j=startRank-1;
                        for (i=startFile+1; i<endFile; i++) {
                            if (board[i][j] != null) {
                                return false;
                            }
                            j--;
                        }
                    }
                } else {
                    if (startRank < endRank) {
                        j=startRank+1;
                        for (i=startFile-1; i>endFile; i--) {
                            if (board[i][j] != null) {
                                return false;
                            }
                            j++;
                        }
                    } else {
                        j=startRank-1;
                        for (i=startFile-1; i>endFile; i--) {
                            if (board[i][j] != null) {
                                return false;
                            }
                            j--;
                        }
                    }
                }
                if (board[i][j] != null) {
                    if (board[i][j].getColor().equals(color)) {
                        return false;
                    }
                }
                break;
            }
            case "Queen":
                if(board[endFile][endRank] != null && board[endFile][endRank].getColor() == color) { // trying to capture own piece - illegal
                    return false;
                }else if(Math.abs(endFile - startFile) != 0 && endRank - startRank == 0) { // trying to move horizontally
                    if(endFile > startFile) { // moving right
                        for(int i = startFile+1; i < endFile; i++) {
                            if(board[i][startRank] != null) { // jumped a piece - illegal
                                return false;
                            }
                        }
                    }else {
                        for(int i = endFile-1; i > startFile; i--) { // moving left
                            if(board[i][startRank] != null) { // jumped a piece - illegal
                                return false;
                            }
                        }
                    }
                }else if(Math.abs(endRank - startRank) != 0 && endFile - startFile == 0) { // trying to move vertically
                    if(endRank > startRank) { // moving up
                        for(int i = startRank+1; i < endRank; i++) {
                            if(board[startFile][i] != null) { // jumped a piece - illegal
                                return false;
                            }
                        }
                    }else {
                        for(int i = startRank-1; i > endRank; i--) { // moving down
                            if(board[startFile][i] != null) { // jumped a piece - illegal
                                return false;
                            }
                        }
                    }
                }else if(Math.abs(endRank - startRank) == Math.abs(endFile - startFile)) { // trying to move diagonally
                    int j;
                    if(endFile > startFile && endRank > startRank) { // moving up and right
                        j = startFile + 1;
                        for(int i = startRank+1; i < endRank; i++) {
                            if(board[j][i] != null) { // jumped a piece - illegal
                                return false;
                            }
                            j++;
                        }
                    }else if(startFile > endFile && endRank > startRank) { // moving up and left
                        j = startFile - 1;
                        for(int i = startRank+1; i < endRank; i++) {
                            if(board[j][i] != null) { // jumped a piece - illegal
                                return false;
                            }
                            j--;
                        }
                    }else if(startFile > endFile && startRank > endRank) { // moving down and left
                        j = startFile - 1;
                        for(int i = endRank-1; i > startRank; i--) {
                            if(board[j][i] != null) { // jumped a piece - illegal
                                return false;
                            }
                            j--;
                        }
                    }else if(endFile > startFile && startRank > endRank) { // moving down and right
                        j = startFile + 1;
                        for(int i = endRank-1; i > startRank; i--) {
                            if(board[j][i] != null) { // jumped a piece - illegal
                                return false;
                            }
                            j++;
                        }
                    }
                }else { // inputted some gibberish input - not horizontal, vertical, or diagonal
                    return false;
                }
                break;
            case "King": {
                if (!(Math.abs(endRank-startRank)<=1 && Math.abs(endFile-startFile)<=1)) {
                    if (!(board[startFile][startRank].hasMoved()) && !inCheckBeforeMove) {
                        if (startRank == endRank) {
                            if (endFile==2 && board[0][startRank] != null && board[1][startRank] == null && board[2][startRank] == null && board[3][startRank] == null) {
                                if (!(board[0][startRank].hasMoved())) {
                                    return true;
                                }
                            } else if (endFile==6 && board[5][startRank] == null && board[6][startRank] == null && board[7][startRank] != null) {
                                if (!(board[7][startRank].hasMoved())) {
                                    return true;
                                }
                            }
                        }
                    }
                    return false;
                }

                if (board[endFile][endRank] != null) {
                    if (board[endFile][endRank].getColor().equals(color)) {
                        return false;
                    }
                }
                break;
            }
            default:
                return false;
        }
        return true;
    }

    /*****************************************************************************************************************************************************************
     * calculateAllPossibleMoves()
     */

    /**
     * method to calculate all possible moves by pieces of a specific color. useful for determining checkmate
     * @param color A String of either "white" or "black" depicting which set of pieces to calculate moves for
     * @return An ArrayList containing Strings of all the calculated moves
     */
    public static ArrayList<String> calculateAllPossibleMoves(String color){

        /**
         * arraylist of moves to be added to throughout method
         */
        ArrayList<String> moves = new ArrayList<String>();

        for(int i = 0; i < 8; i++) { // traverse file
            for(int j = 0; j < 8; j++) { // traverse rank
                Piece currentPiece = board[i][j];
                if(currentPiece == null || currentPiece.getColor() != color) { // skip any empty spaces or other pieces
                    continue;
                }
                int newFile;
                int newRank;
                //System.out.println("calculating moves for: "+currentPiece.getType()+" at "+(char)(i+97)+(char)(j+49));
                switch(currentPiece.getType()) {

                    case "Pawn": //*************************** PAWN *********************************************

                        newFile = i;
                        newRank = j;

                        if(currentPiece.getColor().equals("black")) { // pawn is black

                            // move down 1 space
                            newRank -= 1;
                            if(newRank >= 0 && board[newFile][newRank] == null) { // no piece in front and not at end of board
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                            }

                            // move down 2 spaces
                            newRank = j;
                            if(newRank-2 >= 0 && board[newFile][newRank-1] == null && board[newFile][newRank-2] == null && currentPiece.hasMoved() == false) { // no piece in front and not at end of board
                                //moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank-1+49) );
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank-2+49) );
                            }

                            // move down+left (for capture)
                            newRank = j;
                            newFile = i;
                            newRank -=1;
                            newFile -= 1;
                            if( newRank >= 0 && newFile >= 0 && board[newFile][newRank] != null
                                    && !(board[newFile][newRank].getColor().equals(currentPiece.getColor())))  { // there is piece in capture area and not at end of board
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                            }

                            // move down+right (for capture)
                            newRank = j;
                            newFile = i;
                            newRank -=1;
                            newFile += 1;
                            if( newRank >= 0 && newFile < 8 && board[newFile][newRank] != null
                                    && !(board[newFile][newRank].getColor().equals(currentPiece.getColor())))  { // there is piece in capture area and not at end of board
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                            }

                        }else { // pawn is white

                            // move up 1 space
                            newRank += 1;
                            if(newRank < 8 && board[newFile][newRank] == null) { // no piece in front and not at end of board
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                            }

                            // move up 2 spaces
                            newRank = j;
                            if(newRank+2 < 8 && board[newFile][newRank+1] == null && board[newFile][newRank+2] == null && currentPiece.hasMoved() == false) { // no piece in front and not at end of board
                                //moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+1+49) );
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+2+49) );
                            }

                            // move up+left (for capture)
                            newRank = j;
                            newFile = i;
                            newRank +=1;
                            newFile -= 1;
                            if( newRank < 8 && newFile >= 0 && board[newFile][newRank] != null
                                    && !(board[newFile][newRank].getColor().equals(currentPiece.getColor())))  { // there is piece in capture area and not at end of board
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                            }

                            // move up+right (for capture)
                            newRank = j;
                            newFile = i;
                            newRank +=1;
                            newFile += 1;
                            if( newRank < 8 && newFile < 8 && board[newFile][newRank] != null
                                    && !(board[newFile][newRank].getColor().equals(currentPiece.getColor())))  { // there is piece in capture area and not at end of board
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                            }

                        }

                        break;

                    case "Knight": // ******************************* KNIGHT ****************************************

                        // going up 2 and right 1
                        newFile = i;
                        newRank = j;
                        newRank += 2;
                        newFile += 1;
                        if(newRank < 8 && newRank >= 0 && newFile < 8 && newFile >= 0 &&
                                (board[newFile][newRank] == null || board[newFile][newRank].getColor() != currentPiece.getColor())) { // move is valid
                            moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                        }

                        // going up 2 and left 1
                        newFile = i;
                        newRank = j;
                        newRank += 2;
                        newFile -= 1;
                        if(newRank < 8 && newRank >= 0 && newFile < 8 && newFile >= 0 &&
                                (board[newFile][newRank] == null || board[newFile][newRank].getColor() != currentPiece.getColor())) { // move is valid
                            moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                        }

                        // going down 2 and right 1
                        newFile = i;
                        newRank = j;
                        newRank -= 2;
                        newFile += 1;
                        if(newRank < 8 && newRank >= 0 && newFile < 8 && newFile >= 0 &&
                                (board[newFile][newRank] == null || board[newFile][newRank].getColor() != currentPiece.getColor())) { // move is valid
                            moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                        }

                        // going down 2 and left 1
                        newFile = i;
                        newRank = j;
                        newRank -= 2;
                        newFile -= 1;
                        if(newRank < 8 && newRank >= 0 && newFile < 8 && newFile >= 0 &&
                                (board[newFile][newRank] == null || board[newFile][newRank].getColor() != currentPiece.getColor())) { // move is valid
                            moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                        }

                        // going left 2 and up 1
                        newFile = i;
                        newRank = j;
                        newRank += 1;
                        newFile -= 2;
                        if(newRank < 8 && newRank >= 0 && newFile < 8 && newFile >= 0 &&
                                (board[newFile][newRank] == null || board[newFile][newRank].getColor() != currentPiece.getColor())) { // move is valid
                            moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                        }

                        // going left 2 and down 1
                        newFile = i;
                        newRank = j;
                        newRank -= 1;
                        newFile -= 2;
                        if(newRank < 8 && newRank >= 0 && newFile < 8 && newFile >= 0 &&
                                (board[newFile][newRank] == null || board[newFile][newRank].getColor() != currentPiece.getColor())) { // move is valid
                            moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                        }

                        // going right 2 and up 1
                        newFile = i;
                        newRank = j;
                        newRank += 1;
                        newFile += 2;
                        if(newRank < 8 && newRank >= 0 && newFile < 8 && newFile >= 0 &&
                                (board[newFile][newRank] == null || board[newFile][newRank].getColor() != currentPiece.getColor())) { // move is valid
                            moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                        }

                        // going right 2 and down 1
                        newFile = i;
                        newRank = j;
                        newRank -= 1;
                        newFile += 2;
                        if(newRank < 8 && newRank >= 0 && newFile < 8 && newFile >= 0 &&
                                (board[newFile][newRank] == null || board[newFile][newRank].getColor() != currentPiece.getColor())) { // move is valid
                            moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                        }

                        break;

                    case "Rook": // ********************************** ROOK *********************************************

                        // going up
                        for(newRank = j+1; newRank < 8; newRank++) {
                            if(board[i][newRank] == null) { // can move here - space empty
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(i+97)+(char)(newRank+49) );
                            }else if(board[i][newRank].getColor() != currentPiece.getColor()) { // can move here and capture enemy piece, then done with this direction
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(i+97)+(char)(newRank+49) );
                                break;
                            }else { // space occupied by same color - can't move here and done with this direction
                                break;
                            }
                        }

                        // going down
                        for(newRank = j-1; newRank >= 0; newRank--) {
                            if(board[i][newRank] == null) { // can move here - space empty
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(i+97)+(char)(newRank+49) );
                            }else if(board[i][newRank].getColor() != currentPiece.getColor()) { // can move here and capture enemy piece, then done with this direction
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(i+97)+(char)(newRank+49) );
                                break;
                            }else { // space occupied by same color - can't move here and done with this direction
                                break;
                            }
                        }

                        // going right
                        for(newFile = i+1; newFile < 8; newFile++) {
                            if(board[newFile][j] == null) { // can move here - space empty
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(j+49) );
                            }else if(board[newFile][j].getColor() != currentPiece.getColor()) { // can move here and capture enemy piece, then done with this direction
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(j+49) );
                                break;
                            }else { // space occupied by same color - can't move here and done with this direction
                                break;
                            }
                        }

                        // going left
                        for(newFile = i-1; newFile >= 0; newFile--) {
                            if(board[newFile][j] == null) { // can move here - space empty
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(j+49) );
                            }else if(board[newFile][j].getColor() != currentPiece.getColor()) { // can move here and capture enemy piece, then done with this direction
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(j+49) );
                                break;
                            }else { // space occupied by same color - can't move here and done with this direction
                                break;
                            }
                        }

                        break;

                    case "Bishop": // ******************************* BISHOP ***************************************
                        // going diagonal up+right
                        newFile = i+1;
                        for(newRank = j+1; newRank < 8; newRank++) {
                            if(newFile > 7) { // file goes out of bounds of board
                                break;
                            }
                            if(board[newFile][newRank] == null) { // can move here - space empty
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                            }else if(board[newFile][newRank].getColor() != currentPiece.getColor()) { // can move here and capture enemy piece, then done with this direction
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                                break;
                            }else { // space occupied by same color - can't move here and done with this direction
                                break;
                            }
                            newFile++;
                        }

                        // going diagonal up+left
                        newFile = i-1;
                        for(newRank = j+1; newRank < 8; newRank++) {
                            if(newFile < 0) { // file goes out of bounds of board
                                break;
                            }
                            if(board[newFile][newRank] == null) { // can move here - space empty
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                            }else if(board[newFile][newRank].getColor() != currentPiece.getColor()) { // can move here and capture enemy piece, then done with this direction
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                                break;
                            }else { // space occupied by same color - can't move here and done with this direction
                                break;
                            }
                            newFile--;
                        }

                        // going diagonal down+right
                        newFile = i+1;
                        for(newRank = j-1; newRank >= 0; newRank--) {
                            if(newFile > 7) { // file goes out of bounds of board
                                break;
                            }
                            if(board[newFile][newRank] == null) { // can move here - space empty
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                            }else if(board[newFile][newRank].getColor() != currentPiece.getColor()) { // can move here and capture enemy piece, then done with this direction
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                                break;
                            }else { // space occupied by same color - can't move here and done with this direction
                                break;
                            }
                            newFile++;
                        }

                        // going diagonal down+left
                        newFile = i-1;
                        for(newRank = j-1; newRank >= 0; newRank--) {
                            if(newFile < 0) { // file goes out of bounds of board
                                break;
                            }
                            if(board[newFile][newRank] == null) { // can move here - space empty
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                            }else if(board[newFile][newRank].getColor() != currentPiece.getColor()) { // can move here and capture enemy piece, then done with this direction
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                                break;
                            }else { // space occupied by same color - can't move here and done with this direction
                                break;
                            }
                            newFile--;
                        }

                        break;

                    case "Queen": // ****************************** QUEEN *****************************************

                        // SIMPLY A COMBINATION OF ROOK AND BISHOP

                        // rook part:

                        // going up
                        for(newRank = j+1; newRank < 8; newRank++) {
                            if(board[i][newRank] == null) { // can move here - space empty
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(i+97)+(char)(newRank+49) );
                            }else if(board[i][newRank].getColor() != currentPiece.getColor()) { // can move here and capture enemy piece, then done with this direction
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(i+97)+(char)(newRank+49) );
                                break;
                            }else { // space occupied by same color - can't move here and done with this direction
                                break;
                            }
                        }

                        // going down
                        for(newRank = j-1; newRank >= 0; newRank--) {
                            if(board[i][newRank] == null) { // can move here - space empty
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(i+97)+(char)(newRank+49) );
                            }else if(board[i][newRank].getColor() != currentPiece.getColor()) { // can move here and capture enemy piece, then done with this direction
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(i+97)+(char)(newRank+49) );
                                break;
                            }else { // space occupied by same color - can't move here and done with this direction
                                break;
                            }
                        }

                        // going right
                        for(newFile = i+1; newFile < 8; newFile++) {
                            if(board[newFile][j] == null) { // can move here - space empty
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(j+49) );
                            }else if(board[newFile][j].getColor() != currentPiece.getColor()) { // can move here and capture enemy piece, then done with this direction
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(j+49) );
                                break;
                            }else { // space occupied by same color - can't move here and done with this direction
                                break;
                            }
                        }

                        // going left
                        for(newFile = i-1; newFile >= 0; newFile--) {
                            if(board[newFile][j] == null) { // can move here - space empty
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(j+49) );
                            }else if(board[newFile][j].getColor() != currentPiece.getColor()) { // can move here and capture enemy piece, then done with this direction
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(j+49) );
                                break;
                            }else { // space occupied by same color - can't move here and done with this direction
                                break;
                            }
                        }

                        // bishop part:

                        // going diagonal up+right
                        newFile = i+1;
                        for(newRank = j+1; newRank < 8; newRank++) {
                            if(newFile > 7) { // file goes out of bounds of board
                                break;
                            }
                            if(board[newFile][newRank] == null) { // can move here - space empty
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                            }else if(board[newFile][newRank].getColor() != currentPiece.getColor()) { // can move here and capture enemy piece, then done with this direction
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                                break;
                            }else { // space occupied by same color - can't move here and done with this direction
                                break;
                            }
                            newFile++;
                        }

                        // going diagonal up+left
                        newFile = i-1;
                        for(newRank = j+1; newRank < 8; newRank++) {
                            if(newFile < 0) { // file goes out of bounds of board
                                break;
                            }
                            if(board[newFile][newRank] == null) { // can move here - space empty
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                            }else if(board[newFile][newRank].getColor() != currentPiece.getColor()) { // can move here and capture enemy piece, then done with this direction
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                                break;
                            }else { // space occupied by same color - can't move here and done with this direction
                                break;
                            }
                            newFile--;
                        }

                        // going diagonal down+right
                        newFile = i+1;
                        for(newRank = j-1; newRank >= 0; newRank--) {
                            if(newFile > 7) { // file goes out of bounds of board
                                break;
                            }
                            if(board[newFile][newRank] == null) { // can move here - space empty
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                            }else if(board[newFile][newRank].getColor() != currentPiece.getColor()) { // can move here and capture enemy piece, then done with this direction
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                                break;
                            }else { // space occupied by same color - can't move here and done with this direction
                                break;
                            }
                            newFile++;
                        }

                        // going diagonal down+left
                        newFile = i-1;
                        for(newRank = j-1; newRank >= 0; newRank--) {
                            if(newFile < 0) { // file goes out of bounds of board
                                break;
                            }
                            if(board[newFile][newRank] == null) { // can move here - space empty
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                            }else if(board[newFile][newRank].getColor() != currentPiece.getColor()) { // can move here and capture enemy piece, then done with this direction
                                moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                                break;
                            }else { // space occupied by same color - can't move here and done with this direction
                                break;
                            }
                            newFile--;
                        }

                        break;

                    case "King":

                        // up
                        newFile = i;
                        newRank = j;
                        newRank += 1;
                        if(newRank < 8 && (board[newFile][newRank] == null || board[newFile][newRank].getColor() != currentPiece.getColor())) { // move is valid
                            moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                        }

                        // down
                        newFile = i;
                        newRank = j;
                        newRank -= 1;
                        if(newRank >= 0 && (board[newFile][newRank] == null || board[newFile][newRank].getColor() != currentPiece.getColor())) { // move is valid
                            moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                        }

                        // left
                        newFile = i;
                        newRank = j;
                        newFile -= 1;
                        if(newFile >= 0 && (board[newFile][newRank] == null || board[newFile][newRank].getColor() != currentPiece.getColor())) { // move is valid
                            moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                        }

                        // right
                        newFile = i;
                        newRank = j;
                        newFile += 1;
                        if(newFile < 8 && (board[newFile][newRank] == null || board[newFile][newRank].getColor() != currentPiece.getColor())) { // move is valid
                            moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                        }

                        // up+right diagonal
                        newFile = i;
                        newRank = j;
                        newRank += 1;
                        newFile += 1;
                        if(newRank < 8 && newFile < 8 && (board[newFile][newRank] == null || board[newFile][newRank].getColor() != currentPiece.getColor())) { // move is valid
                            moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                        }

                        // up+left diagonal
                        newFile = i;
                        newRank = j;
                        newRank += 1;
                        newFile -= 1;
                        if(newRank < 8 && newFile >= 0 && (board[newFile][newRank] == null || board[newFile][newRank].getColor() != currentPiece.getColor())) { // move is valid
                            moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                        }

                        // down+right diagonal
                        newFile = i;
                        newRank = j;
                        newRank -= 1;
                        newFile += 1;
                        if(newRank >= 0 && newFile < 8 && (board[newFile][newRank] == null || board[newFile][newRank].getColor() != currentPiece.getColor())) { // move is valid
                            moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                        }

                        // down+left diagonal
                        newFile = i;
                        newRank = j;
                        newRank -= 1;
                        newFile -= 1;
                        if(newRank >= 0 && newFile >= 0 && (board[newFile][newRank] == null || board[newFile][newRank].getColor() != currentPiece.getColor())) { // move is valid
                            moves.add( ""+(char)(i+97)+(char)(j+49)+' '+(char)(newFile+97)+(char)(newRank+49) );
                        }

                        break;

                    default:

                        break;

                }
            }
        }

        return moves;
    }

    /********************************************************************************************************************************************************************
     * getKingLocation()
     */

    /**
     * method used to find the current location of a team's king piece
     * @param color the color of the king to find
     * @return a String depicting the location of desired king
     */
    public static String getKingLocation(String color) {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j] != null && board[i][j].getType().equals("King") && board[i][j].getColor().equals(color)) {
                    return ""+(char)(i+97)+(char)(j+49);
                }
            }
        }
        return "a1"; // will never return this
    }


    /**
     * method used to print out the "board" double matrix after each turn
     */
    public static void printBoard() {
        for(int i = 7; i >= 0; i--) {
            for(int j = 0; j < 8; j++) {
                if(board[j][i] == null && (i + j) % 2 == 0) { // print black space
                    System.out.print("## ");
                }else if(board[j][i] == null && (i + j) % 2 != 0) { // print white space
                    System.out.print("   ");
                }else { // print out piece
                    System.out.print(board[j][i] + " ");
                }
            }
            System.out.print((i+1) + "\n"); // print rank numbers (1 through 8) on right side
        }
        for(int k = 0; k < 8; k++) {
            System.out.print(" " + (char)(k+97) + " "); // print file chars (a through h) on bottom of board
        }
        System.out.println();

    }

}
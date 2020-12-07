package com.example.chess69;

import android.widget.ImageView;

/**
 * This is an abstract class
 * that will be used to encapsulate
 * all types of chess pieces
 * @author Mark Pirella map577
 * @author Nicholas Farinella njf61
 */
public abstract class Piece {

    /**
     * represents the type of the chess piece, i.e. rook or pawn
     */
    protected String type;

    /**
     * represents the color of the piece: either black or white
     */
    protected String color;

    /**
     * holds value for whether or not the piece has been moved yet
     */
    //protected boolean hasMoved;
    protected int numMoves;

    protected ImageView image;

    protected int imageResource;

    protected boolean selected;

    /**
     * constructor for Piece class
     * @param color A String representing the type of the piece
     * @param type A String representing the color of the piece
     */
    public Piece(String color, String type) {
        this.color = color;
        this.type = type;
        this.selected = false;
        //hasMoved = false;
        numMoves = 0;

        // find correct picture representation of piece
        if(color.equals("black") && type.equals("Bishop")){
            //image.setImageResource(R.drawable.black_bishop);
            imageResource = R.drawable.black_bishop;
        }else if(color.equals("white") && type.equals("Bishop")) {
            //image.setImageResource(R.drawable.white_bishop);
            imageResource = R.drawable.white_bishop;
        }else if(color.equals("black") && type.equals("King")) {
            //image.setImageResource(R.drawable.black_king);
            imageResource = R.drawable.black_king;
        }else if(color.equals("white") && type.equals("King")) {
            //image.setImageResource(R.drawable.white_king);
            imageResource = R.drawable.white_king;
        }else if(color.equals("black") && type.equals("Knight")) {
            //image.setImageResource(R.drawable.black_knight);
            imageResource = R.drawable.black_knight;
        }else if(color.equals("white") && type.equals("Knight")) {
            //image.setImageResource(R.drawable.white_knight);
            imageResource = R.drawable.white_knight;
        }else if(color.equals("black") && type.equals("Pawn")) {
            //image.setImageResource(R.drawable.black_pawn);
            imageResource = R.drawable.black_pawn;
        }else if(color.equals("white") && type.equals("Pawn")) {
            //image.setImageResource(R.drawable.white_pawn);
            imageResource = R.drawable.white_pawn;
        }else if(color.equals("black") && type.equals("Queen")) {
            //image.setImageResource(R.drawable.black_queen);
            imageResource = R.drawable.black_queen;
        }else if(color.equals("white") && type.equals("Queen")) {
            //image.setImageResource(R.drawable.white_queen);
            imageResource = R.drawable.white_queen;
        }else if(color.equals("black") && type.equals("Rook")) {
            //image.setImageResource(R.drawable.black_rook);
            imageResource = R.drawable.black_rook;
        }else if(color.equals("white") && type.equals("Rook")) {
            //image.setImageResource(R.drawable.white_rook);
            imageResource = R.drawable.white_rook;
        }

    }

    /**
     * getter method for the field "type"
     * @return A string representing the type of the piece
     */
    public String getType() {
        return type;
    }

    /**
     * getter method for the field "color"
     * @return A String representing the color of the piece
     */
    public String getColor() {
        return color;
    }

    /**
     * getter method for field "hasMoved"
     * @return A boolean value representing whether or not the piece has been moved yet
     */
    public int getNumMoves() {
        return numMoves;
    }

    public boolean hasMoved() {
        if(numMoves > 0){
            return true;
        }
        return false;
    }

    public void incNumMoves(){
        numMoves++;
    }

    public void decNumMoves(){
        numMoves--;
    }

    /**
     * setter method for field "type"
     * @param str A string value that the "type" field will be set to
     */

    public void setType(String str) {
        type = str;
    }

    public ImageView getImage(){
        return image;
    }

    public int getImageResource(){
        return imageResource;
    }

    /**
     * toString() override method to print out proper representation of each piece
     * @return A string representing desired piece, i.e black pawn will be bp and white Rook will be wR
     */
    public String toString() {
        if(type.equals("Pawn")) {
            return ""+color.charAt(0)+"p";
        }else if(type.equals("Knight")) {
            return ""+color.charAt(0)+"N";
        }else {
            return ""+color.charAt(0) + type.charAt(0);
        }
    }
}


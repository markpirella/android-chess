package com.example.chess69;

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
    protected boolean hasMoved;

    /**
     * constructor for Piece class
     * @param color A String representing the type of the piece
     * @param type A String representing the color of the piece
     */
    public Piece(String color, String type) {
        this.color = color;
        this.type = type;
        hasMoved = false;
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
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * setter method for field "hasMoved"
     * @param val A boolean value that the "hasMoved" field will be set to
     */
    public void setHasMoved(boolean val) {
        hasMoved = val;
    }

    /**
     * setter method for field "type"
     * @param str A string value that the "type" field will be set to
     */

    public void setType(String str) {
        type = str;
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


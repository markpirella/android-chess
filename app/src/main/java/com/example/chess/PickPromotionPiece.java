package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PickPromotionPiece extends AppCompatActivity {
    String chosenPiece;
    Intent data = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_promotion_piece);

        // link all buttons
        final Button queen_button = (Button) findViewById(R.id.queen_button);
        final Button rook_button = (Button) findViewById(R.id.rook_button);
        final Button bishop_button = (Button) findViewById(R.id.bishop_button);
        final Button knight_button = (Button) findViewById(R.id.knight_button);

        // queen selected
        queen_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                chosenPiece = "Queen";
                done();
            }
        });

        // rook selected
        rook_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                chosenPiece = "Rook";
                done();
            }
        });

        // bishop selected
        bishop_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                chosenPiece = "Bishop";
                done();
            }
        });

        // knight selected
        knight_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                chosenPiece = "Knight";
                done();
            }
        });
    }

    private void done(){
        data.setData(Uri.parse(chosenPiece));
        setResult(RESULT_OK, data);
        finish();
    }
}
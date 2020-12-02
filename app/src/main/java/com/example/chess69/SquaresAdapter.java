package com.example.chess69;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SquaresAdapter extends BaseAdapter {
    private final Context mContext;
    private final Piece[] pieces;
    private int square_num = 0;

    public SquaresAdapter(Context context, Piece[] pieces){
        this.mContext = context;
        this.pieces = pieces;
    }

    @Override
    public int getCount(){
        return pieces.length;
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    @Override
    public Object getItem(int position){
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        final Piece piece = pieces[position];

        System.out.println("position: " + position +", piece: "+piece);

        if(convertView == null && piece != null){
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.square_layout, null);
        }else if(convertView == null && piece == null){
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.empty_square_layout, null);
        }

        if(piece != null) {
            final ImageView imageView = (ImageView) convertView.findViewById(R.id.imageview_piece);
            imageView.setImageResource(piece.getImageResource());
        }else{
            final TextView empty = (TextView)convertView.findViewById(R.id.textview_empty);
            empty.setText("");
        }

        position = position + (position / 8);
        if(position%2==0){
            /*
            final ImageView imageViewBG = (ImageView) convertView.findViewById(R.id.imageview_background);
            imageViewBG.setImageResource(R.drawable.white_square);
             */
            convertView.setBackgroundColor(Color.parseColor("#BC9A53"));
        }else {
            /*
            final ImageView imageViewBG = (ImageView) convertView.findViewById(R.id.imageview_background);
            imageViewBG.setImageResource(R.drawable.black_square);
             */
            convertView.setBackgroundColor(Color.parseColor("#594017"));
        }

        return convertView;
    }
}

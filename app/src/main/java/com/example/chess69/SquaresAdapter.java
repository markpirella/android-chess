package com.example.chess69;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.chess69.MainActivity;

public class SquaresAdapter extends BaseAdapter {
    private final Context mContext;
    private final Piece[] pieces;
    //private int square_num = 0;

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
    public Piece getItem(int position){
        return pieces[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        GridView chessboard = (GridView) parent;
        final Piece piece = pieces[position];

        //System.out.println("position: " + position +", piece: "+piece);

            //convertView == null &&
        if(piece != null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.square_layout, null);
        }else if(piece == null){
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

        if(piece != null && piece.selected == true){
            convertView.setBackgroundColor(Color.parseColor("#4DBF2E"));
        }

        //convertView.setOnDragListener(new MyDragListener());

        /*
        // drag listener
        convertView.setOnDragListener(new View.OnDragListener() {

            @Override
            public boolean onDrag(View v, DragEvent event) {
                System.out.println("BEING DRAGGED!!!");
                boolean result = true;
                int action = event.getAction();
                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        System.out.println("BEING DRAGGED!!!");
                        break;
                    case DragEvent.ACTION_DRAG_LOCATION:
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        //v.setBackgroundResource(R.drawable.shape_image_view_small_gallery_selected);
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        //v.setBackgroundResource(R.drawable.shape_image_view_small_gallery_unselected);
                        break;
                    case DragEvent.ACTION_DROP:
                        if (event.getLocalState() == v) {
                            result = false;
                        } else {
                            int x2 = (int) event.getX();
                            int y2 = (int) event.getY();
                            int endPosition = chessboard.pointToPosition(x2, y2);
                            System.out.println("END POSITION: "+endPosition);
                        }
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        //v.setBackgroundResource(R.drawable.shape_image_view_small_gallery_unselected);
                        break;
                    default:
                        result = false;
                        break;
                }
                return result;
            }
        });

         */

        return convertView;
    }

    /*
    class MyDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            //System.out.println("DRAG STARTED!!!");

            // Handles each of the expected events
            switch (event.getAction()) {

                //signal for the start of a drag and drop operation.
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    System.out.println("DRAG STARTED!!!");
                    break;

                //the drag point has entered the bounding box of the View
                case DragEvent.ACTION_DRAG_ENTERED:
                    //v.setBackground(targetShape);   //change the shape of the view
                    break;

                //the user has moved the drag shadow outside the bounding box of the View
                case DragEvent.ACTION_DRAG_EXITED:
                    //v.setBackground(normalShape);   //change the shape of the view back to normal
                    break;

                //drag shadow has been released,the drag point is within the bounding box of the View
                case DragEvent.ACTION_DROP:
                    // if the view is the bottomlinear, we accept the drag item
                    System.out.println("DRAG ENDED");
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    int endPosition = MainActivity.chessboard_gridview.pointToPosition(x, y);
                    MainActivity.secondSquareSelection = endPosition;
                    System.out.println("SECOND SQUARE: "+endPosition);
                    break;

                //the drag and drop operation has concluded.
                case DragEvent.ACTION_DRAG_ENDED:
                    //v.setBackground(normalShape);   //go back to normal shape

                default:
                    break;
            }
            return true;
        }
    }

     */
}

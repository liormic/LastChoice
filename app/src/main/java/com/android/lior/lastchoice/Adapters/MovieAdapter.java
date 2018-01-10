package com.android.lior.lastchoice.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.lior.lastchoice.Data.MovieObject;
import com.android.lior.lastchoice.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Lior on 12/31/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MoviewViewHolder> {

    final private ListItemClickListener mOncClickListener;
    private Boolean isFavScreen;
    private ArrayList<MovieObject> movieObjects = new ArrayList<>();

    public MovieAdapter(ArrayList<MovieObject> movieObjects,ListItemClickListener listener,Boolean isFavScreen){
        this.movieObjects=movieObjects;
        mOncClickListener = listener;
        this.isFavScreen = isFavScreen;
    }

    @Override
    public MoviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view;
        if(isFavScreen){
            view = LayoutInflater.from(context).inflate(R.layout.movie_list_item_fav,parent,false);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.movie_list_item, parent, false);
        }
        MoviewViewHolder viewHolder= new MoviewViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MoviewViewHolder holder, int position){
        holder.bind(position);
    }

    public  interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

    @Override
    public int getItemCount() {
        return movieObjects.size();
    }

    class MoviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        Button buttonMore;
        Button buttonFav;
        TextView textView;


        public MoviewViewHolder(View itemView) {

            super(itemView);

            //buttonMore= (Button) itemView.findViewById(R.id.buttonMore);
            //buttonFav = (Butto) itemView.findViewById(R.id.buttonFav);
            imageView = itemView.findViewById(R.id.imageView2);
            textView = (TextView) itemView.findViewById(R.id.sugText);
            itemView.setOnClickListener(this);


        }

        void bind(int position) {

            Picasso.with(itemView.getContext()).load(movieObjects.get(position).getMoviePoster()).fit().into(imageView);
            textView.setText(movieObjects.get(position).getMovieName());

        }

        @Override
        public void onClick(View view) {

            int clickedPosition = getAdapterPosition();
            mOncClickListener.onListItemClick(clickedPosition);

        }
    }
}
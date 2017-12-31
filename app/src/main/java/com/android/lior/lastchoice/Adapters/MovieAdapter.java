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


    private ArrayList<MovieObject> movieObjects = new ArrayList<>();

    public MovieAdapter(ArrayList<MovieObject> movieObjects){
        this.movieObjects=movieObjects;
    }

    @Override
    public MoviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.movie_list_item,parent,false);
        MoviewViewHolder viewHolder= new MoviewViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MoviewViewHolder holder, int position){
        holder.bind(position);
    }



    @Override
    public int getItemCount() {
        return movieObjects.size();
    }

    class MoviewViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        Button buttonMore;
        Button buttonFav;
        TextView textView;


        public MoviewViewHolder(View itemView) {

            super(itemView);
         imageView = (ImageView) itemView.findViewById(R.id.imageView);
         buttonMore= (Button) itemView.findViewById(R.id.buttonMore);
         buttonFav = (Button) itemView.findViewById(R.id.buttonFav);
         textView  =(TextView)itemView.findViewById(R.id.textView);


        }

        void bind (int position){

            Picasso.with(itemView.getContext()).load(movieObjects.get(position).getMoviePoster()).fit().into(imageView);
            textView.setText(movieObjects.get(position).getMovieName());

        }
    }
}

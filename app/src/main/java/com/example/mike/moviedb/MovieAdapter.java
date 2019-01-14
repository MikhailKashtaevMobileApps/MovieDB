package com.example.mike.moviedb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieservice.model.Movie;
import com.example.movieservice.model.movie_detail.MovieDetail;

import java.util.List;

public abstract class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movie> movies;

    public MovieAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public List<Movie> getMovies(){
        return this.movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from( viewGroup.getContext() ).inflate(R.layout.movie_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        Movie m = movies.get(i);

        viewHolder.originalTitle.setText(m.getOriginalTitle());
        viewHolder.overview.setText(m.getOverview());
        viewHolder.release_date.setText(m.getReleaseDate());
        viewHolder.vote_average.setText(m.getVoteAverage().toString());

        Glide.with(viewHolder.itemView)
                .load("https://image.tmdb.org/t/p/w500"+m.getPosterPath())
                .into(viewHolder.poster);

        final Integer ID = m.getId();

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewHolder.itemView.getContext(), MovieDetailActivity.class);
                intent.putExtra("data", ID);
                viewHolder.itemView.getContext().startActivity(intent);
            }
        });

        if ( i == movies.size()-1 ){
            onLastLoaded();
        }

    }

    @Override
    public int getItemCount() {
        return this.movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        final TextView originalTitle;
        final TextView overview;
        final TextView release_date;
        final TextView vote_average;
        final ImageView poster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            originalTitle = itemView.findViewById(R.id.original_title);
            overview = itemView.findViewById(R.id.overview);
            release_date = itemView.findViewById(R.id.release_date);
            vote_average = itemView.findViewById(R.id.vote_average);
            poster = itemView.findViewById(R.id.poster);

        }
    }

    public abstract void onLastLoaded();

}

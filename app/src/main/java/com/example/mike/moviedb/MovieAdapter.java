package com.example.mike.moviedb;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieservice.model.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movie> movies;

    public MovieAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from( viewGroup.getContext() ).inflate(R.layout.movie_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Movie m = movies.get(i);

        viewHolder.originalTitle.setText(m.getOriginalTitle());
        viewHolder.overview.setText(m.getOverview());
        viewHolder.release_date.setText(m.getReleaseDate());
        viewHolder.vote_average.setText(m.getVoteAverage().toString());

        Glide.with(viewHolder.itemView)
                .load("https://image.tmdb.org/t/p/w500"+m.getPosterPath())
                .into(viewHolder.poster);
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

}

package com.example.mike.moviedb;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.movieservice.model.movie_detail.Genre;
import com.example.movieservice.model.movie_detail.MovieDetail;

import java.io.Serializable;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView poster;
    private TextView title;
    private TextView overview;
    private TextView score;
    private TextView genres;
    private MovieDetailPresenter movieDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movieDetailPresenter = new MovieDetailPresenter(this);


        poster = findViewById(R.id.detail_poster);
        title = findViewById( R.id.detail_title );
        overview = findViewById( R.id.detail_overview );
        score = findViewById( R.id.detail_score );
        genres = findViewById( R.id.detail_genres );

        final Context c = this;

        movieDetailPresenter.setOnServiceConnected(new MovieDetailPresenter.OnServiceConnected() {
            @Override
            public void onConnected() {
                Intent intent = getIntent();
                Integer id = intent.getIntExtra("data", 0);


                if ( id == 0 ){
                    Toast.makeText( c, "Something wrong with movie retrieval", Toast.LENGTH_LONG ).show();
                    return;
                }
                MovieDetail movie = null;
                try {
                    movie = movieDetailPresenter.getMovie(id);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                if ( movie == null ){
                    Toast.makeText( c, "Something wrong with movie retrieval", Toast.LENGTH_LONG ).show();
                    return;
                }
                Glide.with(c).load("https://image.tmdb.org/t/p/w500"+movie.getPosterPath()).into(poster);
                title.setText( movie.getTitle() );
                overview.setText( movie.getOverview() );
                score.setText( String.valueOf( movie.getVoteCount() ) );

                StringBuilder genresList = new StringBuilder();
                for (Genre genre : movie.getGenres()) {
                    genresList.append(genre.getName());
                    genresList.append(" ");
                }

                genres.setText( genresList );
            }
        });

    }



}

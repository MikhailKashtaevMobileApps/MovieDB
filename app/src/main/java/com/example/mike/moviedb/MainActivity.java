package com.example.mike.moviedb;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movieservice.MovieService;
import com.example.movieservice.MovieServiceAIDL;
import com.example.movieservice.model.Movie;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "__TAG__";

    private EditText etQuery;
    private MainPresenter presenter;
    private RecyclerView rvMovieList;
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etQuery = findViewById( R.id.query );
        presenter = MainPresenter.getInstance(this);
        rvMovieList = findViewById( R.id.rvMovieList );
        adapter = new MovieAdapter(new ArrayList<Movie>());
        rvMovieList.setAdapter(adapter);
        rvMovieList.setLayoutManager(new LinearLayoutManager(this));

    }

    public void search(View view) {
        presenter.search(etQuery.getText().toString(),
                1)
        .subscribe(new SingleObserver<List<Movie>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<Movie> movies) {
                // Populate movies
                Log.d(TAG, "onSuccess: "+movies.size());
                adapter.setMovies( movies );
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

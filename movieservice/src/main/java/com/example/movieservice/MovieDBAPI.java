package com.example.movieservice;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.movieservice.model.MovieResult;
import com.example.movieservice.model.Movie;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class MovieDBAPI {

    public static final String URL = "https://api.themoviedb.org/3/search/movie";
    private static final String API_KEY = "79a7a50ea1b9278b14ffa53fb50b3b83";
    private Context context;

    public MovieDBAPI(Context context) {
        this.context = context;
    }

    public List<Movie> search(String query, Integer pageNum) throws ExecutionException, InterruptedException {
        RequestFuture<String> future = RequestFuture.newFuture();
        String url = URL+"?api_key="+API_KEY+"&language=en-US&query="+query+"&page="+pageNum+"";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, future, future);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add( stringRequest );

        String response = future.get();
        MovieResult result = new Gson().fromJson( response, MovieResult.class );
        return result.getResults();
    }

}

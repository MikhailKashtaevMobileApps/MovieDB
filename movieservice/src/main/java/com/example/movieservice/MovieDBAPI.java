package com.example.movieservice;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.movieservice.model.MovieResult;
import com.example.movieservice.model.Result;
import com.google.gson.Gson;

import java.util.List;


public class MovieDBAPI {

    public static final String URL = "https://api.themoviedb.org/3/search/movie";
    private static final String API_KEY = "79a7a50ea1b9278b14ffa53fb50b3b83";
    private Context context;

    public MovieDBAPI(Context context) {
        this.context = context;
    }

    public void search(String query, Integer pageNum, final Callback callback){
        RequestQueue queue = Volley.newRequestQueue(context);

        String url = URL+"?api_key="+API_KEY+"&language=en-US&query="+query+"&page="+pageNum+"";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        MovieResult result = new Gson().fromJson( response, MovieResult.class );
                        callback.onSuccess(result.getResults());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error.getMessage());
                    }
                }
        );
        queue.add(stringRequest);
    }

    public interface Callback{
        void onSuccess(List<Result> movies);
        void onError(String error);
    }

}

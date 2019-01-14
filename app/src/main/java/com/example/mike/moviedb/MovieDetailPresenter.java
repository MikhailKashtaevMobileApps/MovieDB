package com.example.mike.moviedb;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.movieservice.MovieService;
import com.example.movieservice.MovieServiceAIDL;
import com.example.movieservice.model.movie_detail.MovieDetail;

public class MovieDetailPresenter {

    public static MovieDetailPresenter instance;
    private MovieServiceAIDL movieService;
    private boolean connected = false;

    public static MovieDetailPresenter getInstance(Context context){
        if ( instance == null ){
            instance = new MovieDetailPresenter(context);
        }
        return instance;
    }

    public MovieDetailPresenter(Context context) {
        Intent intent = new Intent(context, MovieService.class);
        intent.setAction( MovieServiceAIDL.class.getName() );
        context.bindService( intent, mConnection, Context.BIND_AUTO_CREATE );
    }

    public static final String TAG = "__TAG__";

    private ServiceConnection mConnection = new ServiceConnection() {

        // Called when the connection with the service is established
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.d(TAG, "onServiceConnected: ");
            // Following the example above for an AIDL interface,
            // this gets an instance of the IRemoteInterface, which we can use to call on the service
            movieService = MovieServiceAIDL.Stub.asInterface(service);
            connected = true;

            if ( onServiceConnected != null ){
                onServiceConnected.onConnected();
            }
        }

        // Called when the connection with the service disconnects unexpectedly
        public void onServiceDisconnected(ComponentName className) {
            Log.d(TAG, "onServiceDisconnected: ");
            movieService = null;
            connected = false;
        }
    };

    public MovieDetail getMovie(Integer id) throws RemoteException {
        if ( connected ){
            Bundle b = movieService.movie(id);
            return (MovieDetail) b.getSerializable("data");
        }
        return null;
    }

    public void setOnServiceConnected(OnServiceConnected onServiceConnected){
        this.onServiceConnected = onServiceConnected;
        if ( connected ){
            this.onServiceConnected.onConnected();
        }
    }

    OnServiceConnected onServiceConnected = null;

    public interface OnServiceConnected{
        void onConnected();
    }

}

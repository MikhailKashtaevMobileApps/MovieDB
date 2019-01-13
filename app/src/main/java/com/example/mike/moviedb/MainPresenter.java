package com.example.mike.moviedb;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.movieservice.MovieService;
import com.example.movieservice.MovieServiceAIDL;
import com.example.movieservice.model.Movie;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter {

    private static MainPresenter instance;
    private MovieServiceAIDL movieService;
    private Boolean connected;
    public static final String TAG = "__TAG__";

    private ServiceConnection mConnection = new ServiceConnection() {

        // Called when the connection with the service is established
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.d(TAG, "onServiceConnected: ");
            // Following the example above for an AIDL interface,
            // this gets an instance of the IRemoteInterface, which we can use to call on the service
            movieService = MovieServiceAIDL.Stub.asInterface(service);
            connected = true;
        }

        // Called when the connection with the service disconnects unexpectedly
        public void onServiceDisconnected(ComponentName className) {
            Log.d(TAG, "onServiceDisconnected: ");
            movieService = null;
            connected = false;
        }
    };

    public MainPresenter(Context context) {
        Intent intent = new Intent(context, MovieService.class);
        intent.setAction( MovieServiceAIDL.class.getName() );
        context.bindService( intent, mConnection, Context.BIND_AUTO_CREATE );
    }

    public static MainPresenter getInstance(Context context){
        if ( instance == null ){
            instance = new MainPresenter(context);
        }
        return instance;
    }

    public Single<List<Movie>> search(final String q, final Integer pageNum){
        if ( !connected ) return null;

        return Single.fromCallable(new Callable<List<Movie>>() {
                    @Override
                    public List<Movie> call() throws Exception {
                        Bundle b = movieService.search(q, pageNum);
                        return (List<Movie>) b.getSerializable("data");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}

package com.example.movieservice;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.movieservice.model.MovieResult;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

public class MovieService extends Service {

    MovieDBAPI api;

    public MovieService() {
        api = new MovieDBAPI(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final MovieServiceAIDL.Stub mBinder = new MovieServiceAIDL.Stub() {
        @Override
        public String ping() throws RemoteException {
            return "pong";
        }

        @Override
        public Bundle search(String aQuery, int pageNum) throws RemoteException {
            Bundle ret = new Bundle();

            try {
                ret.putSerializable("data", (Serializable) api.search(aQuery, pageNum));
                return ret;
            }catch (Exception e){
                System.out.println("__TAG__ "+e.getMessage());
                return ret;
            }
        }
    };

}

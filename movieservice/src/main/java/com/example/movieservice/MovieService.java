package com.example.movieservice;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

public class MovieService extends Service {
    public MovieService() {
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
            return ret;
        }
    };

}

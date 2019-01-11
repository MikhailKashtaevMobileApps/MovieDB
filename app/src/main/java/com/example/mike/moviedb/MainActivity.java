package com.example.mike.moviedb;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.movieservice.MovieService;
import com.example.movieservice.MovieServiceAIDL;

public class MainActivity extends AppCompatActivity {

    MovieServiceAIDL movieService;
    Boolean connected = false;
    public static final String TAG = "__TAG__";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, MovieService.class);
        intent.setAction( MovieServiceAIDL.class.getName() );
        bindService( intent, mConnection, Context.BIND_AUTO_CREATE );
    }

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

    public void ping(View view) {
        if (connected){
            try {
                Log.d(TAG, "onCreate: "+movieService.ping());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}

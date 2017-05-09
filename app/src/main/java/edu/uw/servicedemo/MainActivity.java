package edu.uw.servicedemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener, ServiceConnection {

    private static final String TAG = "Main";

    private MediaPlayer player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        player = MediaPlayer.create(this, R.raw.scott_joplin_the_entertainer_1902);
//        player.setOnCompletionListener(this);

    }


    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(MainActivity.this, MusicService.class);
        bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    //when "Start" button is pressed
    public void handleStart(View v){
        Log.i(TAG, "Start pressed");
        Intent intent = new Intent(MainActivity.this, CountingService.class);
        startService(intent);

    }

    //when "Stop" button is pressed
    public void handleStop(View v){
        Log.i(TAG, "Stop pressed");

    }


    /* Media controls */
    public void playMedia(View v){
//        player.start();
        startService(new Intent(MainActivity.this, MusicService.class));


    }

    public void pauseMedia(View v){
//        player.pause();
        musicBinder.pauseMusic();

    }

    public void stopMedia(View v){
//        player.stop();
        stopService(new Intent(MainActivity.this, MusicService.class));
    }


    //when "Quit" button is pressed
    public void handleQuit(View v){
        finish();
        //end the Activity
//        handleStop(null);
//        player.release();
//        player = null;
    }

    @Override
    protected void onDestroy() {
        Log.v(TAG, "Activity destroyed");
        super.onDestroy();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
//        mediaPlayer.release();
    }

    private MusicService.LocalBinder musicBinder;

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder service) {
        musicBinder = (MusicService.LocalBinder) service;

    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }
}

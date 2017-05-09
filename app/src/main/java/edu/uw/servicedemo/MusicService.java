package edu.uw.servicedemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Leon on 5/9/17.
 */

public class MusicService extends Service {
    private static final String TAG = "MusicService";
    private MediaPlayer player;

    private String songTitle = "The Entertainer";

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (player == null)
        player = MediaPlayer.create(this, R.raw.scott_joplin_the_entertainer_1902);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(), 0, new Intent(getApplicationContext(), MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setContentTitle("Music Player")
                .setContentText("Now Playing: " + songTitle)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .build();

        startForeground(0, notification);


        player.start();
        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return myBinder;
    }

    private final IBinder myBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public void pauseMusic() {
            player.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        player.stop();
        player.release();
        player = null;
    }
}

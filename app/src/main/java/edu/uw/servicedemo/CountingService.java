package edu.uw.servicedemo;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Leon on 5/9/17.
 */

public class CountingService extends IntentService {

    public static final String TAG = "CountingService";
    private int count;
    private Handler mHandler;

    public CountingService() {
        super("CountingService");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mHandler = new Handler();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.v(TAG, "Received: " + intent.toString());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.v(TAG, "Handling intent");
        for (int count=0; count < 10; count++) {
            Log.v(TAG, "count: " + count);

            final int finalCount = count;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(CountingService.this, "" + finalCount, Toast.LENGTH_SHORT).show();
                }
            });
            try {
                Thread.sleep(5000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

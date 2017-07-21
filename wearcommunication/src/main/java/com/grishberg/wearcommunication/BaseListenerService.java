package com.grishberg.wearcommunication;

import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by bourdi_bay on 01/02/2015.
 */
public class BaseListenerService extends WearableListenerService {
    protected MessageSender messageSender = null;

    @Override
    public void onCreate() {
        super.onCreate();
        messageSender = new MessageSender(this);
    }

    @Override
    public void onDestroy() {
        messageSender.waitForAllThreadsToFinish();
        super.onDestroy();
    }

    protected void showToast(final String message) {
        Handler mHandler = new Handler(getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

}

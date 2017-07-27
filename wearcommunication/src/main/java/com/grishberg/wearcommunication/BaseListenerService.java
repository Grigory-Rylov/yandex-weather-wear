package com.grishberg.wearcommunication;

import android.os.Handler;
import android.support.annotation.MainThread;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by bourdi_bay on 01/02/2015.
 */
public abstract class BaseListenerService<T extends WearMessage> extends WearableListenerService {
    private MessageSender<T> messageSender = null;

    @Override
    public void onCreate() {
        super.onCreate();
        messageSender = new MessageSender<>(this);
    }

    @Override
    public void onDestroy() {
        messageSender.waitForAllThreadsToFinish();
        super.onDestroy();
    }

    @Deprecated
    protected void showToast(final String message) {
        Handler mHandler = new Handler(getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        final T payload = createPayloadFromBytes(messageEvent.getData());
        Handler mHandler = new Handler(getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onMessageReceivedWithData(payload);
            }
        });
    }

    protected void sendMessage(T message) {
        messageSender.sendMessage(message);
    }

    protected abstract T createPayloadFromBytes(byte[] bytes);

    @MainThread
    protected abstract void onMessageReceivedWithData(T data);
}

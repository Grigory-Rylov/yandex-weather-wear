package com.grishberg.yandexweatherwear;

import com.google.android.gms.wearable.MessageEvent;
import com.grishberg.wearcommunication.BaseListenerService;


public class ListenerService extends BaseListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        showToast(messageEvent.getPath());

        // Perform actions like network requests, etc...

        // Send back an answer to the wear.
        messageSender.sendMessage("Current weather is ...");
    }
}


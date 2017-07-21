package com.grishberg.yandexweatherwear;

import com.google.android.gms.wearable.MessageEvent;
import com.grishberg.wearcommunication.BaseListenerService;

/**
 * Created by bourdi_bay on 31/01/2015.
 */
public class ListenerService extends BaseListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        showToast(messageEvent.getPath());
    }

}
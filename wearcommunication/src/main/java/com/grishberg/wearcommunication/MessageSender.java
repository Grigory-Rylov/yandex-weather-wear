package com.grishberg.wearcommunication;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by bourdi_bay on 31/01/2015.
 */
public class MessageSender<T extends WearMessage> {
    private static final long CONNECTION_TIME_OUT = 1; // seconds

    private GoogleApiClient googleApiClient;
    private String nodeId;
    private Thread retrieveDeviceNodeThread = null;
    private Thread messageThread = null;

    /**
     * Initialize the GoogleApiClient and get the Node ID of the connected device.
     */
    public MessageSender(Context context) {
        googleApiClient = getGoogleApiClient(context);
        retrieveDeviceNode();
    }

    private GoogleApiClient getGoogleApiClient(Context context) {
        return new GoogleApiClient.Builder(context)
                .addApi(Wearable.API)
                .build();
    }

    /**
     * Connect to the GoogleApiClient and retrieve the connected device's Node ID. If there are
     * multiple connected devices, the first Node ID is returned.
     */
    private void retrieveDeviceNode() {
        retrieveDeviceNodeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ConnectionResult connectionResult = googleApiClient
                        .blockingConnect(CONNECTION_TIME_OUT, TimeUnit.SECONDS);
                if (connectionResult.isSuccess() == true) {
                    NodeApi.GetConnectedNodesResult result =
                            Wearable.NodeApi.getConnectedNodes(googleApiClient).await();
                    List<Node> nodes = result.getNodes();
                    if (!nodes.isEmpty()) {
                        nodeId = nodes.get(0).getId();
                    }
                    googleApiClient.disconnect();
                }
            }
        });
        retrieveDeviceNodeThread.start();
    }

    /**
     * Send a message to the connected mobile device.
     */
    public void sendMessage(@NonNull final T message) {

        // Ensure we finished to try to get the nodeID first.
        if (retrieveDeviceNodeThread != null) {
            try {
                retrieveDeviceNodeThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (nodeId != null) {
            messageThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    googleApiClient.blockingConnect(CONNECTION_TIME_OUT, TimeUnit.SECONDS);
                    byte[] messageAsBytes = message.toBytes();
                    Wearable.MessageApi.sendMessage(googleApiClient, nodeId, null, messageAsBytes);
                    googleApiClient.disconnect();
                }
            });
            messageThread.start();
        }
    }

    public void waitForAllThreadsToFinish() {
        try {
            if (retrieveDeviceNodeThread != null) {
                retrieveDeviceNodeThread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            if (messageThread != null) {
                messageThread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

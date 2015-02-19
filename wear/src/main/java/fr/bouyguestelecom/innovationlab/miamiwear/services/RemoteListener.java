package fr.bouyguestelecom.innovationlab.miamiwear.services;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import fr.bouyguestelecom.innovationlab.miamiwear.utils.Constants;

/**
 * Created by InnovationLab on 20/08/2014 for Miami Wear
 * Permet d'envoyer les code touches de la télécommande
 */
public class RemoteListener extends WearableListenerService
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "RemoteListener";
    public static GoogleApiClient mGoogleApiClient;
    LocalBroadcastManager broadcaster;
    private static boolean isConnected;

    public static void sendMessage(final String path) {
        sendMessage(path,null);
    }

    public static void sendMessage(final String path, DataMap dataMap) {
        Log.i(TAG, "sendMessage(" + path + ")");
        if (mGoogleApiClient.isConnected()) {
            final byte[] data = dataMap != null ? dataMap.toByteArray() : new byte[0];
            new Thread(new Runnable() {
                @Override
                public void run() {
                    NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();
//                    try {
                    if (nodes.getNodes() != null)
                        for (Node node : nodes.getNodes()) { // TODO: si pas de nodes
                            MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                                    mGoogleApiClient, node.getId(), path, data
                            ).await();
                            if (!result.getStatus().isSuccess()) {
                                Log.e(TAG, "error");
                            } else {
                                Log.i(TAG, "success!! \"" + path + "\" sent to: " + node.getDisplayName());
                            }
                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        Log.e(TAG, String.valueOf(e));
//                    }
                }
            }).start();
        } else {
            Log.e(TAG, "not connected");
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, TAG + " started");
        broadcaster = LocalBroadcastManager.getInstance(this);

        isConnected = false;
        mGoogleApiClient = new GoogleApiClient.Builder(this.getApplicationContext())
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        // TODO: startService(new Intent(this, WearListener.class)); ???
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (!isConnected) {
            Log.i(TAG, "onConnected");
            broadcaster = LocalBroadcastManager.getInstance(this);
            Intent intent = new Intent(Constants.REMOTE_STARTED);
            broadcaster.sendBroadcast(intent);

        }
        RemoteListener.sendMessage(Constants.IS_ALIVE);
        isConnected = true;
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "onConnectionSuspended");
        isConnected = false;

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "onConnectionFailed");

    }


    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d(TAG, "onMessageReceived "+messageEvent.getPath());
        super.onMessageReceived(messageEvent);
        if (messageEvent.getPath().startsWith(Constants.IS_ALIVE)) {
            final boolean alive = Boolean.valueOf(messageEvent.getPath().substring(Constants.IS_ALIVE.length()));
            broadcaster.sendBroadcast(new Intent(Constants.IS_ALIVE).putExtra("alive", alive));
        }
    }
}

package fr.bouyguestelecom.innovationlab.miamiwear.services;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.data.FreezableUtils;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import fr.bouyguestelecom.innovationlab.miamiwear.models.Program;
import fr.bouyguestelecom.innovationlab.miamiwear.utils.Constants;
import fr.bouyguestelecom.innovationlab.miamiwear.utils.EPG;

/**
 * Created by InnovationLab on 20/08/2014 for Miami Wear
 * Permet de récupérer les données envoyées par la montre et de les traiter (il faut 2 listeners parce que j'ai l'impression qu'un seul ne peut pas à la fois envoyer des message et surveiller des data)
 */
public class DataListener extends WearableListenerService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "DataListener";
    public static GoogleApiClient mGoogleApiClient;
    LocalBroadcastManager broadcaster;
    private static boolean isConnected;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, TAG + " started");
        broadcaster = LocalBroadcastManager.getInstance(this);
        isConnected = false;

        mGoogleApiClient = new GoogleApiClient.Builder(this.getApplicationContext())
                .addApi(Wearable.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d(TAG, "onMessageReceived "+messageEvent.getPath());
        super.onMessageReceived(messageEvent);
        if (messageEvent.getPath().equals(Constants.INIT_INFOS)) {
            byte[] data = messageEvent.getData();
            DataMap dataMap = DataMap.fromByteArray(data);
            EPG.setCurrentChannel(dataMap.getInt(Constants.INIT_INDEX));
            Log.d(TAG, "init_index="+dataMap.getInt(Constants.INIT_INDEX));
        } else if (messageEvent.getPath().startsWith(Constants.IS_ALIVE)) {
            final boolean alive = Boolean.valueOf(messageEvent.getPath().substring(Constants.IS_ALIVE.length()));
            broadcaster.sendBroadcast(new Intent(Constants.IS_ALIVE).putExtra("alive", alive));
        }
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        Log.d(TAG, "onDataChanged");
        final List<DataEvent> events = FreezableUtils.freezeIterable(dataEvents);
        dataEvents.close();
        super.onDataChanged(dataEvents);
        for (DataEvent event : events) {
            if (event.getType() == DataEvent.TYPE_DELETED) {
                Log.d(TAG, "DataItem deleted: " + event.getDataItem().getUri());
            } else if (event.getType() == DataEvent.TYPE_CHANGED) {
                Log.d(TAG, "DataItem changed: " + event.getDataItem().getUri());
                if (event.getDataItem().getUri().getPath().equals(Constants.EPG_DATA)) {
                    DataMap dataMap = DataMapItem.fromDataItem(event.getDataItem()).getDataMap();
                    Log.d(TAG, "EPG data changed at " + String.valueOf(dataMap.getDouble(Constants.EPG_TIMESTAMP)) + "\n" + String.valueOf(dataMap.getInt(Constants.EPG_COUNT)) + " new elements");
                    DataMap channelDataMap;
                    for(int i=0;i<dataMap.getInt(Constants.EPG_COUNT);i++) {
                        channelDataMap = dataMap.getDataMap(String.valueOf(i));
                        Log.d(TAG,"EPG channel "+channelDataMap.getInt(Constants.EPG_CHANNEL_NUMBER)+": "+channelDataMap.getString(Constants.EPG_NAME) +"\n"+channelDataMap.getString(Constants.EPG_STARTTIME)+" -> "+channelDataMap.getString(Constants.EPG_ENDTIME));
                        Program program = new Program(
                                channelDataMap.getString(Constants.EPG_NAME),
                                channelDataMap.getString(Constants.EPG_DESC),
                                channelDataMap.getString(Constants.EPG_STARTTIME),
                                channelDataMap.getString(Constants.EPG_ENDTIME),
                                (channelDataMap.getAsset(Constants.EPG_IMAGE)==null?null:loadBitmapFromAsset(channelDataMap.getAsset(Constants.EPG_IMAGE)))
                        );
                        EPG.addEPGInfos(channelDataMap.getInt(Constants.EPG_CHANNEL_NUMBER), program);
                    }
                    Intent intent = new Intent(Constants.EPG_DATA);
                    broadcaster.sendBroadcast(intent);
                    Log.d(TAG, "localbroadcast "+Constants.EPG_DATA+" sent");
                }
            }
        }
    }

    public Bitmap loadBitmapFromAsset(Asset asset) {
        if (asset == null) {
            throw new IllegalArgumentException("Asset must be non-null");
        }
        ConnectionResult result =
                DataListener.mGoogleApiClient.blockingConnect(1000, TimeUnit.MILLISECONDS);
        if (!result.isSuccess()) {
            return null;
        }
        // convert asset into a file descriptor and block until it's ready
        InputStream assetInputStream = Wearable.DataApi.getFdForAsset(
                DataListener.mGoogleApiClient, asset).await().getInputStream();
        DataListener.mGoogleApiClient.disconnect();

        if (assetInputStream == null) {
            Log.w(TAG, "Requested an unknown Asset.");
            return null;
        }
        // decode the stream into a bitmap
        Bitmap bitmap = BitmapFactory.decodeStream(assetInputStream);
        try {
            assetInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  bitmap;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }



    @Override
    public void onConnected(Bundle bundle) {
        if (!isConnected) Log.i(TAG, "onConnected");
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
}

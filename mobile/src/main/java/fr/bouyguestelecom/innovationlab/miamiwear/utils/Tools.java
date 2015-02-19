package fr.bouyguestelecom.innovationlab.miamiwear.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;
import com.google.anymote.Key;

import fr.bouyguestelecom.innovationlab.miamiwear.listeners.PhoneListener;

/**
 * Created by InnovationLab on 20/08/2014 for Miami Wear
 */
public class Tools {
    private static final String TAG = "Tools";

    public static void sendMessage(final String path) {
        sendMessage(path, null);
    }

    public static void sendMessage(final String path, DataMap dataMap) {
        if (PhoneListener.mGoogleApiClient.isConnected()) {
            final byte[] data = dataMap != null ? dataMap.toByteArray() : new byte[0];
            new Thread(new Runnable() {
                @Override
                public void run() {
                    NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(PhoneListener.mGoogleApiClient).await();
                    for (Node node : nodes.getNodes()) {
                        MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                                PhoneListener.mGoogleApiClient, node.getId(), path, data
                        ).await();
                        if (!result.getStatus().isSuccess()) {
                            Log.e(TAG, "error");
                        } else {
                            Log.i(TAG, "success!! \"" + path + "\" sent to: " + node.getDisplayName());
                        }
                    }
                }
            }).start();
        } else {
            Log.e(TAG, "not connected");
        }
    }

    public static void zapTo(final int channel) throws InterruptedException {
        Log.d(TAG, "zapTo " + channel);

        if (channel > 9) zapTo(channel / 10);
        if (channel % 11 == 0) Thread.sleep(600);
        if (TV.isConnectedToMiami()) {
            switch (channel % 10) {
                case 0:
                    TV.getAnymoteSender().sendKeyPress(Key.Code.KEYCODE_0);
                    break;
                case 1:
                    TV.getAnymoteSender().sendKeyPress(Key.Code.KEYCODE_1);
                    break;
                case 2:
                    TV.getAnymoteSender().sendKeyPress(Key.Code.KEYCODE_2);
                    break;
                case 3:
                    TV.getAnymoteSender().sendKeyPress(Key.Code.KEYCODE_3);
                    break;
                case 4:
                    TV.getAnymoteSender().sendKeyPress(Key.Code.KEYCODE_4);
                    break;
                case 5:
                    TV.getAnymoteSender().sendKeyPress(Key.Code.KEYCODE_5);
                    break;
                case 6:
                    TV.getAnymoteSender().sendKeyPress(Key.Code.KEYCODE_6);
                    break;
                case 7:
                    TV.getAnymoteSender().sendKeyPress(Key.Code.KEYCODE_7);
                    break;
                case 8:
                    TV.getAnymoteSender().sendKeyPress(Key.Code.KEYCODE_8);
                    break;
                case 9:
                    TV.getAnymoteSender().sendKeyPress(Key.Code.KEYCODE_9);
                    break;
            }
        } else Log.e(TAG, "not connected to Miami");
    }

    public static int updateChannel(Context context, int channel) {
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        if (preference.getBoolean("fluxTNT", false)) return channel;
        switch (channel) {
            case 15:
                return 153;//BFM (jai mis business à la place)
            case 16:
                return 0;//iTV
            case 17:
                return 0;//D17
            case 18:
                return 164;//Gulli
            case 19:
                return 0;//France Ô
            default:
                return channel;
        }
    }
}

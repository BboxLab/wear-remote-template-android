package fr.bouyguestelecom.innovationlab.miamiwear.services;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.google.tv.anymotelibrary.client.AnymoteClientService;
import com.example.google.tv.anymotelibrary.client.AnymoteSender;

import fr.bouyguestelecom.innovationlab.miamiwear.utils.TV;
import fr.bouyguestelecom.tv.openapi.secondscreen.bbox.WOLPowerManager;

/**
 * Created by InnovationLab on 20/08/2014 for Miami Wear
 */
public class MyService extends Service implements AnymoteClientService.ClientListener {
    private static final String TAG = "MyService";
    private static String IP_PREFERENCE = "bboxIP";
    private static String MAC_PREFERENCE = "bboxMAC";
    public static Context mContext;

    public ServiceConnection mConnection;
    private AnymoteClientService mAnymoteClientService;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");

        mContext = getApplicationContext();

        initAnymoteConnection();

        super.onCreate();
    }

    private void initAnymoteConnection() {
        Log.d(TAG, "initAnymoteConnection");
        mConnection = new ServiceConnection() {
            /*
             * ServiceConnection listener methods.
             */
            public void onServiceConnected(ComponentName name, IBinder service) {
                mAnymoteClientService = ((AnymoteClientService.AnymoteClientServiceBinder) service).getService();
                mAnymoteClientService.attachClientListener(MyService.this);
            }

            public void onServiceDisconnected(ComponentName name) {
                mAnymoteClientService.detachClientListener(MyService.this);
                mAnymoteClientService = null;
            }
        };

        Intent intent = new Intent(MyService.this, AnymoteClientService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onConnected(AnymoteSender anymoteSender) {
        //TV.setAnymoteSender(anymoteSender);
        TV.mAnymoteSender = anymoteSender;
        Log.d(TAG, "onConnected");

        final String IP = mAnymoteClientService.getCurrentDevice().getAddress().getHostAddress();
        Log.d(TAG, "IP: " + IP);
        // We store the IP of the Bbox in the applications preferences.
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(IP_PREFERENCE, IP);
        editor.putString(MAC_PREFERENCE, WOLPowerManager.getMacFromArpCache(IP));
        editor.apply();
        TV.setIP(IP);
    }

    @Override
    public void onDisconnected() {
        Log.d(TAG, "onDisconnected");
        TV.setAnymoteSender(null);
    }

    @Override
    public void onConnectionFailed() {
        Log.d(TAG, "onConnectionFailed");
        TV.setAnymoteSender(null);
    }
}
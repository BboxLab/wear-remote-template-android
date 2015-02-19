package fr.bouyguestelecom.innovationlab.miamiwear.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.wearable.view.CircledImageView;
import android.util.Log;
import android.view.View;

import miamiwear.giom.ilab.com.miamiwear.R;
import fr.bouyguestelecom.innovationlab.miamiwear.services.WOLListener;
import fr.bouyguestelecom.innovationlab.miamiwear.utils.Constants;

/**
 * Created by InnovationLab on 20/08/2014 for Miami Wear
 * Plus utilisée...
 */
public class WOLActivity extends Activity {
    private static final String TAG = "WOLActivity";
    public static Context mContext;
    private CircledImageView button;
    BroadcastReceiver localReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        mContext = this;

        setContentView(R.layout.wol);

        button = (CircledImageView) findViewById(R.id.image_view);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WOLListener.sendMessage(Constants.REMOTE_WOL);
            }
        });

        startService(new Intent(this, WOLListener.class));

        localReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, final Intent intent) {
                Log.i(TAG, "localreceiver onReceive: " + intent.getAction());
                if (intent.getAction().equals(Constants.REMOTE_WOL_OK)) {
                    button.setVisibility(View.VISIBLE);
                    findViewById(R.id.text).setVisibility(View.VISIBLE);
                }
            }
        };

        LocalBroadcastManager.getInstance(mContext).registerReceiver((localReceiver), new IntentFilter(Constants.REMOTE_WOL_OK));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        stopService(new Intent(this, WOLListener.class));
        LocalBroadcastManager.getInstance(this).unregisterReceiver(localReceiver);
    }
}
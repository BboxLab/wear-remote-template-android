package fr.bouyguestelecom.innovationlab.miamiwear.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.wearable.view.GridViewPager;
import android.util.Log;
import android.view.View;

import fr.bouyguestelecom.innovationlab.miamiwear.R;
import fr.bouyguestelecom.innovationlab.miamiwear.adapters.RemoteGridPagerAdapter;
import fr.bouyguestelecom.innovationlab.miamiwear.services.DataListener;
import fr.bouyguestelecom.innovationlab.miamiwear.services.RemoteListener;
import fr.bouyguestelecom.innovationlab.miamiwear.utils.Constants;

/**
 * Created by InnovationLab on 20/08/2014 for Miami Wear
 */
public class RemoteGridActivity extends Activity {
    private static final String TAG = "RemoteActivity";
    @SuppressWarnings("FieldCanBeLocal")
    private GridViewPager gridViewPager;
    BroadcastReceiver localReceiver;
    public static Context mContext;
    private boolean launchedFromEpg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        mContext = this;

        setContentView(R.layout.remote_grid);

        startService(new Intent(this, RemoteListener.class));
        startService(new Intent(this, DataListener.class));

        gridViewPager = (GridViewPager) findViewById(R.id.pager);

        gridViewPager.setAdapter(new RemoteGridPagerAdapter(getFragmentManager()));
        localReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, final Intent intent) {
                Log.i(TAG, "localreceiver onReceive: " + intent.getAction());
                if (intent.getAction().equals(Constants.REMOTE_STARTED)) {
                    findViewById(R.id.splashscreen).setVisibility(View.GONE);
                }
            }
        };

        LocalBroadcastManager.getInstance(mContext).registerReceiver((localReceiver), new IntentFilter(Constants.REMOTE_STARTED));

        try {
            launchedFromEpg =  getIntent().getExtras().getBoolean("launchedFromEpg", false);
        } catch (Exception ignored) {

        }
    }

    @Override
    protected void onResume() {
        if (RemoteListener.mGoogleApiClient != null && RemoteListener.mGoogleApiClient.isConnected()) RemoteListener.sendMessage(Constants.IS_ALIVE);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        if (launchedFromEpg) WearActivity.mustNotBeUpdated = true;
        stopService(new Intent(this, RemoteListener.class));
        LocalBroadcastManager.getInstance(this).unregisterReceiver(localReceiver);
    }
}
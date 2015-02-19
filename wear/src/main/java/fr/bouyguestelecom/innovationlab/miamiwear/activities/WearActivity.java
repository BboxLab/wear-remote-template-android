package fr.bouyguestelecom.innovationlab.miamiwear.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import fr.bouyguestelecom.innovationlab.miamiwear.R;
import fr.bouyguestelecom.innovationlab.miamiwear.adapters.EmptyGridPagerAdapter;
import fr.bouyguestelecom.innovationlab.miamiwear.adapters.GridPagerAdapter;
import fr.bouyguestelecom.innovationlab.miamiwear.services.DataListener;
import fr.bouyguestelecom.innovationlab.miamiwear.services.MessageListener;
import fr.bouyguestelecom.innovationlab.miamiwear.utils.Constants;
import fr.bouyguestelecom.innovationlab.miamiwear.utils.EPG;

/**
 * Created by InnovationLab on 20/08/2014 for Miami Wear
 */
public class WearActivity extends Activity {
    private static final String TAG = "WearActivity";
    private GridViewPager gridViewPager;
    FragmentGridPagerAdapter adapter;
    BroadcastReceiver localReceiver;
    public static Context mContext;
    public static boolean mustNotBeUpdated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        mContext = this;

        setContentView(R.layout.grid);

        gridViewPager = (GridViewPager) findViewById(R.id.pager);

        initGridPager(EPG.getChannelCount());

        startService(new Intent(this, DataListener.class));
        startService(new Intent(this, MessageListener.class));

        localReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, final Intent intent) {
                Log.i(TAG, "localreceiver onReceive: " + intent.getAction());
                if (intent.getAction().equals(Constants.EPG_DATA)) {
                    adapter = new GridPagerAdapter(context, getFragmentManager());
                    gridViewPager.setAdapter(adapter);
                    Runnable dirtyHack = new Runnable() {
                        @Override
                        public void run() {
                            final int currentChannel = EPG.getCurrentChannel();
                            Log.i(TAG, "setCurrentItem:"+currentChannel);
                            if (currentChannel < gridViewPager.getAdapter().getRowCount()) gridViewPager.setCurrentItem(currentChannel-1, 0);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            findViewById(R.id.splashscreen).setVisibility(View.GONE);
                            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                        }
                    };
                    Handler handler = new Handler();
                    handler.postDelayed(dirtyHack, 1000);
                }
            }
        };

        LocalBroadcastManager.getInstance(mContext).registerReceiver((localReceiver), new IntentFilter(Constants.EPG_DATA));
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        if (!mustNotBeUpdated && MessageListener.mGoogleApiClient != null && MessageListener.mGoogleApiClient.isConnected())
            MessageListener.sendMessage(Constants.START_WEAR_APPLICATION);
        mustNotBeUpdated = false;
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        stopService(new Intent(this, DataListener.class));
        stopService(new Intent(this, MessageListener.class));
        LocalBroadcastManager.getInstance(this).unregisterReceiver(localReceiver);
    }

    public void initGridPager(final int count) {
        Log.i(TAG, "initGridPager(" + count + ")");
        adapter = new EmptyGridPagerAdapter(getFragmentManager(), count);
        gridViewPager.setAdapter(adapter);
    }
}
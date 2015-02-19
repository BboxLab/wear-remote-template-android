package fr.bouyguestelecom.innovationlab.miamiwear.activities;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Switch;

import miamiwear.giom.ilab.com.miamiwear.R;
import fr.bouyguestelecom.innovationlab.miamiwear.listeners.PhoneListener;
import fr.bouyguestelecom.innovationlab.miamiwear.services.MyService;

/**
 * Created by InnovationLab on 20/08/2014 for Miami Wear
 */
public class PhoneActivity extends Activity {
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        startService(new Intent(this, PhoneListener.class));

        findViewById(R.id.connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(mContext, MyService.class));
                startService(new Intent(mContext, PhoneListener.class));
            }
        });
        findViewById(R.id.disconnect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(mContext, MyService.class));
                findViewById(R.id.disconnect).setVisibility(View.GONE);
                findViewById(R.id.connect).setVisibility(View.VISIBLE);
            }
        });
    }

    private boolean isMyServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (MyService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isMyServiceRunning()) {
            findViewById(R.id.connect).setVisibility(View.GONE);
            findViewById(R.id.disconnect).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.disconnect).setVisibility(View.GONE);
            findViewById(R.id.connect).setVisibility(View.VISIBLE);
        }
        //startService(new Intent(this, PhoneListener.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //stopService(new Intent(this, PhoneListener.class));
    }

    public void onToggleClicked(View view) {
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preference.edit();
        editor.putBoolean("fluxTNT", ((Switch) view).isChecked());
        editor.apply();
    }
}
package fr.bouyguestelecom.innovationlab.miamiwear.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import fr.bouyguestelecom.innovationlab.miamiwear.listeners.PhoneListener;

/**
 * Created by InnovationLab on 20/08/2014 for Miami Wear
 */
public class StartReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("StartReceiver", "Start");
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            context.startService(new Intent(context, PhoneListener.class));
        }
    }
}

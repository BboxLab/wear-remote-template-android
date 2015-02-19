package fr.bouyguestelecom.innovationlab.miamiwear;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import fr.bouyguestelecom.innovationlab.miamiwear.listeners.PhoneListener;

/**
 * Created by InnovationLab on 20/08/2014 for Miami Wear
 */
public class MobileApplication extends Application {
    final static String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        //Toast.makeText(this, "Service lancé", Toast.LENGTH_LONG).show();
        startService(new Intent(this, PhoneListener.class));
    }
}

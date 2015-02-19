package fr.bouyguestelecom.innovationlab.miamiwear;

import android.app.Application;
import android.content.Context;

/**
 * Created by InnovationLab on 20/08/2014 for Miami Wear
 */
public class WearApplication extends Application {

    private static WearApplication instance;

    public WearApplication() {
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }
}

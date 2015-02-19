package fr.bouyguestelecom.innovationlab.miamiwear.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.google.tv.anymotelibrary.client.AnymoteSender;

/**
 * Created by InnovationLab on 20/08/2014 for Miami Wear
 */
public class TV {
    /**
     * The proxy used to send events to the server using Anymote Protocol
     */
    public static AnymoteSender mAnymoteSender;
    private static String mIP;
    private static String mSessionId;

    public static String getIP(Context context) {
        if (mIP != null) return mIP;
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        return preference.getString("bboxIP", null);
    }

    public static void setIP(String ip) {
        mIP = ip;
    }

    public static boolean isConnectedToMiami() {
        return mAnymoteSender != null;
    }

    public static AnymoteSender getAnymoteSender() {
        return mAnymoteSender;
    }

    public static void setAnymoteSender(AnymoteSender anymoteSender) {
        mAnymoteSender = anymoteSender;
    }

    public static void setSessionId(String sessionId) {
        mSessionId = sessionId;
    }

    public static String getSessionId() {
        return mSessionId;
    }
}

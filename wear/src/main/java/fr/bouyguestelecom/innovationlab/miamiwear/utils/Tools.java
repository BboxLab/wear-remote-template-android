package fr.bouyguestelecom.innovationlab.miamiwear.utils;

import android.content.Context;
import android.content.Intent;
import android.support.wearable.activity.ConfirmationActivity;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import fr.bouyguestelecom.innovationlab.miamiwear.activities.WearActivity;
import fr.bouyguestelecom.innovationlab.miamiwear.services.MessageListener;

/**
 * Created by InnovationLab on 20/08/2014 for Miami Wear
 */
public class Tools {
    private static final String TAG = "Tools";

    private static Date parseEPGTime(String time) {
        Log.d(TAG, "parseEPGTime: "+time);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"); //2014-10-06T16:10:00.000Z
        final Date date;
        try {
            date = df.parse(time.replaceAll("Z$", "+0000"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return date;
    }

    public static String EPGTimeToString(String time) {
        Log.d(TAG, "EPGTimeToString: "+time);
        final Date date = parseEPGTime(time);
        if (date == null) return "";
        DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT")); // TODO: bizarre, on est pas en GMT...
        String result = DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
        if (result.startsWith("0")) result = result.substring(1);
        return result;
    }

    public static int computeProgress(String startTimeString, String endTimeString) {
        Log.d(TAG, "computeProgress: "+endTimeString+" - "+startTimeString);
        if (startTimeString == null || endTimeString == null) return 0;
        final long currentTime = System.currentTimeMillis();
        final long startTime = parseEPGTime(startTimeString).getTime();
        final long endTime = parseEPGTime(endTimeString).getTime();
        Log.d(TAG, "Progress: "+(int) (100*(currentTime-startTime)/(endTime-startTime)));
        return (int) (100*(currentTime-startTime)/(endTime-startTime));
    }

    public static void changeChannel(Context context, int channelId) {
        MessageListener.sendMessage(Constants.ACTION_CHANGE_CHANNEL + channelId); // TODO: id !!!
        WearActivity.mustNotBeUpdated = true;

        Intent i = new Intent(context, ConfirmationActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION)
                .putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.OPEN_ON_PHONE_ANIMATION)
                .putExtra(ConfirmationActivity.EXTRA_MESSAGE, "Zapping effectué !");
        context.startActivity(i);
    }
}

package fr.bouyguestelecom.innovationlab.miamiwear.utils;

import android.util.Log;
import android.util.SparseArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.bouyguestelecom.innovationlab.miamiwear.WearApplication;
import fr.bouyguestelecom.innovationlab.miamiwear.models.Channel;
import fr.bouyguestelecom.innovationlab.miamiwear.models.Program;

/**
 * Created by InnovationLab on 20/08/2014 for Miami Wear
 */
public class EPG {
    private static SparseArray<Channel> channelSparseArray; // TODO: quelque chose de plus ordonné ?
    private static SparseArray<Program> epgSparseArray; // TODO: quelque chose de plus ordonné ?
    private static int sCurrentChannel = 0;
    private static boolean hasBeenInitialized = false;
    private static final String json =
            "[\n" +
            "      {\n" +
            "        \"positionId\": \"1\",\n" +
            "        \"epgChannelNumber\": \"192\",\n" +
            "        \"logo\": \"1.png\",\n" +
            "        \"nom\": \"TF1\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"positionId\": \"2\",\n" +
            "        \"epgChannelNumber\": \"4\",\n" +
            "        \"logo\": \"2.png\",\n" +
            "        \"nom\": \"France 2\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"positionId\": \"3\",\n" +
            "        \"epgChannelNumber\": \"80\",\n" +
            "        \"logo\": \"3.png\",\n" +
            "        \"nom\": \"France 3\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"positionId\": \"4\",\n" +
            "        \"epgChannelNumber\": \"34\",\n" +
            "        \"logo\": \"4.png\",\n" +
            "        \"nom\": \"Canal+\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"positionId\": \"5\",\n" +
            "        \"epgChannelNumber\": \"47\",\n" +
            "        \"logo\": \"5.png\",\n" +
            "        \"nom\": \"France 5\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"positionId\": \"6\",\n" +
            "        \"epgChannelNumber\": \"118\",\n" +
            "        \"logo\": \"6.png\",\n" +
            "        \"nom\": \"M6\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"positionId\": \"7\",\n" +
            "        \"epgChannelNumber\": \"111\",\n" +
            "        \"logo\": \"7.png\",\n" +
            "        \"nom\": \"Arte HD\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"positionId\": \"8\",\n" +
            "        \"epgChannelNumber\": \"445\",\n" +
            "        \"logo\": \"8.png\",\n" +
            "        \"nom\": \"D8\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"positionId\": \"9\",\n" +
            "        \"epgChannelNumber\": \"119\",\n" +
            "        \"logo\": \"9.png\",\n" +
            "        \"nom\": \"W9\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"positionId\": \"10\",\n" +
            "        \"epgChannelNumber\": \"195\",\n" +
            "        \"logo\": \"10.png\",\n" +
            "        \"nom\": \"TMC\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"positionId\": \"11\",\n" +
            "        \"epgChannelNumber\": \"446\",\n" +
            "        \"logo\": \"11.png\",\n" +
            "        \"nom\": \"NT1\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"positionId\": \"12\",\n" +
            "        \"epgChannelNumber\": \"444\",\n" +
            "        \"logo\": \"12.png\",\n" +
            "        \"nom\": \"NRJ 12\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"positionId\": \"13\",\n" +
            "        \"epgChannelNumber\": \"234\",\n" +
            "        \"logo\": \"13.png\",\n" +
            "        \"nom\": \"La Chaîne parlementaire\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"positionId\": \"14\",\n" +
            "        \"epgChannelNumber\": \"78\",\n" +
            "        \"logo\": \"14.png\",\n" +
            "        \"nom\": \"France 4\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"positionId\": \"15\",\n" +
            "        \"epgChannelNumber\": \"481\",\n" +
            "        \"logo\": \"15.png\",\n" +
            "        \"nom\": \"BFM TV\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"positionId\": \"16\",\n" +
            "        \"epgChannelNumber\": \"226\",\n" +
            "        \"logo\": \"16.png\",\n" +
            "        \"nom\": \"I Télé\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"positionId\": \"17\",\n" +
            "        \"epgChannelNumber\": \"458\",\n" +
            "        \"logo\": \"17.png\",\n" +
            "        \"nom\": \"D17\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"positionId\": \"18\",\n" +
            "        \"epgChannelNumber\": \"482\",\n" +
            "        \"logo\": \"18.png\",\n" +
            "        \"nom\": \"Gulli\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"positionId\": \"19\",\n" +
            "        \"epgChannelNumber\": \"160\",\n" +
            "        \"logo\": \"19.png\",\n" +
            "        \"nom\": \"France Ô\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"positionId\": \"20\",\n" +
            "        \"epgChannelNumber\": \"1404\",\n" +
            "        \"logo\": \"107.png\",\n" +
            "        \"nom\": \"HD1\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"positionId\": \"21\",\n" +
            "        \"epgChannelNumber\": \"1401\",\n" +
            "        \"logo\": \"108.png\",\n" +
            "        \"nom\": \"L'Equipe 21\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"positionId\": \"22\",\n" +
            "        \"epgChannelNumber\": \"1403\",\n" +
            "        \"logo\": \"109.png\",\n" +
            "        \"nom\": \"6Ter\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"positionId\": \"23\",\n" +
            "        \"epgChannelNumber\": \"1402\",\n" +
            "        \"logo\": \"131.png\",\n" +
            "        \"nom\": \"Numéro 23\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"positionId\": \"24\",\n" +
            "        \"epgChannelNumber\": \"1400\",\n" +
            "        \"logo\": \"132.png\",\n" +
            "        \"nom\": \"RMC Découverte\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"positionId\": \"25\",\n" +
            "        \"epgChannelNumber\": \"1399\",\n" +
            "        \"logo\": \"133.png\",\n" +
            "        \"nom\": \"Chérie 25\"\n" +
            "      }\n" +
            "    ]";
    final static String TAG = "PDS";
    private static int sChannelCount;

    public static void initPDS() {
        Log.d(TAG, "initPDS");
        epgSparseArray = new SparseArray<Program>();
        try {
            final JSONArray channelsJSONArray = new JSONArray(json);
            channelSparseArray = new SparseArray<Channel>();
            for (int i = 0; i < channelsJSONArray.length(); i++) {
                final JSONObject channelJSONObject = channelsJSONArray.getJSONObject(i);
                final Channel channel = new Channel(
                        channelJSONObject.getInt("positionId"),
                        channelJSONObject.getInt("epgChannelNumber"),
                        WearApplication.getContext().getResources().getIdentifier("c" + channelJSONObject.getString("logo").replace(".png", ""), "drawable", WearApplication.getContext().getPackageName()),
                        channelJSONObject.getString("nom")
                        );
                Log.d(TAG, "icon "+"c" + channelJSONObject.getString("logo")+": "+ WearApplication.getContext().getResources().getIdentifier("c" + channelJSONObject.getString("logo").replace(".png",""), "drawable", WearApplication.getContext().getPackageName()));

                channelSparseArray.put(channelJSONObject.getInt("positionId"),channel);
            }
            sChannelCount = channelsJSONArray.length();
            hasBeenInitialized = true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    public static SparseArray<Channel> getPDS() {
        Log.d(TAG, "getPDS");
        if (!hasBeenInitialized) initPDS();
        return channelSparseArray;
    }

    public static Channel getChannelFromRow(int row) {
        return getChannel(Math.max(1,row+1));
    }

    public static Channel getChannel(int positionId) {
        Log.d(TAG, "getChannel "+positionId);
        if (!hasBeenInitialized) initPDS();
        return  channelSparseArray.get(positionId);
    }

    public static void addEPGInfos(int channelNumber, Program program) {
        Log.d(TAG, "addEPGInfos "+channelNumber);
        if (!hasBeenInitialized) initPDS();
        epgSparseArray.put(channelNumber, program);
    }

    public static int getChannelCount() {
        Log.d(TAG, "getChannelCount");
        if (!hasBeenInitialized) initPDS();
        return sChannelCount;
    }

    public static Program getProgram(int channelNumber) {
        Log.d(TAG, "getProgram "+channelNumber);
        if (!hasBeenInitialized) initPDS();
        return epgSparseArray.get(channelNumber); // TODO: et si c'est null ?
    }

    public static int getCurrentChannel() {
        return sCurrentChannel;
    }

    public static void setCurrentChannel(int currentChannel) {
        EPG.sCurrentChannel = currentChannel;
    }
}

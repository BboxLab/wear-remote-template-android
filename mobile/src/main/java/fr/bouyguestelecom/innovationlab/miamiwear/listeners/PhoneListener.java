package fr.bouyguestelecom.innovationlab.miamiwear.listeners;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;
import com.google.anymote.Key;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import fr.bouyguestelecom.innovationlab.miamiwear.R;
import fr.bouyguestelecom.innovationlab.miamiwear.utils.Constants;
import fr.bouyguestelecom.innovationlab.miamiwear.utils.TV;
import fr.bouyguestelecom.innovationlab.miamiwear.utils.Tools;
import fr.bouyguestelecom.tv.openapi.secondscreen.bbox.WOLPowerManager;

/**
 * Created by InnovationLab on 20/08/2014 for Miami Wear
 */
public class PhoneListener extends WearableListenerService
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<DataApi.DeleteDataItemsResult> {
    private static final String TAG = "PhoneListener";
    public static GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");
        super.onCreate();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "onConnected");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed");
    }

    @Override
    public void onResult(DataApi.DeleteDataItemsResult deleteDataItemsResult) {
        Log.i(TAG, "onResult");
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d(TAG, "onMessageReceived " + messageEvent.getPath());
        Toast.makeText(getApplicationContext(), "Message reçu: " + messageEvent.getPath(), Toast.LENGTH_SHORT).show();

        if (messageEvent.getPath().equals(Constants.START_WEAR_APPLICATION)) {
            // Get Token security
            new GetSecurityTokenAsyncTask().execute();

            // Get EPG
            //---new getEPG().execute();

            // Données initiales (nombre de chaînes, chaîne en cours)
            //---new getCurrentChannel().execute(TV.getIP(getBaseContext()));

            //Tools.sendMessage(Constants.INIT_INFOS, dataMap);
            //Toast.makeText(getApplicationContext(), "Message envoyé: " + Constants.INIT_INFOS, Toast.LENGTH_LONG).show();
        } else if (messageEvent.getPath().startsWith(Constants.ACTION_CHANGE_CHANNEL)) {
            int channelId = Integer.parseInt(messageEvent.getPath().substring(Constants.ACTION_CHANGE_CHANNEL.length()));
            Log.d(TAG, "channel to zap: " + channelId);
            channelId = Tools.updateChannel(this, channelId);
            if (channelId > 0) try {
                Tools.zapTo(channelId);
            } catch (InterruptedException e) {
                Toast.makeText(this, "Problème avec les chaînes où les numéros se repêtent...", Toast.LENGTH_LONG);
                e.printStackTrace();
            }
        } else if (messageEvent.getPath().equals(Constants.REMOTE_VPLUS)) {
            if (TV.isConnectedToMiami())
                TV.getAnymoteSender().sendKeyPress(Key.Code.KEYCODE_VOLUME_UP);
        } else if (messageEvent.getPath().equals(Constants.REMOTE_VMOINS)) {
            if (TV.isConnectedToMiami())
                TV.getAnymoteSender().sendKeyPress(Key.Code.KEYCODE_VOLUME_DOWN);
        } else if (messageEvent.getPath().equals(Constants.REMOTE_PPLUS)) {
            if (TV.isConnectedToMiami())
                TV.getAnymoteSender().sendKeyPress(Key.Code.KEYCODE_CHANNEL_UP);
        } else if (messageEvent.getPath().equals(Constants.REMOTE_PMOINS)) {
            if (TV.isConnectedToMiami())
                TV.getAnymoteSender().sendKeyPress(Key.Code.KEYCODE_CHANNEL_DOWN);
        } else if (messageEvent.getPath().equals(Constants.REMOTE_MUTE)) {
            if (TV.isConnectedToMiami()) TV.getAnymoteSender().sendKeyPress(Key.Code.KEYCODE_MUTE);
        } else if (messageEvent.getPath().equals(Constants.REMOTE_UP)) {
            if (TV.isConnectedToMiami())
                TV.getAnymoteSender().sendKeyPress(Key.Code.KEYCODE_DPAD_UP);
        } else if (messageEvent.getPath().equals(Constants.REMOTE_DOWN)) {
            if (TV.isConnectedToMiami())
                TV.getAnymoteSender().sendKeyPress(Key.Code.KEYCODE_DPAD_DOWN);
        } else if (messageEvent.getPath().equals(Constants.REMOTE_LEFT)) {
            if (TV.isConnectedToMiami())
                TV.getAnymoteSender().sendKeyPress(Key.Code.KEYCODE_DPAD_LEFT);
        } else if (messageEvent.getPath().equals(Constants.REMOTE_RIGHT)) {
            if (TV.isConnectedToMiami())
                TV.getAnymoteSender().sendKeyPress(Key.Code.KEYCODE_DPAD_RIGHT); // TODO: Key.Code.BTN_RIGHT ???
        } else if (messageEvent.getPath().equals(Constants.REMOTE_OK)) {
            if (TV.isConnectedToMiami()) TV.getAnymoteSender().sendKeyPress(Key.Code.KEYCODE_ENTER);
        } else if (messageEvent.getPath().equals(Constants.REMOTE_BACK)) {
            if (TV.isConnectedToMiami())
                TV.getAnymoteSender().sendKeyPress(Key.Code.KEYCODE_BACK); // BTN_BACK ???
        } else if (messageEvent.getPath().equals(Constants.REMOTE_HOME)) {
            if (TV.isConnectedToMiami()) TV.getAnymoteSender().sendKeyPress(Key.Code.KEYCODE_HOME);
        } else if (messageEvent.getPath().equals(Constants.REMOTE_WOL)) {
            SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(this);
            WOLPowerManager.sendWOL(this, preference.getString("bboxMAC", ""), 1);
        } else if (messageEvent.getPath().equals(Constants.IS_ALIVE)) {
            new CheckIfAlive().execute(TV.getIP(getApplicationContext()));
        }
    }

    private class GetSecurityTokenAsyncTask extends AsyncTask<Void, Void, String> {
        final String TAG = "GetSecurityToken";

        @Override
        protected String doInBackground(Void... params) {
            Log.d(TAG, TAG);

            // Create a new HttpClient and Post Header
            HttpClient httpClient = new DefaultHttpClient();
            //HttpPost httpPost = new HttpPost("https://dev.bouyguestelecom.fr/security/token");
            HttpPost httpPost = new HttpPost(Constants.CLOUD_API + Constants.TOKEN);

            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("appId", getString(R.string.appId)));
                nameValuePairs.add(new BasicNameValuePair("appSecret", getString(R.string.appSecret)));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpClient.execute(httpPost);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                Log.d(TAG, "statusCode: " + statusCode);
                if (statusCode == 204) {
                    Header headerToken = response.getFirstHeader(Constants.HEADER_TOKEN);
                    Log.d(TAG, "token: " + headerToken.getValue());
                    return headerToken.getValue();
                } else {
                    Log.e(TAG, "Failed to get token");
                    return null;
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null) {
                new GetSessionIdAsyncTask(TV.getIP(getBaseContext()), result, new SessionIdCallback() {
                    @Override
                    public void onResult(boolean result) {
                        if (result) {
                            // Get EPG
                            new getEPG().execute();

                            // Données initiales (nombre de chaînes, chaîne en cours)
                            new getCurrentChannel().execute(TV.getIP(getBaseContext()));
                        } else {
                            Log.d(TAG, "ERROR sessionId");
                        }
                    }
                }).execute();
            } else {
                Log.d(TAG, "ERROR get token");
            }
        }
    }

    public interface SessionIdCallback {
        public void onResult(boolean result);
    }

    private class GetSessionIdAsyncTask extends AsyncTask<Void, Void, Boolean> {
        final String TAG = "GetSessionId";
        private String ip;
        private String token;

        private SessionIdCallback mListener;

        public GetSessionIdAsyncTask(String ip, String token, SessionIdCallback mListener) {
            this.token = token;
            this.ip = ip;
            this.mListener = mListener;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // Create a new HttpClient and Post Header
            HttpClient httpClient = new DefaultHttpClient();
            //HttpPost httpPost = new HttpPost("http://" + ip + ":8080/api.bbox.lan/v0/security/sessionId");
            HttpPost httpPost = new HttpPost(Constants.HTTP + ip + Constants.BOX_API + Constants.SESSION_ID);

            try {
                JSONObject jsonObjectToken = new JSONObject();
                try {
                    jsonObjectToken.put("token", token);
                    httpPost.setEntity(new ByteArrayEntity(jsonObjectToken.toString().getBytes("UTF8")));

                    // Execute HTTP Post Request
                    // adb shell am startservice -a "fr.bouyguestelecom.bboxapi.StartService" --user 0
                    HttpResponse response = httpClient.execute(httpPost);
                    StatusLine statusLine = response.getStatusLine();
                    int statusCode = statusLine.getStatusCode();
                    Log.d(TAG, "statusCode: " + statusCode);
                    if (statusCode == 204) {
                        Header headerSessionId = response.getFirstHeader(Constants.HEADER_SESSION_ID);
                        Log.d(TAG, "headerSessionId: " + headerSessionId.getValue());
                        TV.setSessionId(headerSessionId.getValue());
                        return true;
                    } else {
                        Log.e(TAG, "Failed to get sessionId");
                        return false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (mListener != null)
                mListener.onResult(result);
        }
    }

    private class getEPG extends AsyncTask<Void, Void, Void> {
        final String TAG = "getEPG";

        @Override
        protected Void doInBackground(Void... params) {
            Log.d(TAG, "doInBackgrousendKeyPressnd");
            StringBuilder builder = new StringBuilder();
            HttpClient client = new DefaultHttpClient();
            //HttpGet httpGet = new HttpGet("http://openbbox.flex.bouyguesbox.fr:81/v0/media/epg/live");
            HttpGet httpGet = new HttpGet(Constants.FLEX + Constants.EPG_LIVE);

            try {
                HttpResponse response = client.execute(httpGet);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if (statusCode == 200) {
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                } else {
                    Log.e(TAG, "Failed to download file");
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                final JSONArray channelsJSONArray = new JSONArray(builder.toString());
                DataMap dataMap;
                PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(Constants.EPG_DATA);
                for (int i = 0; i < channelsJSONArray.length(); i++) {
                    Log.d(TAG, "i=" + i);
                    final JSONObject channelJSONObject = channelsJSONArray.getJSONObject(i);
                    final JSONObject programInfo = channelJSONObject.getJSONObject("programInfo");
                    dataMap = new DataMap();
                    dataMap.putInt(Constants.EPG_ID, channelJSONObject.getInt("epgChannelNumber"));
                    Log.d(TAG, programInfo.getString("shortTitle"));
                    dataMap.putString(Constants.EPG_NAME, programInfo.getString("shortTitle"));
                    if (programInfo.has("longSummary"))
                        dataMap.putString(Constants.EPG_DESC, programInfo.getString("longSummary"));
                    else if (programInfo.has("shortSummary"))
                        dataMap.putString(Constants.EPG_DESC, programInfo.getString("shortSummary"));
                    else dataMap.putString(Constants.EPG_DESC, "");
                    dataMap.putString(Constants.EPG_STARTTIME, channelJSONObject.getString("startTime"));
                    dataMap.putString(Constants.EPG_ENDTIME, channelJSONObject.getString("endTime"));
                    if (channelJSONObject.has("media"))
                        dataMap.putAsset(Constants.EPG_IMAGE, getAssetFromURL(channelJSONObject.getJSONArray("media").getJSONObject(0).getString("url")));
                    else {
                        dataMap.putAsset(Constants.EPG_IMAGE, null);
                    }
                    Log.d(TAG, "epgChannelNumber: " + channelJSONObject.getInt("epgChannelNumber"));
                    dataMap.putInt(Constants.EPG_CHANNEL_NUMBER, channelJSONObject.getInt("epgChannelNumber"));
                    putDataMapRequest.getDataMap().putDataMap(String.valueOf(i), dataMap);
                }
                putDataMapRequest.getDataMap().putDouble(Constants.EPG_TIMESTAMP, (System.currentTimeMillis())); // Pour que les données changent effectivement
                putDataMapRequest.getDataMap().putInt(Constants.EPG_COUNT, channelsJSONArray.length());
                PutDataRequest request = putDataMapRequest.asPutDataRequest();
                PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi
                        .putDataItem(mGoogleApiClient, request);
                pendingResult.setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
                    @Override
                    public void onResult(DataApi.DataItemResult dataItemResult) {
                        Log.d(TAG, "onResult: " + dataItemResult.getStatus().toString());
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        public Asset getAssetFromURL(String src) {
            try {
                java.net.URL url = new java.net.URL("http://195.36.152.209" + src);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return Asset.createFromBytes(InputStreamToByteArray(input));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        private byte[] InputStreamToByteArray(InputStream is) throws IOException {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[16384];

            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();

            return buffer.toByteArray();
        }
    }


    private class getCurrentChannel extends AsyncTask<String, Void, Void> {
        final String TAG = "getCurrentChannel";

        @Override
        protected Void doInBackground(String... ip) {
            Log.d(TAG, "doInBackground");
            StringBuilder builder = new StringBuilder();
            HttpClient client = new DefaultHttpClient();
            //HttpGet httpGet = new HttpGet("http://" + ip[0] + ":8080/api.bbox.lan/v0/media");
            HttpGet httpGet = new HttpGet(Constants.HTTP + ip[0] + Constants.BOX_API + Constants.MEDIA);
            httpGet.setHeader("x-sessionid", TV.getSessionId());
            try {
                HttpResponse response = client.execute(httpGet);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if (statusCode == 200) {
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                    Log.d(TAG, "JSON: " + builder.toString());
                    final JSONObject mediaJSONObject = new JSONObject(builder.toString());
                    int currentChannel = mediaJSONObject.getInt("positionId");
                    if (currentChannel == 0) currentChannel = 1;
                    Log.d(TAG, "Current Channel: " + currentChannel);

                    DataMap dataMap = new DataMap();
                    dataMap.putInt(Constants.INIT_INDEX, currentChannel);
                    Tools.sendMessage(Constants.INIT_INFOS, dataMap);
                } else {
                    Log.e(TAG, "Failed to connect");
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private class CheckIfAlive extends AsyncTask<String, Void, Void> {
        final String TAG = "CheckIfAlive";

        @Override
        protected Void doInBackground(String... ip) {
            Log.d(TAG, "doInBackground");
            StringBuilder builder = new StringBuilder();
            HttpClient client = new DefaultHttpClient();
            //HttpGet httpGet = new HttpGet("http://" + ip[0] + ":8080/api.bbox.lan/v0/media");
            HttpGet httpGet = new HttpGet(Constants.HTTP + ip[0] + Constants.BOX_API + Constants.MEDIA);
            httpGet.setHeader("x-sessionid", TV.getSessionId());
            try {
                HttpResponse response = client.execute(httpGet);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if (statusCode == 200) {
                    Tools.sendMessage(Constants.IS_ALIVE + "true");
                } else {
                    Tools.sendMessage(Constants.IS_ALIVE + "false");
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
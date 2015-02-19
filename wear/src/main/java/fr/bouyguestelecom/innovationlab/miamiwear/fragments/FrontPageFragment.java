package fr.bouyguestelecom.innovationlab.miamiwear.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import miamiwear.giom.ilab.com.miamiwear.R;
import fr.bouyguestelecom.innovationlab.miamiwear.activities.RemoteGridActivity;
import fr.bouyguestelecom.innovationlab.miamiwear.utils.Tools;

/**
 * Created by InnovationLab on 20/08/2014 for Miami Wear
 */
@SuppressLint("ValidFragment")
public class FrontPageFragment extends CardFragment implements WatchViewStub.OnLayoutInflatedListener /*, implements WatchViewStub.OnLayoutInflatedListener*/ {
    private final String TAG = "PageFragment";
    private Context mContext;
    private String mTitle;
    private String mStartTime;
    private String mEndTime;
    private int icon = 0;
    private int mChannelNumber;
    @SuppressWarnings("FieldCanBeLocal")
    private ViewGroup mRootView;

    public FrontPageFragment(Context context, String title, String startTime, String endTime, int icon, int channelNumber) {
        this.mContext = context;
        this.mTitle = title;
        this.mStartTime = startTime;
        this.mEndTime = endTime;
        this.icon = icon;
        this.mChannelNumber = channelNumber;
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateContentView");
        // Hack pour la moto 360
        if (Build.MODEL.equals("Moto 360") || Build.MODEL.equals("LG G Watch R")) {
            mRootView = (ViewGroup) inflater.inflate(R.layout.page_round, container, false);
            ((TextView) mRootView.findViewById(R.id.title)).setText(mTitle);
            ((TextView) mRootView.findViewById(R.id.text)).setText(Tools.EPGTimeToString(mStartTime));
            if (icon > 0) ((ImageView) mRootView.findViewById(R.id.logo)).setImageResource(icon);
            ((ProgressBar) mRootView.findViewById(R.id.progressbar)).setProgress(Tools.computeProgress(mStartTime, mEndTime));
        } else { // Fonctionnement normal
            //View root = inflater.inflate(R.layout.page, container, false);
            mRootView = (ViewGroup) inflater.inflate(R.layout.page, container, false);

            //((WatchViewStub) root.findViewById(R.id.watch_view_stub)).setOnLayoutInflatedListener(this);
            ((WatchViewStub) mRootView.findViewById(R.id.watch_view_stub)).setOnLayoutInflatedListener(this);

            //return root;
        }

        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Action!");
                Tools.changeChannel(mContext, mChannelNumber);
            }
        });

        mRootView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(getActivity(), RemoteGridActivity.class).putExtra("launchedFromEpg", true));
                return false;
            }
        });

        return mRootView;
    }

    @Override
    public void onLayoutInflated(WatchViewStub watchViewStub) {
        //Log.i(TAG,"onLayoutInflated");

        ((TextView) watchViewStub.findViewById(R.id.title)).setText(mTitle);
        ((TextView) watchViewStub.findViewById(R.id.text)).setText(Tools.EPGTimeToString(mStartTime));
        if (icon > 0) {
            ((ImageView) watchViewStub.findViewById(R.id.logo)).setImageResource(icon);
        }
        ((ProgressBar) watchViewStub.findViewById(R.id.progressbar)).setProgress(Tools.computeProgress(mStartTime, mEndTime));
    }
}
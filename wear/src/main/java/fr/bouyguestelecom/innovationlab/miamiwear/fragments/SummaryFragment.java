package fr.bouyguestelecom.innovationlab.miamiwear.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.bouyguestelecom.innovationlab.miamiwear.R;
import fr.bouyguestelecom.innovationlab.miamiwear.activities.RemoteGridActivity;

/**
 * Created by InnovationLab on 20/08/2014 for Miami Wear
 */
@SuppressLint("ValidFragment")
public class SummaryFragment extends CardFragment {
    @SuppressWarnings("FieldCanBeLocal")
    private final String TAG = "SummaryFragment";
    private String title;
    private String text;
    @SuppressWarnings("FieldCanBeLocal")
    private ViewGroup mRootView;

    public SummaryFragment(String title, String text) {
        this.title = title;
        this.text = text;
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateContentView");
        if (Build.MODEL.equals("Moto 360") || Build.MODEL.equals("LG G Watch R"))
            mRootView = (ViewGroup) inflater.inflate(R.layout.page_round, container, false);
        else mRootView = (ViewGroup) inflater.inflate(R.layout.page_rect, container, false);
        ((TextView) mRootView.findViewById(R.id.title)).setText(title);
        ((TextView) mRootView.findViewById(R.id.text)).setText(text);
        mRootView.findViewById(R.id.logo).setVisibility(View.GONE);
        mRootView.findViewById(R.id.progressbar).setVisibility(View.GONE);

        mRootView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(getActivity(), RemoteGridActivity.class).putExtra("launchedFromEpg",true));
                return false;
            }
        });

        return mRootView;
    }
}

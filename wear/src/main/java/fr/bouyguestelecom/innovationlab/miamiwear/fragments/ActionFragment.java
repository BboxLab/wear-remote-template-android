package fr.bouyguestelecom.innovationlab.miamiwear.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.wearable.view.CircledImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import miamiwear.giom.ilab.com.miamiwear.R;
import fr.bouyguestelecom.innovationlab.miamiwear.activities.RemoteGridActivity;
import fr.bouyguestelecom.innovationlab.miamiwear.utils.Tools;

/**
 * Created by InnovationLab on 20/08/2014 for Miami Wear
 */
@SuppressLint("ValidFragment")
public class ActionFragment extends Fragment implements View.OnTouchListener {
    private static final String TAG = "ActionFragment";
    private Context mContext;
    private String mTitle;
    private int mIconId;
    CircledImageView circledImageView;
    private int mChannelNumber;

    public ActionFragment(Context context, String mTitle, int mIconId, int channelNumber) {
        this.mContext = context;
        this.mTitle = mTitle;
        this.mIconId = mIconId;
        this.mChannelNumber = channelNumber;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        if (Build.MODEL.equals("Moto 360") || Build.MODEL.equals("LG G Watch R"))
            view = inflater.inflate(R.layout.action_round, container, false);
        else
            view = inflater.inflate(R.layout.action_rect, container, false);

        ((TextView) view.findViewById(R.id.text)).setText(mTitle);
        circledImageView = ((CircledImageView) view.findViewById(R.id.image_view));
        circledImageView.setImageResource(mIconId);
        view.setOnTouchListener(this);

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(getActivity(), RemoteGridActivity.class).putExtra("launchedFromEpg", true));
                return false;
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        circledImageView.setCircleColor(getResources().getColor(R.color.action_button));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // PRESSED
//                circledImageView.setCircleColor(getResources().getColor(R.color.action_button_pressed));
                return true; // if you want to handle the touch event
            case MotionEvent.ACTION_UP:
                // RELEASED
                Log.i(TAG, "Action!");
                Tools.changeChannel(mContext, mChannelNumber);
                return true; // if you want to handle the touch event
        }
        return false; // TODO: tester s'il ne faut pas remettre true pour que le onlongclick fonctionne
    }
}

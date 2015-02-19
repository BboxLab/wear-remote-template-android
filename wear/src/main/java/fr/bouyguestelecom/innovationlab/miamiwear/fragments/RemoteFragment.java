package fr.bouyguestelecom.innovationlab.miamiwear.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import fr.bouyguestelecom.innovationlab.miamiwear.R;
import fr.bouyguestelecom.innovationlab.miamiwear.services.RemoteListener;
import fr.bouyguestelecom.innovationlab.miamiwear.utils.Constants;

/**
 * Created by InnovationLab on 20/08/2014 for Miami Wear
 */
@SuppressLint("ValidFragment")
public class RemoteFragment extends Fragment {
    private static final String TAG = "RemoteFragment";
    @SuppressWarnings("FieldCanBeLocal")
    private BroadcastReceiver localReceiver;

    public RemoteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.remote2, container, false);

        view.findViewById(R.id.test).setRotation(45);
        view.findViewById(R.id.p_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoteListener.sendMessage(Constants.REMOTE_PPLUS);
            }
        });
        view.findViewById(R.id.p_moins).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoteListener.sendMessage(Constants.REMOTE_PMOINS);
            }
        });
        view.findViewById(R.id.v_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoteListener.sendMessage(Constants.REMOTE_VPLUS);
            }
        });
        view.findViewById(R.id.v_moins).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoteListener.sendMessage(Constants.REMOTE_VMOINS);
            }
        });
        view.findViewById(R.id.power).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoteListener.sendMessage(Constants.REMOTE_WOL);
            }
        });
        view.findViewById(R.id.mute).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoteListener.sendMessage(Constants.REMOTE_MUTE);
                view.findViewById(R.id.power).setVisibility(View.GONE);
                view.findViewById(R.id.mute).setVisibility(View.VISIBLE);
                view.findViewById(R.id.mute_image).setVisibility(View.VISIBLE);
            }
        });

        (view.findViewById(R.id.mute)).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((ImageView) view.findViewById(R.id.mute_image)).setImageResource(R.drawable.mute_down);
                } else {
                    ((ImageView) view.findViewById(R.id.mute_image)).setImageResource(R.drawable.mute);
                }
                return false;
            }
        });

        final ImageView background = (ImageView) view.findViewById(R.id.background_img);

        if (Build.MODEL.equals("Moto 360") || Build.MODEL.equals("LG G Watch R")) { // Montres rondes // TODO: faire ça plus proprement (et rajouter la LG ronde...)
            background.setImageResource(R.drawable.remote1_round);
            view.findViewById(R.id.p_plus).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        background.setImageResource(R.drawable.remote1_round_pplus);
                    } else {
                        background.setImageResource(R.drawable.remote1_round);
                    }
                    return false;
                }
            });
            view.findViewById(R.id.p_moins).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        background.setImageResource(R.drawable.remote1_round_pmoins);
                    } else {
                        background.setImageResource(R.drawable.remote1_round);
                    }
                    return false;
                }
            });
            view.findViewById(R.id.v_plus).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        background.setImageResource(R.drawable.remote1_round_vplus);
                    } else {
                        background.setImageResource(R.drawable.remote1_round);
                    }
                    return false;
                }
            });
            view.findViewById(R.id.v_moins).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        background.setImageResource(R.drawable.remote1_round_vmoins);
                    } else {
                        background.setImageResource(R.drawable.remote1_round);
                    }
                    return false;
                }
            });
            view.findViewById(R.id.power).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        background.setImageResource(R.drawable.remote1_round_power);
                    } else {
                        background.setImageResource(R.drawable.remote1_round);
                    }
                    return false;
                }
            });
        } else { // Montres carrées
            view.findViewById(R.id.p_plus).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        background.setImageResource(R.drawable.remote1_pplus);
                    } else {
                        background.setImageResource(R.drawable.remote1);
                    }
                    return false;
                }
            });
            view.findViewById(R.id.p_moins).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        background.setImageResource(R.drawable.remote1_pmoins);
                    } else {
                        background.setImageResource(R.drawable.remote1);
                    }
                    return false;
                }
            });
            view.findViewById(R.id.v_plus).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        background.setImageResource(R.drawable.remote1_vplus);
                    } else {
                        background.setImageResource(R.drawable.remote1);
                    }
                    return false;
                }
            });
            view.findViewById(R.id.v_moins).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        background.setImageResource(R.drawable.remote1_vmoins);
                    } else {
                        background.setImageResource(R.drawable.remote1);
                    }
                    return false;
                }
            });
            view.findViewById(R.id.power).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        background.setImageResource(R.drawable.remote1_power);
                    } else {
                        background.setImageResource(R.drawable.remote1);
                    }
                    return false;
                }
            });
        }

        localReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, final Intent intent) {
                Log.i(TAG, "localreceiver onReceive: " + intent.getAction());
                if (intent.getAction().equals(Constants.IS_ALIVE)) {
                    final boolean alive = intent.getBooleanExtra("alive", false);
                    if (alive) {
                        view.findViewById(R.id.power).setVisibility(View.GONE);
                        view.findViewById(R.id.mute).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.mute_image).setVisibility(View.VISIBLE);
                    } else {
                        view.findViewById(R.id.power).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.mute).setVisibility(View.GONE);
                        view.findViewById(R.id.mute_image).setVisibility(View.GONE);
                    }
                }
            }
        };

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((localReceiver), new IntentFilter(Constants.IS_ALIVE));

        return view;
    }
}
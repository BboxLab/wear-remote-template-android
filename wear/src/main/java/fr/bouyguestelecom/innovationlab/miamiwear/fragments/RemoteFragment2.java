package fr.bouyguestelecom.innovationlab.miamiwear.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import fr.bouyguestelecom.innovationlab.miamiwear.R;
import fr.bouyguestelecom.innovationlab.miamiwear.services.RemoteListener;
import fr.bouyguestelecom.innovationlab.miamiwear.utils.Constants;

/**
 * Created by InnovationLab on 20/08/2014 for Miami Wear
 */
@SuppressLint("ValidFragment")
public class RemoteFragment2 extends Fragment {
    @SuppressWarnings("UnusedDeclaration")
    private static final String TAG = "RemoteFragment2";

    public RemoteFragment2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.remote2_bis, container, false);

        view.findViewById(R.id.up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoteListener.sendMessage(Constants.REMOTE_UP);
            }
        });
        view.findViewById(R.id.down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoteListener.sendMessage(Constants.REMOTE_DOWN);
            }
        });
        view.findViewById(R.id.left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoteListener.sendMessage(Constants.REMOTE_LEFT);
            }
        });
        view.findViewById(R.id.right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoteListener.sendMessage(Constants.REMOTE_RIGHT);
            }
        });
        view.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoteListener.sendMessage(Constants.REMOTE_OK);
            }
        });
        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoteListener.sendMessage(Constants.REMOTE_BACK);
            }
        });
        view.findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoteListener.sendMessage(Constants.REMOTE_HOME);
            }
        });

        final View background = view.findViewById(R.id.background);

        if (Build.MODEL.equals("Moto 360") || Build.MODEL.equals("LG G Watch R")) { // Montres rondes // TODO: faire ça plus proprement
            background.setBackgroundResource(R.drawable.remote2_round);
            view.findViewById(R.id.up).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        background.setBackgroundResource(R.drawable.remote2_round_up);
                    } else {
                        background.setBackgroundResource(R.drawable.remote2_round);
                    }
                    return false;
                }
            });
            view.findViewById(R.id.down).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        background.setBackgroundResource(R.drawable.remote2_round_down);
                    } else {
                        background.setBackgroundResource(R.drawable.remote2_round);
                    }
                    return false;
                }
            });
            view.findViewById(R.id.left).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        background.setBackgroundResource(R.drawable.remote2_round_left);
                    } else {
                        background.setBackgroundResource(R.drawable.remote2_round);
                    }
                    return false;
                }
            });
            view.findViewById(R.id.right).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        background.setBackgroundResource(R.drawable.remote2_round_right);
                    } else {
                        background.setBackgroundResource(R.drawable.remote2_round);
                    }
                    return false;
                }
            });
            view.findViewById(R.id.ok).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        background.setBackgroundResource(R.drawable.remote2_round_ok);
                    } else {
                        background.setBackgroundResource(R.drawable.remote2_round);
                    }
                    return false;
                }
            });
            view.findViewById(R.id.back).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        background.setBackgroundResource(R.drawable.remote2_round_back);
                    } else {
                        background.setBackgroundResource(R.drawable.remote2_round);
                    }
                    return false;
                }
            });
            view.findViewById(R.id.home).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        background.setBackgroundResource(R.drawable.remote2_round_home);
                    } else {
                        background.setBackgroundResource(R.drawable.remote2_round);
                    }
                    return false;
                }
            });
        }
        else { // Montres carrées
            view.findViewById(R.id.up).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        background.setBackgroundResource(R.drawable.remote2_up);
                    } else {
                        background.setBackgroundResource(R.drawable.remote2);
                    }
                    return false;
                }
            });
            view.findViewById(R.id.down).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        background.setBackgroundResource(R.drawable.remote2_down);
                    } else {
                        background.setBackgroundResource(R.drawable.remote2);
                    }
                    return false;
                }
            });
            view.findViewById(R.id.left).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        background.setBackgroundResource(R.drawable.remote2_left);
                    } else {
                        background.setBackgroundResource(R.drawable.remote2);
                    }
                    return false;
                }
            });
            view.findViewById(R.id.right).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        background.setBackgroundResource(R.drawable.remote2_right);
                    } else {
                        background.setBackgroundResource(R.drawable.remote2);
                    }
                    return false;
                }
            });
            view.findViewById(R.id.ok).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        background.setBackgroundResource(R.drawable.remote2_ok);
                    } else {
                        background.setBackgroundResource(R.drawable.remote2);
                    }
                    return false;
                }
            });
            view.findViewById(R.id.back).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        background.setBackgroundResource(R.drawable.remote2_back);
                    } else {
                        background.setBackgroundResource(R.drawable.remote2);
                    }
                    return false;
                }
            });
            view.findViewById(R.id.home).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        background.setBackgroundResource(R.drawable.remote2_home);
                    } else {
                        background.setBackgroundResource(R.drawable.remote2);
                    }
                    return false;
                }
            });
        }

        return view;
    }
}

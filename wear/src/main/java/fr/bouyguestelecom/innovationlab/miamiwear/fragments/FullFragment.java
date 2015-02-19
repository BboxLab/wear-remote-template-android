package fr.bouyguestelecom.innovationlab.miamiwear.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import miamiwear.giom.ilab.com.miamiwear.R;

/**
 * Created by InnovationLab on 20/08/2014 for Miami Wear
 */
@SuppressWarnings("UnusedDeclaration")
@SuppressLint("ValidFragment")
public class FullFragment extends Fragment {
    Bitmap mBitmap;

    public FullFragment(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.full, container, false);
        ((ImageView) view.findViewById(R.id.image_view)).setImageBitmap(mBitmap);

        return view;

    }
}

package fr.bouyguestelecom.innovationlab.miamiwear.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.drawable.Drawable;
import android.support.wearable.view.FragmentGridPagerAdapter;
//import android.support.wearable.view.ImageReference;
import android.util.Log;

import fr.bouyguestelecom.innovationlab.miamiwear.fragments.RemoteFragment;
import fr.bouyguestelecom.innovationlab.miamiwear.fragments.RemoteFragment2;

/**
 * Created by InnovationLab on 20/08/2014 for Miami Wear
 */
public class RemoteGridPagerAdapter extends FragmentGridPagerAdapter {
    private static final String TAG = "RemoteGridPagerAdapter";

    public RemoteGridPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getFragment(int row, int col) {
        if (row == 0) return new RemoteFragment();
        return new RemoteFragment2();
    }

    @Override
    public Drawable getBackgroundForPage(int row, int column) {
        Log.d(TAG, "getBackground(" + row + "," + column + ")");
        return super.getBackgroundForPage(row, column);
    }
    /*@Override
    public ImageReference getBackground(int row, int col) {
        Log.d(TAG, "getBackground(" + row + "," + col + ")");
        return null;
    }*/

    @Override
    public int getRowCount() {
        return 2;
    }

    @Override
    public int getColumnCount(int row) {
        return 1;
    }

}


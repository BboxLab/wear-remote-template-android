package fr.bouyguestelecom.innovationlab.miamiwear.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.drawable.Drawable;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;
//import android.support.wearable.view.ImageReference;
import android.util.Log;

/**
 * Created by InnovationLab on 20/08/2014 for Miami Wear
 */
public class EmptyGridPagerAdapter extends FragmentGridPagerAdapter {
    private static final String TAG = "EmptyGridPagerAdapter";
    private int count;

    public EmptyGridPagerAdapter(FragmentManager fm, final int count) {
        super(fm);
        Log.d(TAG, "EmptyGridPagerAdapter(" + count + ")");
        this.count = count;
    }

    @Override
    public Fragment getFragment(int row, int col) {
        Log.d(TAG, "getFragment(" + row + "," + col + ")");
        return CardFragment.create("Chargement","");
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
    }**/

    @Override
    public int getRowCount() {
        return count;
    }

    @Override
    public int getColumnCount(int row) {
        return 1;
    }

}


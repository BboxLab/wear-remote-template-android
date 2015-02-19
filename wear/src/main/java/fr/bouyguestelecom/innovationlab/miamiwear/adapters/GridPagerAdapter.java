package fr.bouyguestelecom.innovationlab.miamiwear.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.wearable.view.FragmentGridPagerAdapter;
//import android.support.wearable.view.ImageReference;
import android.util.Log;

import java.util.Random;

import miamiwear.giom.ilab.com.miamiwear.R;
import fr.bouyguestelecom.innovationlab.miamiwear.fragments.ActionFragment;
import fr.bouyguestelecom.innovationlab.miamiwear.fragments.FrontPageFragment;
import fr.bouyguestelecom.innovationlab.miamiwear.fragments.SummaryFragment;
import fr.bouyguestelecom.innovationlab.miamiwear.fragments.VolumeFragment;
import fr.bouyguestelecom.innovationlab.miamiwear.models.Channel;
import fr.bouyguestelecom.innovationlab.miamiwear.models.Program;
import fr.bouyguestelecom.innovationlab.miamiwear.utils.EPG;

/**
 * Created by InnovationLab on 20/08/2014 for Miami Wear
 */
public class GridPagerAdapter extends FragmentGridPagerAdapter {
    private static final String TAG = "GridPagerAdapter";

    private final Context mContext;

    public GridPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        mContext = context;
    }

    @Override
    public Fragment getFragment(int row, int col) {
        Log.d(TAG, "getFragment(" + row + "," + col + ")");
        final Channel channel = EPG.getChannelFromRow(row);
        final Program program = channel.getProgram();

        if (col == 0)
            return new FrontPageFragment(mContext, program.getTitle(), program.getStartTime(), program.getEndTime(), channel.getLogo(), channel.getPosition());
        if (program.getDescription().length() == 0) col += 1;
        if (col == 1) return new SummaryFragment(program.getTitle(), program.getDescription());
        if (col == 2)
            return new ActionFragment(mContext, program.getTitle(), R.drawable.ic_remote, channel.getPosition());
        if (col == 3) return new VolumeFragment("Volume -", R.drawable.ic_remote, false);
        return new VolumeFragment("Volume +", R.drawable.ic_remote, true);
    }

    @Override
    public Drawable getBackgroundForPage(int row, int column) {
        Log.d(TAG, "getBackground(" + row + "," + column + ")");
        final Program program = EPG.getChannelFromRow(row).getProgram();
        return (program.hasImage() ? new BitmapDrawable(mContext.getResources(), program.getImage()) : mContext.getResources().getDrawable(getRandomGenericImage()));
        //return super.getBackgroundForPage(row, column);
    }
    /*@Override
    public ImageReference getBackground(int row, int col) {
        Log.d(TAG, "getBackground(" + row + "," + col + ")");
        final Program program = EPG.getChannelFromRow(row).getProgram();
        return (program.hasImage() ? ImageReference.forBitmap(program.getImage()) : ImageReference.forDrawable(getRandomGenericImage()));
    }*/

    private int getRandomGenericImage() {
        switch (new Random().nextInt(4)) {
            case 0:
                return R.drawable.genericimage1;
            case 1:
                return R.drawable.genericimage2;
            case 2:
                return R.drawable.genericimage3;
            case 3:
            default:
                return R.drawable.genericimage4;
        }
    }

    @Override
    public int getRowCount() {
        return EPG.getChannelCount();
    }

    @Override
    public int getColumnCount(int row) {
        Log.d(TAG, "getColumnCount(" + row + ")");
        final Program program = EPG.getChannelFromRow(row).getProgram();
        //return (program == null ? 1 : (program.getDescription().length()>0 ? 5 : 4)); // Pour ajouter les deux boutons V+/v-
        return (program == null ? 1 : (program.getDescription().length() > 0 ? 3 : 2));
    }

}


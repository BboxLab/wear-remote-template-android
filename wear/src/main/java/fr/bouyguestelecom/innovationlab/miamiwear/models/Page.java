package fr.bouyguestelecom.innovationlab.miamiwear.models;

import android.graphics.Bitmap;

/**
 * Created by InnovationLab on 20/08/2014 for Miami Wear
 */
@SuppressWarnings("UnusedDeclaration")
public class Page {

    public String mTitle;
    public String mText;
    public String mStartTime;
    public String mEndTime;
    public int mIconId = 0;
    public Bitmap mIconBitmap = null;
    public int mBackgroundId;
    public Bitmap mBackgroundBitmap = null;
    public boolean mIsAction;

    public Page(String title, String startTime, String endTime, int iconId, Bitmap backgroundBitmap) {
        mIsAction = false;
        this.mTitle = title;
        this.mStartTime = startTime;
        this.mEndTime = endTime;
        this.mIconId = iconId;
        this.mBackgroundBitmap = backgroundBitmap;
    }

    public Page(String title, String text, Bitmap logoBitmap, Bitmap backgroundBitmap) {
        mIsAction = false;
        this.mTitle = title;
        this.mText = text;
        this.mIconBitmap = logoBitmap;
        this.mBackgroundBitmap = backgroundBitmap;
    }

    public Page(String title, String text, int iconId, int backgroundId) {
        mIsAction = false;
        this.mTitle = title;
        this.mText = text;
        this.mIconId = iconId;
        this.mBackgroundId = backgroundId;
    }

    public Page(String title, String text, Bitmap backgroundBitmap) {
        mIsAction = false;
        this.mTitle = title;
        this.mText = text;
        this.mBackgroundBitmap = backgroundBitmap;
    }

    public Page(String title, String text, int backgroundId) {
        mIsAction = false;
        this.mTitle = title;
        this.mText = text;
        this.mBackgroundId = backgroundId;
    }

    public Page(String title, int iconId, int backgroundId) {
        mIsAction = true;
        this.mTitle = title;
        this.mIconId = iconId;
        this.mBackgroundId = backgroundId;
    }

    public Page(String title, int iconId, Bitmap backgroundBitmap) {
        mIsAction = true;
        this.mTitle = title;
        this.mIconId = iconId;
        this.mBackgroundBitmap = backgroundBitmap;
    }
}


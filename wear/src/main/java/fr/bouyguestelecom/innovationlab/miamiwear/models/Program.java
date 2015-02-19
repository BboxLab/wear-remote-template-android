package fr.bouyguestelecom.innovationlab.miamiwear.models;

import android.graphics.Bitmap;

/**
 * Created by InnovationLab on 20/08/2014 for Miami Wear
 */
public class Program {
    private String mTitle;
    private String mDescription;
    private String mStartTime;
    private String mEndTime;
    private Bitmap mImage;

    public Program(String title, String description, String startTime, String endTime, Bitmap image) {
        this.mTitle = title;
        this.mDescription = description;
        this.mStartTime = startTime;
        this.mEndTime = endTime;
        this.mImage = image;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public Bitmap getImage() {
        return mImage;
    }

    public boolean hasImage() {
        return mImage != null;
    }
}
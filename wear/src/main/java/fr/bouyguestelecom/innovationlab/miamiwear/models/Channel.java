package fr.bouyguestelecom.innovationlab.miamiwear.models;


import fr.bouyguestelecom.innovationlab.miamiwear.utils.EPG;

/**
 * Created by InnovationLab on 20/08/2014 for Miami Wear
 */
@SuppressWarnings("UnusedDeclaration")
public class Channel {
    private int mPosition;
    private int mEpgChannelNumber;
    private int mLogo;
    private String mNom;

    public Channel(int position, int epgChannelNumber, int logo, String nom) {
        this.mPosition = position;
        this.mEpgChannelNumber = epgChannelNumber;
        this.mLogo = logo;
        this.mNom = nom;
    }

    public int getPosition() {
        return mPosition;
    }

    public int getEpgChannelNumber() {
        return mEpgChannelNumber;
    }

    public int getLogo() {
        return mLogo;
    }

    public String getNom() {
        return mNom;
    }

    public Program getProgram() {
        return EPG.getProgram(mEpgChannelNumber);
    }

}

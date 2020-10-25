package com.example.miwokapp;

/* Represents a vocabulary word that the user wants to learn.
* It contains a default translation and a Miwok translation for that word
*  */
public class Word {
    // Default translation for the word
    private String mDefaultTranslation;
    // Miwok translation for the word
    private String mMiwokTranslation;
    // Image resource Id for each word
    private int mImageResourceId = NO_IMAGE_RESOURCE;
    private static final int NO_IMAGE_RESOURCE = -1;
    // Audio resource Id for each word
    private int mAudioResourceId;

    public Word(String defaultTranslation, String miwokTranslation, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioResourceId;
    }

    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }

    // get Default Translation of the word
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    // get Miwok Translation of the word
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    // returns the image resource Id
    public int getImageResourceId() {
        return mImageResourceId;
    }

    // returns the audio resource Id
    public int getAudioResourceId() {
        return mAudioResourceId;
    }
    // returns whether there or not there is an image for this word
    public boolean hasImage() {
        // if image != -1, there is a valid image
        // else if image == -1, there is no image
        return mImageResourceId != NO_IMAGE_RESOURCE;
    }
}

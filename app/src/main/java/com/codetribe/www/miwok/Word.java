package com.codetribe.www.miwok;

/**
 * Created by codetribe on 6/23/2017.
 */

public class Word
{
    //Translation for different languages for a single word
    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int image_resource_id = NO_IMAGE_PROVIDED;
    private int audio_resource_id;
    private static int NO_IMAGE_PROVIDED = -1;

    public Word(String mDefaultTranslation, String mMiwokTranslation,int audio_resource_id) {
        this.mDefaultTranslation = mDefaultTranslation;
        this.mMiwokTranslation = mMiwokTranslation;
        this.audio_resource_id = audio_resource_id;
    }

    public Word(String mDefaultTranslation, String mMiwokTranslation, int image_resource_id,int audio_resource_id) {
        this.mDefaultTranslation = mDefaultTranslation;
        this.mMiwokTranslation = mMiwokTranslation;
        this.image_resource_id = image_resource_id;
        this.audio_resource_id = audio_resource_id;
    }
    //Get different languages translation
    public String getmDefaultTranslation() {
        return mDefaultTranslation;
    }

    public String getmMiwokTranslation() {
        return mMiwokTranslation;
    }

    public int getImageResource_id() {
        return image_resource_id;
    }
    //Test if activity has an image or not
    public boolean hasImage()
    {
        return  image_resource_id != NO_IMAGE_PROVIDED;
    }

    public int getAudio_resource_id(){
        return  audio_resource_id;
    }
}

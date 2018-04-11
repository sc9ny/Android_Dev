package com.example.shuo.musicplayer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by seungleechoi on 10/24/17.
 */

public class indexMem implements Parcelable{
    int index, indexOver, indexOver2, indexOver3;
    private MusicService music;
    private int mData;
    int seekBarPro, seekBarPro2, seekBarPro3;
    String musicName,imageName;
    public indexMem()
    {
        //index = 0;
        //music = null;
    }

    public void setIndex(int i )
    {
        index = i;
    }
    public int getIndex() {
        return index;
    }
    public void setMusic(MusicService m)
    {
        music = m;
    }
    public MusicService getMusic()
    {
        return music;
    }
    public void setIndexOver(int j) {indexOver = j;}
    public int getIndexOver(){return indexOver;}
    public void setIndexOver2(int j) {indexOver2 = j;}
    public int getIndexOver2(){return indexOver2;}
    public void setIndexOver3(int j) {indexOver3 = j;}
    public int getIndexOver3(){return indexOver3;}

    public void setSeekBarPro(int i)
    {
        seekBarPro = i;
    }
    public int getSeekBarPro()
    {
        return seekBarPro;
    }
    public void setSeekBarPro2(int i)
    {
        seekBarPro2 = i;
    }
    public int getSeekBarPro2()
    {
        return seekBarPro2;
    }
    public void setSeekBarPro3(int i)
    {
        seekBarPro3 = i;
    }
    public int getSeekBarPro3()
    {
        return seekBarPro3;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mData);
    }
    public static final Parcelable.Creator<indexMem> CREATOR
            = new Parcelable.Creator<indexMem>() {
        public indexMem createFromParcel(Parcel in) {
            return new indexMem(in);
        }

        public indexMem[] newArray(int size) {
            return new indexMem[size];
        }
    };
    private indexMem(Parcel in) {
        mData = in.readInt();
    }
}

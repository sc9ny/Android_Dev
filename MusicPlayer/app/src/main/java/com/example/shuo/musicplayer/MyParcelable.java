package com.example.shuo.musicplayer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by seungleechoi on 10/28/17.
 */

public class MyParcelable implements Parcelable {
    private int mData;

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(mData);
    }



    public static final Parcelable.Creator<MyParcelable> CREATOR
            = new Parcelable.Creator<MyParcelable>() {
        public MyParcelable createFromParcel(Parcel in) {
            return new MyParcelable(in);
        }

        public MyParcelable[] newArray(int size) {
            return new MyParcelable[size];
        }
    };

    /** recreate object from parcel */
    private MyParcelable(Parcel in) {
        mData = in.readInt();
    }
}

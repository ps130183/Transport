package com.ps.androidlib.widget.nicespinner;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kamangkeji on 17/2/17.
 */

public class SpinnerModel implements Parcelable {
    private String id;
    private String name;

    public SpinnerModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    protected SpinnerModel(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    public static final Creator<SpinnerModel> CREATOR = new Creator<SpinnerModel>() {
        @Override
        public SpinnerModel createFromParcel(Parcel in) {
            return new SpinnerModel(in);
        }

        @Override
        public SpinnerModel[] newArray(int size) {
            return new SpinnerModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }
}

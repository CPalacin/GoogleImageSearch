package com.crubio.googleimagesearch.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchConfiguration implements Parcelable {
    private String size;
    private String color;
    private String type;
    private String site;

    public SearchConfiguration() {}

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @Override
    public String toString() {
        return "SearchConfiguration{" +
                "size='" + size + '\'' +
                ", color='" + color + '\'' +
                ", type='" + type + '\'' +
                ", site='" + site + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.size);
        dest.writeString(this.color);
        dest.writeString(this.type);
        dest.writeString(this.site);
    }

    protected SearchConfiguration(Parcel in) {
        this.size = in.readString();
        this.color = in.readString();
        this.type = in.readString();
        this.site = in.readString();
    }

    public static final Creator<SearchConfiguration> CREATOR = new Creator<SearchConfiguration>() {
        public SearchConfiguration createFromParcel(Parcel source) {
            return new SearchConfiguration(source);
        }

        public SearchConfiguration[] newArray(int size) {
            return new SearchConfiguration[size];
        }
    };
}

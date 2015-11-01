package com.crubio.googleimagesearch.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {

    private String url;
    private String tbUrl;
    private String title;

    public Image(String url, String tbUrl, String title) {
        this.url = url;
        this.tbUrl = tbUrl;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTbUrl() {
        return tbUrl;
    }

    public void setTbUrl(String tbUrl) {
        this.tbUrl = tbUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Image{" +
                "url='" + url + '\'' +
                ", tbUrl='" + tbUrl + '\'' +
                ", title='" + title + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.tbUrl);
        dest.writeString(this.title);
    }

    private Image(Parcel in) {
        this.url = in.readString();
        this.tbUrl = in.readString();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}

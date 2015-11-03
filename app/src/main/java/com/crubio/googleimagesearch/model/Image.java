package com.crubio.googleimagesearch.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {

    private String url;
    private String tbUrl;
    private String title;
    private int tbWidth;
    private int tbHeight;

    public Image(String url, String tbUrl, String title, int tbWidth, int tbHeight) {
        this.url = url;
        this.tbUrl = tbUrl;
        this.title = title;
        this.tbWidth = tbWidth;
        this.tbHeight = tbHeight;
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

    public int getTbWidth() {
        return tbWidth;
    }

    public void setTbWidth(int tbWidth) {
        this.tbWidth = tbWidth;
    }

    public int getTbHeight() {
        return tbHeight;
    }

    public void setTbHeight(int tbHeight) {
        this.tbHeight = tbHeight;
    }

    @Override
    public String toString() {
        return "Image{" +
                "url='" + url + '\'' +
                ", tbUrl='" + tbUrl + '\'' +
                ", title='" + title + '\'' +
                ", tbWidth=" + tbWidth +
                ", tbHeight=" + tbHeight +
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
        dest.writeInt(this.tbWidth);
        dest.writeInt(this.tbHeight);
    }

    protected Image(Parcel in) {
        this.url = in.readString();
        this.tbUrl = in.readString();
        this.title = in.readString();
        this.tbWidth = in.readInt();
        this.tbHeight = in.readInt();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}

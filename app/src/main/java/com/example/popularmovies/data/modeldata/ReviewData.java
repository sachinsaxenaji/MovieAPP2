package com.example.popularmovies.data.modeldata;

import android.os.Parcel;
import android.os.Parcelable;

/** ReviewData is responsible to store one review data, that is needed.
 */

public class ReviewData implements Parcelable {

    private final String author;
    private String content;

    public ReviewData(
            String author,
            String content){
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.author);
        dest.writeString(this.content);
    }

    private ReviewData(Parcel in) {
        this.author = in.readString();
        this.content = in.readString();
    }

    static final Creator<ReviewData> CREATOR = new Creator<ReviewData>() {
        @Override
        public ReviewData createFromParcel(Parcel source) {
            return new ReviewData(source);
        }

        @Override
        public ReviewData[] newArray(int size) {
            return new ReviewData[size];
        }
    };
}

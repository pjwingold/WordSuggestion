package com.tcs.wordsuggestion.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Future proof, in case more fields need to be returned
 *
 */
public class ParseResult implements Parcelable {
    private String result;

    public ParseResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.result);
    }

    protected ParseResult(Parcel in) {
        this.result = in.readString();
    }

    public static final Parcelable.Creator<ParseResult> CREATOR = new Parcelable.Creator<ParseResult>() {
        public ParseResult createFromParcel(Parcel source) {
            return new ParseResult(source);
        }

        public ParseResult[] newArray(int size) {
            return new ParseResult[size];
        }
    };
}
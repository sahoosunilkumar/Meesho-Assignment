package com.meesho.assignment.features.pulls.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Pagination implements Parcelable {
    private int next;
    private int previous;
    private int last;
    private int perPage;

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getPrevious() {
        return previous;
    }

    public void setPrevious(int previous) {
        this.previous = previous;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
    }

    protected Pagination(Parcel in) {
        next = in.readInt();
        previous = in.readInt();
        last = in.readInt();
        perPage = in.readInt();
    }

    public Pagination(int next, int previous, int last, int perPage){
        this.next = next;
        this.previous = previous;
        this.last = last;
        this.perPage = perPage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(next);
        dest.writeInt(previous);
        dest.writeInt(last);
        dest.writeInt(perPage);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Pagination> CREATOR = new Parcelable.Creator<Pagination>() {
        @Override
        public Pagination createFromParcel(Parcel in) {
            return new Pagination(in);
        }

        @Override
        public Pagination[] newArray(int size) {
            return new Pagination[size];
        }
    };
}
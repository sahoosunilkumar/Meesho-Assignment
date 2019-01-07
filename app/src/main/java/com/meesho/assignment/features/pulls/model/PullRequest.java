package com.meesho.assignment.features.pulls.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PullRequest implements Parcelable {
    private final String userName;
    private final String repoName;
    private Pagination pagination;

    public PullRequest(String userName, String repoName, Pagination pagination) {
        this.userName = userName;
        this.repoName = repoName;
        this.pagination = pagination;
    }

    public String getUserName() {
        return userName;
    }

    public String getRepoName() {
        return repoName;
    }

    public Pagination getPagination() {
        return pagination;
    }
    protected PullRequest(Parcel in) {
        userName = in.readString();
        repoName = in.readString();
        pagination = (Pagination) in.readValue(Pagination.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(repoName);
        dest.writeValue(pagination);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PullRequest> CREATOR = new Parcelable.Creator<PullRequest>() {
        @Override
        public PullRequest createFromParcel(Parcel in) {
            return new PullRequest(in);
        }

        @Override
        public PullRequest[] newArray(int size) {
            return new PullRequest[size];
        }
    };
}

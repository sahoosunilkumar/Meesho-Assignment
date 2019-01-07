package com.meesho.assignment.features.pulls.model;

import android.support.annotation.Nullable;

import com.meesho.assignment.features.pulls.repository.model.Pull;

import java.util.List;

public class PullResponse {
    private final List<Pull> pulls;
    private final String metaData;

    public PullResponse(List<Pull> pulls, @Nullable String metaData) {
        this.pulls = pulls;
        this.metaData = metaData;
    }

    public List<Pull> getPulls() {
        return pulls;
    }

    public String getMetaData() {
        return metaData;
    }
}

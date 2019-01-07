package com.meesho.assignment.background.network.api;

import com.meesho.assignment.features.pulls.repository.model.Pull;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("/repos/{user}/{repo}/pulls")
    Single<Response<List<Pull>>> getPulls(@Path("user") String username, @Path("repo") String repo, @Query("page") int page, @Query("per_page") int per_page);
}
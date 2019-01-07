package com.meesho.assignment.features.pulls.repository;

import com.meesho.assignment.background.network.api.BaseRepository;
import com.meesho.assignment.background.network.NetworkClient;
import com.meesho.assignment.features.pulls.repository.model.Pull;
import com.meesho.assignment.features.pulls.model.PullRequest;
import com.meesho.assignment.features.pulls.model.PullResponse;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public final class PullsRepository extends BaseRepository<PullRequest, PullResponse> {

    protected Disposable onExecute(final PullRequest requestModel) {
        return NetworkClient.getInstance()
                .getPulls(requestModel.getUserName(), requestModel.getRepoName(), requestModel.getPagination().getNext(), requestModel.getPagination().getPerPage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::onProgress)
                .subscribe(this::onComplete, this::onError);
    }

    private void onComplete(Response<List<Pull>> listResponse) {
        if(listResponse.isSuccessful()) {
            onSuccess(new PullResponse(listResponse.body(), listResponse.headers().get("Link")));
        }else{
            onError(new Exception(listResponse.message()));
        }
    }
}

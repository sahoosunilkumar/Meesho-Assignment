package com.meesho.assignment.features.pulls.viewmodel

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import com.meesho.assignment.background.network.api.Status
import com.meesho.assignment.background.network.model.ApiResponse

import com.meesho.assignment.background.network.model.IResponse
import com.meesho.assignment.exceptions.InvalidRepositoryException
import com.meesho.assignment.features.pulls.model.Pagination
import com.meesho.assignment.features.pulls.repository.PullsRepository
import com.meesho.assignment.features.pulls.model.PullRequest
import com.meesho.assignment.features.pulls.model.PullResponse
import com.meesho.assignment.formatter.pagination.LastPageFormatter
import com.meesho.assignment.formatter.pagination.NextPageFormatter
import com.meesho.assignment.formatter.pagination.PaginationFormatterManager
import com.meesho.assignment.formatter.pagination.PreviousPageFormatter

class PullsViewModel : ViewModel(), Observer<IResponse<PullResponse>> {
    val apiResponse = MediatorLiveData<IResponse<PullResponse>>()
    private val repositoryResponse = MediatorLiveData<IResponse<PullResponse>>()
    private val repository = PullsRepository()
    private val formatterManager = PaginationFormatterManager(NextPageFormatter(), PreviousPageFormatter(), LastPageFormatter())
    private var request: PullRequest?=null
    private val itemsPerPageLimit = 10
    private val repositoryPathSeparator = "/"
    val formatter = DateFormatter()

    init {
        repositoryResponse.addSource(repository.response, this)
        apiResponse.addSource(repositoryResponse, this)
    }

    fun execute() {
        request?.let {
            if(it.pagination.last>it.pagination.next){
                repository.execute(request)
            }
        }
    }

    override fun onChanged(listIResponse: IResponse<PullResponse>?) {
        this.apiResponse.postValue(listIResponse)
        listIResponse?.let {
            if(it.status==Status.SUCCESS) {
                val pagination = this.request!!.pagination
                it.data.metaData?.let { it ->
                    formatterManager.format(it, pagination)
                }
            }
        }
    }

    fun initialize(repositoryPath:String) {
        val repoInfo = repositoryPath.split(repositoryPathSeparator)
        if(repoInfo.size !=2){
            apiResponse.value=ApiResponse<PullResponse>(InvalidRepositoryException())
            return
        }
        this.request = PullRequest(repoInfo[0], repoInfo[1],Pagination(1, Integer.MIN_VALUE, Integer.MAX_VALUE, itemsPerPageLimit))
        execute()
    }
}

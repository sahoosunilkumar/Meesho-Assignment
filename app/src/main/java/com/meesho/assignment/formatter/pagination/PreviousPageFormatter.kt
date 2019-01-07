package com.meesho.assignment.formatter.pagination

import com.meesho.assignment.features.pulls.model.Pagination

class PreviousPageFormatter : BasePageFormatter("rel=\"prev\"") {

    override fun update(number: Int, destination: Pagination) {
        destination.previous = number
    }
}

package com.meesho.assignment.formatter.pagination

import com.meesho.assignment.features.pulls.model.Pagination

class NextPageFormatter : BasePageFormatter("rel=\"next\"") {

    override fun update(number: Int, destination: Pagination) {
        destination.next = number
    }
}

package com.meesho.assignment.formatter.pagination

import com.meesho.assignment.features.pulls.model.Pagination

class LastPageFormatter : BasePageFormatter("rel=\"last\"") {
    override fun update(number: Int, destination: Pagination) {
        destination.last = number
    }
}

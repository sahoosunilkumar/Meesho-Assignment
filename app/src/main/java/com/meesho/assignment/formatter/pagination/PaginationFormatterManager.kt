package com.meesho.assignment.formatter.pagination

import com.meesho.assignment.features.pulls.model.Pagination
import com.meesho.assignment.formatter.BaseFormatterManager
import com.meesho.assignment.formatter.IFormatter

class PaginationFormatterManager(vararg formatters: IFormatter<String, Pagination>) : BaseFormatterManager<String, Pagination>(*formatters)

package com.meesho.assignment.formatter.pagination

import android.text.TextUtils
import com.meesho.assignment.features.pulls.model.Pagination
import com.meesho.assignment.formatter.IFormatter
import java.util.regex.Pattern


abstract class BasePageFormatter internal constructor(private val token: String) : IFormatter<String,Pagination> {
    private val pageNumberRegex = "page"
    private var queryParamDecoder = Pattern.compile("([^?=]+)=([^&]*)")
    override fun format(source: String, destination: Pagination) {
        if(!TextUtils.isEmpty(source)){
            if(source.contains(token)) {
                val infoList = source.split(",")
                infoList.forEach {
                    if (it.contains(token)) {
                        val matcher = queryParamDecoder.matcher(it.substring(0, it.lastIndexOf(token)))
                        while (matcher.find()) {
                            if (matcher.group(1) == pageNumberRegex) {
                                update(Integer.parseInt(matcher.group(2)), destination)
                                break
                            }
                        }
                    }
                }
            }else {
                update(-1, destination)
            }
        }
    }

    abstract fun update(number:Int, destination: Pagination)

}

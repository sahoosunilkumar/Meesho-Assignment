package com.meesho.assignment.formatter

open class BaseFormatterManager<Source,Destination>(vararg formatters: IFormatter<Source,Destination>) : IFormatterManager<Source,Destination> {
    private val formatters: Array<IFormatter<Source,Destination>> = formatters as Array<IFormatter<Source,Destination>>

    override fun getFormatters(): Array<IFormatter<Source,Destination>> {
        return formatters
    }

    override fun format(data: Source, output:Destination) {
        for (formatter in formatters){
            formatter.format(data,output)
        }
    }
}

package com.meesho.assignment.formatter

interface IFormatterManager<Source,Destination> : IFormatter<Source,Destination> {
    fun getFormatters(): Array<IFormatter<Source,Destination>>
}

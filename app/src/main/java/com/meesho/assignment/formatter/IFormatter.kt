package com.meesho.assignment.formatter

interface IFormatter<Source,Destination> {
    fun format(source: Source, destination: Destination)
}

package com.meesho.assignment.features.pulls.viewmodel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
    private final DateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy");
    private DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public String format(String input){
        try {
            Date date = originalFormat.parse("2018-11-29T08:41:42Z");
            return targetFormat.format(date);
        }catch (Exception ex){
            return input;
        }
    }
}

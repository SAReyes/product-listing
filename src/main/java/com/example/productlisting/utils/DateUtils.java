package com.example.productlisting.utils;

import java.util.Date;

public class DateUtils {

    public final static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public final static String DATE_TIMEZONE = "Europe/Berlin";

    public Date now() {
        return new Date();
    }
}

package com.example.tracklocation;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Administrator on 11/12/2015.
 */
public class Common {

    public static final String ACTION_STRING_SERVICE = "ACTION_STRING_SERVICE";
    public static final String GPS_FIX = "GPS_FIX";
    public static final String GPS_INFO = "GPS_INFO";
    public static final String GPS_LOCATION = "GPS_LOCATION";
    public static final String SAVE_DATA_IN_DB = "SAVE_DATA_IN_DB";

    public static String getDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}

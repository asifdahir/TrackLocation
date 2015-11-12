package com.example.tracklocation;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Administrator on 11/12/2015.
 */
public class Common {

    public static String getDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}

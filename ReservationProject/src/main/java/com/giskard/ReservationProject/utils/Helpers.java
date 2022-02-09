package com.giskard.ReservationProject.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helpers {

    public static String getDayFromDate(Date date){
        if (date!= null){
            SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE");
            return simpleDateformat.format(date).toUpperCase();
        }
        return "";
    }
}

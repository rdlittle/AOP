/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author rlittle
 */
public class DateUtils {
    
    public static Date ofLocalDate(LocalDate ld) {
        int mm = ld.getMonthValue();
        int dd = ld.getDayOfMonth();
        int yy = ld.getYear();
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.set(Calendar.MONTH, mm-1);
        cal.set(Calendar.DAY_OF_MONTH, dd);
        cal.set(Calendar.YEAR, yy);
        return cal.getTime();
    }
    
    public static LocalDate ofUtilDate(Date d) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTime(d);
        int mm = cal.get(Calendar.MONTH)+1;
        int dd = cal.get(Calendar.DAY_OF_MONTH);
        int yy = cal.get(Calendar.YEAR);
        LocalDate ld = LocalDate.of(yy, mm, dd);
        return ld;
    }
    
    public static String asString(Date d) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(d);
    }
    
}

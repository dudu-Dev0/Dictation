package com.dudu.dictation;
import java.util.Date;
import android.icu.text.SimpleDateFormat;
import java.text.ParseException;

public class Date_tool {
    

    public static String DateToString(Date time,String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String s = sdf.format(time);
        System.out.println(s);
        return s;
    }

    public static Date StringToDate(String time,String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = sdf.parse(time);
        System.out.println(date);
        return date;
	}
    
}

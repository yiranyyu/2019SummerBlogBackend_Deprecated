package buaasoft.blog.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Yirany
 * @version 1.0
 * @since 8/23/2019
 **/
public class Date {
    private Calendar date;

    private Date(Calendar date) {
        this.date = date;
    }

    public static Date getNow() {
        return new Date(Calendar.getInstance());
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date.getTime());
    }
}

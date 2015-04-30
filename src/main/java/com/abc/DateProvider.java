package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {
    private static DateProvider instance = null;

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    public Date now() {
        return Calendar.getInstance().getTime();
    }
    
    /**
     * Return date 'days' days ago
     * @return Date
     */
    public Date beforeDays(Date date, int days) {
    	return new Date(date.getTime() - (1000 * 60 * 60 * 24 * days));
    }
    
    /**
     * Return date 'days' after
     * @return Date
     */
    public Date afterDays(Date date, int days) {
    	return new Date(date.getTime() + (1000 * 60 * 60 * 24 * days));
    }
}

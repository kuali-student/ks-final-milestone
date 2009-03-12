package org.kuali.student.brms.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CurrentDateTime implements java.io.Serializable {
    /** Class serial version uid */
    private static final long serialVersionUID = 1L;

    private final static String pattern = "yyyyMMddHHmmss";
    private final static SimpleDateFormat format = new SimpleDateFormat(pattern);

    public long getCurrentDateAsLong() {
		return getDateAsLong(new Date());
	}

	public long getDateAsLong(Date date) {
		Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Long time = new Long(format.format(date));
        return time.longValue();
	}
}

package org.kuali.student.rules.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CurrentDateTime {
    /** Class serial version uid */
    private static final long serialVersionUID = 1L;

	public long getCurrentDateAsLong() {
		return getDateAsLong(new Date());
	}

	public long getDateAsLong(Date date) {
		Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String pattern = "yyyyMMddHHmmss";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return new Long(format.format(date)).longValue();
	}
}

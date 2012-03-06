package org.kuali.student.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatThread {

	private static ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {

		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}

	};

	public static Date parse(String source) throws ParseException {
		Date d = df.get().parse(source);
		return d;
	}

	public static String format(Date date) {
		return df.get().format(date);
	}

}

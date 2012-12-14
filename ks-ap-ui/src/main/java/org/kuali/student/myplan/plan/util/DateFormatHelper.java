package org.kuali.student.myplan.plan.util;

import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by IntelliJ IDEA.
 * User: hemanthg
 * Date: 5/2/12
 * Time: 4:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class DateFormatHelper {
    private static final Logger logger = Logger.getLogger(DateFormatHelper.class);

    /*Formats Date in format 2012-05-01 11:21:29.496 to 01/05/2012*/
    public static String getDateFomatted(String date){
        String dateStr = date.substring(0, 10);
        DateFormat dfYMD =
                new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dfDMY =
                new SimpleDateFormat("MM/dd/yyyy");

        try {
            dateStr = dfDMY.format(dfYMD.parse(dateStr));
        } catch (Exception e) {
            logger.error(e);
        }
        return dateStr;
    }
}

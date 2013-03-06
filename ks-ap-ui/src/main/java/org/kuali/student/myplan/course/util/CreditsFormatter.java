package org.kuali.student.myplan.course.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;

/**
 * Turns credits info into Strings.
 */
public class CreditsFormatter {

    final static Logger logger = Logger.getLogger(CreditsFormatter.class);

    /**
     * Formats credit options list as a String.
     *
     * @param courseInfo
     * @return
     */
    public static String formatCredits(CourseInfo courseInfo) {
        String credits = "";

        List<ResultValuesGroupInfo> options = courseInfo.getCreditOptions();
        if (options.size() == 0) {
            logger.warn("Credit options list was empty.");
            return credits;
        }
        /* At UW this list should only contain one item. */
        if (options.size() > 1) {
            logger.warn("Credit option list contained more than one value.");
        }
        ResultValuesGroupInfo rci = options.get(0);

        /**
         *  Credit values are provided in three formats: FIXED, LIST (Multiple), and RANGE (Variable). Determine the
         *  format and parse it into a String representation.
         */
        String type = rci.getTypeKey();
        if (type.equals("kuali.result.values.group.type.fixed")) {
            credits = rci.getResultValueKeys().get(0).replace("kuali.result.value.credit.degree.","");;
            credits = trimCredits(credits);
        } else if (type.equals("kuali.result.values.group.type.multiple")) {
            StringBuilder cTmp = new StringBuilder();
            Collections.sort(rci.getResultValueKeys(), new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    String temp1 = o1.replace("kuali.result.value.credit.degree.","");
                    String temp2 = o2.replace("kuali.result.value.credit.degree.","");
                    if (Double.parseDouble(temp1) > Double.parseDouble(temp2))
                        return +1;
                    else if (Double.parseDouble(temp1) < Double.parseDouble(temp2))
                        return -1;
                    else
                        return 0;


                }
            });
            for (String c : rci.getResultValueKeys()) {
                if (cTmp.length() != 0) {
                    cTmp.append(", ");
                }
                String temp = c.replace("kuali.result.value.credit.degree.","");
                cTmp.append(trimCredits(temp));
            }
            credits = cTmp.toString();
        } else if (type.equals("kuali.result.values.group.type.range")) {
            String minCredits = rci.getAttributeValue("minCreditValue");
            String maxCredits = rci.getAttributeValue("maxCreditValue");
            credits = trimCredits(minCredits) + "-" + trimCredits(maxCredits);
        } else {
            logger.error("Unknown Course Credit type [" + type + "].");
        }
        return credits;
    }

    /**
     * Drop the decimal point and and trailing zero from credits.
     *
     * @return The supplied value minus the trailing ".0"
     */
    public static String trimCredits(String credits) {
        if (StringUtils.isBlank(credits)) {
            return "";
        }
        credits = credits.trim();
        if (credits.endsWith(".0")) {
            credits = credits.substring(0, credits.indexOf("."));
        }
        return credits;
    }
}

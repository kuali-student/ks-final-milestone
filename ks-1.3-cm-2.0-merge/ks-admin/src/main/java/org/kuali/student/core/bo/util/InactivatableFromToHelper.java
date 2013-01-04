package org.kuali.student.core.bo.util;

import org.kuali.rice.krad.bo.InactivatableFromTo;

public class InactivatableFromToHelper {

	public static boolean isActive(InactivatableFromTo bo) {
        long asOfDate;
        
        if (bo.getActiveAsOfDate() != null) {
            asOfDate = bo.getActiveAsOfDate().getTime();
        }
        else {
        	asOfDate = System.currentTimeMillis();
        }

        return (bo.getActiveFromDate() == null || asOfDate >= bo.getActiveFromDate().getTime())
                && (bo.getActiveToDate() == null || asOfDate < bo.getActiveToDate().getTime());
    }
}

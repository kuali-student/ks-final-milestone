package org.kuali.student.core.bo.util;

import org.kuali.rice.kns.bo.InactivateableFromTo;

public class InactivatableFromToHelper {

	public static boolean isActive(InactivateableFromTo bo) {
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

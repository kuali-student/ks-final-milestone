package org.kuali.student.commons.ui.utilities;

import java.util.Comparator;
import java.util.Date;
/**
 * 
 * Simple comparator for use with dates.  Has no member variables, so use as a singleton is enforced. 
 * 
 * @author Kuali Student Team
 *
 */
public class DateComparator implements Comparator<Date> {
    /**
     * Static instance of DateComparator
     */
    public static final DateComparator INSTANCE = new DateComparator();
    private DateComparator() {
        
    }
    /**
     * 
     * Compares two dates.
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Date o1, Date o2) {
        long d1 = (o1 == null) ? Long.MIN_VALUE : o1.getTime();
        long d2 = (o2 == null) ? Long.MIN_VALUE : o2.getTime();
        
        if (d1 == d2) {
            return 0;
        } else if (d1 < d2) {
            return -1;
        } else {
            return 1;
        }
    }

}

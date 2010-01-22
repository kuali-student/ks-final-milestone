package org.kuali.student.commons.ui.utilities;

import java.util.Comparator;
/**
 * 
 * Simple comparator for use with integers.  Has no member variables, so use as a singleton is enforced. 
 * 
 * @author Kuali Student Team
 *
 */
public class IntegerComparator implements Comparator<Integer> {
    /**
     * Static instance of DateComparator
     */
    public static final IntegerComparator INSTANCE = new IntegerComparator();
    private IntegerComparator() {
        
    }
    /**
     * 
     * Compares two integers.
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Integer o1, Integer o2) {
        if (o1 == o2) {
            return 0;
        } else if (o1 < o2) {
            return -1;
        } else {
            return 1;
        }
    }

}

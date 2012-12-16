package org.kuali.student.myplan.course.controller;

import java.util.Comparator;

/**
 * Default ATP type comparator.
 * 
 * <p>
 * This implementation sorts alphabetically. Override spring bean
 * atpTypeComparator for institution-specific behavior.
 * </p>
 * 
 * @author Mark Fyffe <mwfyffe@indiana.edu>
 * @version ks-ap-0.1.1
 */
public class DefaultAtpTypeComparator implements Comparator<String> {

	@Override
	public int compare(String o1, String o2) {
		if (o1 == null && o2 == null)
			return 0;
		if (o1 == null)
			return -1;
		if (o2 == null)
			return 1;
		return o1.compareTo(o2);
	}

}

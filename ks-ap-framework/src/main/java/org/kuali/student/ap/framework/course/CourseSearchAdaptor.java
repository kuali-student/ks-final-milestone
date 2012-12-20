package org.kuali.student.ap.framework.course;

import java.util.List;

import org.kuali.student.r2.core.organization.infc.Org;

/**
 * Defines campus-specific behavior for course search.
 * 
 * @author Mark Fyffe <mwfyffe@indiana.edu>
 * @version ksap-0.1.2
 */
public interface CourseSearchAdaptor {

	/**
	 * Retrieve a list of all valid campuses.
	 * 
	 * <p>
	 * This list is used for presenting a list of campuses to the user, and for
	 * validating selections from the list.
	 * <p>
	 * 
	 * @return A list of all valid campuses.
	 */
	List<Org> getCampuses();

	/**
	 * Retrieve a list of all valid division codes.
	 * 
	 * <p>
	 * This list is used to identify division codes within search parameters.
	 * </p>
	 * 
	 * @return A list of all valid division.
	 */
	List<String> getDivisionCodes();

}

package org.kuali.student.ap.schedulebuilder;

import org.kuali.student.ap.schedulebuilder.infc.CourseOption;
import org.kuali.student.ap.schedulebuilder.infc.PossibleScheduleOption;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.ap.academicplan.infc.PlanItem;

import java.util.List;

/**
 * Defines methods for interacting with the shopping cart.
 * 
 * @author Mark Fyffe <mwfyffe@iu.edu>
 * @version 0.7.5
 */
public interface ShoppingCartStrategy {

	/**
	 * Determine if shopping cart is available for the student on the given
	 * term.
	 * 
	 * @param termId
	 *            The term ID.
	 * @param campusCode
	 *            The campus code value, from the "campusCode" dynamic attribute
	 *            on the course. When null, this method will check for access to
	 *            the shopping cart for the given term on all campuses.
	 * @return True if the student has access to the shopping cart for the given
	 *         term, false if not.
	 */
	boolean isCartAvailable(String termId, String campusCode);

	/**
	 * Get the course options for use with shopping cart for a specific term,
	 * based on a plan item.
	 * 
	 * @param term
	 *            The term to create a shopping cart request for.
	 * @param planItem
	 *            The plan item to create a shopping cart request for.
	 * @return A shopping cart request for the specific plan item and term.
	 */
	List<CourseOption> getCourseOptionsForPlanItem(Term term, PlanItem planItem);

	/**
	 * Create a shopping cart request for a specific term, based on a plan item.
	 * 
	 * @param term
	 *            The term to create a shopping cart request for.
	 * @param planItem
	 *            The plan item to create a shopping cart request for.
	 * @return A shopping cart request for the specific plan item and term.
	 */
	List<ShoppingCartRequest> createRequests(Term term, List<CourseOption> courseOptions);

	/**
	 * Create a shopping cart request for a specific term, based on a plan item.
	 * 
	 * @param term
	 *            The term to create a shopping cart request for.
	 * @param planItem
	 *            The plan item to create a shopping cart request for.
	 * @return A shopping cart request for the specific plan item and term.
	 */
	List<ShoppingCartRequest> createRequests(String learningPlanId, Term term, PossibleScheduleOption schedule);

	/**
	 * Process shopping cart requests.
	 * 
	 * @param requests
	 *            The shopping cart requests.
	 * @return Shopping cart requests, with results populated.
	 */
	List<ShoppingCartRequest> processRequests(List<ShoppingCartRequest> requests);

}

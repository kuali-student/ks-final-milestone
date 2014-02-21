package org.kuali.student.ap.schedulebuilder;

import org.kuali.student.ap.planner.PlanItemForm;
import org.kuali.student.ap.schedulebuilder.infc.CourseOption;

import java.util.List;

/**
 * Form for interacting with the shopping cart.
 * 
 * @author Mark Fyffe <mwfyffe@iu.edu>
 * @version 0.7.5
 */
public interface ShoppingCartForm extends PlanItemForm {

	/**
	 * Session attribute key used for passing schedule options between schedule
	 * build and shopping cart.
	 */
	static final String POSSIBLE_OPTIONS_KEY = ShoppingCartForm.class.getName()
			+ ".scheduleOptions";

	/**
	 * Get the URL for the main shopping cart application.
	 * 
	 * @return The URL for the main shopping cart application.
	 */
	String getShoppingCartUrl();
	
	/**
	 * Get the requested activity offering ID, if applicable.
	 * 
	 * @return The requested activity offering ID.
	 */
	String getActivityOfferingId();

	/**
	 * Get the possible schedule ID.
	 * 
	 * @return The possible schedule ID.
	 */
	String getPossibleScheduleId();

	/**
	 * Get the course options for driving the shopping cart request.
	 * 
	 * @return The course options for driving the shopping cart request.
	 */
	List<CourseOption> getCourseOptions();

	/**
	 * Set the course options for driving the shopping cart request.
	 * 
	 * @param courseOptions
	 *            The course options for driving the shopping cart request.
	 */
	void setCourseOptions(List<CourseOption> courseOptions);

	/**
	 * Get the shopping cart request.
	 * 
	 * @return The shopping cart request.
	 */
	List<ShoppingCartRequest> getShoppingCartRequests();

	/**
	 * Populate the shopping cart request.
	 * 
	 * @param shoppingCartRequest
	 *            The shopping cart request.
	 */
	void setShoppingCartRequests(List<ShoppingCartRequest> shoppingCartRequest);

}

package org.kuali.student.ap.schedulebuilder;

import org.kuali.student.ap.common.infc.HasUniqueId;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.lum.course.infc.Course;

import java.math.BigDecimal;
import java.util.List;

/**
 * Represents a request to add a course to the enrollment shopping cart for a
 * term.
 * 
 * @author Mark Fyffe <mwfyffe@iu.edu>
 * @version 1.1
 */
public interface ShoppingCartRequest extends HasUniqueId {

	/**
	 * Determine if the indicated course should be added or removed from the
	 * cart.
	 * 
	 * @return True if the course should be added, false if it should be
	 *         removed.
	 */
	boolean isAddToCart();

	/**
	 * Get the course.
	 * 
	 * @return The course.
	 */
	Course getCourse();

	/**
	 * Get the requested term.
	 * 
	 * @return The requested term.
	 */
	Term getTerm();

	/**
	 * Get the number of credits, for a variable credit class.
	 * 
	 * @return The number of credits to enroll with.
	 */
	BigDecimal getCredits();

	/**
	 * Get the primary registration code.
	 * 
	 * @return The primary registration code.
	 */
	String getPrimaryRegistrationCode();

	/**
	 * Get the secondary registration code.
	 * 
	 * @return The secondary registration code.
	 */
	List<String> getSecondaryRegistrationCodes();

	/**
	 * Determine if this request was processed successfully.
	 * 
	 * @return True if the request was process successfully, false if either the
	 *         request has not been processed or if processing was not
	 *         successful.
	 */
	boolean isProcessed();

	/**
	 * Determine if this request resulted in an error.
	 * 
	 * <p>
	 * This flag should only be set if processing was unable to complete due to
	 * an unexpected error. If processing completed normally, but
	 * 
	 * @return True if the request resulted in an error, false if either the
	 *         request has not been processed or if processing completed
	 *         normally.
	 */
	boolean isError();

	/**
	 * Get a message detailing the results of processing this request.
	 * 
	 * @return A message detailing the results of processing this request. Note
	 *         that a message may be sent on success or failure.
	 */
	RichText getMessage();

}

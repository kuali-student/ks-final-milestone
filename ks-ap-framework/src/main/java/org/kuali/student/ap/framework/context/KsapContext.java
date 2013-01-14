package org.kuali.student.ap.framework.context;

import org.kuali.student.r2.common.dto.ContextInfo;

/**
 * Simple interface for acquiring context information from the active
 * transaction.
 * 
 * @author Mark Fyffe <mwfyffe@indiana.edu>
 * @version ks-ap 0.1.1
 */
public interface KsapContext {

	/**
	 * Get the context info for the active transaction.
	 * 
	 * <p>
	 * NOTE: It is expected that user principal name and principal ID will be
	 * populated with the current user's information on the context returned by
	 * this method.
	 * </p>
	 * 
	 * @return The KS context info for the active transaction.
	 */
	ContextInfo getContextInfo();

}

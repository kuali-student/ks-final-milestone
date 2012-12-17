package org.kuali.student.ap.framework.context.support;

import org.kuali.student.ap.framework.context.KsapContext;
import org.kuali.student.r2.common.dto.ContextInfo;

/**
 * Provides minimal context support for interaction with KS services.
 * 
 * @author Mark Fyffe <mwfyffe@indiana.edu>
 * @version ksap-0.1.1
 */
public class DefaultKsapContext implements KsapContext {

	/**
	 * Singleton context.
	 */
	private static final ContextInfo CTX = new ContextInfo();

	@Override
	public ContextInfo getContextInfo() {
		return CTX;
	}

}
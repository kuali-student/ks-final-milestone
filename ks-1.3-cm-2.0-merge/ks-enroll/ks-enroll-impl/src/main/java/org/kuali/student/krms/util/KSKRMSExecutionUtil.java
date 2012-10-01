package org.kuali.student.krms.util;

import java.util.Map;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;

public class KSKRMSExecutionUtil {

	public static void convertExceptionsToTermResolutionException(
			Map<String, String> parameters, Exception e, TermResolver termResolver) {
		throw new TermResolutionException(e.getMessage(), termResolver, parameters);
	}
}

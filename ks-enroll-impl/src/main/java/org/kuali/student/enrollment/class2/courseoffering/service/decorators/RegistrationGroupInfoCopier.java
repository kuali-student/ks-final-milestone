/**
 * 
 */
package org.kuali.student.enrollment.class2.courseoffering.service.decorators;

import org.kuali.student.common.cache.KSCacheUtils.CacheElementCopier;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;

/**
 * @author Kuali Student Team
 * 
 *  Helper for deep copying {@link RegistrationGroupInfo}'s within the CourseOfferingServiceCacheDecorator.
 *
 */
public class RegistrationGroupInfoCopier implements CacheElementCopier<RegistrationGroupInfo> {

	/**
	 * 
	 */
	public RegistrationGroupInfoCopier() {
	}

	@Override
	public RegistrationGroupInfo deepCopy(RegistrationGroupInfo source) {
		return new RegistrationGroupInfo(source);
	}

	
}

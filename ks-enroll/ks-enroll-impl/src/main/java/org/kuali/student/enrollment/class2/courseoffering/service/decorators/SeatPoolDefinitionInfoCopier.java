/**
 * 
 */
package org.kuali.student.enrollment.class2.courseoffering.service.decorators;

import org.kuali.student.common.cache.KSCacheUtils.CacheElementCopier;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;

/**
 * @author Kuali Student Team
 * 
 * Deep copy helper for {@link SeatPoolDefinitionInfo}'s used within the CourseOfferingServiceCacheDecorator
 *
 */
public class SeatPoolDefinitionInfoCopier implements CacheElementCopier<SeatPoolDefinitionInfo> {

	/**
	 * 
	 */
	public SeatPoolDefinitionInfoCopier() {
	}

	@Override
	public SeatPoolDefinitionInfo deepCopy(SeatPoolDefinitionInfo source) {
		return new SeatPoolDefinitionInfo(source);
	}

	
	
}

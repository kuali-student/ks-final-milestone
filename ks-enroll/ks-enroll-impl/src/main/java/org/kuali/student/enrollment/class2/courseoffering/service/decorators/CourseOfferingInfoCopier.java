/**
 * 
 */
package org.kuali.student.enrollment.class2.courseoffering.service.decorators;

import org.kuali.student.common.cache.KSCacheUtils.CacheElementCopier;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;

/**
 * @author Kuali Student Team
 * 
 * Deep object copier for CourseOffering's for use by the cache decorator.
 *
 */
public class CourseOfferingInfoCopier implements CacheElementCopier<CourseOfferingInfo> {

	/**
	 * 
	 */
	public CourseOfferingInfoCopier() {
	}

	@Override
	public CourseOfferingInfo deepCopy(CourseOfferingInfo source) {
		return new CourseOfferingInfo (source);
	}
	
	

}

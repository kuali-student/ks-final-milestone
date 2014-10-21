/**
 * 
 */
package org.kuali.student.r2.lum.course.service.impl;

import org.kuali.student.common.cache.KSCacheUtils.CacheElementCopier;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

/**
 * @author Kuali Student Team
 * 
 * Create a deep copy of an {@link CourseInfo} object.
 * 
 */
public class CourseInfoCopier implements CacheElementCopier<CourseInfo> {

	@Override
	public CourseInfo deepCopy(CourseInfo source) {
		return new CourseInfo(source);
	}

	
}

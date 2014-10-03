/**
 * 
 */
package org.kuali.student.enrollment.class2.courseoffering.service.decorators;

import org.kuali.student.common.cache.KSCacheUtils.CacheElementCopier;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;

/**
 * @author Kuali Student Team
 * 
 * Deep copy helper for the {@link CourseOfferingServiceCacheDecorator}.
 *
 */
public class ActivityOfferingClusterInfoCopier implements CacheElementCopier<ActivityOfferingClusterInfo> {

	/**
	 * 
	 */
	public ActivityOfferingClusterInfoCopier() {
	}

	@Override
	public ActivityOfferingClusterInfo deepCopy(
			ActivityOfferingClusterInfo source) {
		return new ActivityOfferingClusterInfo(source);
	}

	
}

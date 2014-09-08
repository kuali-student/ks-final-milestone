/**
 * 
 */
package org.kuali.student.enrollment.class2.courseoffering.service.decorators;

import org.kuali.student.common.cache.KSCacheUtils.CacheElementCopier;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingDisplayInfo;

/**
 * @author Kuali Student Team
 * 
 * Deep Copy Helper for {@link ActivityOfferingDisplayInfo}'s within the {@link CourseOfferingServiceCacheDecorator}.
 *
 */
public class ActivityOfferingDisplayInfoCopier implements CacheElementCopier<ActivityOfferingDisplayInfo> {

	/**
	 * 
	 */
	public ActivityOfferingDisplayInfoCopier() {
	}

	@Override
	public ActivityOfferingDisplayInfo deepCopy(
			ActivityOfferingDisplayInfo source) {
		return new ActivityOfferingDisplayInfo(source);
	}

	
}

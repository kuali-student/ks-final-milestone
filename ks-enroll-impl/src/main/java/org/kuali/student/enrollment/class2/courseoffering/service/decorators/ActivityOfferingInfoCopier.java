/**
 * 
 */
package org.kuali.student.enrollment.class2.courseoffering.service.decorators;

import org.kuali.student.common.cache.KSCacheUtils.CacheElementCopier;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;

/**
 * @author ocleirig
 *
 */
public class ActivityOfferingInfoCopier implements CacheElementCopier<ActivityOfferingInfo> {

	/**
	 * 
	 */
	public ActivityOfferingInfoCopier() {
	}

	@Override
	public ActivityOfferingInfo deepCopy(ActivityOfferingInfo source) {
		return new ActivityOfferingInfo(source);
	}
	
	

}

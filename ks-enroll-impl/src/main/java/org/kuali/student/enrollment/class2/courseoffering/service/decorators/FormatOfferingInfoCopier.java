/**
 * 
 */
package org.kuali.student.enrollment.class2.courseoffering.service.decorators;

import org.kuali.student.common.cache.KSCacheUtils.CacheElementCopier;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;

/**
 * @author Kuali Student Team
 *
 */
public class FormatOfferingInfoCopier implements CacheElementCopier<FormatOfferingInfo> {

	@Override
	public FormatOfferingInfo deepCopy(FormatOfferingInfo source) {
		return new FormatOfferingInfo(source);
	}

 	
}

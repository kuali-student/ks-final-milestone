/**
 * 
 */
package org.kuali.student.enrollment.class1.lui.service.decorators;

import org.kuali.student.common.cache.KSCacheUtils.CacheElementCopier;
import org.kuali.student.enrollment.lui.dto.LuiInfo;

/**
 * @author Kuali Student Team
 * 
 * Deep Copy Helper for the {@link LuiServiceCacheDecorator}
 *
 */
public class LuiInfoCopier implements CacheElementCopier<LuiInfo> {

	/**
	 * 
	 */
	public LuiInfoCopier() {
	}

	@Override
	public LuiInfo deepCopy(LuiInfo source) {
		return new LuiInfo(source);
	}

	 
}

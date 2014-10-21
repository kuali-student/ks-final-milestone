/**
 * 
 */
package org.kuali.student.enrollment.class1.lui.service.decorators;

import org.kuali.student.common.cache.KSCacheUtils.CacheElementCopier;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;

/**
 * @author Kuali Student Team
 * 
 * Deep Copy Helper for the {@link LuiServiceCacheDecorator}
 *
 */
public class LuiLuiRelationInfoCopier implements CacheElementCopier<LuiLuiRelationInfo> {

	/**
	 * 
	 */
	public LuiLuiRelationInfoCopier() {
	}

	@Override
	public LuiLuiRelationInfo deepCopy(LuiLuiRelationInfo source) {
		return new LuiLuiRelationInfo(source);
	}

	 
}

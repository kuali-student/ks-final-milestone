/**
 * 
 */
package org.kuali.student.r2.core.class1.type.service;

import org.kuali.student.common.cache.KSCacheUtils.CacheElementCopier;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;

/**
 * @author Kuali Student Team.
 * 
 * Deep copy helper for {@link TypeInfo} for use in Service Cache Decorator's.
 *
 */
public class TypeInfoCopier implements CacheElementCopier<TypeInfo> {

	/**
	 * 
	 */
	public TypeInfoCopier() {
	}

	@Override
	public TypeInfo deepCopy(TypeInfo source) {
		return new TypeInfo(source);
	}

	
}

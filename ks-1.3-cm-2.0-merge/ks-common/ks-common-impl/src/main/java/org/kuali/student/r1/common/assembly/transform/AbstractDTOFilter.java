package org.kuali.student.r1.common.assembly.transform;

import java.util.Map;


@Deprecated
public abstract class AbstractDTOFilter implements TransformFilter {
	/**
	 * This can be used to apply filters in the inbound dto object. 
	 * 
	 * @param data The inbound dto object
	 *  @param metadata The metadata for the inbound dto object.
	 */
	public void applyInboundDtoFilter(Object dto, Map<String, Object> properties) throws Exception{
		
	}

	/**
	 * 
	 * @param data The outbound data object
	 * @param metadata The metadata for the outbound dto object.
	 */
	public void applyOutboundDtoFilter(Object dto, Map<String, Object> properties) throws Exception{
		
	}

}

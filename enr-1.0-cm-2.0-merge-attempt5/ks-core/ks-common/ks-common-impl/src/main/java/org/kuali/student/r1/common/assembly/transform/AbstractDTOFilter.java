package org.kuali.student.r1.common.assembly.transform;

import java.util.Map;

public abstract class AbstractDTOFilter implements TransformFilter {
	/**
	 * This can be used to apply filters in the inbound dto object. 
	 * 
	 * @param dto The inbound dto object
	 *  @param properties The properties for the inbound dto object.
	 */
	public void applyInboundDtoFilter(Object dto, Map<String, Object> properties) throws Exception{
		
	}

	/**
	 * 
	 * @param dto The outbound data object
	 * @param properties The properties for the outbound dto object.
	 */
	public void applyOutboundDtoFilter(Object dto, Map<String, Object> properties) throws Exception{
		
	}

}

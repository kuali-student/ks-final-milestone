package org.kuali.student.r1.common.assembly.transform;

import java.util.Map;

import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Metadata;

@Deprecated
public abstract class AbstractDataFilter implements TransformFilter {

	/**
	 * This can be used to apply filters in the inbound data object. 
	 * 
	 * @param data The inbound data object
	 * @param metadata The metadata for the inbound data object. The metadata will be the unfiltered metadata.
	 */
	public void applyInboundDataFilter(Data data, Metadata metadata, Map<String, Object> properties) throws Exception{
		
	}

	/**
	 * 
	 * @param data The outbound data object
	 * @param metadata The metadata for the outbound data object. The metadata will be the unfiltered meatadata
	 */
	public void applyOutboundDataFilter(Data data, Metadata metadata, Map<String, Object> properties) throws Exception{
		
	}

	@Override
	public Class<?> getType() {
		return Data.class;
	}
}

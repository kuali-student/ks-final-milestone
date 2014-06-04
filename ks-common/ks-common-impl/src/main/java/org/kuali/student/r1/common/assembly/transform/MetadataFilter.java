package org.kuali.student.r1.common.assembly.transform;

import java.util.Map;

import org.kuali.student.r1.common.assembly.data.Metadata;

/**
 * Implementations of the MetadataFilter can apply changes to the metadata structure 
 * before it is sent to the UI client for consumption.
 * 
 * @author Will
 *
 */
public interface MetadataFilter {
    public static final String METADATA_ID_TYPE		= "MetadataIdType";
    public static final String METADATA_ID_VALUE	= "MetadataIdValue";
	
	/**
	 * NOTE: The metadata filter is only applied in order as defined in the outbound filter path chain.
	 * 
	 * @param dtoName
	 * @param metadata
	 * @param filterProperties
	 */
    public void applyMetadataFilter(String dtoName, Metadata metadata, Map<String, Object> filterProperties);
}

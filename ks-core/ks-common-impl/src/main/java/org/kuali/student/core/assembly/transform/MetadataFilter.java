package org.kuali.student.core.assembly.transform;

import java.util.Map;

import org.kuali.student.core.assembly.data.Metadata;

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
	
	public void applyMetadataFilter(String dtoName, Metadata metadata, Map<String, Object> filterProperties);
}

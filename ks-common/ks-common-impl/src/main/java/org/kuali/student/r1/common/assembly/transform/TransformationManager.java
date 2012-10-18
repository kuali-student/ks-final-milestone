package org.kuali.student.r1.common.assembly.transform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.dictionary.MetadataServiceImpl;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r1.common.rice.StudentWorkflowConstants;

public class TransformationManager {
	final Logger LOG = Logger.getLogger(TransformationManager.class);

	private MetadataServiceImpl metadataService;
	private DataBeanMapper mapper = new DefaultDataBeanMapper();
	private List<TransformFilter> inboundFilterList = new ArrayList<TransformFilter>();
	private List<TransformFilter> outboundFilterList = new ArrayList<TransformFilter>();	

	/**
	 * This is an outbound transform request which will convert incoming DTO objects
	 * to a Data map and apply outbound filter operation to both the data object
	 * and the converted DTO object.
	 *
	 * @param value
	 * @param objectType TODO
	 * @return
	 * @throws Exception
	 */
	public Data transform(Object value, String objectType) throws Exception{
		Metadata metadata = null;
		metadata = (metadata != null ? metadata:getMetadata(objectType, new HashMap<String,Object>()));
		
		applyOutboundFilters(value.getClass().getName(), value, new HashMap<String,Object>());
		Data dataValue = mapper.convertFromBean(value, metadata);
		applyOutboundFilters(value.getClass().getName(), dataValue, new HashMap<String,Object>());

		return dataValue;
	}

	public Data transform(Object value, String objectType, Map<String,Object> filterProperties) throws Exception{
		Metadata metadata = null;
		metadata = (metadata != null ? metadata:getMetadata(objectType, new HashMap<String,Object>()));

		applyOutboundFilters(value.getClass().getName(), value, filterProperties);
		Data dataValue = mapper.convertFromBean(value, metadata);
		applyOutboundFilters(value.getClass().getName(), dataValue, filterProperties);

		return dataValue;
	}


	/**
	 * This is an inbound transform request which will convert incoming Data objects
	 * to the corresponding DTO object and apply inbound filter operations to both the data object
	 * and the converted DTO object.
	 *
	 * @param value The incoming Data value to transform to DTO object
	 * @param clazz The DTO object class represented by the Data value
	 * @return The converted DTO with both inbound Data and DTO filters applied.
	 */
	public Object transform(Data value, Class<?> clazz) throws Exception{
		Metadata metadata = null;
		metadata = (metadata != null ? metadata:getMetadata(clazz.getName(), new HashMap<String,Object>()));
		applyInboundFilters(clazz.getName(), value, new HashMap<String,Object>(),metadata);
		Object dtoValue = mapper.convertFromData(value, clazz,metadata);
		applyInboundFilters(clazz.getName(), dtoValue, new HashMap<String,Object>(),metadata);

		return dtoValue;
	}

	/**
	 * This is an inbound transform request which will convert incoming Data objects
	 * to the corresponding DTO object and apply inbound filters to both the data object
	 * and the converted DTO object.
	 *
	 * @param value The incoming Data value to transform to DTO object
	 * @param clazz The DTO object class represented by the Data value
	 * @param filterProperties properties that can be consumed by the filters
	 * @return The converted DTO with both inbound Data and DTO filters applied.
	 */
	public Object transform(Data value, Class<?> clazz, Map<String,Object> filterProperties) throws Exception{
		Metadata metadata = getMetadata(clazz.getName(), filterProperties);
		applyInboundFilters(clazz.getName(), value, filterProperties,metadata);
		Object dtoValue = mapper.convertFromData(value, clazz,metadata);
		applyInboundFilters(clazz.getName(), dtoValue, filterProperties,metadata);
		return dtoValue;
	}

	/**
	 * This method applies all inbound filters known to his transformation manager.
	 *
	 * @param dtoName The name of the dto represented by a Data value, for Dto values,
	 *  this can simply be the name of the dto class
	 * @param value The dto or data object to apply filters to
	 * @throws Exception
	 */
	public void applyInboundFilters(String dtoName, Object value, Map<String,Object> properties, Metadata metadata) throws Exception{
		for (TransformFilter filter:inboundFilterList){
			if (filter.getType().isInstance(value)){
				if (filter instanceof AbstractDataFilter) {
					((AbstractDataFilter)filter).applyInboundDataFilter((Data)value, metadata, properties);
				} else {
					((AbstractDTOFilter)filter).applyInboundDtoFilter(value, properties);
				}
				LOG.info(filter.getClass().getName() + ": Filter Applied");
			}
		}
	}

	/**
	 * This method applies all outbound filters known to his transformation manager.
	 *
	 * @param dtoName The name of the dto represented by a Data value, for Dto values,
	 *  this can simply be the name of the dto class
	 * @param value The dto or data object to apply filters to
	 * @throws Exception
	 */
	public void applyOutboundFilters(String dtoName, Object value, Map<String,Object> properties) throws Exception{
		for (TransformFilter filter:outboundFilterList){
			if (filter.getType().isInstance(value)){
				if (filter instanceof AbstractDataFilter) {
					//FIXME: It might be more efficient to getMetadata outside of the for loop (unless metadata might be different)
					Metadata metadata = getMetadata(dtoName, properties);
					((AbstractDataFilter)filter).applyOutboundDataFilter((Data)value, metadata, properties);
				} else {
					((AbstractDTOFilter)filter).applyOutboundDtoFilter(value, properties);
				}
				LOG.info(filter.getClass().getName() + ": Filter Applied");
			}
		}
	}

	public Metadata getMetadata(String dtoName, Map<String,Object> filterProperties){
		String state = (String)filterProperties.get(DtoConstants.DTO_STATE);
		String nextState = (String)filterProperties.get(DtoConstants.DTO_NEXT_STATE);
		String workflowNode = (String)filterProperties.get(DtoConstants.DTO_WORKFLOW_NODE);
		//The docTypeName is actually set with ProposalWorkflowFilter.WORKFLOW_DOC_TYPE, however it is not visible in this project.
		String documentTypeName = (String)filterProperties.get(StudentWorkflowConstants.WORKFLOW_DOCUMENT_TYPE);

		Metadata metadata;
		if (workflowNode == null || workflowNode.isEmpty()){
			metadata = metadataService.getMetadata(dtoName, null, state, nextState, documentTypeName);
		} else {
			metadata = metadataService.getMetadataByWorkflowNode(dtoName, workflowNode, documentTypeName);
		}		 

		applyMetadataFilters(dtoName, metadata, filterProperties);
		return metadata;
	}

	public void applyMetadataFilters(String dtoName, Metadata metadata, Map<String, Object> filterProperties){
		for (TransformFilter filter:outboundFilterList){
			if (filter instanceof MetadataFilter){
				((MetadataFilter) filter).applyMetadataFilter(dtoName, metadata, filterProperties);
			}
		}
	}

	public Metadata getUnfilteredMetadata(String dtoName){
		Metadata metadata = metadataService.getMetadata(dtoName);
		return metadata;
	}

	public void setMetadataService(MetadataServiceImpl metadataService) {
		this.metadataService = metadataService;
	}
	
	
	public DataBeanMapper getMapper() {
		return mapper;
	}

	public void setMapper(DataBeanMapper mapper) {
		this.mapper = mapper;
	}

	/**
	 * Use setInboundFilters and setOutboundFilters instead. This sets both
	 * the inbound and outbound filter chain to be the same.
	 *  
	 * @param filters
	 */
	@Deprecated
	public void setFilters(List<TransformFilter> filters){
		inboundFilterList.addAll(filters);
		outboundFilterList.addAll(filters);
	}	

	public void setInboundFilters(List<TransformFilter> filters){
		inboundFilterList.addAll(filters);
	}	
		
	public void setOutboundFilters(List<TransformFilter> filters){
		outboundFilterList.addAll(filters);
	}	

}
